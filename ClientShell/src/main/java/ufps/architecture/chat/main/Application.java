package ufps.architecture.chat.main;

import java.io.IOException;
import java.net.URISyntaxException;

import ufps.architecture.chat.business.AsistentSpeech;
import ufps.architecture.chat.business.ClientSocketIO;
import ufps.architecture.chat.utilities.Message;
import ufps.architecture.chat.utilities.Scanner;

/**
 *  
 * @author  William Torres
 * @version 2.0.0
 * @since   2018-03-28 
 */
public class Application {	
	
	/**
	 * Constructor 
	 */
	private Application() {	}

   /**
    * This is the main method which makes use of ClientSocketIO class.
    * @param args Unused.
    */
	public static void main(String[] args) {
		
		Scanner sc = new Scanner();
		Message.thisIsSystemMessage("Bienvenido al mejor chat de la clase");		
				
		try {
			Message.thisIsSystemMessage("Ingrese el dominio/ip del servidor: ");
			String ip_server = sc.nextLine();
			Message.thisIsSystemMessage("Ingrese el puerto: ");
			String port = sc.nextLine();		
		
			ClientSocketIO client = new ClientSocketIO(ip_server, port);
			client.setConfigClient();
			client.runConection();
			
			AsistentSpeech asistSpeech = new AsistentSpeech();
			
			Message.thisIsRulesMessage();
			Message.thisIsSystemMessage("Ingrese su NickName: ");			
			String username = sc.nextLine();
			String message = "";
			
			client.login(username);
			
			while(true) {
				message = sc.nextLine();
				
				if ("/exit".equalsIgnoreCase(message)) {
					ClientSocketIO.logout();
					break;
				} else if ("/status".equalsIgnoreCase(message)) {
					client.getStatus();								
				} else if ("/startAudio".equalsIgnoreCase(message)) {
					asistSpeech.getAudio(client);								
				} else if ("/stopAudio".equalsIgnoreCase(message)) {
					asistSpeech.getStop();								
				} else {
					client.sendMessage(message);
				}								
			}			
				
		} catch(URISyntaxException e) {
			System.out.println("Error al crear la conexión con el servidor");
			
		} catch (IOException e) {			
			System.out.println("Error al capturar el texto ingresado por teclado");
		}				
	}
}
