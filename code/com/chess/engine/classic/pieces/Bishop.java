package com.chess.engine.classic.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.Move.AttackMove;
import com.chess.engine.classic.board.Move.PlainMove;
import com.chess.engine.classic.board.Tile;

public final class Bishop extends Piece {

    private final static int[] candidateMoveCoordinates = { -9, -7, 7, 9 };

    public Bishop(final Alliance allegiance) {
        super(Type.BISHOP, allegiance);
    }

    public Bishop(final Bishop b) {
        super(b);
    }

    @Override
    public List<Move> calculateLegalMoves(final Board board) {
        final List<Move> legals = new ArrayList<Move>();
        int candidate_destination_coordinate;
        for (final int current_candidate : candidateMoveCoordinates) {
            candidate_destination_coordinate = this.piecePosition;
            while (true) {
                if ((Board.firstColumn[candidate_destination_coordinate] && ((current_candidate == -9) || (current_candidate == 7)))
                        || (Board.eighthColumn[candidate_destination_coordinate] && ((current_candidate == -7) || (current_candidate == 9)))) {
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
                            legals.add(new AttackMove(this.piecePosition, candidate_destination_coordinate, this, piece_at_destination));
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
        return Type.BISHOP.getPieceValue();
    }

    @Override
    public int locationBonus() {
        return this.pieceAlliance.bishopBonus(this.piecePosition);
    }

    @Override
    public Bishop createCopy() {
        return new Bishop(this);
    }

    @Override
    public String toString() {
        return Type.BISHOP.toString();
    }

}