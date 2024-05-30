import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;



class StickyNotesWindow {

    private  final int GAP = 10; // Gap between notes
    private  final int NOTE_WIDTH = 400;
    private  final int NOTE_HEIGHT = 200;
    private  final int ICON_WIDTH = 30;
    private  final int ICON_HEIGHT = 30;
    private  int xPos, yPos;
    private  List<JWindow> noteWindows = new ArrayList<>();
    private  List<Note> noteWindowContent = new ArrayList<>();
    private Map<String, Note> noteWindowContentMap = new HashMap<>();
    private Map<String, JWindow> noteWindowMap = new HashMap<>();
    private SaveNotesInFile saveNotesInFileObj;
    
    private int noteX = Toolkit.getDefaultToolkit().getScreenSize().width - ICON_WIDTH;
    private int noteY = GAP;


    public StickyNotesWindow() {
    	saveNotesInFileObj = new SaveNotesInFile();
	}

//    public static void main(String[] args) {
//    
//    	
//        SwingUtilities.invokeLater(() -> {
//            try {
//            	StickyNotesWindow obj = new StickyNotesWindow();
//                obj.start();
//            } catch (IOException | ParseException e) {
//                e.printStackTrace();
//            }
//        });
//    }

    // Start function to display notes on screen
    public void start() throws IOException, ParseException {
    	noteX = Toolkit.getDefaultToolkit().getScreenSize().width - ICON_WIDTH;
        noteY = GAP;
        
//    	System.out.println("start : ");
    	makeInvisible();
        List<Note> notes = readNotesFromJSON("noteData.json");

        int x = noteX;
        int y = noteY;

        for (Note note : notes) {
//        	System.out.println("Note: " + note.note + " ID: " + note.id);
            note.x = x;
            note.y = y;
            note.initX = x;
            note.initY = y;
            createStickyNoteIcon(note);
            noteWindowContent.add(note);
            

//            y += ICON_HEIGHT + GAP;
//            if (y + ICON_HEIGHT + GAP > Toolkit.getDefaultToolkit().getScreenSize().height) {
//                y = GAP;
//                x -= ICON_WIDTH + GAP;
//            }
            
            if (y + ICON_HEIGHT + GAP + 100 > Toolkit.getDefaultToolkit().getScreenSize().height && x==0) {
            	JOptionPane.showMessageDialog(null, "Maximum number of notes reached. Unable to display more notes on screen.", "Sticky Note", JOptionPane.PLAIN_MESSAGE);
                break;
                
            }
            
            y += ICON_HEIGHT + GAP;
            if (y + ICON_HEIGHT + GAP + 100 > Toolkit.getDefaultToolkit().getScreenSize().height) {
                y = GAP;
                x = 0;
                
            }
        }
        noteX = x;
        noteY = y;
    }
    
    
    public void addNewNote(String noteContent,String noteId,Color navbarColor,Color contentColor) throws IOException, ParseException {
//    	System.out.println("add note : ");
//    	makeInvisible();

        int x = noteX;
        int y = noteY;

//        for (Note note : notes) {
        	Note note = new Note();
            note.x = x;
            note.y = y;
            note.initX = x;
            note.initY = y;
            note.note = noteContent;
            note.id = noteContent;
            note.nBarColor = navbarColor.getRGB();
            note.nContentColor=contentColor.getRGB();
            createStickyNoteIcon(note);
            noteWindowContent.add(note);
            

//            y += ICON_HEIGHT + GAP;
//            if (y + ICON_HEIGHT + GAP > Toolkit.getDefaultToolkit().getScreenSize().height) {
//                y = GAP;
//                x -= ICON_WIDTH + GAP;
//            }
            
            if (y + ICON_HEIGHT + GAP + 100 > Toolkit.getDefaultToolkit().getScreenSize().height && x==0) {
            	JOptionPane.showMessageDialog(null, "Maximum number of notes reached. Unable to display more notes on screen.", "Sticky Note", JOptionPane.PLAIN_MESSAGE);
               
            }
            
            y += ICON_HEIGHT + GAP;
            if (y + ICON_HEIGHT + GAP + 100 > Toolkit.getDefaultToolkit().getScreenSize().height) {
                y = GAP;
                x = 0;
                
            }
            
            noteX = x;
            noteY = y;
            
    }
    
    
    public void stickIndividual(Note note,JWindow window) {
 

            JPanel contentPanel = (JPanel) window.getContentPane().getComponent(1); // Assuming contentPanel is at index 1
            contentPanel.setVisible(false);

            JPanel btnPanel = (JPanel) window.getContentPane().getComponent(0);
            JPanel btnContentPanel = (JPanel) btnPanel.getComponent(1); // Assuming btnContentPanel is at index 1
            btnPanel.remove(btnContentPanel);

            window.setSize(ICON_WIDTH, ICON_HEIGHT);
            window.setLocation(note.initX, note.initY);
            window.validate();
            window.repaint();
   
    }
    
   
    


