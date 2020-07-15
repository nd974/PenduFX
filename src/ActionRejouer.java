import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javax.swing.*;
import java.util.Optional;

/**
 * Contrôleur à activer lorsque l'on clique sur le bouton rejouer
 */
public class ActionRejouer implements EventHandler<ActionEvent> {
	/**
	 * modèle du jeu
	 */
	private MotMystere m;
	/*
	 * vue du jeu
	 **/
	private Pendu p;

	/**
	 * @param m modèle du jeu
	 * @param p vue du jeu
	 */
	public ActionRejouer(MotMystere m, Pendu p) {
	    this.m = m;
		this.p = p;
	}


	/**
	 * L'action consiste à recommencer une partie. Il faut vérifier qu'il n'y avait pas une partie en cours
	 * @param actionEvent l'événement action
	 */
	@Override
	public void handle(ActionEvent actionEvent) {
		Alert alert = new Alert(Alert.AlertType.NONE);
		alert.setHeaderText("Rejouer une partie");
		alert.setContentText("Si une partie est en cours, elle sera cloturée.");
		ButtonType bVal = new ButtonType("Valider");
		ButtonType bAnn = new ButtonType("Annuler");
		alert.getButtonTypes().addAll(bVal, bAnn);
		alert.showAndWait();
		if (alert.getResult().getText().endsWith("Valider")) {
			m.setMotATrouver();
			p.getChrono().resetTime();
			p.getChrono().start();
			p.majAffichage();
		}
	}
}
