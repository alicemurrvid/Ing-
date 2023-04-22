package communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import lejos.hardware.Bluetooth;
import lejos.remote.nxt.BTConnection;
import lejos.remote.nxt.BTConnector;
import lejos.remote.nxt.NXTConnection;

public class ConnexionDistante extends Thread{
	
	private static DataOutputStream dataOut; 
	private static DataInputStream dataIn;
	private static BTConnection BTLink;
	private static boolean app_alive;
	
	public volatile int byteRecu=0;
	
		public void run() {

			connect();
			app_alive = true;
			while(app_alive){

				try {

					byteRecu = (int) dataIn.readByte(); 
					
					Thread.sleep(100);
					System.out.println("Recu " + byteRecu);
					
					byteRecu = 0;
		
				}

				catch (IOException ioe) {
					System.out.println("IO Exception readInt");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		public int getTransmit() {
			return this.byteRecu;
		}
		
		public static void connect()
		{  
			System.out.println("Connexion établie");
			BTConnector ncc = (BTConnector) Bluetooth.getNXTCommConnector();
			BTLink = (BTConnection) ncc.waitForConnection(30000, NXTConnection.RAW);
			dataOut = BTLink.openDataOutputStream();
			dataIn = BTLink.openDataInputStream();
		}
		
		public static void disconnect() {
			try {
				dataOut.close();
				dataIn.close();
				BTLink.close();
				System.out.println("Déconnexion effective");
			} catch (IOException e) {
				System.out.println("Erreur : "+ e.getStackTrace() +" lors de la déconnexion");
			}
		}

}
