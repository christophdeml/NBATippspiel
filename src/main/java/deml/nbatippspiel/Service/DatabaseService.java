package deml.nbatippspiel.Service;

import deml.nbatippspiel.Model.Game;
import deml.nbatippspiel.Model.Matchup;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static deml.nbatippspiel.NbatippspielApplication.LOGGER;

@Service
@EnableAsync
public class DatabaseService {
    private final GameService gameService;
    private final TeamService teamService;
    private final MatchupService matchupService;

    public DatabaseService(
            final GameService gameService,
            final TeamService teamService,
            final MatchupService matchupService) {
        this.gameService = gameService;
        this.teamService = teamService;
        this.matchupService = matchupService;
    }

    @Async
    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void insertGames() throws IOException {
        LOGGER.info("Start updating games");
        int gameCount = 0;
        final URL url = new URL("https://cdn.nba.com/static/json/staticData/scheduleLeagueV2_14.json");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int status = connection.getResponseCode();
        if (status == HttpStatus.OK.value()) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            connection.disconnect();

            JSONObject jsonObject = new JSONObject(content.toString());

            JSONArray gameDates = jsonObject.getJSONObject("leagueSchedule").getJSONArray("gameDates");
            for (int iGames = 0; iGames < gameDates.length(); iGames++) {
                JSONArray games = gameDates.getJSONObject(iGames).getJSONArray("games");
                for (int iGame = 0; iGame < games.length(); iGame++) {
                    JSONObject jsonGame = games.getJSONObject(iGame);
                    final JSONObject homeTeam = jsonGame.getJSONObject("homeTeam");
                    final JSONObject awayTeam = jsonGame.getJSONObject("awayTeam");
                    final String homeTeamTricode = homeTeam.getString("teamTricode");
                    final String awayTeamTricode = awayTeam.getString("teamTricode");
                    final Integer homeTeamPoints = homeTeam.getInt("score");
                    final Integer awayTeamPoints = awayTeam.getInt("score");
                    final String homeTeamRecord = homeTeam.getBigInteger("wins") + ":" + homeTeam.getBigInteger("losses");
                    final String awayTeamRecord = awayTeam.getBigInteger("wins") + ":" + awayTeam.getBigInteger("losses");
                    final String date = jsonGame.getString("gameDateUTC").substring(0, 10);
                    final String time = jsonGame.getString("gameTimeUTC").substring(0, 19);
                    final Long gameId = jsonGame.getLong("gameId");

                    final Game game = gameService.getById(gameId);
                    Game newGame = Game.fromGame(game);

                    if (newGame != null) {
                        newGame.setMatchupKey(homeTeamTricode + awayTeamTricode);
                        newGame.setHomeTeamPoints(homeTeamPoints);
                        newGame.setHomeTeamRecord(homeTeamRecord);
                        newGame.setAwayTeamPoints(awayTeamPoints);
                        newGame.setAwayTeamRecord(awayTeamRecord);
                    } else {
                        newGame = new Game(
                                gameId,
                                homeTeamTricode + awayTeamTricode,
                                teamService.getIdFromTricode(awayTeamTricode),
                                awayTeamPoints,
                                awayTeamRecord,
                                teamService.getIdFromTricode(homeTeamTricode),
                                homeTeamPoints,
                                homeTeamRecord,
                                LocalDate.parse(date),
                                LocalDateTime.parse(time)
                        );
                    }

                    if (newGame.isValid() && !newGame.isEqual(game)) {
                        gameService.save(newGame);
                        gameCount++;
                    }
                }
            }
        }

