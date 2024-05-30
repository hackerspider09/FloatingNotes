import java.awt.Color;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.JWindow;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class SaveNotesInFile {

    private  JSONObject noteData;
    private static final String FILE_PATH = "noteData.json";
    private static final int MAX_NOTES = 42; // Maximum allowed notes

    public SaveNotesInFile() {
 
        noteData = new JSONObject();
        noteData.put("userNotes", new JSONArray());
        noteData.put("noteWindows", new JSONArray());
        loadFromFile();
    }
    


    // Load existing notes from file
    private void loadFromFile() {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Object obj = parser.parse(reader);
            noteData = (JSONObject) obj;
        } catch (IOException | ParseException e) {
//            System.out.println("No existing notes found or an error occurred while loading notes."+e);
            saveJsonToFile();
        }
    }

    // Method to add a note
    public void addNote(String userNote, Color nBarColor, Color nContentColor) {
        JSONArray notes = (JSONArray) noteData.get("userNotes");
        if (notes.size() < MAX_NOTES) {
            JSONObject newNote = new JSONObject();
            newNote.put("note", userNote);
            newNote.put("nBarColor", nBarColor.getRGB()); // Store color as RGB integer
            newNote.put("nContentColor", nContentColor.getRGB());
            newNote.put("id", UUID.randomUUID().toString());

            notes.add(newNote);
            System.out.println("Note added successfully.");
        } else {
//            System.out.println("Maximum number of notes reached. Cannot add more notes.");
//            JOptionPane dialogBox = new JOptionPane("Maximum number of notes reached. Cannot add more notes.", )
            JOptionPane.showMessageDialog(null,"Maximum number of notes reached. Cannot add more notes.","Sticky Note",JOptionPane.PLAIN_MESSAGE);
        }
    }

 // Method to remove a note by ID
    public void removeNote(String id) {
    	loadFromFile();   // Use for some notes as they created but on remove them they are not deleting similar in UpdateNote
        JSONArray notes = (JSONArray) noteData.get("userNotes");
        for (int i = 0; i < notes.size(); i++) {
            JSONObject note = (JSONObject) notes.get(i);
            if (note.get("id").equals(id)) {
                notes.remove(i);
                System.out.println("Note removed successfully.");
                saveJsonToFile();
                return;
            }
        }
        System.out.println("Note with ID " + id + " not found.");
    }
    
    public void updateNote(String id, String newNote,Color nBarColor, Color nContentColor) {
    	loadFromFile();
        JSONArray notes = (JSONArray) noteData.get("userNotes");
        for (int i = 0; i < notes.size(); i++) {
            JSONObject note = (JSONObject) notes.get(i);
//            System.out.println(newNote);
//            System.out.println("id " + id + " note " + note.get("id"));
            if (note.get("id").equals(id)) {
                note.put("note", newNote); // Replace the note content
                note.put("nBarColor", nBarColor.getRGB()); // Store color as RGB integer
                note.put("nContentColor", nContentColor.getRGB());
                System.out.println("Note edited successfully.");
                saveJsonToFile();
                return;
            }
        }
        
        System.out.println("Note with ID " + id + " not found.");
    }


    // Method to get the JSON data as a string
    public String getJsonData() {
        return noteData.toJSONString();
    }

    // Method to get the JSON data object
    public JSONObject getJsonObject() {
        return noteData;
    }

    // Method to save JSON data to a file
    public void saveJsonToFile() {
        try (FileWriter file = new FileWriter(FILE_PATH)) {
            file.write(noteData.toJSONString());
            file.flush();
            System.out.println("JSON data saved to " + FILE_PATH);
        } catch (IOException e) {
            System.out.println("An error occurred while saving JSON data to file.");
            e.printStackTrace();
        }
    }
    
 
    

    // Main method for testing
//    public static void main(String[] args) {
//        StoreNotes notes = new StoreNotes();
//        for (int i = 0; i < 40; i++) {
//            notes.addNote("Note " + (i + 1), Color.red, Color.white);
//        }
//        notes.saveJsonToFile();
//        System.out.println("JSON Data after additions:");
//        System.out.println(notes.getJsonData());
//
//        notes.removeNote(0);
//        notes.saveJsonToFile();
//        System.out.println("JSON Data after removal:");
//        System.out.println(notes.getJsonData());
//    }
}
