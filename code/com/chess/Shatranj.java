package com.chess;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.StandardBoardConfigurator;
import com.chess.engine.classic.player.MiniMax;
import com.chess.engine.classic.player.Player;

public class Shatranj {

    static int NUM_ITERATIONS = 10000000;

    public static void main(final String args[]) {

        final int i = 6;
        final Board board = new Board(new StandardBoardConfigurator());
        final Player currentPlayer = board.currentPlayer();
        currentPlayer.setMoveStrategy(new MiniMax());
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, i);

//        if (args.length > 0) {
//            NUM_ITERATIONS = Integer.parseInt(args[0]);
//        }
//
//        final Board board = new Board(new StandardBoardConfigurator());
//
//        board.debugPrint();
//        board.printCurrentBoardState();
//
//        while (!board.currentPlayer().isInCheckMate()) {
//            System.out
//                    .println("current player = " + board.currentPlayer() + " check status = " +
//                            board.currentPlayer().isInCheck());
//            board.currentPlayer().printLegalMoves();
//            final Scanner sc = new Scanner(System.in);
//            final int start = Board.getCoordinateAtPosition(sc.next().trim());
//            final int dest = Board.getCoordinateAtPosition(sc.next().trim());
//            final Move move = Move.MoveFactory.createMove(board, start, dest);
//            board.printCurrentBoardState();
//            System.out.println("Making move " + move);
//            Player.makeMove(board, move);
//            board.printCurrentBoardState();
//        }
    }

//    public static void testClassic() {
//
//        System.out.println("TEST CLASSIC BOARD WITH NUM_ITERATIONS = " + NUM_ITERATIONS);
//
//        final Board board = new Board(new StandardBoardConfigurator());
//
//        final long start = System.currentTimeMillis();
//
//        for (int i = 0; i < NUM_ITERATIONS; i++) {
//            board.currentPlayer().calculateLegalMoves();
//        }
//
//        System.out.println(" took " + (System.currentTimeMillis() - start) + " ms");
//        board.printCurrentBoardState();
//        board.currentPlayer().printLegalMoves();
//
//    }
//
//    public static void testBitBoard() {
//
//        System.out.println("TEST BIT BOARD WITH NUM_ITERATIONS = " + NUM_ITERATIONS);
//
//        final BitBoard board = new BitBoard();
//
//        final long start = System.currentTimeMillis();
//
//        for (int i = 0; i < NUM_ITERATIONS; i++) {
//            board.generateStandardLegalMoves();
//        }
//
//        System.out.println(" took " + (System.currentTimeMillis() - start) + " ms");
//        board.printCurrentBoardState();
//        board.printLegalMoves();
//
//    }

}
