package com.chess.engine.classic;

public enum Alliance {

    WHITE() {
        @Override
        public int getVector() {
            return 1;
        }

        @Override
        public String toString() {
            return "White";
        }

        @Override
        public int pawnBonus(int position) {
            return WHITE_PAWN_PREFERRED_COORDINATES[position];
        }

        @Override
        public int knightBonus(int position) {
            return 0;
        }

        @Override
        public int bishopBonus(int position) {
            return 0;
        }

        @Override
        public int rookBonus(int position) {
            return 0;
        }

        @Override
        public int queenBonus(int position) {
            return 0;
        }

        @Override
        public int kingBonus(int position) {
            return 0;
        }

    },
    BLACK() {
        @Override
        public int getVector() {
            return -1;
        }

        @Override
        public String toString() {
            return "Black";
        }

        @Override
        public int pawnBonus(int position) {
            return BLACK_PAWN_PREFERRED_COORDINATES[position];
        }

        @Override
        public int knightBonus(int position) {
            return 0;
        }

        @Override
        public int bishopBonus(int position) {
            return 0;
        }

        @Override
        public int rookBonus(int position) {
            return 0;
        }

        @Override
        public int queenBonus(int position) {
            return 0;
        }

        @Override
        public int kingBonus(int position) {
            return 0;
        }
    };

    public abstract int getVector();

    public abstract int pawnBonus(int position);

    public abstract int knightBonus(int position);

    public abstract int bishopBonus(int position);

    public abstract int rookBonus(int position);

    public abstract int queenBonus(int position);

    public abstract int kingBonus(int position);

    public final static int[] WHITE_PAWN_PREFERRED_COORDINATES = {
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 7, 7, 0, 0, 0,
        0, 0, 0, 20, 20, 0, 0, 0,
        0, 0, 5, 7, 7, 5, 0, 0,
        0, 0, -5, -10, -10, -5, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0
    };

    public final static int[] BLACK_PAWN_PREFERRED_COORDINATES = {
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, -5, -10, -10, -5, 0, 0,
        0, 0, 5, 7, 7, 5, 0, 0,
        0, 0, 0, 20, 20, 0, 0, 0,
        0, 0, 0, 7, 7, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0
    };

    public final static int[] WHITE_KNIGHT_PREFERRED_COORDINATES = { -10, -5,
        0, 0, 0, 0, -5, -10, -5, 5, 10, 10, 10, 10, 5, -5, -5, 5, 15, 15,
        15, 15, 5, -5, -5, 5, 15, 15, 15, 15, 5, -5, -5, 0, 10, 10, 10, 10,
        5, -5, -5, 0, 5, 10, 10, 5, 0, -5, -10, 0, 0, 0, 0, 0, 0, -10, -20,
        -5, -5, -5, -5, -5, -5, -20 };

    public final static int[] BLACK_KNIGHT_PREFERRED_COORDINATES = { -20, -5,
        -5, -5, -5, -5, -5, -20, -10, 0, 0, 0, 0, 0, 0, -10, -5, 0, 5, 10,
        10, 5, 0, -5, -5, 0, 10, 10, 10, 10, 5, -5, -5, 5, 15, 15, 15, 15,
        5, -5, -5, 5, 15, 15, 15, 15, 5, -5, -5, 5, 10, 10, 10, 10, 5, -5,
        -10, -5, 0, 0, 0, 0, -5, -10, };

    public final static int[] WHITE_QUEEN_PREFFERED_COORDINATES = { -10, 0, 0,
        0, 0, 0, 0, -10, -10, 0, 0, 0, 0, 0, 0, -10, -10, 0, 5, 5, 5, 5, 0,
        -10, -10, 0, 5, 10, 10, 5, 0, -10, -10, 0, 5, 10, 10, 5, 0, -10,
        -10, 0, 5, 5, 5, 5, 0, -10, -10, 0, 0, 0, 0, 0, 0, -10, -10, 0, 0,
        0, 0, 0, 0, -10 };

    public final static int[] BLACK_QUEEN_PREFFERED_COORDINATES = { -10, 0, 0,
        0, 0, 0, 0, -10, -10, 0, 0, 0, 0, 0, 0, -10, -10, 0, 5, 5, 5, 5, 0,
        -10, -10, 0, 5, 10, 10, 5, 0, -10, -10, 0, 5, 10, 10, 5, 0, -10,
        -10, 0, 5, 5, 5, 5, 0, -10, -10, 0, 0, 0, 0, 0, 0, -10, -10, 0, 0,
        0, 0, 0, 0, -10 };

}