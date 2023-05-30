const alphabet = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"];
const el = document.getElementById("loading");
const word = el.innerHTML.trim();
let letter_count = 0;
let finished = false;

el.innerHTML = "";
for (let i = 0; i < word.length; i++) {
    const span = document.createElement("span");
    span.textContent = word.charAt(i);
    el.appendChild(span);
}

setTimeout(write, 50);
const incrementer = setTimeout(inc, 1000);

function write() {
    for (let i = letter_count; i < word.length; i++) {
        const c = Math.floor(Math.random() * 36);
        const span = el.getElementsByTagName("span")[i];
        span.innerHTML = alphabet[c];
    }
    if (!finished) {
        setTimeout(write, 50);
    }
}

function inc() {
    const spans = el.getElementsByTagName("span");
    spans[letter_count].innerHTML = word[letter_count];
    spans[letter_count].classList.add("glow");
    letter_count++;
    if (letter_count >= word.length) {
        finished = true;
        setTimeout(reset, 1000000);
    } else {
        setTimeout(inc, 200);
    }
}

function reset() {
    letter_count = 0;
    finished = false;
    setTimeout(inc, 1000);
    setTimeout(write, 75);
    const spans = el.getElementsByTagName("span");
    for (let i = 0; i < spans.length; i++) {
        spans[i].classList.remove("glow");
    }
}
