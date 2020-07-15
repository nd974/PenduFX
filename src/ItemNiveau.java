import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;

/**
 * Controleur des radio boutons gérant le niveau
 */
public class ItemNiveau implements EventHandler<ActionEvent> {

	/**
	 * modèle du jeu
	 */
	private MotMystere m;
	/**
	 * vue du jeu
	 */
	private Pendu p;

	/**
	 * @param m modèle du jeu
	 * @param p vue du jeu
	 */
	public ItemNiveau(MotMystere m, Pendu p) {
		this.m = m;
		this.p = p;
	}

	/**
	 * affiche message de confirmation
	 * @param actionEvent
	 */
	@Override
	public void handle(ActionEvent actionEvent) {
		RadioButton b = (RadioButton) actionEvent.getSource();
		Alert alert = new Alert(Alert.AlertType.NONE);
		alert.setHeaderText("Changement de niveau");
		alert.setContentText("Si une partie est en cours, elle sera cloturée.");
		ButtonType bVal = new ButtonType("Valider");
		ButtonType bAnn = new ButtonType("Annuler");
		alert.getButtonTypes().addAll(bVal, bAnn);
		alert.showAndWait();
		if (alert.getResult().getText().endsWith("Valider"))
		{
			changeEtReset(b);
		}
	}

	/**
	 * gère le changement de niveau
	 * @param b
	 */
	public void changeEtReset(RadioButton b) {
		if (b.getText() == "Facile") {
			this.m.setNiveau(0);
		}
		if (b.getText() == "Medium") {
			this.m.setNiveau(1);
		}
		if (b.getText() == "Difficile") {
			this.m.setNiveau(2);
		}
		if (b.getText() == "Expert") {
			this.m.setNiveau(3);
		}
		this.m.setMotATrouver();
		this.p.getClavier().desactiveTouches("");
		this.p.getChrono().resetTime();
		this.p.getChrono().start();
		this.p.majAffichage();
	}
}

