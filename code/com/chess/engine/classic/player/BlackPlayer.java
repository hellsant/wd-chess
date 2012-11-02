package com.chess.engine.classic.player;

import java.util.List;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Piece;

public class BlackPlayer extends Player {

    final King blackKing;

    public BlackPlayer(final Board board) {
        super(board);
        for(final Piece p : board.getBlackPieces()) {
            if(p.isKing()) {
                this.blackKing = (King) p;
                return;
            }
        }
        throw new RuntimeException("Should not reach here! Black King could not be established!");
    }

    @Override
    public void calculateLegalMoves() {
        this.legalMoves.clear();
        for(final Piece p : board.getBlackPieces()) {
            this.legalMoves.addAll(p.calculateLegalMoves(board));
        }
    }

    @Override
    public WhitePlayer getOpponent() {
        return this.board.whitePlayer();
    }

    @Override
    public void switchPlayer() {
        this.board.setCurrentPlayer(this.board.whitePlayer());
    }

    @Override
    public King getPlayerKing() {
        return this.blackKing;
    }

    @Override
    public List<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public String toString() {
        return "Black";
    }

}
