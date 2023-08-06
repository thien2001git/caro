package model;

public class PlayerHuman implements Player {
	BoardState boardState;
	int playerFlag = 1;

	public PlayerHuman(BoardState board) {
		this.boardState = board;
	}

	@Override
	public Point movePoint(int player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPlayerFlag() {
		// TODO Auto-generated method stub
		return playerFlag;
	}

	@Override
	public void setPlayerFlag(int playerFlag) {
		this.playerFlag = playerFlag;
	}

	@Override
	public BoardState getBoardState() {
		// TODO Auto-generated method stub
		return boardState;
	}

}
