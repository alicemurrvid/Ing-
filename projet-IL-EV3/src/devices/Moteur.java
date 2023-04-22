package devices;

import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;

public class Moteur {
	
	NXTRegulatedMotor lejosMotor;
	String nom;

	  public Moteur(char port)
	  {
	    switch(port)
	    {
	      case 'A':
	        this.lejosMotor = Motor.A;
	        this.nom = "Moteur A";
	        break;
	      case 'B':
	        this.lejosMotor = Motor.B;
	        this.nom = "Moteur B";
	        break;
	      case 'C':
	        this.lejosMotor = Motor.C;
	        this.nom = "Moteur C";
	        break;
	      case 'D':
	        this.lejosMotor = Motor.D;
	        this.nom = "Moteur D";
	        break;
	    }
	  }

	  public void setVitesse(int speed)
	  {
	    this.lejosMotor.setSpeed(speed);
	  }


	  public void rotate(int angle)
	  {
	    this.lejosMotor.rotate(angle);
	  }
	  
	  public void forward() {
			this.lejosMotor.forward();
	  }

	  public void backward() {
			this.lejosMotor.backward();
	  }
	  
	  public String getNom() {
		  return this.nom;
	  }
	  
	  public Boolean enBute() {
		  return this.lejosMotor.isStalled();
	  }
	  
	  public void stop() {
			this.lejosMotor.stop();
	  }
}

