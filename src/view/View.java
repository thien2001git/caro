package view;

import java.io.InputStream;

import Controller.Controller;
import Controller.IController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.BoardState;
import model.Caro;
import model.Player;
import model.PlayerHuman;

public class View implements EventHandler<ActionEvent> {
	static final int W = 20;
	static final int H = 20;
	IController controller;
	public Button[][] a;
	public static Stage primaryStage;

	public View() {
	}

	public void start(Stage primaryStage) {
		try {
			View.primaryStage = primaryStage;
			a = new Button[W][H];
			BoardState boardState = new BoardState(W, H);
			controller = new Controller();
			controller.setView(this);
			Player player = new PlayerHuman(boardState);
			Player computer = new Caro(boardState);
			controller.setPlayer(computer);

			BorderPane borderPane = new BorderPane();
			BorderPane borderPane1 = new BorderPane();
			menu(borderPane1);
			AnchorPane anchorPane = new AnchorPane();
			GridPane root = new GridPane();
			// anchorPane.setTopAnchor(root, 10.0);
			// anchorPane.setLeftAnchor(root, 10.0);
			// anchorPane.setRightAnchor(root, 10.0);
			// anchorPane.setBottomAnchor(root, 10.0);
			// anchorPane.getChildren().add(root);
			// anchorPane.setId("panR");
			Scene scene = new Scene(borderPane, 1200, 700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			borderPane.setPadding(new Insets(20));
			borderPane.setCenter(root);
			borderPane.setRight(borderPane1);
			controller.setPlayerFlag(1);
			controller.setTimePlayer(timePlayer1, timePlayer2);
			for (int i = 0; i < W; i++) {
				for (int j = 0; j < H; j++) {
					Button button = new Button();
					button.setPrefSize(40, 40);
					button.setAccessibleText(i + ";" + j);
					a[i][j] = button;
					root.add(button, j, i);
					button.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							if (!controller.isEnd()) {
//								controller.runTimer(controller.getPlayerFlag());
								controller.play(button, a);

							}
						}
					});
				}
			}
			// a[0][0].setStyle("-fx-background-color: linear-gradient(to
			// bottom, #f2f2f2, #d4d4d4);");
			primaryStage.setScene(scene);
			primaryStage.setTitle("Game caro");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	Button btnNguoi;
	Button btnCom;
	Button btnEx;
	Button btnUndo;
	Button btnLoad;
	Button btnSave;

