package com.chess.engine.classic.board;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.pieces.Pawn;
import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.classic.pieces.Rook;

public abstract class Move implements Comparable<Move>{

    protected final int currentCoordinate;
    protected final int destinationCoordinate;
    protected final Piece movedPiece;

    private Move(final int current, final int destination,
                 final Piece piece_moved) {
        this.currentCoordinate = current;
        this.destinationCoordinate = destination;
        this.movedPiece = piece_moved;
    }

    public int getCurrentCoordinate() {
        return this.currentCoordinate;
    }

    public int getDestinationCoordinate() {
        return this.destinationCoordinate;
    }

    public Alliance getMoveMaker() {
        return this.movedPiece.getPieceAllegiance();
    }

    public Piece getMovedPiece() {
        return this.movedPiece;
    }

    public boolean isCastle() {
        return false;
    }

    public boolean isAttack() {
        return false;
    }

    public abstract void execute(Board board);

    public abstract void undo(Board board);

    @Override
    public String toString() {
        String s = "";
        s = Board.getPositionAtCoordinate(this.currentCoordinate);
        s += "-";
        s += Board.getPositionAtCoordinate(this.destinationCoordinate);
        if (this.movedPiece instanceof Pawn) {
            if (((Pawn) this.movedPiece).earnedPromotion()) {
                final Piece p = ((Pawn) this.movedPiece).getSelectedPromotionPiece();
                s += "=" + p;
            }
        }
        return (s);
    }

    public static class PlainMove extends Move {

        public PlainMove(final int current_coordinate, final int destination_coordinate, final Piece piece_moved) {
            super(current_coordinate, destination_coordinate, piece_moved);
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + this.currentCoordinate;
            result = prime * result + this.destinationCoordinate;
            result = prime * result
                    + ((this.movedPiece == null) ? 0 : this.movedPiece.hashCode());
            return result;
        }

        @Override
        public boolean equals(final Object aThat) {
            if ( this == aThat ) {
                return true;
            }
            if ( !(aThat instanceof PlainMove) ) {
                return false;
            }
            final PlainMove that = (PlainMove)aThat;

            return((getCurrentCoordinate() == that.getCurrentCoordinate()) &&
                    (getDestinationCoordinate() == that.getDestinationCoordinate()) &&
                    (getMovedPiece().equals(that.getMovedPiece())));
        }

        @Override
        public void execute(final Board board) {
            final Tile destination_location = board.getTile(this.destinationCoordinate);
            final Piece movedPiece = board.getTile(this.currentCoordinate).removePiece();
            destination_location.setPiece(movedPiece);
        }

        @Override
        public void undo(final Board board) {
            final Tile start_location = board.getTile(this.destinationCoordinate);
            final Tile destination_location = board.getTile(this.currentCoordinate);
            final Piece p = start_location.removePiece();
            destination_location.setPiece(p);
        }

        public int compareTo(final Move arg0) {
            return 0;
        }
    }

    public static class PawnJump extends Move {

        public PawnJump(final int current_coordinate, final int destination_coordinate, final Piece piece_moved) {
            super(current_coordinate, destination_coordinate, piece_moved);
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + this.currentCoordinate;
            result = prime * result + this.destinationCoordinate;
            result = prime * result
                    + ((this.movedPiece == null) ? 0 : this.movedPiece.hashCode());
            return result;
        }

        @Override
        public boolean equals(final Object aThat) {
            if (this == aThat) {
                return true;
            }
            if ( !(aThat instanceof PawnJump) ) {
                return false;
            }
            final PawnJump that = (PawnJump)aThat;
            return((getCurrentCoordinate() == that.getCurrentCoordinate()) &&
                    (getDestinationCoordinate() == that.getDestinationCoordinate()) &&
                    (getMovedPiece().equals(that.getMovedPiece())));
        }

        @Override
        public void execute(final Board board) {
            final Tile destination_location = board.getTile(this.destinationCoordinate);
            final Piece movedPiece = board.getTile(this.currentCoordinate).removePiece();
            destination_location.setPiece(movedPiece);
        }

        @Override
        public void undo(final Board board) {
            final Tile start_location = board.getTile(this.destinationCoordinate);
            final Tile destination_location = board.getTile(this.currentCoordinate);
            final Piece p = start_location.removePiece();
            destination_location.setPiece(p);
        }

        public int compareTo(final Move arg0) {
            return 0;
        }
    }

    public static class CastleMove extends Move {

        private final Rook castleRook;
        private final String rookCastleDestination;
        private static final int[] kingSideCastlePositions = { 62, 6 };
        private static final int[] queenSideCastlePositions = { 58, 2 };

        CastleMove(final int current, final int destination,
                final Piece piece_moved, final Rook castle_rook, final String rook_castle_destination) {
            super(current, destination, piece_moved);
            this.castleRook = castle_rook;
            this.rookCastleDestination = rook_castle_destination;
        }

