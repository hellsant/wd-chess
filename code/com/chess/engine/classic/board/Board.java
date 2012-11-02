package com.chess.engine.classic.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.classic.player.BlackPlayer;
import com.chess.engine.classic.player.Player;
import com.chess.engine.classic.player.WhitePlayer;

public final class Board {

    private final Tile[] gameBoard;
    private final List<Piece> whitePieces;
    private final List<Piece> blackPieces;
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private Player currentPlayer;
    private final int boardOrientation;
    private static Map<String, Integer> positionToCoordinateMap = new HashMap<String, Integer>();

    private static final String[] algebreicNotation = {
        "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
        "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
        "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
        "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
        "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
        "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
        "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
        "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"
    };

    public static final int NUM_TILES = 64;
    public static final boolean[] firstColumn = new boolean[NUM_TILES];
    public static final boolean[] secondColumn = new boolean[NUM_TILES];
    public static final boolean[] thirdColumn = new boolean[NUM_TILES];
    public static final boolean[] fourthColumn = new boolean[NUM_TILES];
    public static final boolean[] fifthColumn = new boolean[NUM_TILES];
    public static final boolean[] sixthColumn = new boolean[NUM_TILES];
    public static final boolean[] seventhColumn = new boolean[NUM_TILES];
    public static final boolean[] eighthColumn = new boolean[NUM_TILES];
    public static final boolean[] firstRow = new boolean[NUM_TILES];
    public static final boolean[] secondRow = new boolean[NUM_TILES];
    public static final boolean[] thirdRow = new boolean[NUM_TILES];
    public static final boolean[] fourthRow = new boolean[NUM_TILES];
    public static final boolean[] fifthRow = new boolean[NUM_TILES];
    public static final boolean[] sixthRow = new boolean[NUM_TILES];
    public static final boolean[] seventhRow = new boolean[NUM_TILES];
    public static final boolean[] eighthRow = new boolean[NUM_TILES];

    static {
        initializeGeneralRegionCoordinates();
        initializePositionToCoordinateMap();
    }

    public Board(final BoardConfigurator configurator) {
        this.gameBoard = new Tile[NUM_TILES];
        this.boardOrientation = 0;
        this.whitePieces = new ArrayList<Piece>();
        this.blackPieces = new ArrayList<Piece>();
        for (int i = 0; i < NUM_TILES; i++) {
            this.gameBoard[i] = new Tile(i);
        }
        configurator.configure(this);
        calculateActivePieces();
        this.whitePlayer = new WhitePlayer(this);
        this.blackPlayer = new BlackPlayer(this);
        this.currentPlayer = this.whitePlayer;
        this.whitePlayer.calculateLegalMoves();
        this.blackPlayer.calculateLegalMoves();
    }

    private Board (final Board board) {
        this.gameBoard = new Tile[NUM_TILES];
        this.boardOrientation = board.boardOrientation;
        this.whitePieces = new ArrayList<Piece>();
        this.blackPieces = new ArrayList<Piece>();
        final Tile[] boardTiles = board.getGameBoard();
        for (int i = 0; i < boardTiles.length; i++) {
            this.gameBoard[i] = new Tile(boardTiles[i]);
        }
        calculateActivePieces();
        this.whitePlayer = new WhitePlayer(this);
        this.blackPlayer = new BlackPlayer(this);
        this.currentPlayer = board.currentPlayer() == board.whitePlayer() ? this.whitePlayer : this.blackPlayer;
        this.whitePlayer.calculateLegalMoves();
        this.blackPlayer.calculateLegalMoves();
    }

    public Board createCopy() {
        return new Board(this);
    }

    public List<Piece> getBlackPieces() {
        return this.blackPieces;
    }

    public List<Piece> getWhitePieces() {
        return this.whitePieces;
    }

    public WhitePlayer whitePlayer() {
        return this.whitePlayer;
    }

    public BlackPlayer blackPlayer() {
        return this.blackPlayer;
    }

