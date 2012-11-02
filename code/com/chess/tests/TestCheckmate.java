package com.chess.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.Move.MoveFactory;
import com.chess.engine.classic.player.Player;
import com.chess.engine.classic.board.StandardBoardConfigurator;


public class TestCheckmate {

    @Test
    public void testFoolsMate() {
        final Board board = new Board(new StandardBoardConfigurator());
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertEquals(board.currentPlayer().getLegalMoves().size(), 20);
        assertFalse(board.currentPlayer().isInCheckMate());
        assertFalse(board.currentPlayer().isInCheck());
        final Move M1 = MoveFactory.createMove(board, Board.getCoordinateAtPosition("f2"), Board.getCoordinateAtPosition("f3"));
        Player.makeMove(board, M1);
        final Move M2 = MoveFactory.createMove(board, Board.getCoordinateAtPosition("e7"), Board.getCoordinateAtPosition("e5"));
        Player.makeMove(board, M2);
        final Move M3 = MoveFactory.createMove(board, Board.getCoordinateAtPosition("g2"), Board.getCoordinateAtPosition("g4"));
        Player.makeMove(board, M3);
        final Move M4 = MoveFactory.createMove(board, Board.getCoordinateAtPosition("d8"), Board.getCoordinateAtPosition("h4"));
        Player.makeMove(board, M4);
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertTrue(board.currentPlayer().isInCheckMate());
        //now undo
        Player.unMakeMove(board, M4);
        Player.unMakeMove(board, M3);
        Player.unMakeMove(board, M2);
        Player.unMakeMove(board, M1);
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertEquals(board.currentPlayer().getLegalMoves().size(), 20);
        assertFalse(board.currentPlayer().isInCheckMate());
        assertFalse(board.currentPlayer().isInCheck());
    }

    @Test
    public void testScholarsMate() {
        final Board board = new Board(new StandardBoardConfigurator());
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("e4")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("a7"), Board.getCoordinateAtPosition("a6")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("d1"), Board.getCoordinateAtPosition("f3")));
        Player.makeMove(board,  MoveFactory.createMove(board, Board.getCoordinateAtPosition("a6"), Board.getCoordinateAtPosition("a5")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("f1"), Board.getCoordinateAtPosition("c4")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("a5"), Board.getCoordinateAtPosition("a4")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("f3"), Board.getCoordinateAtPosition("f7")));
        assertTrue(board.currentPlayer().isInCheckMate());
    }

    @Test
    public void testLegalsMate() {
        final Board board = new Board(new StandardBoardConfigurator());
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("e4")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("e7"), Board.getCoordinateAtPosition("e5")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("f1"), Board.getCoordinateAtPosition("c4")));
        Player.makeMove(board,  MoveFactory.createMove(board, Board.getCoordinateAtPosition("d7"), Board.getCoordinateAtPosition("d6")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("g1"), Board.getCoordinateAtPosition("f3")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("c8"), Board.getCoordinateAtPosition("g4")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("b1"), Board.getCoordinateAtPosition("c3")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("g7"), Board.getCoordinateAtPosition("g6")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("f3"), Board.getCoordinateAtPosition("e5")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("g4"), Board.getCoordinateAtPosition("d1")));
        Player.makeMove(board,  MoveFactory.createMove(board, Board.getCoordinateAtPosition("c4"), Board.getCoordinateAtPosition("f7")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("e8"), Board.getCoordinateAtPosition("e7")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("c3"), Board.getCoordinateAtPosition("d5")));
        assertTrue(board.currentPlayer().isInCheckMate());
    }

    @Test
    public void testSevenMoveMate() {
        final Board board = new Board(new StandardBoardConfigurator());
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("e4")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("e7"), Board.getCoordinateAtPosition("e5")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("d2"), Board.getCoordinateAtPosition("d4")));
        Player.makeMove(board,  MoveFactory.createMove(board, Board.getCoordinateAtPosition("d7"), Board.getCoordinateAtPosition("d6")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("d4"), Board.getCoordinateAtPosition("e5")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("d8"), Board.getCoordinateAtPosition("e7")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("e5"), Board.getCoordinateAtPosition("d6")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("e7"), Board.getCoordinateAtPosition("e4")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("f1"), Board.getCoordinateAtPosition("e2")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("e4"), Board.getCoordinateAtPosition("g2")));
        Player.makeMove(board,  MoveFactory.createMove(board, Board.getCoordinateAtPosition("d6"), Board.getCoordinateAtPosition("c7")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("g2"), Board.getCoordinateAtPosition("h1")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("d1"), Board.getCoordinateAtPosition("d8")));
        assertTrue(board.currentPlayer().isInCheckMate());
    }

}
