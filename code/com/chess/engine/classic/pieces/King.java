package com.chess.engine.classic.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.Move.AttackMove;
import com.chess.engine.classic.board.Move.PlainMove;
import com.chess.engine.classic.board.Tile;

public final class King extends Piece {

    private final static int[] candidateMoveCoordinates = { -9, -8, -7, -1, 1,
        7, 8, 9 };
    private boolean isCastled;
    private boolean isInCheck;
    private boolean isCheckMated;
    private boolean isCastleCapable;

    public King(final Alliance allegiance) {
        super(Type.KING, allegiance);
    }

    public King(final King king) {
        super(king);
        this.isCastled = king.isCastled();
        this.isInCheck = king.isInCheck();
        this.isCheckMated = king.isCheckMated();
        this.isCastleCapable = king.isCastleCapable();
    }


    @Override
    public List<Move> calculateLegalMoves(final Board board) {

        final List<Move> legals = new ArrayList<Move>();
        int candidate_destination_coordinate;

        for (final int current_candidate : candidateMoveCoordinates) {

            if (Board.firstColumn[this.piecePosition]
                    && ((current_candidate == -9) || (current_candidate == -1) || (current_candidate == 7))) {
                continue;
            }

            if (Board.eighthColumn[this.piecePosition]
                    && ((current_candidate == -7) || (current_candidate == 1) || (current_candidate == 9))) {
                continue;
            }

            candidate_destination_coordinate = this.piecePosition
                    + current_candidate;

            if (!(candidate_destination_coordinate >= 0 && candidate_destination_coordinate < Board.NUM_TILES)) {
                continue;
            }

            final Tile candidate_destination_tile = board
                    .getTile(candidate_destination_coordinate);

            if (!candidate_destination_tile.isTileOccupied()) {
                legals.add(new PlainMove(this.piecePosition,
                        candidate_destination_coordinate, this));
            } else {
                final Piece piece_at_destination = candidate_destination_tile
                        .getPiece();
                final Alliance piece_at_destination_allegiance = piece_at_destination
                        .getPieceAllegiance();

                if (this.pieceAlliance != piece_at_destination_allegiance) {
                    legals.add(new AttackMove(this.piecePosition,
                            candidate_destination_coordinate, this,
                            piece_at_destination));
                }

            }

        }

        return (legals);

    }

    @Override
    public int getPieceValue() {
        return Type.KING.getPieceValue();
    }

    @Override
    public String toString() {
        return Type.KING.toString();
    }

    public boolean isCastled() {
        return (this.isCastled);
    }

    public boolean isCastleCapable() {
        return (this.isCastleCapable);
    }

    public boolean isInCheck() {
        return (this.isInCheck);
    }

    public boolean isCheckMated() {
        return (this.isCheckMated);
    }

    public void setIsCastled(final boolean b) {
        this.isCastled = b;
    }

    public void setIsInCheck(final boolean b) {
        this.isInCheck = b;
    }

    public void isCheckMated(final boolean b) {
        this.isCheckMated = b;
    }

    @Override
    public int locationBonus() {
        return this.pieceAlliance.kingBonus(this.piecePosition);
    }

    @Override
    public King createCopy() {
        return new King(this);
    }

}