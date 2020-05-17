<?php
/**
 * Created by PhpStorm.
 * User: WILLTOR
 * Date: 31/03/2018
 * Time: 10:42 AM
 */

require_once 'config/dataConfig.php';

use PHPSocketIO\SocketIO;

class Server
{

    private $socketIO;
    private $usernames;
    private $numUsers;

    public function __construct()
    {
        $this->numUsers = 0;
        $this->usernames = array();
        // escuchamos en el puerto 2020
        $this->socketIO = new SocketIO(PORT_LISTENING);
    }

    public function configuration()
    {
        //evento de socketio cada vez que un nuevo socket se conecta (abre la web)
        $this->socketIO->on('connection', function($socket)
        {
            $socket->loggedUser = false;

            // cuando se ejecute en el cliente el evento add user
            $socket->on('add user', function ($username) use($socket)
            {

                // guardamos al usuario en sesión
                $socket->username = $username;

                //añadimos al cliente a la lista global
                #$this->usernames[$username] = $username;
                array_push($this->usernames, $username);
                ++$this->numUsers;

                $socket->loggedUser = true;
                $socket->emit('login', array(
                    'numUsers' => $this->numUsers,
                    'usernames' => $this->usernames,
                ));

                // notificamos a todos que un usuario ha entrado
                $socket->broadcast->emit('user joined', array(
                    'username' => $socket->username,
                    'numUsers' => $this->numUsers,
                    "usernames"=> $this->usernames
                ));

            });

            // cuando se ejecute en el cliente el evento new message
            $socket->on('new message', function($message) use($socket)
            {
                //me notifico del mensaje que he escrito
                $socket->emit("new message", array(
                        "action" => "yo",
                        "message" => "Yo: " . $message
                    )
                );

                //notificamos al resto del mensaje que he escrito
                $socket->broadcast->emit("new message", array(
                    "action" => "chat",
                    "message" => $socket->username . " dice: " . $message
                ));
            });

            // cuando se ejecute en el cliente el evento status
            $socket->on('status', function () use ($socket) {

                $socket->emit('status', array(
                    'numUsers' => $this->numUsers,
                    "usernames"=> array_values($this->usernames)
                ));
            });

            // cuando se ejecute en el cliente el evento user logout
            $socket->on('user logout', function () use($socket)
            {

                if($socket->loggedUser)
                {
                    //actualizamos la lista de usuarios conectados
                    unset($this->usernames[$socket->username]);
                    $this->numUsers--;

                    // notificamos de forma global que el usuario está fuera
                    $socket->broadcast->emit('user left', array(
                        'username' => $socket->username,
                        'numUsers' => $this->numUsers,
                        "usernames"=> $this->usernames
                    ));
                }
            });

            //evento de socketio cada vez que un nuevo socket se desconecta (cierra la web o actualiza el navegador)
            $socket->on('disconnect', function () use($socket)
            {
                // eliminamos al usuario de la lista de usuarios
                if($socket->loggedUser) {
                    //actualizamos la lista de usuarios conectados
                    //unset($this->usernames[$socket->username]);
                    //$this->numUsers--;
                    $key = array_search($socket->username, $this->usernames);
                    unset($this->usernames[$key]);
                    --$this->numUsers;

                    // notificamos de forma global que el usuario está fuera
                    $socket->broadcast->emit('user left', array(
                        'username' => $socket->username,
                        'numUsers' => $this->numUsers,
                        'usernames' => $this->usernames
                    ));
                }
            });
        });
    }




}