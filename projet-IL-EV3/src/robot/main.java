package robot;

import devices.*;
import etat.*;
import communication.ConnexionDistante;
import lejos.hardware.port.SensorPort;
import java.util.HashMap;

public class main {
	
	private  static int codeTelecommade = 0;
	private static  boolean app_alive;
	
	// moteur qui controle la pince
	private static Moteur moteur_A = new Moteur('A');
	//moteur qui gère la lever
	private static Moteur moteur_B = new Moteur('B');
	//moteur qui gère la rotation
	private static Moteur moteur_D = new Moteur('D');
	
	// capteur tactile
	private static CapteurTactile capteurTactile = new CapteurTactile(SensorPort.S1);
    //capteur de couleur
	//private static CapteurCouleur capteurCouleur = new CapteurCouleur(SensorPort.S3);

	// pour stocker les positions  des moteurs
	private static EtatsPince etatPince = EtatsPince.valueOf("INCONNU");
	private static EtatsMoteur etatMoteurB = EtatsMoteur.valueOf("INCONNU");
	private static EtatsMoteur etatMoteurD = EtatsMoteur.valueOf("INCONNU");
	private static HashMap<String, EtatsMoteur> etatMoteurs = new HashMap<String, EtatsMoteur>();
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		
		//la vitesse set au moment de l'instanciation est trop rapide
		moteur_A.setVitesse(50);
		moteur_B.setVitesse(50);
		moteur_D.setVitesse(50);
		
		//création du thread de connexion
		ConnexionDistante CoDist = new ConnexionDistante();
		
		//instanciation du hashmap (nom moteur, etat du moteur)
		etatMoteurs.put(moteur_B.getNom(), etatMoteurB);
		etatMoteurs.put(moteur_D.getNom(), etatMoteurD);
		System.out.println("1");
		init(moteur_D, etatMoteurs,moteur_A,etatPince, moteur_B);
		System.out.println("2");
		//début de l'écoute
		CoDist.start();
		System.out.print(codeTelecommade+"-");
		app_alive = true;
	
