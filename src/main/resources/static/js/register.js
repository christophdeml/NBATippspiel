document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("formRegister").addEventListener("submit", (e) => {

        let username = document.getElementById("username").value;
        let password = document.getElementById("password").value;
        let passwordAgain = document.getElementById("password_again").value;

        if(username.includes(' ') || username.includes('\t')) {
            document.getElementById("username").classList.add("border-danger");
            document.getElementById("errorbox").querySelector('span').innerText = "Der Benutzername darf keine Leerzeichen enthalten";
            document.getElementById("errorbox").classList.remove("visually-hidden");
            e.preventDefault();
            return false;
        }

        if(password.includes(' ') || password.includes('\t')) {
            document.getElementById("password").classList.add("border-danger");
            document.getElementById("errorbox").querySelector('span').innerText = "Das Passwort darf keine Leerzeichen enthalten";
            document.getElementById("errorbox").classList.remove("visually-hidden");
            e.preventDefault();
            return false;
        }

        if(password.length < 8) {
            document.getElementById("password").classList.add("border-danger");
            document.getElementById("errorbox").querySelector('span').innerText = "Das Passwort muss mindestens aus 8 Zeichen bestehen";
            document.getElementById("errorbox").classList.remove("visually-hidden");
            e.preventDefault();
            return false;
        }

        if(password !== passwordAgain) {
            document.getElementById("password").classList.add("border-danger");
            document.getElementById("password_again").classList.add("border-danger");
            document.getElementById("errorbox").querySelector('span').innerText = "Die Passwörter stimmen nicht überein";
            document.getElementById("errorbox").classList.remove("visually-hidden");
            e.preventDefault();
            return false;
        }
        return true;
   });
});