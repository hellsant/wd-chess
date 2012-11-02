package com.chess.engine.classic.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.Move.AttackMove;
import com.chess.engine.classic.board.Move.PlainMove;
import com.chess.engine.classic.board.Tile;

public final class Knight extends Piece {

    private final static int[] candidateMoveCoordinates = { -17, -15, -10, -6,
        6, 10, 15, 17 };

    public Knight(final Alliance allegiance) {
        super(Type.KNIGHT, allegiance);
        //final ImageIcon ic = new ImageIcon(this.getClass().getResource(
        //        "com/chess/images/pieces/simple" + allegiance + "Knight" + ".gif"));
        //this.pieceImage = new ImageIcon(ic.getImage().getScaledInstance(ic.getIconWidth() + 5, ic.getIconHeight() + 5, Image.SCALE_SMOOTH));
    }

    public Knight(final Knight knight) {
        super(knight);
    }

    @Override
    public List<Move> calculateLegalMoves(final Board board) {

        final List<Move> legals = new ArrayList<Move>();
        int candidate_destination_coordinate;

        for (final int current_candidate : candidateMoveCoordinates) {

            if (Board.firstColumn[this.piecePosition] && ((current_candidate == -17) ||
                    (current_candidate == -10) || (current_candidate == 6) || (current_candidate == 15))) {
                continue;
            } else if (Board.secondColumn[this.piecePosition] && ((current_candidate == -10) || (current_candidate == 6))) {
                continue;
            } else if (Board.seventhColumn[this.piecePosition] && ((current_candidate == -6) || (current_candidate == 10))) {
                continue;
            } else if (Board.eighthColumn[this.piecePosition] && ((current_candidate == -15) || (current_candidate == -6) ||
                    (current_candidate == 10) || (current_candidate == 17))) {
                continue;
            }

            candidate_destination_coordinate = this.piecePosition + current_candidate;

            if (!(candidate_destination_coordinate >= 0 && candidate_destination_coordinate < Board.NUM_TILES)) {
                continue;
            } else {
                final Tile candidate_destination_tile = board.getTile(candidate_destination_coordinate);
                if (!candidate_destination_tile.isTileOccupied()) {
                    legals.add(new PlainMove(this.piecePosition, candidate_destination_coordinate, this));
                } else {
                    final Piece piece_at_destination = candidate_destination_tile.getPiece();
                    final Alliance piece_at_destination_allegiance = piece_at_destination.getPieceAllegiance();
                    if (this.pieceAlliance != piece_at_destination_allegiance) {
                        legals.add(new AttackMove(this.piecePosition, candidate_destination_coordinate, this, piece_at_destination));
                    }
                }
            }
        }

        return (legals);

    }

    @Override
    public int getPieceValue() {
        return Type.KNIGHT.getPieceValue();
    }

    @Override
    public int locationBonus() {
        return this.pieceAlliance.knightBonus(this.piecePosition);
    }

    @Override
    public Knight createCopy() {
        return new Knight(this);
    }

    @Override
    public String toString() {
        return Type.KNIGHT.toString();
    }

}