        if(gameCount > 0) LOGGER.info("Finished inserting or updating " + gameCount + " games");
        else LOGGER.info("All games are up-to-date");
    }

    @Async
    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void removeUnnecessaryGames() {
        LOGGER.info("Start removing unnecessary games");
        int gamesCount = 0;
        List<Matchup> closedMatchups = matchupService.getAllClosedMatchups();
        List<Game> games = gameService.findAll();
        for (Matchup closedMatchup : closedMatchups) {
            for (Game game : games) {
                if (matchupKeysAreOk(closedMatchup.getMatchupKey(), game.getMatchupKey()) && game.getHomeTeamPoints() == 0 && game.getAwayTeamPoints() == 0) {
                    gameService.delete(game);
                    gamesCount++;
                }
            }
        }
        if(gamesCount != 0) {
            LOGGER.info("Finished removing " + gamesCount + " games");
        }
        else {
            LOGGER.info("No games needed to be removed");
        }
    }

    @Async
    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void insertMatchups() throws IOException {
        LOGGER.info("Start updating matchups");
        int matchupCount = 0;
        final URL url = new URL("https://stats.nba.com/stats/playoffbracket?LeagueID=00&SeasonYear=2020&State=2");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int status = connection.getResponseCode();
        if (status == HttpStatus.OK.value()) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            connection.disconnect();

            JSONObject jsonObject = new JSONObject(content.toString());
            JSONArray matchupJsons = jsonObject.getJSONObject("bracket").getJSONArray("playoffBracketSeries");
            for (int iMatchup = 0; iMatchup < matchupJsons.length(); iMatchup++) {
                JSONObject matchupJson = matchupJsons.getJSONObject(iMatchup);
                int roundNumber = matchupJson.getInt("roundNumber");
                int seriesNumber = matchupJson.getInt("seriesNumber");
                String highSeedTricode = matchupJson.optString("highSeedTricode");
                String lowSeedTricode = matchupJson.optString("lowSeedTricode");
                String matchupDescription = matchupJson.optString("seriesConference") + " " + matchupJson.getInt("roundNumber");
                int highSeedSeriesWins = matchupJson.optInt("highSeedSeriesWins");
                int lowSeedSeriesWins = matchupJson.optInt("lowSeedSeriesWins");
                boolean hasStarted = (highSeedSeriesWins + lowSeedSeriesWins) > 0;
                boolean isClosed = highSeedSeriesWins == 4 || lowSeedSeriesWins == 4;

                if (!teamService.isTricodeValid(highSeedTricode) || !teamService.isTricodeValid(lowSeedTricode) || matchupDescription.contains("Play-In"))
                    continue;

                final Matchup matchup = matchupService.getByMatchupKey(highSeedTricode + lowSeedTricode);
                Matchup newMatchup = Matchup.fromMatchup(matchup);

                if (newMatchup != null) {
                    newMatchup.setClosed(isClosed);
                    newMatchup.setStarted(hasStarted);
                    newMatchup.setTeam1Wins(highSeedSeriesWins);
                    newMatchup.setTeam2Wins(lowSeedSeriesWins);
                } else {
                    newMatchup = new Matchup
                            (
                                    10*roundNumber + seriesNumber,
                                    highSeedTricode + lowSeedTricode,
                                    matchupDescription,
                                    teamService.getIdFromTricode(highSeedTricode),
                                    teamService.getIdFromTricode(lowSeedTricode),
                                    highSeedSeriesWins,
                                    lowSeedSeriesWins,
                                    isClosed,
                                    hasStarted
                            );
                }
                if (newMatchup.isValid() && !newMatchup.isEqual(matchup) && !newMatchup.getMatchupDescription().contains("Play-In")) {
                    matchupService.save(newMatchup);
                    matchupCount++;
                }
            }
        }
        if(matchupCount > 0) LOGGER.info("Finished inserting or updating " + matchupCount + " matchups");
        else LOGGER.info("All matchups are up-to-date");
    }

    private boolean matchupKeysAreOk(final String matchupKey, final String gameMatchupKey) {
        if(matchupKey == null || gameMatchupKey == null) return false;
        return matchupKey.equals(gameMatchupKey) || matchupKey.equals(gameMatchupKey.substring(3, 6) + gameMatchupKey.substring(0, 3));
    }
}
