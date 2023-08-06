package Controller;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import model.BoardState;
import model.Caro;
import model.Player;
import model.PlayerHuman;
import model.Point;
import model.TaskTimer;
import view.Swing;
import view.View;

public class Controller implements IController {
	Swing swing;
	public View view;
	Player player;
	int x1 = 0, y1 = 0;
	Stack<Point> stack = new Stack<>();

	public Controller() {

	}

	@Override
	public Point AI(int player) {
		return this.player.movePoint(player);
	}

	@Override
	public int getPlayerFlag() {
		return player.getPlayerFlag();
	}

	@Override
	public void setPlayerFlag(int playerFlag) {
		player.setPlayerFlag(playerFlag);
	}

	@Override
	public BoardState getBoardState() {
		return player.getBoardState();
	}

	@Override
	public int checkEnd(int x, int y) {
		return player.getBoardState().checkEnd(x, y);
	}

	@Override
	public void play(JButton c, JButton[][] a) {
		StringTokenizer tokenizer = new StringTokenizer(c.getActionCommand(), ";");
		int x = Integer.parseInt(tokenizer.nextToken());
		int y = Integer.parseInt(tokenizer.nextToken());
		if (player instanceof PlayerHuman) {
			if (getPlayerFlag() == 1) {
				getBoardState().setPosition(x, y, 1);
				a[x][y].setText("X");
				setPlayerFlag(2);
				if (getBoardState().checkEnd(x, y) == 1) {
					JOptionPane.showMessageDialog(swing, "Thang rau!");
					return;
				}
			} else {
				if (getPlayerFlag() == 2) {
					getBoardState().setPosition(x, y, 2);
					a[x][y].setText("O");
					a[x][y].setForeground(Color.BLUE);
					setPlayerFlag(1);
					if (getBoardState().checkEnd(x, y) == 2) {
						JOptionPane.showMessageDialog(swing, "Thua rau!");
						return;
					}
				}
			}

		} else {

			if (getPlayerFlag() == 1) {
				if (getBoardState().getPosition(x, y) == 0) {
					getBoardState().setPosition(x, y, 1);
					a[x][y].setText("X");
					if (getBoardState().checkEnd(x, y) == 1) {
						JOptionPane.showMessageDialog(swing, "Thang rau!");
						return;
					}

					// May di

					Point p = AI(2);
					getBoardState().setPosition(p.x, p.y, 2);
					a[p.x][p.y].setText("O");
					if (getBoardState().checkEnd(p.x, p.y) == 2) {
						JOptionPane.showMessageDialog(swing, "Thua rau!");
						return;
					}
				}
			}

		}

	}

	Class<?> clazz = this.getClass();
	InputStream o = clazz.getResourceAsStream("/icon/o.png");
	InputStream x = clazz.getResourceAsStream("/icon/x.png");
	Image imageO = new Image(o);
	Image imageX = new Image(x);
	boolean end = false;

	@Override
	public boolean isEnd() {
		return end;
	}

	@Override
	public void play(Button c, Button[][] a) {
		StringTokenizer tokenizer = new StringTokenizer(c.getAccessibleText(), ";");
		int x = Integer.parseInt(tokenizer.nextToken());
		int y = Integer.parseInt(tokenizer.nextToken());
		if (player instanceof PlayerHuman) {
			if (getPlayerFlag() == 1 && getBoardState().boardArr[x][y] == 0) {
				danhCo(x, y, 1, a);
				setPlayerFlag(2);
			} else {
				if (getPlayerFlag() == 2 && getBoardState().boardArr[x][y] == 0) {
					danhCo(x, y, 2, a);
					setPlayerFlag(1);
				}
			}

		} else {
			if (getPlayerFlag() == 1) {
				if (getBoardState().getPosition(x, y) == 0) {
					danhCo(x, y, 1, a);
					// May di
					if (!end) {
						Point p = AI(2);
						danhCo(p.x, p.y, 2, a);
					}
				}
			}

		}
		if (end) {
			if (player instanceof Caro && playerWin.equals("2")) {
				playerWin = "Computer";
			}
			// JOptionPane.showMessageDialog(swing, "Player " + playerWin + "
			// win!");
			timer1.cancel();
			timer2.cancel();
			
			dialog("Player " + playerWin + " win!");
			return;
		}
		runTimer(getPlayerFlag());
	}

	int tongNuocDi = 0;
	String playerWin = "";

