package test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import devices.Moteur;
import etat.EtatsMoteur;
import lejos.utility.Delay;
import robot.main;

public class ControleurTest {
	private main mainTest;
	private Moteur moteurA;
	private Moteur moteurB;
	private Moteur moteurD;
	private HashMap<String, EtatsMoteur> etatMoteurs = new HashMap<String, EtatsMoteur>();

	@Before
	public void setup() {
		this.mainTest = new main();
		this.moteurA = new Moteur('A');
		this.moteurB = new Moteur('B');
		this.moteurD = new Moteur('D');
	}
	
	/*
	// Tests sur le moteur A
	@Test
	public void testMoteurAOuvert() {
		this.mainTest.forward_Bras(moteurA, etatMoteurs, null);
		Delay.msDelay(6000);
		assertEquals(this.mainTest.getStateMoteurA(moteurA, null), "Ouvert");
	}
	
	@Test
	public void testMoteurAFerme() {
		this.mainTest.backward_Bras(moteurA, etatMoteurs, null);
		Delay.msDelay(6000);
		assertEquals(this.mainTest.getStateMoteurA(moteurA, null), "Ferme");
	}
	*/
	// Tests sur le moteur B
	@Test
	public void testMoteurBHaut() {
		this.mainTest.forward_Bras(moteurB, etatMoteurs, null);
		Delay.msDelay(6000);
		assertEquals(this.mainTest.getStateMoteurBD(moteurB, null), "Full_Forward");
	}
	
	@Test
	public void testMoteurBBas() {
		this.mainTest.backward_Bras(moteurB, etatMoteurs, null);
		Delay.msDelay(6000);
		assertEquals(this.mainTest.getStateMoteurBD(moteurB, null), "Full_Backward");
	}
	
	// Tests sur le moteur D
	@Test
	public void testMoteurDDroite() {
		this.mainTest.forward_Bras(moteurD, etatMoteurs, null);
		Delay.msDelay(6000);
		assertEquals(this.mainTest.getStateMoteurBD(moteurD, null), "Full_Forward");
	}
	
	@Test
	public void testMoteurDGauche() {
		this.mainTest.backward_Bras(moteurD, etatMoteurs, null);
		Delay.msDelay(6000);
		assertEquals(this.mainTest.getStateMoteurBD(moteurD, null), "Full_Backward");
	}
	

	
	
}
