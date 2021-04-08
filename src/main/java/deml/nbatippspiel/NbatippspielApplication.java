package deml.nbatippspiel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication
@EnableScheduling
public class NbatippspielApplication {

    public static final Logger LOGGER = LoggerFactory.getLogger(NbatippspielApplication.class);

    public static void main(String[] args) throws IOException, SQLException {
        SpringApplication.run(NbatippspielApplication.class, args);
        LOGGER.info("" +
                "\n\n\n\n" +
                "  _   _ ____            _______ _                       _      _ \n" +
                " | \\ | |  _ \\   /\\     |__   __(_)                     (_)    | |\n" +
                " |  \\| | |_) | /  \\       | |   _ _ __  _ __  ___ _ __  _  ___| |\n" +
                " | . ` |  _ < / /\\ \\      | |  | | '_ \\| '_ \\/ __| '_ \\| |/ _ \\ |\n" +
                " | |\\  | |_) / ____ \\     | |  | | |_) | |_) \\__ \\ |_) | |  __/ |\n" +
                " |_| \\_|____/_/    \\_\\    |_|  |_| .__/| .__/|___/ .__/|_|\\___|_|\n" +
                "                                 | |   | |       | |             \n" +
                "                                 |_|   |_|       |_|             \n" +
                "\n");
    }
}
