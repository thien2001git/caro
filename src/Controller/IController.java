package Controller;

import javax.swing.JButton;

import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import model.BoardState;
import model.Player;
import model.Point;
import view.Swing;
import view.View;

public interface IController {

	public void setPlayerFlag(int playerFlag);

	public int getPlayerFlag();

	public Point AI(int player);

	int checkEnd(int x, int y);

	BoardState getBoardState();

	void play(JButton c, JButton[][] a);

	void setSwing(Swing swing);

	public void setPlayer(Player player);

	void play(Button c, Button[][] a);

	boolean isEnd();

	void setView(View view);

	void setEnd(boolean end);

	void undo(Button[][] a);

	void save();

	void reset(Button[][] a);

	void open(Button[][] a);

	public void setTimePlayer(Labeled timePlayer1, Labeled timePlayer2);

	void runTimer(int player);

}
