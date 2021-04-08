document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll('[is="matchup"]').forEach(m => {
        m.querySelectorAll('.radioguess').forEach(r => {
            r.querySelectorAll('input').forEach(i => {
                i.addEventListener("click", () => {
                    m.querySelectorAll('input[value="4"]').forEach(input => {
                        if(input !== i && i.value === "4") {
                            document.querySelector('[for="' + input.id + '"]').classList.add("visually-hidden");
                            let inputNowOnTop = input.parentElement.querySelector('[name="' + input.name + '"][value="3"]');
                            document.querySelector("[for='" + inputNowOnTop.id + "']").classList.add("radioguess-radio-element--top");
                        } else if(input.parentElement !== i.parentElement) {
                            document.querySelector('[for="' + input.id + '"]').classList.remove("visually-hidden");
                            let inputNowInMiddle = input.parentElement.querySelector('[name="' + input.name + '"][value="3"]');
                            document.querySelector("[for='" + inputNowInMiddle.id + "']").classList.remove("radioguess-radio-element--top");
                        }
                    });
                });
            });
        });
    });
    document.querySelectorAll('[is="table"]').forEach(t => {
        t.querySelectorAll('[data-index] td').forEach(td => td.classList.remove('border-bottom-0'));
        t.querySelectorAll('[data-indexed]').forEach(td => td.classList.add('visually-hidden'));

       t.querySelectorAll('[data-index]').forEach(di => {
           di.addEventListener('click', () => {
               di.querySelectorAll('td').forEach(td => td.classList.add('border-bottom-0'));
               di.parentElement.querySelectorAll('[data-indexed]').forEach(did => did.classList.remove('visually-hidden'));
           });
       });
    });

    document.querySelectorAll('.table-user').forEach(tu => {
        tu.addEventListener('click', () => {window.location.href = tu.getAttribute('href')});
    });

    loadSelects();
});

function loadSelects() {
    document.querySelectorAll('[is="select"]').forEach(s => {
        let selectOptions = s.querySelector('.select-options');
        selectOptions.style.width = 100 + "%";
        selectOptions.style.left = 0 + "px";
        selectOptions.style.bottom = - selectOptions.clientHeight - 10 + "px";
        selectOptions.querySelectorAll('div').forEach(o => {
            o.addEventListener('click', () => {
                let optionValue = o.querySelector('span').getAttribute('value');
                if(optionValue !== "close") {
                    s.querySelector('select').value = optionValue;
                    s.querySelector('select').dispatchEvent(new InputEvent('input'));
                    s.querySelector('button span').textContent = o.querySelector('span').textContent;
                }
                selectOptions.style.bottom = - selectOptions.clientHeight - 10 + "px";
                document.querySelector('#overlay').style.opacity = "0";
                document.querySelector('#overlay').style.zIndex = "-1";
            });
        });

        s.querySelector('button').addEventListener("click", () => {
            selectOptions.style.bottom = document.querySelector('footer').offsetHeight + "px";
            document.querySelector('#overlay').style.opacity = "0.7";
            document.querySelector('#overlay').style.zIndex = "2";
        });
    });
}