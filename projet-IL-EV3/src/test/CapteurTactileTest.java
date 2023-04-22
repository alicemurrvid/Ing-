package test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import devices.CapteurTactile;
import lejos.hardware.port.SensorPort;

public class CapteurTactileTest {

	@Test
	public void enContactTest() {
		CapteurTactile unCapteurTactile = new CapteurTactile(SensorPort.S1);
		assertEquals(0, unCapteurTactile.enContact());
	}
}
