package ufps.architecture.chat.business.interfaces;
/**
 *  
 * @author  William Torres
 * @version 2.0.0
 * @since   2018-03-28 
 */
public interface IMessenger {
	
	/**
	 * Se establecen todos los parametros de configuración
	 * */
	public void setConfigClient();
	
	/**
	 * Establece una conexión con el servidor encargado de transmitir los mensajes
	 * */
	public void runConection();
	
	/**
	 * Cierra la conexión con el servidor encargado de transmitir los mensajes
	 * */
	public void closeConection();
	
	/**
	 * Envia mensajes al servido
	 * */
	public void sendMessage(String message);
	
	/**
	 * Consulta el estado de la conexión (numero de participantes, nombres de los participantes)
	 * */
	public void getStatus();	
	
	/**
	 * Establece una sesión en el servidor encargado de transmitir los mensajes
	 * */
	public void login(String username);

}
