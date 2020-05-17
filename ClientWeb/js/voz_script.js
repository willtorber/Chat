try {
    var SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
    var recognition = new SpeechRecognition();
} catch (e) {
    console.error(e);
    $('.no-browser-support').show();
    $('.app').hide();
}

var noteTextarea = $('#note-textarea');
var noteContent = '';
//Control de estado
var estado = false;

/*-----------------------------
      Voice Recognition
------------------------------*/

// If false, the recording will stop after a few seconds of silence.
// When true, the silence period is longer (about 15 seconds),
// allowing us to keep recording even when the user pauses.
recognition.continuous = true;

// This block is called every time the Speech APi captures a line.
recognition.onresult = function(event) {

    // event is a SpeechRecognitionEvent object.
    // It holds all the lines we have captured so far.
    // We only need the current one.
    var current = event.resultIndex;

    // Get a transcript of what was said.
    var transcript = event.results[current][0].transcript;

    // Add the current transcript to the contents of our Note.
    // There is a weird bug on mobile, where everything is repeated twice.
    // There is no official solution so far so we have to handle an edge case.
    var mobileRepeatBug = (current === 1 && transcript === event.results[0][0].transcript);

    if (!mobileRepeatBug) {
        if (transcript.toLowerCase() === " enviar") {
            var message = $(".msg").val();
            if (message.length > 0) {
                socket.emit("new message", message);
                $(".msg").val("");
                noteContent = '';
            }
        } else {
            noteContent += ' ' + transcript;
            noteTextarea.val(noteContent);
        }
    }
};


/*-----------------------------
      App buttons and input
------------------------------*/

$('#start-record-btn').on('click', function(e) {
    if (!estado) {
        recognition.start();
        estado = true;
        $("#start-record-btn").attr("src", "img/microphone-red.png");
    } else {
        recognition.stop();
        noteContent = '';
        estado = false;
        $("#start-record-btn").attr("src", "img/microfono.png");
    }
});

// Sync the text inside the text area with the noteContent variable.
noteTextarea.on('input', function() {
    noteContent = $(this).val();
});

/*-----------------------------
      Speech Synthesis
------------------------------*/
function readOutLoud(message) {
    var speech = new SpeechSynthesisUtterance();

    // Set the text and voice attributes.
    speech.text = message;
    speech.volume = 1;
    speech.rate = 1;
    speech.pitch = 1;

    window.speechSynthesis.speak(speech);
}