package Menü;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Game.PlayersAndResult;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class BeforeMenuTest {

    private BeforeMenu beforeMenu;
    private String testFileName = "testResults.txt";

    @BeforeEach
    void setUp() {
        //fálj törlése hamár létezik
        java.io.File file = new java.io.File(testFileName);
        if (file.exists()) {
            file.delete();
        }

        beforeMenu = new BeforeMenu(testFileName, new ArrayList<>());
    }

    @Test
    void testLoadResultsFromFile_EmptyFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFileName))) {
            writer.write(""); // Üres fájl
        } catch (IOException e) {
            fail("Error writing empty file: " + e.getMessage());
        }

        // Betöltjük az adatokat
        beforeMenu.loadResultsFromFile();

        // Ellenőrizzük, hogy a results lista üres
        assertTrue(beforeMenu.getResults().isEmpty(), "The results list should be empty for an empty file.");
    }

    @Test
    void testLoadResultsFromFile_ValidData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFileName))) {
            writer.write("player1;player2;0\n"); 
            writer.write("player3;player4;1\n"); 
        } catch (IOException e) {
            fail("Error writing file with valid data: " + e.getMessage());
        }

        beforeMenu.loadResultsFromFile();

        //2 elem van-e
        List<PlayersAndResult> results = beforeMenu.getResults();
        assertEquals(2, results.size(), "There should be 2 results in the list.");

        //eredmények ellenőrzése
        PlayersAndResult firstResult = results.get(0);
        assertEquals("player1", firstResult.getCowPlayer(), "First cow player should be player1.");
        assertEquals("player2", firstResult.getLeopardPlayer(), "First leopard player should be player2.");
        assertTrue(firstResult.getisCowWon(), "First result should indicate the cow won.");

        PlayersAndResult secondResult = results.get(1);
        assertEquals("player3", secondResult.getCowPlayer(), "Second cow player should be player3.");
        assertEquals("player4", secondResult.getLeopardPlayer(), "Second leopard player should be player4.");
        assertFalse(secondResult.getisCowWon(), "Second result should indicate the leopard won.");
    }

    @Test
    void testLoadResultsFromFile_InvalidData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFileName))) {
            writer.write("invaliddata;noresult;\n"); 
        } catch (IOException e) {
            fail("Error writing invalid file: " + e.getMessage());
        }


        beforeMenu.loadResultsFromFile();

        //lista üres-e
        assertTrue(beforeMenu.getResults().isEmpty(), "The results list should be empty for invalid data.");
    }

    @Test
    void testLoadResultsFromFile_MultipleValidLines() {
        //Több sorban érvényes adatok
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFileName))) {
            writer.write("player1;player2;0\n"); 
            writer.write("player3;player4;1\n"); 
            writer.write("player5;player6;0\n"); 
        } catch (IOException e) {
            fail("Error writing file with multiple valid data: " + e.getMessage());
        }

        beforeMenu.loadResultsFromFile();

        List<PlayersAndResult> results = beforeMenu.getResults();
        assertEquals(3, results.size(), "There should be 3 results in the list.");

        assertEquals("player1", results.get(0).getCowPlayer());
        assertEquals("player2", results.get(0).getLeopardPlayer());
        assertTrue(results.get(0).getisCowWon());

        assertEquals("player3", results.get(1).getCowPlayer());
        assertEquals("player4", results.get(1).getLeopardPlayer());
        assertFalse(results.get(1).getisCowWon());

        assertEquals("player5", results.get(2).getCowPlayer());
        assertEquals("player6", results.get(2).getLeopardPlayer());
        assertTrue(results.get(2).getisCowWon());
    }

    @Test
    void testLoadResultsFromFile_FileNotFound() {
        String invalidFileName = "nonExistentFile.txt";
        beforeMenu = new BeforeMenu(invalidFileName, new ArrayList<>());

        //fájlt nem találhatja meg => lista üres
        assertTrue(beforeMenu.getResults().isEmpty(), "The results list should be empty if the file is not found.");
    }
}
