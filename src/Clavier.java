import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;

/**
 * Génère la vue d'un clavier et associe le contrôleur aux touches
 * le choix ici est d'un faire un héritié d'un GridPane
 */
public class Clavier extends GridPane{
    /**
     * il est conseillé de stocker les touches dans un ArrayList
     */
    private Map<String, Button> buttonMap;

    /**
     * constructeur du clavier
     * @param touches une chaine de caractères qui contient les lettres à mettre sur les touches
     * @param actionTouches le contrôleur des touches
     * @param tailleLigne nombre de touches par ligne
     */
    public Clavier(String touches, EventHandler<ActionEvent> actionTouches, int tailleLigne) {
        super();
        buttonMap = new HashMap<>();
        this.setHgap(1);
        this.setVgap(1);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10));
        for ( int i = 0; i < touches.length() ; i++) {
            Button but = new Button(touches.charAt(i) + "");
            but.setOnAction(actionTouches);
            but.setPrefWidth(50);
            but.setStyle("-fx-background-color:gold;");
            if (touches.charAt(i)=='-')
            {
                but.setDisable(true);
            }
            this.add(but, i%tailleLigne+1, i/tailleLigne);
            this.buttonMap.put((but.getText()), but);
        }
    }

    /**
     * permet de désactiver certaines touches du clavier (et active les autres)
     * @param touchesDesactivees une chaine de caractères contenant la liste des touches désactivées
     */
    public void desactiveTouches(String touchesDesactivees){
        if(touchesDesactivees.equals("")){
            System.out.println(this.buttonMap.values());
            for(Button b : this.buttonMap.values()){
                b.setDisable(true);
                b.setStyle("-fx-background-color:gray;");
            }
        }
        else{
            if(touchesDesactivees.length() == 1){
                this.buttonMap.get(touchesDesactivees).setDisable(true);
                this.buttonMap.get(touchesDesactivees).setStyle("-fx-background-color:red;");
            }
            else{
                for(Button b : this.buttonMap.values()) {
                    if(touchesDesactivees.contains(b.getText())){
                        b.setDisable(true);
                        b.setStyle("-fx-background-color:blue;");
                    }
                }
            }
        }
    }
}

