package com.chess.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.MoveStatus;
import com.chess.engine.classic.board.BoardConfigurator;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.Move.MoveFactory;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Pawn;
import com.chess.engine.classic.player.MiniMax;
import com.chess.engine.classic.player.Player;
import com.chess.engine.classic.board.StandardBoardConfigurator;
import com.chess.engine.classic.player.SimpleBoardEvaluator;

public class TestPlayer {

    @Test
    public void testMinimax1() {
        final int i = 2;
        final Board board = new Board(new StandardBoardConfigurator());
        final Player currentPlayer = board.currentPlayer();
        currentPlayer.setMoveStrategy(new MiniMax());
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, i);
        assertEquals(bestMove, MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("e4")));
    }

    @Test
    public void testMinimax2() {
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
        final int i = 2;
        final Player currentPlayer = board.currentPlayer();
        currentPlayer.setMoveStrategy(new MiniMax());
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, i);
        assertEquals(bestMove, MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("e4")));
    }

    @Test
    public void testSimpleEvaluation() {
        final Board board = new Board(new StandardBoardConfigurator());
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("e4")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("e7"), Board.getCoordinateAtPosition("e5")));
        assertEquals(new SimpleBoardEvaluator().invoke(board), 0);
    }

    @Test
    public void testBug() {
        final Board board = new Board(new StandardBoardConfigurator());
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("c2"), Board.getCoordinateAtPosition("c3")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("b8"), Board.getCoordinateAtPosition("a6")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("d1"), Board.getCoordinateAtPosition("a4")));
        assertEquals(Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("d7"), Board.getCoordinateAtPosition("d6"))), MoveStatus.ILLEGAL_LEAVES_PLAYER_IN_CHECK);
    }

}
