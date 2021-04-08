document.addEventListener('DOMContentLoaded', () => {
    let btnClosed = document.querySelector('#btnClosed');
    let btnStarted = document.querySelector('#btnStarted');

    if(btnClosed !== null && btnStarted !== null) {
        btnClosed.addEventListener('click', () => {
            btnClosed.classList.remove('background-color-grey');
            btnClosed.classList.add('background-color-nbablue');
            btnStarted.classList.remove('background-color-nbablue');
            btnStarted.classList.add('background-color-grey');
            document.querySelectorAll('[is="closed"]').forEach(c => {
                c.classList.remove('visually-hidden');
            });
            document.querySelectorAll('[is="started"]').forEach(s => {
                s.classList.add('visually-hidden');
            });
        });
        btnStarted.addEventListener('click', () => {
            btnStarted.classList.remove('background-color-grey');
            btnStarted.classList.add('background-color-nbablue');
            btnClosed.classList.remove('background-color-nbablue');
            btnClosed.classList.add('background-color-grey');
            document.querySelectorAll('[is="closed"]').forEach(c => {
                c.classList.add('visually-hidden');
            });
            document.querySelectorAll('[is="started"]').forEach(s => {
                s.classList.remove('visually-hidden');
            });
        });
    }
});