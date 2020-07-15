import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.text.Normalizer;
import java.util.ArrayList;


/**
 * Gère une liste de mots
 */
public class Dictionnaire {
    private ArrayList<String> lesMots;
    private Random rand;

	/**
	 * permet d'enlever les accents à un mot
	 * @param mot le mot à traiter
	 * @return le même mot mais sans accent
	 */
	public static String sansAccents(String mot) {
	String normalized = Normalizer.normalize(mot, Normalizer.Form.NFD);
	return normalized.replaceAll("[\u0300-\u036F]", ""); 
    }

	/**
	 * Constructeur qui lit un fichier de mot et retient ceux qui ont une longueur
	 * comprise entre longMin et longMax
	 * @param nomFic nom du fichier contenant le dictionnaire
	 * @param longMin longueur minimale des mots retenus
	 * @param longMax longueur maximale des mots retenus
	 */
    public Dictionnaire(String nomFic, int longMin, int longMax){
	BufferedReader lecteurAvecBuffer = null;
	String ligne;
	lesMots=new ArrayList<String>();
	try{
	    lecteurAvecBuffer = new BufferedReader(new FileReader(nomFic));
	}
	catch(FileNotFoundException exc){
	    System.out.println("Erreur d'ouverture");
	}
	try{
	    while ((ligne = lecteurAvecBuffer.readLine()) != null){
		ligne=sansAccents(ligne).toUpperCase();
		int lg=ligne.length();
		if (ligne.matches("([A-Z]|-)*"))
		    if (lg>=longMin && lg<=longMax)
			lesMots.add(ligne);
	    }
	    lecteurAvecBuffer.close();
	}
	catch (Exception e){
	    System.out.println("Erreur de lecture dictionnaire");
	}
	rand = new Random();
    }

	/**
	 * choisit un mot au hasard dans le dictionnaire
	 * @return le mot choisi
	 */
    public String choisirMot(){
	int i=rand.nextInt(lesMots.size());
	return lesMots.get(i);
    }
}
