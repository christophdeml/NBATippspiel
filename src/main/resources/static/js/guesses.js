document.addEventListener("DOMContentLoaded", () => {
    let formMatchups = document.querySelector("#formMatchups");

    if(formMatchups !== null) {
        formMatchups.addEventListener("submit", (event) => {
            let list = [];
            document.querySelectorAll("[name*='matchupRows']").forEach(m => {
                if (m.checked) list.push(m);
            });

            let retVal = true;

            for (let i = 0; i < list.length; i += 2) {
                if (list[i].value !== "4" && list[i + 1].value !== "4") {
                    event.preventDefault();
                    list[i].parentElement.parentElement.parentElement.style.border = "1px solid red";
                    retVal = false;
                } else {
                    list[i].parentElement.parentElement.parentElement.style.border = "0px solid red";
                }
            }
            return retVal;
        });
    }

    let selectConference = document.querySelector("#select_conference");
    let selectRound = document.querySelector('#select_round');
    if(selectConference !== null && selectRound !== null) {
        selectConference.addEventListener('input', showSelected);
        selectRound.addEventListener("input", showSelected);
    }

    let showOtherGuessesButtons = document.querySelectorAll(".btn-show-other-guesses");
    showOtherGuessesButtons.forEach(b => {
        b.addEventListener('click', () => {
            let matchupKey = b.id.slice(9);
            let otherGuesses = document.querySelector("#otherGuesses__" + matchupKey);
            if(otherGuesses.classList.contains("visually-hidden")) {
                otherGuesses.classList.remove("visually-hidden");
                b.querySelector('span img').style.transform = "rotate(180deg)";
            } else {
                otherGuesses.classList.add("visually-hidden");
                b.querySelector('span img').style.transform = "rotate(0deg)";
            }
        });
    });
});

function showSelected() {
    let selectConferenceValue = document.querySelector("#select_conference").value;
    let selectRoundValue = document.querySelector("#select_round").value;
    let guessedMatchups = document.querySelectorAll(".guessed-matchup");
    guessedMatchups.forEach(m => {
        let isConferenceOk = false;
        let isRoundOk = false;
        if(selectConferenceValue === "Beide" || m.classList.contains(selectConferenceValue)) isConferenceOk = true;
        if(selectRoundValue === "Alle" || m.classList.contains(selectRoundValue)) isRoundOk = true;
        if(isRoundOk && isConferenceOk) m.classList.remove("visually-hidden");
        else m.classList.add("visually-hidden");
    });
}