		/** while(app_alive) {
			codeTelecommade = CoDist.byteRecu;
			System.out.print(codeTelecommade+"-");
			switch(codeTelecommade) {
//--------- MOTEUR D ----------------------------------
				// démarrage rotation gauche (MOTEUR D) 
			case 1: 
				backward_Bras(moteur_D, etatMoteurs, etatPince);
				break;
				// Stop moteur D
			case 2: 
			case 4: 
				moteur_D.stop();
				break;
				// démarrage rotation droite (MOTEUR D) 
			case 3: 
				forward_Bras(moteur_D, etatMoteurs, etatPince);
				break;			
//--------- MOTEUR B ----------------------------------
				// démarrage levé bras (MOTEUR B)
			case 5: 
				backward_Bras(moteur_B, etatMoteurs, etatPince);
				break;
				// stop MOTEUR B
			case 6: 
			case 8:
				moteur_B.stop();
				break;
				// démarrage baissé bras (MOTEUR B)
			case 7: 
				forward_Bras(moteur_B, etatMoteurs, etatPince);
				break;
//--------- MOTEUR A ----------------------------------
				// démarrage ouverture pince (MOTEUR A)
			case 9: 
				forward_Bras(moteur_A, etatMoteurs, etatPince);
				break;
				// stop ouverture pince (MOTEUR A)
			case 10: 
			case 12: 
				moteur_A.stop();
				break;
				// démarrage fermeture pince (MOTEUR A)
			case 11: 
				backward_Bras(moteur_A, etatMoteurs, etatPince);
				break;
//--------- Déconnexion ----------------------------------
			case 13: 
				app_alive = false;
				break;
			default :break;
			}
		}
		//Déconnexion
		CoDist.disconnect(); */
	}
	
	//on instancie en s'assurant que le bras est en position de toucher le capteur et on ouvre la pince
	private static void init(Moteur moteurBras, HashMap<String, EtatsMoteur> etatMs,Moteur moteurPince,EtatsPince etatPince, Moteur moteurBrasLeve) {
		if(moteurBras.getNom() == "Moteur D") {
			
			//Bras 
			mouvement_forward(moteurBras);
			EtatsMoteur eM = etatMs.get(moteurBras.getNom());
			eM = EtatsMoteur.valueOf("Full_Forward");
			etatMs.remove(moteurBras.getNom());
			etatMs.put(moteurBras.getNom(), eM);
			
			mouvement_backward(moteurBrasLeve);
			EtatsMoteur eML = etatMs.get(moteurBrasLeve.getNom());
			eML = EtatsMoteur.valueOf("Full_Backward");
			etatMs.remove(moteurBrasLeve.getNom());
			etatMs.put(moteurBrasLeve.getNom(), eML);
			//Pince
			mouvement_forward(moteurPince);
			etatPince = EtatsPince.valueOf("Ouvert");
			
		}else
			System.out.println("Erreur impossible d'initialiser à partir du moteur " + moteurBras.getNom());
	}
	
	// -------------------------------------------------------------------------
	// Méthodes pour les moteurs élémentaires
	// -------------------------------------------------------------------------
	private static void mouvement_backward(Moteur moteur) {
		
		  while(!(moteur.enBute())) {
			  moteur.backward();
		  }
		  moteur.stop();
	}
	
	private static void mouvement_forward(Moteur moteur) {
		System.out.println("test moteur : "+moteur.getNom());
		if(moteur.getNom() == "Moteur D") {
			while(!capteurTactile.enContact()) {
				System.out.println("capteur renvoit false");
				  moteur.forward();
			}
		} else {
			while(!(moteur.enBute())) {
				moteur.forward();
			}
		}
		 moteur.stop();
	}
	// -------------------------------------------------------------------------
	// Méthodes pour les moteurs du bras avec ctrl des états avant action
	// -------------------------------------------------------------------------
	
	//déclanche un mouvement horaire, ctrl si l'état du bras le permet 
	public static void forward_Bras(Moteur moteur, HashMap<String,EtatsMoteur> etatMs, EtatsPince etatP) {
		
		String nomMoteurCourant = moteur.getNom();
		
		//cas moteur B et D
		if(moteur.getNom() != "Moteur A") {		
			if (etatMs.get(nomMoteurCourant).name() != EtatsMoteur.valueOf("Full_Forward").toString()) {
				//On fait avancer le moteur courant puis on met à jour son état
				mouvement_forward(moteur);
				EtatsMoteur eM = etatMs.get(nomMoteurCourant) ;
				eM = EtatsMoteur.valueOf("Full_Forward");
				etatMs.remove(nomMoteurCourant);
				etatMs.put(nomMoteurCourant, eM);
			}else {
				System.out.println("Mouvement forward impossible pour le moteur "+ moteur.getNom() + " deja au maximum de l'amplitude possible");
			}
		//cas moteur A (pince)
		}else {
			if (etatP.name() != EtatsPince.valueOf("Ouvert").toString()) {
				mouvement_forward(moteur);
				etatP = EtatsPince.valueOf("Ouvert");
			}else {
				System.out.println("Impossible d'ouvrir la pince deja ouverte");
			}
		}
	}
	
	//déclenche un mouvement anti-horaire, ctrl si l'état du bras le permet 
	public static void backward_Bras(Moteur moteur, HashMap<String,EtatsMoteur> etatMs, EtatsPince etatP) {
		
		String nomMoteurCourant = moteur.getNom();
		
		//cas moteur B et D
		if(moteur.getNom() != "Moteur A")  {
			if (etatMs.get(nomMoteurCourant).name() != EtatsMoteur.valueOf("Full_Backward").toString()) {
				//On fait reculer le moteur courant puis on met à jour son état
				mouvement_backward(moteur);
				EtatsMoteur eM = etatMs.get(moteur.getNom());
				eM = EtatsMoteur.valueOf("Full_Backward");
				etatMs.remove(moteur.getNom());
				etatMs.put(moteur.getNom(), eM);
			}else {
				System.out.println("Mouvement backward impossible pour le moteur "+  moteur.getNom() + " deja au maximum de l'amplitude possible");
			}
		//cas moteur A (pince)
		}else {
			if (etatP.name() != EtatsPince.valueOf("Ferme").toString()) {
				mouvement_backward(moteur);
				etatP = EtatsPince.valueOf("Ferme");
			}else {
				System.out.println("Impossible de fermer la pince deja fermee" );
			}
		}
	}
	
	//permet de récupérer l'état du moteur B ou D
	public EtatsMoteur getStateMoteurBD(Moteur moteur, HashMap<String, EtatsMoteur> etatMs) {
		String nomMoteurCourant = moteur.getNom();
		return etatMs.get(nomMoteurCourant);
	}
	
	
}