package com.chess.engine.classic.board;

import com.chess.engine.classic.pieces.Piece;

public final class Tile {

    private Piece pieceOnTile;
    private boolean isOccupied;
    private final int tileCoordinate;

    public Tile(final int coordinate) {
        this.isOccupied = false;
        this.pieceOnTile = null;
        this.tileCoordinate = coordinate;
    }

    public Tile(final Tile tile) {
        this.isOccupied = tile.isTileOccupied();
        this.tileCoordinate = tile.getTileCoordinate();
        this.pieceOnTile = this.isOccupied ? tile.getPiece().createCopy() : null ;
    }

    public boolean isTileOccupied() {
        return this.isOccupied;
    }

    public Piece getPiece() {
        return this.pieceOnTile;
    }

    public int getTileCoordinate() {
        return this.tileCoordinate;
    }

    public Piece removePiece() {
        final Piece p = this.pieceOnTile;
        this.pieceOnTile = null;
        this.isOccupied = false;
        return (p);
    }

    // set methods
    public void setPiece(final Piece p) {
        this.pieceOnTile = p;
        this.isOccupied = true;
        p.setPiecePosition(this.tileCoordinate);
    }

    @Override
    public String toString() {
        if (isTileOccupied()) {
            return getPiece().toString();
        }
        return "-";
    }

}