    // Stick function to reposition notes to the right side of the screen
    public  void stick() {

    	for (Map.Entry<String, JWindow> noteWindowEntry : noteWindowMap.entrySet()) {
    		String key = noteWindowEntry.getKey();
            JWindow window = noteWindowEntry.getValue();
            
            Note note = noteWindowContentMap.get(key);
            
//            System.out.println("st "+key+ " "+window+ " "+note);
            
          JPanel contentPanel = (JPanel) window.getContentPane().getComponent(1); // Assuming contentPanel is at index 1
      contentPanel.setVisible(false);
      try {
		
      JPanel btnPanel = (JPanel) window.getContentPane().getComponent(0);
      JPanel btnContentPanel = (JPanel) btnPanel.getComponent(1); // Assuming btnContentPanel is at index 1
    	  
//      btnContentPanel.setVisible(false);
      btnPanel.remove(btnContentPanel);
	} catch (Exception e) {
		// TODO: handle exception
	}

      window.setSize(ICON_WIDTH, ICON_HEIGHT);
      window.setLocation(note.initX, note.initY);
      window.validate();
      window.repaint();
            
        }
//        System.out.println("hello");
//        for(int i=0;i<noteWindows.size();i++){
//
//            JWindow obj = noteWindows.get(i);
//            Note notObj = noteWindowContent.get(i);
//
//             System.out.println("ojb"+ notObj.x+" "+notObj.y);
//            System.out.println("ojb "+ obj);
//
//            // Disable content panel
//            JPanel contentPanel = (JPanel) obj.getContentPane().getComponent(1); // Assuming contentPanel is at index 1
//        contentPanel.setVisible(false);
//        
//        JPanel btnPanel = (JPanel) obj.getContentPane().getComponent(0);
//        JPanel btnContentPanel = (JPanel) btnPanel.getComponent(1); // Assuming btnContentPanel is at index 1
//
////        btnContentPanel.setVisible(false);
//        btnPanel.remove(btnContentPanel);
//
//            obj.setSize(ICON_WIDTH, ICON_HEIGHT);
//            obj.setLocation(notObj.initX, notObj.initY);
//            obj.validate();
//            obj.repaint();
//        }
    }

    public  void makeInvisible() {
        for (JWindow window : noteWindows) {
            window.dispose();
        }
    }
    
