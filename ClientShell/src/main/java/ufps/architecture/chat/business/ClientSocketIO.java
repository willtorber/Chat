package ufps.architecture.chat.business;

import java.net.URISyntaxException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import ufps.architecture.chat.business.interfaces.IMessenger;
import ufps.architecture.chat.utilities.Message;

/**
 * Clase ClientSocketIO se encarga de la administración 
 * de los eventos del Objeto Socket.
 *   
 * @author  William Torres
 * @version 2.0.0
 * @since   2018-03-28 
 */
public class ClientSocketIO implements IMessenger {
	
	private Socket socket;	
	
	/**
	 * @param ip_server Dirección del servidor
	 * @param port Puerto por el cual el servidor escuchará peticiones 
	 * @throws URISyntaxException 
	 */
	public ClientSocketIO(String ip_server, String port) throws URISyntaxException {
		socket = IO.socket("http://" + ip_server + ":" + port);
	}
	
	
	/**
	 * Establece los metodos a escuchar y a lanzar por el cliente.
	 * 
	 */
	@Override
	public void setConfigClient() {
		socket.on("new message", new Emitter.Listener() {

			public void call(Object... arg0) {				
				String message = getMessage(String.valueOf(arg0[0]), "message");
				System.out.println(message);
			}
			
		}).on("status", new Emitter.Listener() {

			public void call(Object... arg0) {
				System.out.println(String.valueOf(arg0[0]));
			}
			
		}).on("user joined", new Emitter.Listener() {

			public void call(Object... arg0) {
				String message = getMessage(String.valueOf(arg0[0]), "username");
				Message.thisIsStatusUsersMessage("** Se ha unido "+ message + " **");				
			}
			
		}).on("user left", new Emitter.Listener() {

			public void call(Object... arg0) {
				String message = getMessage(String.valueOf(arg0[0]), "username");
				if (message == null) {
					Message.thisIsStatusUsersMessage("-- Cerraste sesion --");					
				}else {
					Message.thisIsStatusUsersMessage("-- Se ha desconectado "+ message + " --");					
				}
			}			
		});
	}
	
		
	
	/**
	 * Establece la conexión con el servidor
	 */
	@Override
	public void runConection() {
		socket.connect();
	}

	/**
	 * Ciera la conexión con el servidor
	 */
	@Override
	public void closeConection() {
		socket.disconnect();
	}
		
	/**
	 * Solicita al servidor enviar un mensaje
	 * @param message Cadena de texto a enviar
	 */
	@Override
	public void sendMessage(String message) {	
		if (message.length() > 0) {
			socket.emit("new message", message);
		}		
	}
	
	/**
	 * Solicita al servidor conocer los usuarios conectados
	 */
	@Override
	public void getStatus() {
		socket.emit("status");
	}
	
	/**
	 * Solicita al servidor crear una sesión
	 * @param username Nombre del usuario con el cual se registrará en el servidor
	 */	
	@Override
	public void login(String username) {
		socket.emit("add user", username);
	}
	
	/**
	 * Cierra la ventana del chat y ocasiona que se lanze el evento 
	 * "disconect" (destruyendo la sesión creada en el servidor)
	 */
	public static void logout() {
		System.exit(-1);
	}
	
	/**
	 * 
	 * @param args Texto (mensaje) proveniente del servidor
	 * @param field Fragmento del texto a obtener
	 * @return Mensaje formateado 
	 */
	private static String getMessage(String args, String field) {		
        JSONParser parser = new JSONParser();		        
        String message = "";
        try {
	        Object obj = parser.parse(args);
	        JSONObject objJson = null;
	        if (obj != null) {
	        	objJson = (JSONObject) obj;	        
	        	message = (String) objJson.get(field);
	        }	        		        	        
		} catch (ParseException e) {
			System.out.println("Los datos entrantes tiene un formato no soportado");
		} 
		return message;
	}

}
