package devices;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;

public class CapteurCouleur{

	private EV3ColorSensor capteurCouleur;
	
	//entier qui représente une couleur
	private int idCouleur;

	public CapteurCouleur(Port port) {
		this.capteurCouleur = new EV3ColorSensor(port);
	}
	

	public int getCouleur() {
		idCouleur = this.capteurCouleur.getColorID();
		return idCouleur;
	}
	
}
