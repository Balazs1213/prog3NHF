package Game;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class EndOfTheGameTest {
    private String testFilename;
    private List<PlayersAndResult> testResults;
    private PlayersAndResult testGame;

    @BeforeEach
    void setUp() throws IOException {
        testFilename = "testResults.txt";
        testResults = new ArrayList<>();
        testGame = new PlayersAndResult("CowPlayer", "LeopardPlayer", true); // Cow nyert
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(testFilename));
    }

    @Test
    void testSaveGame() throws IOException {
        // Inicializáljuk az EndOfTheGame osztályt
        EndOfTheGame endOfTheGame = new EndOfTheGame(testResults, testGame, testFilename);
        endOfTheGame.saveGame();

        // Ellenőrizzük a fájl tartalmát
        List<String> lines = Files.readAllLines(Paths.get(testFilename));
        assertEquals(1, lines.size(), "The file should contain exactly one line.");
        assertEquals("CowPlayer;LeopardPlayer;0;", lines.get(0), "The content of the file is incorrect.");
    }

    @Test
    void testSaveGameWithMultipleResults() throws IOException {
        // Több eredmény hozzáadása
        testResults.add(new PlayersAndResult("Player1", "Player2", false)); // Leopard nyert
        testResults.add(new PlayersAndResult("Player3", "Player4", true));  // Cow nyert

        // Inicializáljuk az EndOfTheGame osztályt
        EndOfTheGame endOfTheGame = new EndOfTheGame(testResults, testGame, testFilename);
        endOfTheGame.saveGame();

        // Ellenőrizzük a fájl tartalmát
        List<String> lines = Files.readAllLines(Paths.get(testFilename));
        assertEquals(3, lines.size(), "The file should contain three lines.");
        assertEquals("CowPlayer;LeopardPlayer;0;", lines.get(0), "The first line is incorrect.");
        assertEquals("Player1;Player2;1;", lines.get(1), "The second line is incorrect.");
        assertEquals("Player3;Player4;0;", lines.get(2), "The third line is incorrect.");
    }
}
