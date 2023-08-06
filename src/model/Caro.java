package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Caro implements Player {
	EvalBoard eBoard;
	BoardState boardState;
	int playerFlag = 2; //
	int _x, _y; //

	public static int maxDepth = 11;
	public static int maxMove = 3;

	public int[] DScore = { 0, 1, 9, 81, 729 };// Mảng điểm chặn
	public int[] AScore = { 0, 2, 18, 162, 1458 };// Mảng điểm tấn công
	public boolean cWin = false;
	Point goPoint;
	public Caro(BoardState board) {
		this.boardState = board;
		this.eBoard = new EvalBoard(board.width, board.height);
	}

	public void evalChessBoard(int player, EvalBoard eBoard) {
		int rw, cl;
		int ePC, eHuman;
		eBoard.resetBoard();
		// Danh gia theo hang
		for (rw = 0; rw < eBoard.width; rw++)
			for (cl = 0; cl < eBoard.height - 4; cl++) {
				ePC = 0;
				eHuman = 0;
				for (int i = 0; i < 5; i++) {
					if (boardState.getPosition(rw, cl + i) == 1)
						eHuman++;
					if (boardState.getPosition(rw, cl + i) == 2)
						ePC++;
				}
				if (eHuman * ePC == 0 && eHuman != ePC)
					for (int i = 0; i < 5; i++) {
						// neu o chua danh
						if (boardState.getPosition(rw, cl + i) == 0) {
							if (eHuman == 0) // ePC #0
								if (player == 1)
									eBoard.EBoard[rw][cl + i] += DScore[ePC]; // cho
																				// điểm
																				// chặn
								else
									eBoard.EBoard[rw][cl + i] += AScore[ePC];// Cho
																				// điểm
																				// PC
																				// công
							if (ePC == 0) // eHuman #0
								if (player == 2) {
									eBoard.EBoard[rw][cl + i] += DScore[eHuman];// Cho
																				// điểm
																				// PC
																				// chặn
								} else {
									eBoard.EBoard[rw][cl + i] += AScore[eHuman];// Cho
																				// điểm
																				// Công
								}
							if (eHuman == 4 || ePC == 4)
								eBoard.EBoard[rw][cl + i] *= 2;
						}
					}
			}

		// Danh gia theo cot
		for (cl = 0; cl < eBoard.height; cl++)
			for (rw = 0; rw < eBoard.width - 4; rw++) {
				ePC = 0;
				eHuman = 0;
				for (int i = 0; i < 5; i++) {
					if (boardState.getPosition(rw + i, cl) == 1)
						eHuman++;
					if (boardState.getPosition(rw + i, cl) == 2)
						ePC++;
				}
				if (eHuman * ePC == 0 && eHuman != ePC)
					for (int i = 0; i < 5; i++) {
						if (boardState.getPosition(rw + i, cl) == 0) // Neu o
																		// chua
																		// duoc
																		// danh
						{
							if (eHuman == 0)
								if (player == 1)
									eBoard.EBoard[rw + i][cl] += DScore[ePC];
								else
									eBoard.EBoard[rw + i][cl] += AScore[ePC];
							if (ePC == 0)
								if (player == 2)
									eBoard.EBoard[rw + i][cl] += DScore[eHuman];
								else
									eBoard.EBoard[rw + i][cl] += AScore[eHuman];
							if (eHuman == 4 || ePC == 4)
								eBoard.EBoard[rw + i][cl] *= 2;
						}

					}
			}

		// Danh gia duong cheo xuong
		for (cl = 0; cl < eBoard.height - 4; cl++)
			for (rw = 0; rw < eBoard.width - 4; rw++) {
				ePC = 0;
				eHuman = 0;
				for (int i = 0; i < 5; i++) {
					if (boardState.getPosition(rw + i, cl + i) == 1)
						eHuman++;
					if (boardState.getPosition(rw + i, cl + i) == 2)
						ePC++;
				}
				if (eHuman * ePC == 0 && eHuman != ePC)
					for (int i = 0; i < 5; i++) {
						if (boardState.getPosition(rw + i, cl + i) == 0) // Neu
																			// o
																			// chua
																			// duoc
						// danh
						{
							if (eHuman == 0)
								if (player == 1)
									eBoard.EBoard[rw + i][cl + i] += DScore[ePC];
								else
									eBoard.EBoard[rw + i][cl + i] += AScore[ePC];
							if (ePC == 0)
								if (player == 2)
									eBoard.EBoard[rw + i][cl + i] += DScore[eHuman];
								else
									eBoard.EBoard[rw + i][cl + i] += AScore[eHuman];
							if (eHuman == 4 || ePC == 4)
								eBoard.EBoard[rw + i][cl + i] *= 2;
						}

					}
			}

		// Danh gia duong cheo len
		for (rw = 4; rw < eBoard.width; rw++)
			for (cl = 0; cl < eBoard.height - 4; cl++) {
				ePC = 0;
				eHuman = 0;
				for (int i = 0; i < 5; i++) {
					if (boardState.getPosition(rw - i, cl + i) == 1)
						eHuman++;
					if (boardState.getPosition(rw - i, cl + i) == 2)
						ePC++;
				}
				if (eHuman * ePC == 0 && eHuman != ePC)
					for (int i = 0; i < 5; i++) {
						if (boardState.getPosition(rw - i, cl + i) == 0) {
							if (eHuman == 0)
								if (player == 1)
									eBoard.EBoard[rw - i][cl + i] += DScore[ePC];
								else
									eBoard.EBoard[rw - i][cl + i] += AScore[ePC];
							if (ePC == 0)
								if (player == 2)
									eBoard.EBoard[rw - i][cl + i] += DScore[eHuman];
								else
									eBoard.EBoard[rw - i][cl + i] += AScore[eHuman];
							if (eHuman == 4 || ePC == 4)
								eBoard.EBoard[rw - i][cl + i] *= 2;
						}

					}
			}

	}

	int depth = 0;

	void pr(int[][] a) {
//		for (int i = 0; i < a.length; i++) {
//			for (int j = 0; j < a.length; j++) {
//				System.out.print(a[i][j] + "\t");
//			}
//			System.out.println();
//		}
	}
	public void alphaBeta(int alpha, int beta, int depth, int player) {
		if(player==2){
			maxVal(boardState, alpha, beta, depth);
			
		}else{
			minVal(boardState, alpha, beta, depth);
		}
	}

	private int maxVal(BoardState state, int alpha, int beta, int depth) {
		evalChessBoard(2, eBoard);
		eBoard.MaxPos();
		int value = eBoard.evaluationBoard;
		if (depth >= maxDepth) {
			return value;
		}
		evalChessBoard(2, eBoard);
		pr(eBoard.EBoard);
		ArrayList<Point> list = new ArrayList<>();
		for (int i = 0; i < maxMove; i++) {
			Point node = eBoard.MaxPos();
			if(node == null)break;
			list.add(node);
			eBoard.EBoard[node.x][node.y] = 0;
		}
		int v = Integer.MIN_VALUE;
		for (int i = 0; i < list.size(); i++) {
			Point com = list.get(i);
			state.setPosition(com.x, com.y, 2);
			v = Math.max(v, minVal(state, alpha, beta, depth+1));
			state.setPosition(com.x, com.y, 0);
			if(v>= beta || state.checkEnd(com.x, com.y)==2){
				goPoint = com;
				return v;
				
			}
			alpha = Math.max(alpha, v);
		}

		return v;
	}

	private int minVal(BoardState state, int alpha, int beta, int depth) {
		evalChessBoard(1, eBoard);
		eBoard.MaxPos();
		int value = eBoard.evaluationBoard;
		if (depth >= maxDepth) {
			return value;
		}
		evalChessBoard(1, eBoard);
		ArrayList<Point> list = new ArrayList<>();
		for (int i = 0; i < maxMove; i++) {
			Point node = eBoard.MaxPos();
			if(node==null)break;
			list.add(node);
			eBoard.EBoard[node.x][node.y] = 0;
		}
		int v = Integer.MAX_VALUE;
		for (int i = 0; i < list.size(); i++) {
			Point com = list.get(i);
			state.setPosition(com.x, com.y, 1);
			v = Math.min(v, maxVal(state, alpha, beta, depth+1));
			state.setPosition(com.x, com.y, 0);
			if(v<= alpha || state.checkEnd(com.x, com.y)==1){
				return v;
			}
			beta = Math.min(beta, v);
		}
		return v;
	}

	
	private ArrayList<Point> listEmty(){
		ArrayList<Point> list = new ArrayList<>();
		for (int i = 0; i < boardState.width; i++) {
			for (int j = 0; j < boardState.height; j++) {
				if (boardState.getPosition(i, j) == 0) {
					list.add(new Point(i, j));
				}
			}
		}
		return list;
	}

	private Point randomNuocCo() {
		List<Point> ran = new ArrayList<>();
		for (int i = 0; i < boardState.width; i++) {
			for (int j = 0; j < boardState.height; j++) {
				if (boardState.getPosition(i, j) == 0) {
					ran.add(new Point(i, j));
				}
			}
		}
		if (!ran.isEmpty()) {
			Random rd = new Random();
			int p = rd.nextInt(ran.size());
			return ran.get(p);
		} else {
			return null;
		}
	}

	// @Override

	public Point AI(int player) {
		depth = 0;
		alphaBeta(0, 1, 2,player);
		Point temp = goPoint;
		if (temp != null) {
			_x = temp.x;
			_y = temp.y;
		}
		return new Point(_x, _y);
	}

	@Override
	public int getPlayerFlag() {
		return playerFlag;
	}

	@Override
	public void setPlayerFlag(int playerFlag) {
		this.playerFlag = playerFlag;
	}

	@Override
	public BoardState getBoardState() {
		return boardState;
	}

	@Override
	public Point movePoint(int player) {
		// TODO Auto-generated method stub
		return AI(player);
	}

}
