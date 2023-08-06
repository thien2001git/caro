package application;

import java.awt.event.WindowEvent;

import javafx.application.Application;
import javafx.stage.Stage;
import view.View;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			View view = new View();
			view.start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void handle(WindowEvent event) {
		System.exit(0);
	}

}
