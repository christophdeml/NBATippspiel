document.addEventListener("DOMContentLoaded", () => {
    let overlay = document.querySelector('#overlay');
    if(overlay !== null) {
        overlay.style.opacity = '0';
        overlay.style.zIndex = '-1';
    }
});