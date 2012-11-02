package com.chess.tests;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.BoardConfigurator;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Knight;

public class TestKnight {
    @Test
    public void testLegalMoveAllAvailable() {
        final Board board = new Board(new BoardConfigurator() {
            public void configure(final Board board) {
                board.clearGameBoard();
                // Black Layout
                board.setPiece(4, new King(Alliance.BLACK));
                board.setPiece(28, new Knight(Alliance.BLACK));
                // White Layout
                board.setPiece(36, new Knight(Alliance.WHITE));
                board.setPiece(60, new King(Alliance.WHITE));
                board.setCurrentPlayer(board.whitePlayer());
            }
        });
        assertEquals(board.whitePlayer().getLegalMoves().size(), 13);
        assertEquals(board.blackPlayer().getLegalMoves().size(), 13);
    }
}