    public Player currentPlayer() {
        return this.currentPlayer;
    }

    public void clearGameBoard() {
        for (final Tile tile : this.gameBoard) {
            if (tile.isTileOccupied()) {
                tile.removePiece();
            }
        }
    }

    public Tile getTile(final int coordinate) {
        return this.gameBoard[coordinate];
    }

    public Tile[] getGameBoard() {
        return this.gameBoard;
    }

    public int getBoardOrientation() {
        return this.boardOrientation;
    }

    public int size() {
        return this.gameBoard.length;
    }

    public void setCurrentPlayer(final Player player) {
        this.currentPlayer = player;
    }

    public void calculateActivePieces() {
        for (final Tile t : this.gameBoard) {
            if (t.isTileOccupied()) {
                final Piece p = t.getPiece();
                switch(p.getPieceAllegiance()) {
                case WHITE:
                    this.whitePieces.add(p);
                    break;
                case BLACK:
                    this.blackPieces.add(p);
                    break;
                default:
                    throw new RuntimeException("wtf");
                }
            }
        }
    }

    public void generateCastles() {
        //final Tile whiteKingTile = gameBoard[60];
        //final Tile blackKingTile = gameBoard[4];
        //
        //		if (whiteKingTile.isTileOccupied()) {
        //			final Piece whiteKing = whiteKingTile.getPiece();
        //			if (whiteKingTile.getPiece().isKing()) {
        //				legalMoves.addAll(((King) whiteKing).calculateCastles(this));
        //			}
        //		}
        //
        //		if (blackKingTile.isTileOccupied()) {
        //			final Piece blackKing = blackKingTile.getPiece();
        //			if (blackKing.isKing()) {
        //				legalMoves.addAll(((King) blackKing).calculateCastles(this));
        //			}
        //		}
    }

    public void printCurrentBoardState() {
        System.out.println("--------------------------");
        for (int i = 0; i < this.gameBoard.length; i++) {
            System.out.print(this.gameBoard[i]);
            if ((i + 1) % 8 == 0) {
                System.out.print("\n");
            }
        }
        System.out.println("--------------------------");
    }

    public void debugPrint() {
        System.out.println("--------------------------");
        for (int i = 0; i < this.gameBoard.length; i++) {
            System.out.print(i + " ");
            if ((i + 1) % 8 == 0) {
                System.out.print("\n");
            }
        }
        System.out.println("--------------------------");
    }

