import java.text.Normalizer;

/**
 * Modèle du jeu de pendu.
 */
public class MotMystere {
	// constantes gérant les différents niveaux de jeu
	static int AUCUNE=3;
	static int TIRET=2;
	static int PREMIERE=1;
	static int PREMIEREETDERNIERE=0;

	/**
	 * le mot à trouver
	 */
	private String motATrouver;
	/**
	 * le niveau de jeu
	 */
	private int niveau;
	/**
	 * chaine contenant les lettres déjà trouvées
	 */
	private String lettresTrouvees;
	/**
	 * chaine contenant les lettres déjà essayées
	 */
	private String lettresEssayees;
	/**
	 * entier inquant le nombre de lettres restant à trouver
	 */
	private int nbLettresRestantes;
	/**
	 * le nombre d'essais déjà effectués
	 */
	private int nbEssais;
	/**
	 * le nombre d'erreurs encore possibles
	 */
    private int nbErreursRestantes;
    /**
     * le nombre total de tentatives authorisées
     */
    private int nbEerreursMax;
    /**
     * dictionnaire dans lequel on choisit les mots
	 */
    private Dictionnaire dict;


	/**
	 * constructeur dans lequel on impose le mot à trouver
	 * @param motATrouve mot à trouver
	 * @param niveau niveau du jeu
	 * @param nbErreursMax le nombre total d'essais autorisés
	 */
    public MotMystere(String motATrouve, int niveau, int nbErreursMax) {
	super();
	initMotMystere(motATrouve, niveau, nbErreursMax);
    }

	/**
	 * Constructeur dans lequel on va initialiser un dictionnaire pour choisir les mots à trouver
	 * @param nomFicDico nom du fichier de dictionnaire
	 * @param longMin longueur minimale des mots retenus dans le dictionnaire
	 * @param longMax longueur maximale des mots retenus dans le dictionnaire
	 * @param niveau niveau initial de jeu
	 * @param nbErreursMax le nombre total d'essais autorisés
	 */
    public MotMystere(String nomFicDico, int longMin, int longMax, int niveau, int nbErreursMax) {
    	super();
    	this.dict=new Dictionnaire(nomFicDico,longMin,longMax);
    	String motATrouve=dict.choisirMot();
    	initMotMystere(motATrouve, niveau, nbErreursMax);
        }

	/**
	 * initialisation du jeu
	 * @param motATrouver le mot à trouver
	 * @param niveau le niveau de jeu
	 * @param nbErreursMax  le nombre total d'essais autorisés
	 */
    private void initMotMystere(String motATrouver, int niveau, int nbErreursMax){
    this.niveau =niveau;
	this.nbEssais=0;
	this.motATrouver = Dictionnaire.sansAccents(motATrouver).toUpperCase();
	this.lettresTrouvees=new String();
	this.lettresEssayees=new String();

	nbLettresRestantes=0;
	if (niveau==MotMystere.AUCUNE || niveau==MotMystere.TIRET){
	    lettresTrouvees="*";
	    this.nbLettresRestantes+=1;
	}
	else
	    lettresTrouvees+=this.motATrouver.charAt(0);
		
	for (int i=1;i<motATrouver.length()-1;i++){
	    char l=this.motATrouver.charAt(i);
	    if (this.niveau ==MotMystere.AUCUNE || Character.isAlphabetic(l)){
		lettresTrouvees+="*";
		this.nbLettresRestantes+=1;
	    }	
	    else
		lettresTrouvees+=l;
	}
	if (niveau!=MotMystere.PREMIEREETDERNIERE){
	    lettresTrouvees+="*";
	    this.nbLettresRestantes+=1;
	}
	else
	    lettresTrouvees+=this.motATrouver.charAt(motATrouver.length()-1);
	this.nbEerreursMax=this.nbErreursRestantes=nbErreursMax;
    }

	/**
	 * @return le mot à trouver
	 */
    public String getMotATrouve() {
	return motATrouver;
    }

	/**
	 * @return le niveau de jeu
	 */
    public int getNiveau(){
    	return niveau;
    }

	/** réinitialise le jeu avec un nouveau à trouver
	 * @param motATrouver le nouveau mot à trouver
	 */
    public void setMotATrouver(String motATrouver) {
    	initMotMystere(motATrouver, this.niveau, this.nbEerreursMax);
    }

	/**
	 * Réinitialise le jeu avec un nouveau mot à trouver choisi au hasard dans le dictionnaire
	 */
    public void setMotATrouver() {
    	initMotMystere(dict.choisirMot(), this.niveau, this.nbEerreursMax);
    }

	/**
	 * change le niveau de jeu (n'a pas d'effet en cours de partie)
	 * @param niveau le nouveau niveu de jeu
	 */
    public void setNiveau(int niveau){
    	this.niveau = niveau;
    }

	/**
	 * @return le mot avec les lettres trouvée affichée (et des étoiles pour les autres)
	 */
    public String getLettresTrouvees() {
	return lettresTrouvees;
    }

	/**
	 * @return les lettres déjà essayées
	 */
    public String getLettresEssayees() { return this.lettresEssayees;}

	/**
	 * @return le nombre de lettres restant à trouver
	 */
    public int getNbLettresRestantes() {
	return nbLettresRestantes;
    }

	/**
	 * @return le nombre d'essais déjà effectués
	 */
    public int getNbEssais(){
	return nbEssais;
    }

	/**
	 * @return le nombre total de tentatives autorisées
	 */
    public int getNbErreursMax(){
	return nbEerreursMax;
    }

	/**
	 * @return le nombre d'erreurs encore autorisées
	 */
    public int getNbErreursRestants(){
	return nbErreursRestantes;
    }

	/**
	 * @return un booléen indiquant si le joueur a perdu
	 */
    public boolean perdu(){
	return nbErreursRestantes==0;
    }

	/**
	 * @return un booléen indiquant si le joueur a gangé
	 */
    public boolean gagne(){
	return nbLettresRestantes==0;
    }

	/**
	 * permet au joueur d'essayer une lettre
	 * @param lettre la lettre essayée par le joueur
	 * @return le nombre de fois où la lettre apparait dans le mot à trouver
	 */
    public int essaiLettre(char lettre){
	int nbNouvelles=0;
	char[] aux = this.lettresTrouvees.toCharArray();
	for (int i=0;i<this.motATrouver.length();i++){
	    if (this.motATrouver.charAt(i)==lettre && this.lettresTrouvees.charAt(i)=='*'){
		nbNouvelles+=1;
		aux[i]=lettre;
	    }
	}
	this.lettresTrouvees=String.valueOf(aux);
	this.nbLettresRestantes-=nbNouvelles;
	if (! this.lettresEssayees.contains(""+lettre))
		this.lettresEssayees+=""+lettre;
	this.nbEssais+=1;
	if (nbNouvelles==0)
		this.nbErreursRestantes-=1;
	return nbNouvelles;
    }

	/**
	 * @return une chaine de caractère donnant l'état du jeu
	 */
    public String toString(){
		return "Mot a trouve: "+this.motATrouver+" Lettres trouvees: "+
			   this.lettresTrouvees+" nombre de lettres restantes "+this.nbLettresRestantes+
			   " nombre d'essais restents: "+this.nbErreursRestantes;
    }

}
