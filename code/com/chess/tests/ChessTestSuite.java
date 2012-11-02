package com.chess.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({TestCheckmate.class, TestStaleMate.class, TestBoard.class, TestPlayer.class, TestKnight.class})
public class ChessTestSuite {
}
