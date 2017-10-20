package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.Game;

public class GameBoard {
	//Set up Pieces
	GamePiece[] gamePieces = new GamePiece[16];
	
	//Set up Squares
	BoardSquare[][] boardSquares = new BoardSquare[7][9];

	public GameBoard() {
		setUpBoard();
		setUpPieces();
	}
	
	public GameBoard(GamePiece[] gamePieces) {
		this.gamePieces = gamePieces;
	}

	private void setUpPieces() {
		//Player 1
		gamePieces[0] = new GamePiece(PieceType.Rat, 0, 2);
		gamePieces[1] = new GamePiece(PieceType.Cat, 5, 1);
		gamePieces[2] = new GamePiece(PieceType.Dog, 1, 1);
		gamePieces[3] = new GamePiece(PieceType.Wolf, 4, 2);
		gamePieces[4] = new GamePiece(PieceType.Leopard, 2, 2);
		gamePieces[5] = new GamePiece(PieceType.Tiger, 6, 0);
		gamePieces[6] = new GamePiece(PieceType.Lion, 0, 0);
		gamePieces[7] = new GamePiece(PieceType.Elephant, 6, 2);		
		//Player 2
		gamePieces[8] = new GamePiece(PieceType.Rat, 6, 6);
		gamePieces[9] = new GamePiece(PieceType.Cat, 1, 7);
		gamePieces[10] = new GamePiece(PieceType.Dog, 5, 7);
		gamePieces[11] = new GamePiece(PieceType.Wolf, 2, 6);
		gamePieces[12] = new GamePiece(PieceType.Leopard, 4, 6);
		gamePieces[13] = new GamePiece(PieceType.Tiger, 0, 8);
		gamePieces[14] = new GamePiece(PieceType.Lion, 6, 8);
		gamePieces[15] = new GamePiece(PieceType.Elephant, 0, 6);
	}
	
	private void setUpBoard() {
		//row 1
		boardSquares[0][0] = new BoardSquare(SquareType.Normal);
		boardSquares[0][1] = new BoardSquare(SquareType.Normal);
		boardSquares[0][2] = new BoardSquare(SquareType.Trap);
		boardSquares[0][3] = new BoardSquare(SquareType.Den);
		boardSquares[0][4] = new BoardSquare(SquareType.Trap);
		boardSquares[0][5] = new BoardSquare(SquareType.Normal);
		boardSquares[0][6] = new BoardSquare(SquareType.Normal);
		//row 2
		boardSquares[1][0] = new BoardSquare(SquareType.Normal);
		boardSquares[1][1] = new BoardSquare(SquareType.Normal);
		boardSquares[1][2] = new BoardSquare(SquareType.Normal);
		boardSquares[1][3] = new BoardSquare(SquareType.Normal);
		boardSquares[1][4] = new BoardSquare(SquareType.Normal);
		boardSquares[1][5] = new BoardSquare(SquareType.Normal);
		boardSquares[1][6] = new BoardSquare(SquareType.Normal);
		//row 3
		boardSquares[2][0] = new BoardSquare(SquareType.Normal);
		boardSquares[2][1] = new BoardSquare(SquareType.Normal);
		boardSquares[2][2] = new BoardSquare(SquareType.Normal);
		boardSquares[2][3] = new BoardSquare(SquareType.Normal);
		boardSquares[2][4] = new BoardSquare(SquareType.Normal);
		boardSquares[2][5] = new BoardSquare(SquareType.Normal);
		boardSquares[2][6] = new BoardSquare(SquareType.Normal);
		//row 4
		boardSquares[3][0] = new BoardSquare(SquareType.Normal);
		boardSquares[3][1] = new BoardSquare(SquareType.Normal);
		boardSquares[3][2] = new BoardSquare(SquareType.Normal);
		boardSquares[3][3] = new BoardSquare(SquareType.Normal);
		boardSquares[3][4] = new BoardSquare(SquareType.Normal);
		boardSquares[3][5] = new BoardSquare(SquareType.Normal);
		boardSquares[3][6] = new BoardSquare(SquareType.Normal);
		//row 5
		boardSquares[4][0] = new BoardSquare(SquareType.Normal);
		boardSquares[4][1] = new BoardSquare(SquareType.Normal);
		boardSquares[4][2] = new BoardSquare(SquareType.Normal);
		boardSquares[4][3] = new BoardSquare(SquareType.Normal);
		boardSquares[4][4] = new BoardSquare(SquareType.Normal);
		boardSquares[4][5] = new BoardSquare(SquareType.Normal);
		boardSquares[4][6] = new BoardSquare(SquareType.Normal);
		//row 6
		boardSquares[5][0] = new BoardSquare(SquareType.Normal);
		boardSquares[5][1] = new BoardSquare(SquareType.Normal);
		boardSquares[5][2] = new BoardSquare(SquareType.Normal);
		boardSquares[5][3] = new BoardSquare(SquareType.Normal);
		boardSquares[5][4] = new BoardSquare(SquareType.Normal);
		boardSquares[5][5] = new BoardSquare(SquareType.Normal);
		boardSquares[5][6] = new BoardSquare(SquareType.Normal);
		//row 7
		boardSquares[6][0] = new BoardSquare(SquareType.Normal);
		boardSquares[6][1] = new BoardSquare(SquareType.Normal);
		boardSquares[6][2] = new BoardSquare(SquareType.Normal);
		boardSquares[6][3] = new BoardSquare(SquareType.Normal);
		boardSquares[6][4] = new BoardSquare(SquareType.Normal);
		boardSquares[6][5] = new BoardSquare(SquareType.Normal);
		boardSquares[6][6] = new BoardSquare(SquareType.Normal);
	}
}
