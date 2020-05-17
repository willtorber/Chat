package ufps.architecture.chat.utilities;

/**
 * La clase Message se encarga de formatear los 
 * mensajes proporcionados al usuario.
 * 
 * @author  William Torres
 * @version 2.0.0
 * @since   2018-03-28 
 */
public class Message {
	
	private final static String colorI = "\u001B[33m";
	private final static String colorF = "\u001B[0m";
	/**
	 * Constructor 
	 */
    private Message () {}
	
	/**
	 * Muestra el mensaje de reglas basicas del chat
	 */
	public static void thisIsRulesMessage() {
		System.out.println(colorI + "Cosas a saber: " + colorF);
		System.out.println(colorI + "*  Para salir ingrese '/exit'" + colorF);
		System.out.println(colorI + "*  Para conocer los usuarios conectados ingrese '/status" + colorF);
		System.out.println(colorI + "*  Para activar el microfono ingrese '/startAudio" + colorF);
		System.out.println(colorI + "*  Para desactivar el microfono ingrese '/stopAudio" + colorF);
	}
	
	/**
	 * @param message Mensaje del usuario a formatear (cambiar al color magenta)
	 */
	public static void thisIsStatusUsersMessage(String message) {
		System.out.println("\u001B[35m" + message + colorF);
	}
	
	/**
	 * @param message Mensaje del sistema a formatear (cambiar al color verde)
	 */
	public static void thisIsSystemMessage(String message) {
		System.out.println("\u001B[32m" + message + colorF);
	}
	
	
	
	
		
}
