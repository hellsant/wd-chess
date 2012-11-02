package com.chess.engine.bitboards;

import com.chess.engine.bitboards.BitBoard.Piece;

public class Move {

	final int currentLocation;
	final int destinationLocation;
	final Piece movedPiece;

	public Move(final int current, final int destination, final Piece moved) {
		currentLocation = current;
		destinationLocation = destination;
		movedPiece = moved;
	}

	@Override
	public String toString() {
		return BitBoard.getPositionAtCoordinate(currentLocation) + "-"
		        + BitBoard.getPositionAtCoordinate(destinationLocation);
	}

	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 31 + movedPiece.hashCode() + currentLocation
		        + destinationLocation;
		return hash;
	}

	@Override
	public boolean equals(final Object other) {

		if (this == other) {
			return true;
		}
		if (!(other instanceof Move)) {
			return false;
		}

		final Move otherMove = (Move) other;

		return (movedPiece == otherMove.getMovedPiece())
		        && (currentLocation == otherMove.getCurrentLocation())
		        && (destinationLocation == otherMove.getDestinationLocation());

	}

	public int getDestinationLocation() {
		return destinationLocation;
	}

	public int getCurrentLocation() {
		return currentLocation;
	}

	public Piece getMovedPiece() {
		return movedPiece;
	}

}
