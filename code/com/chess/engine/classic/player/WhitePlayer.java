package com.chess.engine.classic.player;

import java.util.List;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Piece;

public class WhitePlayer extends Player {

    final King whiteKing;

    public WhitePlayer(final Board board) {
        super(board);
        for(final Piece p : board.getWhitePieces()) {
            if(p.isKing()) {
                this.whiteKing = (King) p;
                return;
            }
        }
        throw new RuntimeException("White King could not be established!");
    }

    @Override
    public void calculateLegalMoves() {
        this.legalMoves.clear();
        for(final Piece p : this.board.getWhitePieces()) {
            this.legalMoves.addAll(p.calculateLegalMoves(this.board));
        }
    }

    @Override
    public BlackPlayer getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    public void switchPlayer() {
        board.setCurrentPlayer(this.board.blackPlayer());
    }

    @Override
    public King getPlayerKing() {
        return this.whiteKing;
    }

    @Override
    public List<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public String toString() {
        return "White";
    }
}
