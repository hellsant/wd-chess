package com.chess.engine.classic.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.MoveStatus;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Piece;

public abstract class Player {

    protected Board board;
    protected final List<Move> legalMoves;
    private boolean isInCheck;
    private boolean isInCheckMate;
    private boolean isInstaleMate;
    private MoveStrategy strategy;

    Player(final Board board) {
        this.board = board;
        this.legalMoves = new ArrayList<Move>();
    }

    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }

    public boolean isInCheck() {
        return this.isInCheck;
    }

    public boolean isInCheckMate() {
        return this.isInCheckMate;
    }

    public boolean isInStaleMate() {
        return this.isInstaleMate;
    }

    public MoveStrategy getMoveStrategy() {
        return this.strategy;
    }

    public static MoveStatus makeMove(final Board board,
                                      final Move move) {
        if(!board.currentPlayer().isMoveLegal(move)) {
            return MoveStatus.ILLEGAL_NOT_IN_MOVES_LIST;
        }
        final Player currentPlayer = board.currentPlayer();
        final Player opponent = currentPlayer.getOpponent();
        move.execute(board);
        final List<Move> kingAttacks = currentPlayer.calculateAttacksOnTile(currentPlayer.getPlayerKing().getPiecePosition());
        final MoveStatus moveStatus = kingAttacks.isEmpty() ? MoveStatus.DONE : MoveStatus.ILLEGAL_LEAVES_PLAYER_IN_CHECK;
        if (moveStatus != MoveStatus.DONE) {
            move.undo(board);
            opponent.calculateLegalMoves();
            return moveStatus;
        }
        currentPlayer.calculateLegalMoves();
        opponent.calculateCheckStatus(board);
        currentPlayer.switchPlayer();
        return moveStatus;
    }

    public static MoveStatus unMakeMove(final Board board,
                                        final Move move) {
        final Player currentPlayer = board.currentPlayer();
        final Player opponent = currentPlayer.getOpponent();
        move.undo(board);
        currentPlayer.calculateLegalMoves();
        opponent.calculateLegalMoves();
        opponent.calculateCheckStatus(board);
        currentPlayer.switchPlayer();
        return MoveStatus.UNDONE;
    }

    private void calculateCheckStatus(final Board board) {
        final int kingPos = getPlayerKing().getPiecePosition();
        final List <Move> kingAttacks = calculateAttacksOnTile(kingPos);
        final boolean hasEscapeMoves = hasEscapeMoves(board);
        if(!kingAttacks.isEmpty()) {
            this.isInCheck = true;
            this.isInCheckMate = !(hasEscapeMoves);
            this.isInstaleMate = false;
        } else {
            this.isInCheck = false;
            this.isInCheckMate = false;
            this.isInstaleMate = !(hasEscapeMoves);
        }
    }

    private boolean hasEscapeMoves(final Board board) {
        for(final Move move : this.legalMoves) {
            move.execute(board);
            getOpponent().calculateLegalMoves();
            final List <Move> kingAttacks = calculateAttacksOnTile(getPlayerKing().getPiecePosition());
            if(kingAttacks.isEmpty()) {
                move.undo(board);
                return true;
            }
            move.undo(board);
        }
        return false;
    }

    private List <Move> calculateAttacksOnTile(final int tile) {
        final Player opponent = getOpponent();
        opponent.calculateLegalMoves();
        final List <Move> moves = new ArrayList<Move>();
        for (final Move move : opponent.getLegalMoves()) {
            if (tile == move.getDestinationCoordinate()) {
                moves.add(move);
            }
        }
        return (moves);
    }

    public List<Move> getLegalMoves() {
        return this.legalMoves;
    }

    public void setMoveStrategy(MoveStrategy strategy) {
        this.strategy = strategy;
    }

    public void printLegalMoves() {
        System.out.println(this.legalMoves.size() + " legals moves ");
        Collections.sort(this.legalMoves);
        for(final Move move : this.legalMoves) {
            System.out.print(move + ",");
        }
        System.out.println();
    }

    public abstract List<Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();
    public abstract King getPlayerKing();
    public abstract void calculateLegalMoves();
    public abstract void switchPlayer();

}
