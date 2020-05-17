package ufps.architecture.chat.business;

import javax.sound.sampled.LineUnavailableException;

import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.recognizer.GSpeechDuplex;
import com.darkprograms.speech.recognizer.GSpeechResponseListener;
import com.darkprograms.speech.recognizer.GoogleResponse;

import net.sourceforge.javaflacencoder.FLACFileWriter;
import ufps.architecture.chat.business.interfaces.IMessenger;

/**
 * 
 * @author William Torres
 * @version 2.0.0
 * @since 2018-03-28
 */
public class AsistentSpeech implements GSpeechResponseListener {

	private Microphone mic;
	private GSpeechDuplex duplex;

	/** Instacia los objetos necesarios para la captura de audio */
	public AsistentSpeech() {
		mic = new Microphone(FLACFileWriter.FLAC);
		duplex = new GSpeechDuplex("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
		duplex.setLanguage("es");
	}

	/**
	 * Obtiene el audio, lo transforma a texto y procede a enviarlo
	 * 
	 * @param messenger
	 *            Objeto que permite enviar mensajes
	 */
	private void listenAudio(IMessenger messenger) {
		duplex.addResponseListener(new GSpeechResponseListener() {
			public void onResponse(GoogleResponse gr) {
				String output = "";
				output = gr.getResponse();

				if (gr.isFinalResponse()) {
					messenger.sendMessage(output);
				}

				if (output.contains("(")) {
					output = output.substring(0, output.indexOf('('));
				}

				if (!gr.getOtherPossibleResponses().isEmpty()) {
					output = output + " (" + (String) gr.getOtherPossibleResponses().get(0) + ")";
				}
			}

		});
	}

	/**
	 * Inicia el proceso de captura de audio y envio del mensaje
	 */
	public void getAudio(IMessenger messenger) {
		runListening();
		listenAudio(messenger);
	}

	/**
	 * Inicia el hilo encargado del escuchador
	 */
	private void runListening() {
		new Thread(() -> {
			try {
				duplex.recognize(mic.getTargetDataLine(), mic.getAudioFormat());
			} catch (LineUnavailableException | InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Error al iniciar el escuchador");
			}

		}).start();
	}

	/**
	 * Apaga el microfono y detiene el proceso de escucha
	 */
	public void getStop() {
		mic.close();
		duplex.stopSpeechRecognition();
	}

	@Override
	public void onResponse(GoogleResponse gr) {
		// TODO Auto-generated method stub

	}

}