	private void danhCo(int x, int y, int player, Button[][] a) {
		a[x1][y1].setStyle("-fx-background-color: rgb(87.0,163.0,3.0);");
		getBoardState().setPosition(x, y, player);
		x1 = x;
		y1 = y;
		if (player == 1) {
			Point point = new Point(x, y);
			point.setPlayer(1);
			stack.push(point);
			a[x][y].setGraphic(new ImageView(imageX));
			tongNuocDi++;
		} else {
			Point point = new Point(x, y);
			point.setPlayer(2);
			stack.push(point);
			a[x][y].setGraphic(new ImageView(imageO));
			a[x][y].setStyle(
					"-fx-background-color: linear-gradient( from 0.0% 0.0% to 100.0% 100.0%, rgb(77,128,77) 0.0, rgb(255,255,77) 100.0);");
			tongNuocDi++;
		}
		if (getBoardState().checkEnd(x, y) == player) {
			playerWin = player + "";
			end = true;
		}
		if (tongNuocDi == (getBoardState().height * getBoardState().width)) {
			playerWin = 2 + "";
			end = true;
		}

	}

	void print(int[][] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				System.out.print(a[i][j]);
			}
			System.out.println();
		}
	}

	@Override
	public void save() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Lưu bàn cờ");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("DATA", "*.dat"),
				new FileChooser.ExtensionFilter("All Images", "*.*"));
		File file = fileChooser.showSaveDialog(View.primaryStage);
		if (file != null) {
			ghiFile(file);
		}
	}

	public void ghiFile(File file) {
		try {
			PrintStream printStream = new PrintStream(file);
			while (!stack.isEmpty()) {
				printStream.println(stack.pop().toString());
			}
			printStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void open(Button[][] a) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Mở bàn cờ");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("DATA", "*.dat"),
				new FileChooser.ExtensionFilter("All Images", "*.*"));
		File file = fileChooser.showOpenDialog(View.primaryStage);
		if (file != null) {
			load(file);
			reset(a);

			stack = new Stack<>();
			while (!queue.isEmpty()) {
				Point point = queue.poll();
				stack.push(point);
				danhCo(point.x, point.y, point.player, a);
			}

		}

	}

	Queue<Point> queue;

	public boolean load(File file) {
		if (file != null) {
			queue = new LinkedList<>();
			try {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
				String dong;
				while ((dong = bufferedReader.readLine()) != null) {
					StringTokenizer stringTokenizer = new StringTokenizer(dong, ";");
					int x = Integer.parseInt(stringTokenizer.nextToken());
					int y = Integer.parseInt(stringTokenizer.nextToken());
					int player = Integer.parseInt(stringTokenizer.nextToken());
					Point point = new Point(x, y);
					point.setPlayer(player);
					queue.add(point);
				}
				bufferedReader.close();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	@Override
	public void undo(Button[][] a) {
		if (!stack.isEmpty()) {
			tongNuocDi--;
			Point point = stack.pop();
			getBoardState().boardArr[point.x][point.y] = 0;
			a[point.x][point.y].setGraphic(null);
			a[point.x][point.y].setStyle("-fx-background-color: rgb(87.0,163.0,3.0);");

		}
	}

	@Override
	public void setSwing(Swing swing) {
		this.swing = swing;
	}

	@Override
	public void setPlayer(Player player) {
		this.player = player;
	}

	public EventHandler<ActionEvent> action(String action) {
		return null;
	}

	EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {

		}
	};

	public void dialog(String title) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Trò chơi kết thúc");
		alert.setHeaderText(title);
		alert.setContentText("Bạn có muốn chơi lại?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			if (getPlayer() instanceof PlayerHuman) {
				view.replayHuman();
			} else {
				view.replayComputer();
			}
		} else {
			// ... user chose CANCEL or closed the dialog
		}
	}

	@Override
	public void setView(View view) {
		this.view = view;
	}

	@Override
	public void setEnd(boolean end) {
		this.end = end;
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public void reset(Button[][] a) {
		tongNuocDi = 0;
		timer1.cancel();
		timer2.cancel();
		timePlayer1.setText("30");
		timePlayer2.setText("30");
		getBoardState().resetBoard();
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j].setGraphic(null);
				a[i][j].setStyle("-fx-background-color: rgb(87.0,163.0,3.0);");
			}
		}
	}

	Labeled timePlayer1, timePlayer2;

	@Override
	public void setTimePlayer(Labeled timePlayer1, Labeled timePlayer2) {
		this.timePlayer1 = timePlayer1;
		this.timePlayer2 = timePlayer2;
	}
	Timer timer1 = new Timer();
	Timer timer2 = new Timer();
	@Override
	public void runTimer(int player) {
		if(end){
			timer1.cancel();
			timer2.cancel();
		}else{
			timer1.cancel();
			timer2.cancel();
			TaskTimer task1 = new TaskTimer(timePlayer1);
			TaskTimer task2 = new TaskTimer(timePlayer2);
			task1.setController(this);
			task2.setController(this);
			if (player == 1) {
				timer2.cancel();
				timer1 = new Timer();
				timer1.schedule(task1, 0, 1000);
			} else {
				timer1.cancel();
				timer2 = new Timer();
				timer2.schedule(task2, 0, 1000);
			}
		}
	}
}