    public void removeIndividual(String id) {
    	saveNotesInFileObj.removeNote(id);
    	// Remove from maps
        if (noteWindowContentMap.containsKey(id)) {
            noteWindowContentMap.remove(id);
//            System.out.println("Note removed from noteWindowContentMap.");
        }

        if (noteWindowMap.containsKey(id)) {
            JWindow window = noteWindowMap.remove(id);
            window.dispose();
//            System.out.println("Note window removed and disposed from noteWindowMap.");
        }
    }
    
    
//    public void updateIndividualNote(String id,String newNote) {
//    	saveNotesInFileObj.removeNote(id);
//    	// Remove from maps
//        if (noteWindowContentMap.containsKey(id)) {
//            noteWindowContentMap.remove(id);
//            System.out.println("Note removed from noteWindowContentMap.");
//        }
//
//        if (noteWindowMap.containsKey(id)) {
//            JWindow window = noteWindowMap.remove(id);
//            window.dispose();
//            System.out.println("Note window removed and disposed from noteWindowMap.");
//        }
//    }
    
    
    private ImageIcon getImageToIcon(String iconPath,int wid ,int hei) {
    	try {
            // Load the image from the resources
            java.net.URL imgURL = getClass().getClassLoader().getResource(iconPath);
            if (imgURL == null) {
                throw new IOException("Image not found: " + iconPath);
            }

            // Read the image
            Image photo = ImageIO.read(imgURL);

            // Scale the image
            Image scaledImage = photo.getScaledInstance(wid, hei, Image.SCALE_SMOOTH);

            // Create and return the ImageIcon
            return new ImageIcon(scaledImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private void prepareBtnForIcon(JButton btn) {
    	btn.setContentAreaFilled(false);
    	btn.setBorderPainted(false);
    	btn.setFocusPainted(false);
    	btn.setOpaque(false);
    	btn.setMargin(new Insets(0, 0, 0, 0));
    }

    private  List<Note> readNotesFromJSON(String filePath) throws IOException, ParseException {
//    	System.out.println("file "+filePath);
        JSONParser parser = new JSONParser();
        JSONObject rootObject = (JSONObject) parser.parse(new FileReader(filePath));
        JSONArray jsonArray = (JSONArray) rootObject.get("userNotes");

        List<Note> notes = new ArrayList<>();
        for (Object obj : jsonArray) {
            JSONObject noteObject = (JSONObject) obj;
            Note note = new Note();
            note.note = (String) noteObject.get("note");
            note.id = (String) noteObject.get("id");
            note.nBarColor = ((Number) noteObject.get("nBarColor")).intValue();
            note.nContentColor = ((Number) noteObject.get("nContentColor")).intValue();
            notes.add(note);
        }
        return notes;
    }

    private  void createStickyNoteIcon(Note note) throws IOException {
    	Border border = BorderFactory.createLineBorder(Color.BLACK, 2); // Line border with black color and 2 pixels thickness
        
        
        JWindow window = new JWindow();
        window.setAlwaysOnTop(true);
        window.setSize(ICON_WIDTH, ICON_HEIGHT);
        window.setLayout(new BorderLayout());

        JPanel btnPanel = new JPanel(new GridLayout(1,1));
        JPanel btnMainPanel = new JPanel(new GridLayout(1, 1));
        JPanel btnContentPanel = new JPanel(new GridLayout(1, 3));
        

        JButton button = new JButton("");
          prepareBtnForIcon(button);
        
//      add icon
          button.setIcon(getImageToIcon("paperClip.png",20,20));
          button.setPreferredSize(new Dimension(ICON_WIDTH, ICON_HEIGHT));
          
          btnMainPanel.add(button);
        
        
//        btn COntent panel
        JButton stickbtn = new JButton("");
        prepareBtnForIcon(stickbtn);
        
	//      add icon
	stickbtn.setIcon(getImageToIcon("magnet.png",20,20));
	stickbtn.setPreferredSize(new Dimension(ICON_WIDTH, ICON_HEIGHT));
        
        stickbtn.addActionListener(e -> stickIndividual(note,window));
        
        
//        edit btn 
        JButton editbtn = new JButton("");
        prepareBtnForIcon(editbtn);
        
	//      add icon
        editbtn.setIcon(getImageToIcon("pencil.png",20,20));
        editbtn.setPreferredSize(new Dimension(ICON_WIDTH, ICON_HEIGHT));
	
//	end edit btn
        
        JButton trashkbtn = new JButton("");
        prepareBtnForIcon(trashkbtn);
        
    	//      add icon
        trashkbtn.setIcon(getImageToIcon("trash.png",20,20));
        trashkbtn.setPreferredSize(new Dimension(ICON_WIDTH, ICON_HEIGHT));        
        trashkbtn.addActionListener(e -> removeIndividual(note.id));
        
        

//        btn main panel set
        btnMainPanel.add(button);
        btnContentPanel.add(stickbtn);
        btnContentPanel.add(editbtn);
        btnContentPanel.add(trashkbtn);
        
        btnContentPanel.setVisible(false);
//        btnMainPanel.setBorder(border);

        btnPanel.add(btnMainPanel);
        
        btnPanel.setBackground(new Color(note.nBarColor));
        btnMainPanel.setBackground(new Color(note.nBarColor));
//        btnContentPanel.setBackground(new Color(note.nBarColor));

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setVisible(false);

        JTextArea noteArea = new JTextArea(note.note);
        noteArea.setEditable(false);
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);
        noteArea.setOpaque(false);
        noteArea.setFont(new Font("Arial", Font.PLAIN, 14));
        noteArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(noteArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setOpaque(true);
        scrollPane.getViewport().setBackground(new Color(note.nContentColor));

        int preferredHeight = noteArea.getPreferredSize().height;
        int width = Math.min(NOTE_WIDTH, 300);
        int height = Math.min(preferredHeight + 5, NOTE_HEIGHT);

        scrollPane.setPreferredSize(new Dimension(width, height));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        contentPanel.add(scrollPane, BorderLayout.CENTER);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (contentPanel.isVisible()) {
                    // contentPanel.setVisible(false);
                } else {
                    contentPanel.setVisible(true);
                    btnContentPanel.setVisible(true);
                    btnPanel.add(btnContentPanel);
                    window.pack();
                    window.setLocation(note.x - NOTE_WIDTH, note.y);
                    window.validate();
                    window.repaint();
                }
            }
        });
        
        
        editbtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//            	System.out.println("edit btn");
            	CreateNoteWindow createNoteWindow = new CreateNoteWindow(note.note,note.id);
            	createNoteWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        String noteContent = createNoteWindow.getUserNote();
                        String noteId = createNoteWindow.getUserNoteId();
//                        System.out.println("in editor"+noteId);
                        if (noteContent != null && noteId != null) {
                        	
                            
                        	saveNotesInFileObj.updateNote(noteId,noteContent,createNoteWindow.getNavbarColor(),createNoteWindow.getContentColor());
                        	noteArea.setText(noteContent);
//                        	saveNotesInFileObj.saveJsonToFile();
//                        	getAllNotes();
                        	
//                        	try {
//                        		StickyNotesWidget.start();
//                        	} catch (IOException | ParseException err) {
//                        		err.printStackTrace();
//                        	}
                        }
                    }
                });
            	
          
            }
        });

        window.add(btnPanel, BorderLayout.NORTH);
        window.add(contentPanel, BorderLayout.CENTER);

        window.setLocation(note.x, note.y);
        window.setVisible(true);

        noteWindows.add(window);
//        System.out.println(note+" === "+note.note+"   "+note.id);
        noteWindowMap.put(note.id, window);
        noteWindowContentMap.put(note.id, note);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                xPos = me.getX();
                yPos = me.getY();
            }
        });

        button.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                int deltaX = me.getXOnScreen() - xPos;
                int deltaY = me.getYOnScreen() - yPos;
                window.setLocation(deltaX, deltaY);
            }
        });

        btnPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                xPos = me.getX();
                yPos = me.getY();
            }
        });

        btnPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                int deltaX = me.getXOnScreen() - xPos;
                int deltaY = me.getYOnScreen() - yPos;
                window.setLocation(deltaX, deltaY);
            }
        });

    }

}


