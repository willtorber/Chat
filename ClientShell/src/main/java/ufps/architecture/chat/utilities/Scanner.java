package ufps.architecture.chat.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * La clase Scanner se encarga de la captura de texto por teclado.
 * Su implementación se debe a que tiene un mejor rendimiento en
 * comparación con la clase Scanner del paquete IO de Java. 
 * 
 * @author  Gerson Lazaro
 * @version 1.0.0
 * @since   2017-06-15 
 */
public class Scanner {

	/**
	 *  Es un puente entre las secuencias de bytes y las secuencias de caracteres: 
	 *  lee los bytes y los decodifica en caracteres utilizando un charset específico.
	 */
	InputStreamReader isr = new InputStreamReader(System.in);
	
	/**
	 *  Lee texto de una secuencia de entrada de caracteres, almacenando en el búfer 
	 *  caracteres para proporcionar la lectura eficiente de caracteres, matrices y líneas.
	 */
	BufferedReader br = new BufferedReader(isr);
	
	/**
	 * Permite que una aplicación rompa una cadena en tokens.
	 */
	StringTokenizer st = new StringTokenizer("");
	
	/**
	 * Numero de espacios 
	 */
	int espacios = 0;

	/**
	 * Devuelve la siguiente linea entera en el stream.
	 * 
	 * @return String con la linea siguiente
	 * @throws IOException
	 */
	public String nextLine() throws IOException {
		if (espacios > 0) {
			espacios--;
			return "";
		} else if (st.hasMoreTokens()) {
			StringBuilder salida = new StringBuilder();
			while (st.hasMoreTokens()) {
				salida.append(st.nextToken());
				if (st.countTokens() > 0) {
					salida.append(" ");
				}
			}
			return salida.toString();
		}
		return br.readLine();
	}

	/**
	 * Devuelve el siguiente token delimitado por espacios o saltos de linea. Es
	 * decir, devuelve la siguiente palabra.
	 * 
	 * @return String con el siguiente token
	 * @throws IOException
	 */
	public String next() throws IOException {
		espacios = 0;
		while (!st.hasMoreTokens()) {
			st = new StringTokenizer(br.readLine());
		}
		return st.nextToken();
	}

	/**
	 * Retorna true si hay mas tokens en el flujo de datos, o falso en caso
	 * contrario.
	 * 
	 * @return true si y solo si hay mas tokens
	 * @throws IOException
	 */
	public boolean hasNext() throws IOException {
		while (!st.hasMoreTokens()) {
			String linea = br.readLine();
			if (linea == null) {
				return false;
			}
			if (linea.length()==0) {
				espacios++;
			}
			st = new StringTokenizer(linea);
		}
		return true;
	}

}
