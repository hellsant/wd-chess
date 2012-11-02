package com.chess.engine.classic.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.Move.AttackMove;
import com.chess.engine.classic.board.Move.PlainMove;
import com.chess.engine.classic.board.Tile;

public final class Rook extends Piece {

    private final static int[] candidateMoveCoordinates = { -8, -1, 1, 8 };

    public Rook(final Alliance allegiance) {
        super(Type.ROOK, allegiance);
    }

    public Rook(final Rook rook) {
        super(rook);
    }

    @Override
    public List<Move> calculateLegalMoves(final Board board) {
        final List<Move> legals = new ArrayList<Move>();
        int candidate_destination_coordinate;
        for (final int current_candidate : candidateMoveCoordinates) {
            candidate_destination_coordinate = this.piecePosition;
            while (true) {
                if ((Board.firstColumn[candidate_destination_coordinate] && (current_candidate == -1)) ||
                        (Board.eighthColumn[candidate_destination_coordinate] && (current_candidate == 1))) {
                    break;
                }
                candidate_destination_coordinate += current_candidate;
                if (!((candidate_destination_coordinate >= 0) && (candidate_destination_coordinate < Board.NUM_TILES))) {
                    break;
                } else {
                    final Tile candidate_destination_tile = board.getTile(candidate_destination_coordinate);
                    if (!candidate_destination_tile.isTileOccupied()) {
                        legals.add(new PlainMove(this.piecePosition, candidate_destination_coordinate, this));
                    } else {
                        final Piece piece_at_destination = candidate_destination_tile.getPiece();
                        final Alliance piece_at_destination_allegiance = piece_at_destination.getPieceAllegiance();
                        if (this.pieceAlliance != piece_at_destination_allegiance) {
                            legals.add(new AttackMove(this.piecePosition, candidate_destination_coordinate, this,
                                    piece_at_destination));
                        }
                        break;
                    }
                }
            }
        }

        return (legals);
    }

    @Override
    public int getPieceValue() {
        return Type.ROOK.getPieceValue();
    }

    @Override
    public int locationBonus() {
        return this.pieceAlliance.rookBonus(this.piecePosition);
    }

    @Override
    public Rook createCopy() {
        return new Rook(this);
    }

    @Override
    public String toString() {
        return Type.ROOK.toString();
    }

}