        @Override
        public String toString() {

            String s = "";

            for (int i = 0; i < (kingSideCastlePositions.length) && i < (queenSideCastlePositions.length ); i++) {

                if (kingSideCastlePositions[i] ==  this.destinationCoordinate) {
                    s = "0-0";
                } else if (queenSideCastlePositions[i] == this.destinationCoordinate) {
                    s = "0-0-0";
                }

            }

            return (s);

        }

        @Override
        public boolean isCastle() {
            return true;
        }

        @Override
        public void execute(final Board board) {
        }

        @Override
        public void undo(final Board board) {
        }

        public int compareTo(final Move arg0) {
            return 0;
        }

    }

    static class EnPassantMove extends Move {

        private final Pawn enPassantPawn;

        public EnPassantMove(final int current, final int destination,
                final Piece piece_moved) {
            super(current, destination, piece_moved);
            this.enPassantPawn = null;
        }

        @Override
        public String toString() {
            final String s = "";
            return s;
        }

        @Override
        public void execute(final Board board) {
        }

        @Override
        public void undo(final Board board) {
        }

        public int compareTo(final Move arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

    }

    public static class AttackMove extends Move {

        private final Piece attackedPiece;

        public AttackMove(final int current,
                final int destination, final Piece piece_moved,
                final Piece piece_attacked) {
            super(current, destination, piece_moved);
            this.attackedPiece = piece_attacked;
        }

        @Override
        public String toString() {
            String s = "";
            s = Board.getPositionAtCoordinate(this.currentCoordinate);
            s += "x";
            s += Board.getPositionAtCoordinate(this.destinationCoordinate);
            if (this.movedPiece instanceof Pawn) {
                if (((Pawn) this.movedPiece).earnedPromotion()) {
                    final Piece p = ((Pawn) this.movedPiece).getSelectedPromotionPiece();
                    s += "=" + p;
                }
            }
            return (s);
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + this.currentCoordinate;
            result = prime * result + this.destinationCoordinate;
            result = prime * result
                    + ((this.movedPiece == null) ? 0 : this.movedPiece.hashCode());
            result = prime * result + this.attackedPiece.hashCode();
            return result;
        }

        @Override
        public boolean equals(final Object aThat) {
            if (this == aThat) {
                return true;
            }
            if (!(aThat instanceof AttackMove)) {
                return false;
            }
            final AttackMove that = (AttackMove)aThat;
            return ((getCurrentCoordinate() == that.getCurrentCoordinate()) &&
                    (getDestinationCoordinate() == that.getDestinationCoordinate()) &&
                    (getMovedPiece().equals(that.getMovedPiece())) &&
                    (getAttackedPiece().equals(that.getAttackedPiece())));
        }

        @Override
        public boolean isAttack() {
            return true;
        }

        public Piece getAttackedPiece() {
            return this.attackedPiece;
        }

        @Override
        public void execute(final Board board) {
            final Tile destination_location = board.getTile(this.destinationCoordinate);
            final Piece movedPiece = board.getTile(this.currentCoordinate).removePiece();
            final Piece attackedPiece = destination_location.getPiece();
            destination_location.setPiece(movedPiece);
            board.currentPlayer().getOpponent().getActivePieces().remove(attackedPiece);
        }

        @Override
        public void undo(final Board board) {
            final Tile start_location = board.getTile(this.destinationCoordinate);
            final Tile destination_location = board.getTile(this.currentCoordinate);
            destination_location.setPiece(this.movedPiece);
            start_location.setPiece(this.attackedPiece);
            board.currentPlayer().getOpponent().getActivePieces().add(this.attackedPiece);
        }

        public int compareTo(final Move arg0) {
            return -1;
        }

    }

    public static class MoveFactory {

        private MoveFactory() {
        }

        public static Move createMove(final Board board, final int start, final int destination) {

            final Tile startTile = board.getTile(start);
            final Tile destTile = board.getTile(destination);
            final Piece moving_piece = board.getTile(start).getPiece();

            if((Board.secondRow[start] && Board.fourthRow[destination] && (board.currentPlayer() == board.blackPlayer())) ||
                    (Board.seventhRow[start] && Board.fifthRow[destination] && (board.currentPlayer() == board.whitePlayer()))) {
                return new PawnJump(start, destination, moving_piece);
            }

            if (!destTile.isTileOccupied()) {
                return new PlainMove(start, destination, moving_piece);
            }
            else {
                final Piece piece_at_destination = destTile.getPiece();
                if (moving_piece.getPieceAllegiance() != piece_at_destination.getPieceAllegiance()) {
                    return new AttackMove(start,destination, moving_piece, piece_at_destination);
                }
            }
            throw new RuntimeException("Invalid move!");
        }

    }

}