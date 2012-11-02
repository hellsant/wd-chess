package com.chess.engine.classic.player;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.MoveStatus;
import com.chess.engine.classic.board.Move;

public class MiniMax implements MoveStrategy {

    BoardEvalulator evalulator;

    public MiniMax() {
        evalulator = new SimpleBoardEvaluator();
    }

    public Move execute(final Board board,
                        final int depth) {
        final Player currentPlayer = board.currentPlayer();
        final Alliance alliance = currentPlayer.getAlliance();
        Move best_move = null;
        int highest_seen_value = Integer.MIN_VALUE;
        int lowest_seen_value = Integer.MAX_VALUE;
        int current_value = 0;
        for (final Move move : board.currentPlayer().getLegalMoves()) {
            final Board imaginary = board.createCopy();
            final MoveStatus status = Player.makeMove(imaginary, move);
            current_value = alliance == Alliance.WHITE ? min(imaginary, depth - 1) : max(imaginary, depth - 1);
            System.out.println(toString() + " move " + move + " scores " + current_value);
            if (alliance == Alliance.WHITE &&
                    current_value >= highest_seen_value && status == MoveStatus.DONE) {
                highest_seen_value = current_value;
                best_move = move;
            }
            else if (alliance == Alliance.BLACK &&
                        current_value <= lowest_seen_value && status == MoveStatus.DONE) {
                lowest_seen_value = current_value;
                best_move = move;
            }
        }
        System.out.println(board.currentPlayer() + " SELECTS " + best_move + " score = " + highest_seen_value);
        return best_move;
    }

    public int min(final Board board, final int depth) {
        if (depth == 0 ||
                board.currentPlayer().isInCheckMate() ||
                board.currentPlayer().getOpponent().isInCheckMate()) {
            return this.evalulator.invoke(board);
        }
        int lowest_seen_value = Integer.MAX_VALUE;
        for (final Move move : board.currentPlayer().getLegalMoves()) {
            final Board imaginary = board.createCopy();
            final MoveStatus status = Player.makeMove(imaginary, move);
            final int current_value = max(imaginary, depth - 1);
            if (current_value <= lowest_seen_value && status == MoveStatus.DONE) {
                lowest_seen_value = current_value;
            }
        }
        return lowest_seen_value;
    }

    public int max(final Board board, final int depth) {
        if (depth == 0 ||
                board.currentPlayer().isInCheckMate() ||
                board.currentPlayer().getOpponent().isInCheckMate()) {
            return this.evalulator.invoke(board);
        }
        int highest_seen_value = Integer.MIN_VALUE;
        for (final Move move : board.currentPlayer().getLegalMoves()) {
            final Board imaginary = board.createCopy();
            final MoveStatus status = Player.makeMove(imaginary, move);
            final int current_value = min(imaginary, depth - 1);
            if (current_value >= highest_seen_value && status == MoveStatus.DONE) {
                highest_seen_value = current_value;
            }
        }
        return highest_seen_value;
    }

    @Override
    public String toString() {
        return "MiniMax";
    }
}
