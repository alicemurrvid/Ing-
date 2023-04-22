package test;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import devices.CapteurCouleur;
import lejos.hardware.port.SensorPort;

public class CapteurCouleurTest{
	
	@Test
	public void getCouleurTest() {
		CapteurCouleur unCapteurCouleur = new CapteurCouleur(SensorPort.S3);
		assertNotNull(unCapteurCouleur.getCouleur());
	}
}