    public void setPiece(final int coordinate, final Piece piece) {
        this.gameBoard[coordinate].setPiece(piece);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < NUM_TILES; i++) {
            s.append(String.format("%2s", this.gameBoard[i]));
            if ((i + 1) % 8 == 0) {
                s.append("\n");
            }
        }
        return s.toString();
    }

    public static void initializePositionToCoordinateMap() {
        for (int i = 0; i < algebreicNotation.length; i++) {
            positionToCoordinateMap.put(algebreicNotation[i], new Integer(i));
        }
        positionToCoordinateMap = Collections.unmodifiableMap(positionToCoordinateMap);
    }

    public static void initializeGeneralRegionCoordinates() {

        firstColumn[0] = true;
        firstColumn[8] = true;
        firstColumn[16] = true;
        firstColumn[24] = true;
        firstColumn[32] = true;
        firstColumn[40] = true;
        firstColumn[48] = true;
        firstColumn[56] = true;

        secondColumn[1] = true;
        secondColumn[9] = true;
        secondColumn[17] = true;
        secondColumn[25] = true;
        secondColumn[33] = true;
        secondColumn[41] = true;
        secondColumn[49] = true;
        secondColumn[57] = true;

        thirdColumn[2] = true;
        thirdColumn[10] = true;
        thirdColumn[18] = true;
        thirdColumn[26] = true;
        thirdColumn[34] = true;
        thirdColumn[42] = true;
        thirdColumn[50] = true;
        thirdColumn[58] = true;

        fourthColumn[3] = true;
        fourthColumn[11] = true;
        fourthColumn[19] = true;
        fourthColumn[27] = true;
        fourthColumn[35] = true;
        fourthColumn[43] = true;
        fourthColumn[51] = true;
        fourthColumn[59] = true;

        fifthColumn[4] = true;
        fifthColumn[12] = true;
        fifthColumn[20] = true;
        fifthColumn[28] = true;
        fifthColumn[36] = true;
        fifthColumn[44] = true;
        fifthColumn[52] = true;
        fifthColumn[60] = true;

        sixthColumn[5] = true;
        sixthColumn[13] = true;
        sixthColumn[21] = true;
        sixthColumn[29] = true;
        sixthColumn[37] = true;
        sixthColumn[45] = true;
        sixthColumn[53] = true;
        sixthColumn[61] = true;

        seventhColumn[6] = true;
        seventhColumn[14] = true;
        seventhColumn[22] = true;
        seventhColumn[30] = true;
        seventhColumn[38] = true;
        seventhColumn[46] = true;
        seventhColumn[54] = true;
        seventhColumn[62] = true;

        eighthColumn[7] = true;
        eighthColumn[15] = true;
        eighthColumn[23] = true;
        eighthColumn[31] = true;
        eighthColumn[39] = true;
        eighthColumn[47] = true;
        eighthColumn[55] = true;
        eighthColumn[63] = true;

        firstRow[0] = true;
        firstRow[1] = true;
        firstRow[2] = true;
        firstRow[3] = true;
        firstRow[4] = true;
        firstRow[5] = true;
        firstRow[6] = true;
        firstRow[7] = true;

        secondRow[8] = true;
        secondRow[9] = true;
        secondRow[10] = true;
        secondRow[11] = true;
        secondRow[12] = true;
        secondRow[13] = true;
        secondRow[14] = true;
        secondRow[15] = true;

        thirdRow[16] = true;
        thirdRow[17] = true;
        thirdRow[18] = true;
        thirdRow[19] = true;
        thirdRow[20] = true;
        thirdRow[21] = true;
        thirdRow[22] = true;
        thirdRow[23] = true;

        fourthRow[24] = true;
        fourthRow[25] = true;
        fourthRow[26] = true;
        fourthRow[27] = true;
        fourthRow[28] = true;
        fourthRow[29] = true;
        fourthRow[30] = true;
        fourthRow[31] = true;

        fifthRow[32] = true;
        fifthRow[33] = true;
        fifthRow[34] = true;
        fifthRow[35] = true;
        fifthRow[36] = true;
        fifthRow[37] = true;
        fifthRow[38] = true;
        fifthRow[39] = true;

        sixthRow[40] = true;
        sixthRow[41] = true;
        sixthRow[42] = true;
        sixthRow[43] = true;
        sixthRow[44] = true;
        sixthRow[45] = true;
        sixthRow[46] = true;
        sixthRow[47] = true;

        seventhRow[48] = true;
        seventhRow[49] = true;
        seventhRow[50] = true;
        seventhRow[51] = true;
        seventhRow[52] = true;
        seventhRow[53] = true;
        seventhRow[54] = true;
        seventhRow[55] = true;

        eighthRow[56] = true;
        eighthRow[57] = true;
        eighthRow[58] = true;
        eighthRow[59] = true;
        eighthRow[60] = true;
        eighthRow[61] = true;
        eighthRow[62] = true;
        eighthRow[63] = true;

    }

    public static final int getCoordinateAtPosition(final String pos) {
        return positionToCoordinateMap.get(pos);
    }

    public static final String getPositionAtCoordinate(final int c) {
        return algebreicNotation[c];
    }

    public enum MoveStatus {
        DONE,
        ILLEGAL_NOT_IN_MOVES_LIST,
        ILLEGAL_LEAVES_PLAYER_IN_CHECK,
        UNDONE;
    }

}
