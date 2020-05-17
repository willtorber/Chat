// Cuando el documento esté listo
$(document).ready(function() {
    // Open modal
    $("#m_modal_4").modal('show');

    // Al hacer click en el botón de guardar se guardan el ip y el puerto
    $("#btn_set_domain").on("click", function() {
        establecerSocket(socket);
    });

    // Al hacer submit del formulario de login
    $("form").on("submit", function(e) {
        e.preventDefault();
        var username = $(".username").val();
        login(username);
        $("#titleChat").hide();
    });

    //al hacer click en el botón de logout emitimos el evento user logout
    $("#logout").on("click", function() {
        logout();
        $("#home").hide();
        $("#index").show();
        $("#logout").hide();
        $("#titleChat").show();
    });

    // Cuando se pulse la tecla enter en la aplicación emitimos el evento new message
    // Si ponemos el código donde escribimos mensajes en un form no es necesario
    $(document).keypress(function(e) {
        enviarMensaje(e);
    });
});

function enviarMensaje(e) {
    if (e.which === 13) {
        var message = $(".msg").val();
        if (message.length > 0) {
            sendMessage(message);
            $(".msg").val("");
        }
    }
}

// Crea el objeto SocketIO y establece la configuración de eventos a escuchar
function establecerSocket() {
    var domain = $('#domain').val();
    var port = $('#port').val();
    if (domain === '' || port === '') {
        $("#msn").append('<div class="alert alert-danger ">' + 'Todos los valores se deben llenar' + '</div>');
    } else {
        $("#m_modal_4").modal("hide");
        // Establecemos el socket
        makeSocketIoObject(domain, port);
        configureObjectSocketIO();
    }
}

// Muestra los usuarios en el sidebar
function registerUsersSidebar(usernames) {
    var users = '';
    $.each(usernames, function(i, item) {
        users += '<li>' + item + '</li>';
    });
    $('#users').html(users);
}