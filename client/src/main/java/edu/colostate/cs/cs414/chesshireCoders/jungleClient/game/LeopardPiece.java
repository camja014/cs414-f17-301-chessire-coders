package edu.colostate.cs.cs414.chesshireCoders.jungleClient.game;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PieceType;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType;

public class LeopardPiece extends JungleGamePiece {

	public LeopardPiece() {
	}

	public LeopardPiece(PlayerEnumType ownerType, int column, int row) {
        super(ownerType,  column, row);
		setPowerDefault();
		setPieceType(PieceType.LEOPARD);
	}


	@Override
	public boolean canOccupy(BoardSquare square) {
		if (square == null)
			return false;
		
		boolean result = true;
		
		result = result && canCapture(square);
		result = result && !(square instanceof RiverSquare);
		result = result && !isFriendlyDen(square);
		
		return result;
	}
	
	@Override
	public void setPowerDefault() {
		setPowerLevel(5);
	}

}
