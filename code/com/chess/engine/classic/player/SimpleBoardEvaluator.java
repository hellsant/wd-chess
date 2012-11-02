package com.chess.engine.classic.player;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.pieces.Piece;

public class SimpleBoardEvaluator
        implements BoardEvalulator {

    @Override
    public int invoke(Board board) {
        int white = 0;//board.whitePlayer().getLegalMoves().size();
        for (final Piece wp : board.getWhitePieces()) {
            white += (wp.getPieceValue() + wp.locationBonus()) * wp.getPieceAllegiance().getVector();
        }

        int black = 0;//-board.blackPlayer().getLegalMoves().size();
        for (final Piece bp : board.getBlackPieces()) {
            black += (bp.getPieceValue() + bp.locationBonus()) * bp.getPieceAllegiance().getVector();
        }
        return white + black;
    }

}
