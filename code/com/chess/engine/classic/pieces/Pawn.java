package com.chess.engine.classic.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.Move.AttackMove;
import com.chess.engine.classic.board.Move.PawnJump;
import com.chess.engine.classic.board.Move.PlainMove;
import com.chess.engine.classic.board.Tile;

public final class Pawn extends Piece {

    private boolean earnedPromotion;
    private final static int[] candidateMoveCoordinates = { 8, 16, 7, 9 };
    private Piece selectedPromotionPiece;

    public Pawn(final Alliance allegiance) {
        super(Type.PAWN, allegiance);
    }

    public Pawn(final Pawn pawn) {
        super(pawn);
        this.earnedPromotion = pawn.earnedPromotion();
    }

    public Piece getSelectedPromotionPiece() {
        return this.selectedPromotionPiece;
    }

    public boolean earnedPromotion() {
        return this.earnedPromotion;
    }

    @Override
    public int locationBonus() {
        return this.pieceAlliance.pawnBonus(this.piecePosition);
    }

    @Override
    public List<Move> calculateLegalMoves(final Board board) {

        final List<Move> legals = new ArrayList<Move>();
        int candidate_destination_coordinate;

        for (final int current_candidate : candidateMoveCoordinates) {
            candidate_destination_coordinate = this.piecePosition
                    + (this.pieceAlliance.getVector() * current_candidate * -1);
            if ((Board.firstRow[this.piecePosition] && this.pieceAlliance == Alliance.WHITE)
                    || (Board.eighthRow[this.piecePosition] && this.pieceAlliance == Alliance.BLACK)) {
                this.earnedPromotion = true;
            }
            if (!(candidate_destination_coordinate >= 0 && candidate_destination_coordinate < Board.NUM_TILES)) {
                continue;
            }
            final Tile candidate_destination_tile = board.getTile(candidate_destination_coordinate);
            if (current_candidate == 8) {
                if (!candidate_destination_tile.isTileOccupied()) {
                    final Move m = new PlainMove(this.piecePosition, candidate_destination_coordinate, this);
                    legals.add(m);
                    continue;
                }
            } else if (current_candidate == 16 && ((Board.secondRow[this.piecePosition] && this.pieceAlliance == Alliance.BLACK) ||
                    (Board.seventhRow[this.piecePosition] && this.pieceAlliance == Alliance.WHITE ))) {
                final int behind_candidate_destination_coordinate = this.piecePosition
                        + (this.pieceAlliance.getVector() * 8 * -1);
                final Tile behind_candidate_destination_tile = board.getTile(behind_candidate_destination_coordinate);
                if (!candidate_destination_tile.isTileOccupied()
                        && !behind_candidate_destination_tile.isTileOccupied()) {
                    final Move m = new PawnJump(this.piecePosition, candidate_destination_coordinate, this);
                    legals.add(m);
                    continue;
                }
            } else if (current_candidate == 7) {
                if (candidate_destination_tile.isTileOccupied()) {
                    final Piece piece_on_candidate = candidate_destination_tile.getPiece();
                    if (this.pieceAlliance == piece_on_candidate.getPieceAllegiance()) {
                        continue;
                    }
                    if ((Board.eighthColumn[this.piecePosition] && this.pieceAlliance == Alliance.WHITE)
                            || (Board.firstColumn[this.piecePosition] && this.pieceAlliance == Alliance.BLACK)) {
                        continue;
                    }
                    final Move m = new AttackMove(this.piecePosition, candidate_destination_coordinate, this, piece_on_candidate);
                    legals.add(m);
                }
            } else if (current_candidate == 9) {
                if (candidate_destination_tile.isTileOccupied()) {
                    final Piece piece_on_candidate = candidate_destination_tile.getPiece();
                    if (this.pieceAlliance == piece_on_candidate.getPieceAllegiance()) {
                        continue;
                    }
                    if ((Board.firstColumn[this.piecePosition] && this.pieceAlliance == Alliance.WHITE)
                            || (Board.eighthColumn[this.piecePosition] && this.pieceAlliance == Alliance.BLACK)) {
                        continue;
                    }
                    final Move m = new AttackMove(this.piecePosition, candidate_destination_coordinate, this, piece_on_candidate);
                    legals.add(m);
                }
            }
        }
        return (legals);
    }

    public void setSelectedPromotionPiece(final Piece p) {
        this.selectedPromotionPiece = p;
    }

    @Override
    public int getPieceValue() {
        return Type.PAWN.getPieceValue();
    }

    @Override
    public String toString() {
        return Type.PAWN.toString();
    }

    @Override
    public Pawn createCopy() {
        return new Pawn(this);
    }

}