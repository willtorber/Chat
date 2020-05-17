var socket = null;

function makeSocketIoObject(domain, port) {
    socket = io('http://' + domain + ':' + port);
}

function configureObjectSocketIO() {
    // Cuando el servidor emita el evento new message lo recibimos y lo mostramos
    socket.on('new message', function(data) {
        if (data.action === 'yo') {
            $(".containerMessages").append('<div style="text-align: right;"><p class="form-control m-input" style="color: white;border-color: transparent;background: #475eff7a; width: 60%; height: auto; margin-left: auto">' + data.message + '</p></div>');
        } else {
            $(".containerMessages").append('<div style="text-align: left;"><p class="form-control m-input" style="color: white;border-color: transparent;background: #ffffffab; width: 60%; height: auto; margin-left: 0px">' + data.message + '</p></div>');
        }
    });

    // Cuando el servidor emita el eveno login
    socket.on('login', function(data) {
        // Mostrar el mensaje de bienvenida
        $(".titleChat").html('Welcome to Socket.IO Chat');
        registerUsersSidebar(data.usernames);
        $("#home").show();
        $("#logout").show();
        $("#index").hide();
        $(".username").val('');
    });

    // Cuando el servidor emita el evento user joined
    socket.on('user joined', function(data) {
        registerUsersSidebar(data.usernames);
    });

    // Cuando el servidor emita el evento user left
    socket.on('user left', function(data) {
        registerUsersSidebar(data.usernames);
    });
}

function login(username) {
    // Emitimos el evento add user
    socket.emit('add user', username);
}

function logout() {
    // Emitimos el evento user logout
    socket.emit('user logout');
}

function sendMessage(message) {
    // Emitimos el evento new mwssage
    socket.emit("new message", message);
}