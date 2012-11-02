package com.chess.engine.classic.pieces;

import java.util.List;

import javax.swing.ImageIcon;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;

public abstract class Piece {

    protected final Type pieceType;
    protected final Alliance pieceAlliance;
    protected ImageIcon pieceImage;
    protected int piecePosition;

    protected Piece(final Type type,
                    final Alliance alliance) {
        this.pieceType = type;
        this.pieceAlliance = alliance;
    }

    protected Piece(final Piece p) {
        this.pieceType = p.getPieceType();
        this.pieceAlliance = p.getPieceAllegiance();
        this.piecePosition = p.getPiecePosition();
    }

    public Type getPieceType() {
        return this.pieceType;
    }

    public Alliance getPieceAllegiance() {
        return this.pieceAlliance;
    }

    public int getPiecePosition() {
        return this.piecePosition;
    }

    public abstract int getPieceValue();

    public abstract int locationBonus();

    public abstract Piece createCopy();

    public boolean isKing() {
        return this.pieceType == Type.KING;
    }

    public void setPiecePosition(final int piece_position) {
        this.piecePosition = piece_position;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 31 + this.pieceType.hashCode() + this.pieceAlliance.hashCode()
                + this.piecePosition;
        return hash;
    }

    @Override
    public boolean equals(final Object other) {

        if (this == other) {
            return true;
        }
        if (!(other instanceof Piece)) {
            return false;
        }

        final Piece otherPiece = (Piece) other;

        return (this.pieceType == otherPiece.getPieceType())
                && (this.pieceAlliance == otherPiece.getPieceAllegiance())
                && (this.piecePosition == otherPiece.getPiecePosition());

    }

    public abstract List<Move> calculateLegalMoves(final Board b);

    public enum Type {

        PAWN(100) {
            @Override
            public String toString() {
                return "P";
            }
        },
        KNIGHT(300) {
            @Override
            public String toString() {
                return "N";
            }
        },
        BISHOP(300) {
            @Override
            public String toString() {
                return "B";
            }
        },
        ROOK(500) {
            @Override
            public String toString() {
                return "R";
            }
        },
        QUEEN(900) {
            @Override
            public String toString() {
                return "Q";
            }
        },
        KING(10000) {
            @Override
            public String toString() {
                return "K";
            }
        };

        private final int value;

        public int getPieceValue() {
            return this.value;
        }

        Type(final int val) {
            this.value = val;
        }

    }

}