	private void menu(BorderPane pane) {
		VBox box = new VBox();
		box.setSpacing(10);
		Class<?> clazz = this.getClass();
		AnchorPane anchorPane1 = new AnchorPane();
		InputStream input = clazz.getResourceAsStream("/icon/caroo.png");
		Image image = new Image(input);
		ImageView view = new ImageView(image);
		// box.getChildren().add(view);
		btnNguoi = new Button("Người vs Người");
		btnNguoi.setId("btnNguoi");
		btnNguoi.setOnAction(this);
		btnCom = new Button("Người vs Máy");
		btnCom.setId("btnNguoi");
		btnCom.setOnAction(this);
		Label label = new Label("Nguyễn Văn Trọng");
		label.setPrefHeight(30);
		//
		AnchorPane.setTopAnchor(view, 10.0);
		AnchorPane.setLeftAnchor(view, 30.0);
		AnchorPane.setRightAnchor(view, 30.0);
		//
		AnchorPane anchorPane = new AnchorPane();
		AnchorPane.setTopAnchor(btnNguoi, 10.0);
		AnchorPane.setLeftAnchor(btnNguoi, 30.0);
		AnchorPane.setRightAnchor(btnNguoi, 30.0);
		// may
		AnchorPane.setTopAnchor(btnCom, 50.0);
		AnchorPane.setLeftAnchor(btnCom, 30.0);
		AnchorPane.setRightAnchor(btnCom, 30.0);

		// undo
		btnUndo = new Button("Quay lại");
		btnUndo.setId("btnNguoi");
		btnUndo.setOnAction(this);
		AnchorPane.setTopAnchor(btnUndo, 90.0);
		AnchorPane.setLeftAnchor(btnUndo, 30.0);
		AnchorPane.setRightAnchor(btnUndo, 30.0);

		GridPane pane2 = new GridPane();
		btnSave = new Button("Lưu");
		btnSave.setMinSize(130, 40);
		btnSave.setOnAction(this);
		btnSave.setId("btnNguoi");
		btnLoad = new Button("Load");
		btnLoad.setMinSize(130, 40);
		btnLoad.setId("btnNguoi");
		btnLoad.setOnAction(this);
		pane2.add(btnSave, 0, 0);
		pane2.add(btnLoad, 1, 0);
		pane2.setHgap(5.0);
		pane2.setVgap(5.0);
		//
		Button btnOp = new Button("Cài đặt");
		btnOp.setMinSize(130, 40);
		btnOp.setId("btnNguoi");
		Button btnAbout = new Button("Thông tin");
		btnAbout.setMinSize(130, 40);
		btnAbout.setId("btnNguoi");
		pane2.add(btnOp, 0, 2);
		pane2.add(btnAbout, 1, 2);
		//
		AnchorPane.setTopAnchor(pane2, 130.0);
		AnchorPane.setLeftAnchor(pane2, 30.0);
		AnchorPane.setRightAnchor(pane2, 30.0);
		// ex
		btnEx = new Button("Thoát");
		btnEx.setId("btnNguoi");
		btnEx.setOnAction(this);
		AnchorPane.setTopAnchor(btnEx, 210.0);
		AnchorPane.setLeftAnchor(btnEx, 30.0);
		AnchorPane.setRightAnchor(btnEx, 30.0);

		anchorPane1.getChildren().add(view);
		anchorPane.getChildren().addAll(btnNguoi, btnCom, btnUndo, pane2, btnEx);
		box.getChildren().add(anchorPane1);
		box.getChildren().add(anchorPane);

		// Bottom
		// AnchorPane panelBottom = new AnchorPane();
		GridPane gridPaneBottom = new GridPane();
		namePlayer1 = new Label("Player 1");
		namePlayer1.setId("nameplayer");
		Labeled namePlayer2 = new Label("Player 2");
		namePlayer2.setId("nameplayer");
		gridPaneBottom.add(namePlayer1, 0, 0);
		gridPaneBottom.add(namePlayer2, 1, 0);
		box.getChildren().add(gridPaneBottom);
		//
		GridPane gridPaneBottom1 = new GridPane();
		timePlayer1 = new Label("30");
		timePlayer1.setId("timeplayer");
		timePlayer2 = new Label("30");
		timePlayer2.setId("timeplayer");
		gridPaneBottom1.add(timePlayer1, 0, 0);
		gridPaneBottom1.add(timePlayer2, 1, 0);
		box.getChildren().add(gridPaneBottom1);
		//
		pane.setCenter(box);

	}

	Label namePlayer1;
	Labeled timePlayer1, timePlayer2;

	@Override
	public void handle(ActionEvent e) {
		if (e.getSource() == btnEx) {
			primaryStage.close();
		}
		if (e.getSource() == btnNguoi) {
			controller.setEnd(false);
			controller.setTimePlayer(timePlayer1, timePlayer2);
			controller.setPlayer(new PlayerHuman(new BoardState(W, H)));
			controller.reset(a);
		}
		if (e.getSource() == btnCom) {
			controller.setEnd(false);
			controller.setTimePlayer(timePlayer1, timePlayer2);
			controller.setPlayer(new Caro(new BoardState(W, H)));
			controller.setPlayerFlag(1);
			controller.reset(a);
		}
		if (e.getSource() == btnUndo) {
			controller.undo(a);
		}
		if (e.getSource() == btnLoad) {
			controller.open(a);
		}
		if (e.getSource() == btnSave) {
			controller.save();
		}
	}

	public void replayComputer() {
		controller.setEnd(false);
		controller.setPlayer(new Caro(new BoardState(W, H)));
		controller.setPlayerFlag(1);
		controller.reset(a);
	}

	public void replayHuman() {
		controller.setEnd(false);
		controller.setPlayer(new PlayerHuman(new BoardState(W, H)));
		controller.setPlayerFlag(1);
		controller.reset(a);

	}

}
