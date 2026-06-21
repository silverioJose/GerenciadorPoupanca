package application;

import javafx.animation.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.util.Duration;

public class Animacao {
	
	public static void abrirPopup(AnchorPane pane, javafx.scene.layout.VBox vbox) {
		pane.setVisible(true);
		
		FadeTransition fade = new FadeTransition(Duration.millis(200), pane);
		fade.setFromValue(0);
		fade.setToValue(1);
		
		vbox.setScaleX(0.8);
		vbox.setScaleY(0.8);
		ScaleTransition scale = new ScaleTransition(Duration.millis(200), vbox);
		scale.setFromX(0.8);
		scale.setFromY(0.8);
		scale.setToX(1.0);
		scale.setToY(1.0);
		scale.setInterpolator(Interpolator.EASE_OUT);
		
		fade.play();
		scale.play();
	}
	
	public static void fecharPopup(AnchorPane pane) {
		FadeTransition fade = new FadeTransition(Duration.millis(50), pane);
		fade.setFromValue(1);
		fade.setToValue(0);
		fade.setOnFinished(e -> pane.setVisible(false));
		fade.play();
	}
	
	public static void shake(Node node) {
		TranslateTransition shake = new TranslateTransition(Duration.millis(50), node);
		shake.setFromX(0);
		shake.setByX(10);
		shake.setCycleCount(6);
		shake.setAutoReverse(true);
		shake.setOnFinished(e -> node.setTranslateX(0));
		shake.play();
		
	}
	
	public static void slide(Node card, boolean paraDireita, Runnable aoTerminar) {
		double largura = 400;
		double direcao = paraDireita ? largura : -largura;
		
		TranslateTransition saida = new TranslateTransition(Duration.millis(150), card);
		saida.setFromX(0);
		saida.setToX(-direcao);
		
		saida.setOnFinished(e -> {
			aoTerminar.run();
			
			card.setTranslateX(direcao);
			TranslateTransition entrada = new TranslateTransition(Duration.millis(150), card);
			entrada.setFromX(direcao);
			entrada.setToX(0);
			entrada.setInterpolator(Interpolator.EASE_OUT);
			entrada.play();
		});
		saida.setInterpolator(Interpolator.EASE_IN);
		saida.play();
	}
}