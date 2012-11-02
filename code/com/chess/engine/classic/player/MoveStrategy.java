package com.chess.engine.classic.player;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;

public interface MoveStrategy {

    public Move execute(Board board, int depth);

}
