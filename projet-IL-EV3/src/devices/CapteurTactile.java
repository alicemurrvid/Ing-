package devices;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;

public class CapteurTactile {

	private EV3TouchSensor capteurTactile;

	public CapteurTactile(Port port) {
		this.capteurTactile = new EV3TouchSensor (port);
	}

	/*
	 * méthode qui renvoi true si le capteur tactile est pressé, 
	 * utile pour controler la rotation du bras
	 * sample est un tableau de reel, 1 équivaut à une pression sur le capteur
	*/
	public boolean enContact() {
		float[] sample = new float[capteurTactile.sampleSize()];
		
		capteurTactile.fetchSample(sample, 0);

		float etat = sample[0];
		System.out.print(etat);
		if (etat == 1)
			return true;
		else
			return false;
		
	}
	
	
}
