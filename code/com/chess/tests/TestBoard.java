package com.chess.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.BoardConfigurator;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.Move.MoveFactory;
import com.chess.engine.classic.board.StandardBoardConfigurator;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Pawn;
import com.chess.engine.classic.player.Player;
import com.chess.engine.classic.player.SimpleBoardEvaluator;

public class TestBoard {

    @Test
    public void initialBoard() {
        final Board board = new Board(new StandardBoardConfigurator());
        assertEquals(board.whitePlayer().getLegalMoves().size(), 20);
        assertEquals(board.blackPlayer().getLegalMoves().size(), 20);
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
        assertFalse(board.currentPlayer().getOpponent().isInCheck());
        assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertEquals(board.currentPlayer().getOpponent(),board.blackPlayer());
        assertEquals(new SimpleBoardEvaluator().invoke(board), 0);
    }

    @Test
    public void testKing() {
        final Board board = new Board(new BoardConfigurator() {
            @Override
            public void configure(final Board board) {
                board.clearGameBoard();
                // Black Layout
                board.setPiece(4, new King(Alliance.BLACK));
                board.setPiece(12, new Pawn(Alliance.BLACK));
                // White Layout
                board.setPiece(52, new Pawn(Alliance.WHITE));
                board.setPiece(60, new King(Alliance.WHITE));
                board.setCurrentPlayer(board.whitePlayer());
            }
        });
        assertEquals(board.whitePlayer().getLegalMoves().size(), 6);
        assertEquals(board.blackPlayer().getLegalMoves().size(), 6);
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
        assertFalse(board.currentPlayer().getOpponent().isInCheck());
        assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertEquals(board.currentPlayer().getOpponent(),board.blackPlayer());
        assertEquals(new SimpleBoardEvaluator().invoke(board), 0);
        final Board copy = board.createCopy();
        Move m = MoveFactory.createMove(board, Board.getCoordinateAtPosition("e1"), Board.getCoordinateAtPosition("f1"));
        Player.makeMove(board, m);
        assertEquals(board.whitePlayer().getPlayerKing().getPiecePosition(), 61);
        assertEquals(copy.whitePlayer().getPlayerKing().getPiecePosition(), 60);
        System.out.println();
    }

    @Test
    public void testCopy() {
        final Board board = new Board(new StandardBoardConfigurator());
        assertEquals(board.whitePlayer().getLegalMoves().size(), 20);
        assertEquals(board.blackPlayer().getLegalMoves().size(), 20);
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
        assertFalse(board.currentPlayer().getOpponent().isInCheck());
        assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertEquals(board.currentPlayer().getOpponent(),board.blackPlayer());
        assertEquals(new SimpleBoardEvaluator().invoke(board), 0);
        final Board copy = board.createCopy();
        final Move m = MoveFactory.createMove(copy, Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("e4"));
        Player.makeMove(copy, m);
        assertEquals(board.whitePlayer().getLegalMoves().size(), 20);
        assertEquals(board.blackPlayer().getLegalMoves().size(), 20);
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
        assertFalse(board.currentPlayer().getOpponent().isInCheck());
        assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertEquals(board.currentPlayer().getOpponent(),board.blackPlayer());
        assertEquals(new SimpleBoardEvaluator().invoke(board), 0);
    }

    @Test
    public void testMoveAndUnMoveSingle() {
        final Board board = new Board(new StandardBoardConfigurator());
        final Move m = MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("e4"));
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertTrue(board.whitePlayer().getLegalMoves().contains(m));
        assertEquals(board.whitePlayer().getLegalMoves().size(), 20);
        Player.makeMove(board, m);
        Player.unMakeMove(board, m);
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertTrue(board.whitePlayer().getLegalMoves().contains(m));
        assertEquals(board.whitePlayer().getLegalMoves().size(), 20);
    }

    @Test
    public void testMoveAndUnMoveMulti() {
        final Board board = new Board(new StandardBoardConfigurator());
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertFalse(board.currentPlayer().isInCheck());
        assertEquals(board.whitePlayer().getLegalMoves().size(), 20);
        for(int i = 0; i < board.currentPlayer().getLegalMoves().size(); i++) {
            final Move m = board.currentPlayer().getLegalMoves().get(i);
            Player.makeMove(board, m);
            Player.unMakeMove(board, m);
        }
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertFalse(board.currentPlayer().isInCheck());
        assertEquals(board.whitePlayer().getLegalMoves().size(), 20);
    }

}

