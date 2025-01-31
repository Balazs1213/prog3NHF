package Game;

import org.junit.jupiter.api.*;

import Puppets.CowPuppet;
import Puppets.LeopardPuppet;

import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.*;

class SaveGameTest {

    private final String testFilename = "TestSavedGame.txt";
    private List<CowPuppet> testCows;
    private LeopardPuppet testLeopard;
    private PlayersAndResult testPlayers;
    private List<PlayersAndResult> testResults;

    @BeforeEach
    void setUp() {
        // Alapértelmezett állapot létrehozása
        testCows = new ArrayList<>();
        testCows.add(new CowPuppet(0, 1, 0));
        testCows.add(new CowPuppet(0, 3, 0));
        
        testLeopard = new LeopardPuppet(1, 4, 1);
        testPlayers = new PlayersAndResult("CowPlayer", "LeopardPlayer", true);
        testResults = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        // Teszt fájl törlése, ha létezik
        File file = new File(testFilename);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testSaveToFile() {
        SaveGame saveGame = new SaveGame(testCows, testLeopard, testPlayers, testResults, testFilename);

        // Save to file
        saveGame.saveToFile();

        // Ellenőrizzük, hogy a fájl létezik és nem üres
        File file = new File(testFilename);
        assertTrue(file.exists(), "The save file should exist.");
        assertTrue(file.length() > 0, "The save file should not be empty.");
        
    }

    @Test
    void testLoadFromFile() {
        // Először mentsük el az adatokat
        SaveGame saveGame = new SaveGame(testCows, testLeopard, testPlayers, testResults, testFilename);
        saveGame.saveToFile();

        // Olvassuk be az adatokat
        SaveGame loadedGame = new SaveGame(testFilename, testResults);

        // Ellenőrizzük az adatok egyezését
        assertEquals(2, loadedGame.getCows().size(), "There should be 2 cows loaded.");
        assertEquals(1, loadedGame.getLeopard().getPlayer(), "The leopard's player ID should match.");
        assertEquals("CowPlayer", loadedGame.getCowPlayer(), "The cow player name should match.");
        assertEquals("LeopardPlayer", loadedGame.getLeopardPlayer(), "The leopard player name should match.");
    }

 
   @Test
    void testLoadFromInvalidFile() {
        // Nem létező fájl betöltése
        SaveGame saveGame = new SaveGame("NonExistentFile.txt", testResults);

        // Ellenőrizzük, hogy az alapértelmezett értékek kerültek-e betöltésre
        assertTrue(saveGame.getCows().isEmpty(), "The cow list should be empty.");
        assertNull(saveGame.getLeopard(), "The leopard should be null.");
        assertNull(saveGame.getCowPlayer(), "The cow player name should be null.");
        assertNull(saveGame.getLeopardPlayer(), "The leopard player name should be null.");
    }
}
