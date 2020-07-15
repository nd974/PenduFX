import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;
import java.util.ArrayList;

/**
 * Vue du jeu du pendu
 */
public class Pendu extends Application {
    /*
     * modèle du jeu
     **/
    private MotMystere m;
    /**
     * tableau qui contient les images du jeu
     */
    private ArrayList<Image> lesImages;
    /**
     *les différents contrôles qui seront mis à jour
     * ou consultés pour l'affichage
     */
    /**
     * le dessin
     */
    private ImageView dessin;
    /**
     * le mot à trouver avec les lettres déjà trouvé
     */
    private Label motCrypte;
    /**
     * la barre de progression qui indique le nombre de tentatives
     */
    private ProgressBar pg;
    /**
     * le clavier qui sera géré par une classe à implémenter
     */
    private Clavier clavier;
    /**
     * le chronomètre qui sera géré par une clasee à implémenter
     */
    private Chronometre chrono;
    /**
     * les radio boutons gérant les niveaux
     */
    ArrayList<RadioButton> niveaux;


	/**
	 * @return le panel du chronomètre
	 */
    private VBox leChrono(){
    	VBox res = new VBox(5);
    	this.chrono = new Chronometre();
        res.getChildren().add(this.chrono);
        res.setAlignment(Pos.CENTER);
        res.setPrefWidth(Donnee.largeur/7);
    	return res;
	}


    /**
     * @return le panel des niveaux avec les radio boutons
     */
    private VBox lesNiveaux(){
    	VBox res=new VBox(5);
        Label labelNiveau = new Label("Niveau");

        RadioButton radiobutFacile = new RadioButton("Facile");
        RadioButton radiobutMoyen = new RadioButton("Moyen");
        RadioButton radiobutDifficile = new RadioButton("Difficile");
        RadioButton radiobutExpert = new RadioButton("Expert");

        radiobutFacile.setSelected(true);

        radiobutFacile.setOnAction(new ItemNiveau(this.m, this));
        radiobutMoyen.setOnAction(new ItemNiveau(this.m, this));
        radiobutDifficile.setOnAction(new ItemNiveau(this.m, this));
        radiobutExpert.setOnAction(new ItemNiveau(this.m, this));

        ToggleGroup togglegroup = new ToggleGroup();
        radiobutFacile.setToggleGroup(togglegroup);
        radiobutMoyen.setToggleGroup(togglegroup);
        radiobutDifficile.setToggleGroup(togglegroup);
        radiobutExpert.setToggleGroup(togglegroup);
        res.getChildren().addAll(labelNiveau, radiobutFacile, radiobutMoyen, radiobutDifficile, radiobutExpert);

        res.setAlignment(Pos.CENTER);
        res.setPrefWidth(Donnee.largeur/7);
    	return res;
    }


    /**
     * @return le panel central avec le mot crypté, l'image, la barre
     *         de progression et le bouton rejouer
     */
    private VBox central(){
    	VBox res=new VBox(5);

        this.pg = new ProgressBar(0);
        this.dessin = new ImageView(this.lesImages.get(0));
        this.motCrypte = new Label(this.m.getLettresTrouvees());
        this.motCrypte.setFont(new Font(20));

        Button boutonRejouer = new Button("Rejouer");
        boutonRejouer.setOnAction(new ActionRejouer(this.m, this));

        res.getChildren().addAll(this.motCrypte, this.dessin, this.pg, boutonRejouer);
        res.setAlignment(Pos.CENTER);

        res.setStyle("-fx-background-color:lightpink;");

    	return res;
	}

    /**
     * @return le panel contenant le titre du jeu
     */
	private FlowPane titre(){
	    FlowPane res = new FlowPane();
	    res.setAlignment(Pos.CENTER);
	    Label titre =new Label("Jeu du pendu");
	    titre.setFont(new Font(25));
        res.getChildren().add(titre);
        res.setPrefHeight(50);
	    return res;
	}

    /**
     * @return  le clavier avec les 27 caractères et le controleur des touches
     */
	private Clavier leClavier(){
	    return new Clavier("ABCDEFGHIJKLMNOPQRSTUVWXYZ-", new ActionLettre(this.m, this), 9);
       }

    /**
     * @return  le graphe de scène de la vue à partir de methodes précédantes
     */
	private Scene laScene(){
	    BorderPane cont = new BorderPane();
	    cont.setTop(this.titre());
	    cont.setRight(this.lesNiveaux());
	    cont.setCenter(this.central());
	    cont.setBottom(this.leClavier());
	    cont.setLeft(leChrono());
	    cont.setStyle("-fx-background-color:aqua;");
	    return new Scene(cont,Donnee.largeur,Donnee.hauteur);
	}


    /**
     * charge les images à afficher en fonction des erreurs
     * @param repertoire répertoire où se trouvent les images
     */
	private void chargerImages(String repertoire){
		this.lesImages= new ArrayList<Image>();
		for (int i=0;i<m.getNbErreursMax()+1;i++){
		    File file = new File(repertoire+"/pendu"+i+".png");
		    System.out.println(file.toURI().toString());
			this.lesImages.add(new Image(file.toURI().toString()));
		}
	}

    /**
     * Affiche en clair le mot à trouver
     */
	public void afficheReponse(){
        System.out.println(this.m);
	}

    /**
     * raffraichit l'affichage en fonction du modèle
     */
	public void majAffichage(){
	    this.dessin.setImage(this.lesImages.get(m.getNbErreursMax() - m.getNbErreursRestants()));
   		this.motCrypte.setText(m.getLettresTrouvees());
        this.pg.setProgress(((double)this.m.getNbErreursMax()-(double)this.m.getNbErreursRestants())/(double)this.m.getNbErreursMax());
   		if (m.perdu())
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("PERDU");
            alert.setContentText("BIEN JOUE VOUS AVEZ PERDU\nVous pouvez essiayez le niveau d'en dessous");
            alert.show();
            this.clavier.desactiveTouches("ABCDEFGHIJKLMNOPQRSTUVWXYZ-");
            this.chrono.stop();

   		}
   		if (m.gagne())
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("GAGNE");
            alert.setContentText("BIEN JOUE VOUS AVEZ GAGNE\nVous pouvez essiayez le niveau d'en dessus");
            alert.show();
            this.clavier.desactiveTouches("ABCDEFGHIJKLMNOPQRSTUVWXYZ-");
            this.chrono.stop();
        }
	}

    /**
     * accesseur du chronomètre (pour les controleur du jeu)
     * @return le chronomètre du jeu
     */
	public Chronometre getChrono(){
	    return this.chrono;
	}

    public Clavier getClavier(){
        return this.clavier;
    }

    public void activerNiveau(boolean actif) {
        // A implémenter
    }

    /**
     * Crée le modèle, charge les images, créer le graphe de scène et lance le jeu
     * @param stage la fenêtre principale
     */
	@Override
	public void start(Stage stage) {
	    // création du modèle
	    m=new MotMystere("/home/nicolas/IdeaProjects/Pendu/share/dict/french",3,10,MotMystere.PREMIEREETDERNIERE,10);
	    this.clavier = leClavier();
	    // Chargement des images
	    this.chargerImages("./img");
	    stage.setTitle("Jeu du pendu");
	    stage.setScene(this.laScene());
	    stage.show();
	    // démarrage du chrono
	    this.chrono.start();

//	    afficheReponse(); TODO afficher si le user a perdu
	}

    /**
     * Programme principal
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    }

}
