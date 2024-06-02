import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TestWindow  {
	private JDialog createNoteMainWindow;
    private JTextArea textArea;
    private JPanel buttonPanel;
    private String userNote;
    private Color userNBarColor =  Color.decode("#cacbce");
    private Color userContentAreaColor = Color.white;
    private int xPos, yPos;		// Use co ordinates for frame while dragging
    private String id = null;

    // Constructor 1 : use in create note
    public TestWindow() {
    	
        noteWindow();
    }
    
    // Constructor 2 : use in edit
    public TestWindow(String note,String id) {
//        JFrame.setDefaultLookAndFeelDecorated(true);
        noteWindow();
        textArea.setText(note);
        this.id = id;
    }
    
    
    
    public void noteWindow() {
//    	setUndecorated(true);
    	createNoteMainWindow = new JDialog();
//    	createNoteMainWindow.setDefaultLookAndFeelDecorated(false);
    	createNoteMainWindow.setUndecorated(true);

        JPanel mainPanel = new JPanel(new BorderLayout()); // Main panel

        // Panel for color blocks
        JPanel colorPanel = new JPanel(new GridLayout(1, 5)); 
        colorPanel.setPreferredSize(new Dimension(400, 20));
        // Add color blocks
        colorPanel.add(createColorBlock(Color.decode("#ff3d94"), Color.decode("#ff7eb9"))); 
        colorPanel.add(createColorBlock(Color.decode("#00dce0"),Color.decode("#7afcff")));
        colorPanel.add(createColorBlock(Color.decode("#003fff"),Color.decode("#5881fc")));
        colorPanel.add(createColorBlock(Color.decode("#f6ff00"),Color.decode("#feff9c")));
        colorPanel.add(createColorBlock(Color.decode("#ff6e00"),Color.decode("#f99b52")));

        // Panel for text area
        JPanel textAreaPanel = new JPanel(new BorderLayout()); 
        textAreaPanel.setPreferredSize(new Dimension(400, 300));
        
        // Textarea component
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        textAreaPanel.add(textArea, BorderLayout.CENTER);
        
        createNoteMainWindow.addKeyListener(new KeyAdapter() {
        	public void keyPressed(KeyEvent e) {
        		textArea.setText(textArea.getText()+e.getKeyChar());
            }
		});
        
        // Scrollpane component
//        JScrollPane scrollPane = new JScrollPane(textArea);
//        textAreaPanel.add(scrollPane, BorderLayout.CENTER);
//        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        
        

        // Btn panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        // Btns
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        // save btn listener
        saveButton.addActionListener(e -> {
            userNote = textArea.getText();
            createNoteMainWindow.dispose(); 
        });

        // cancel btn listener
        cancelButton.addActionListener(e -> {
        	createNoteMainWindow.dispose(); // Close the note window without saving
        });
        
        // Add btns to button panel
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        // Add color panel, textarea panel, button panel in main panel 
        mainPanel.add(colorPanel, BorderLayout.NORTH);
        mainPanel.add(textAreaPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame
        createNoteMainWindow.getContentPane().add(mainPanel);
        
        // Listener for frame use while dragging to drag smoothly
        createNoteMainWindow.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                xPos = me.getX();
                yPos = me.getY();
            }
        });

        createNoteMainWindow.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                int deltaX = me.getXOnScreen() - xPos;
                int deltaY = me.getYOnScreen() - yPos;
                createNoteMainWindow.setLocation(deltaX, deltaY);
            }
        });

        if (createNoteMainWindow.getLayeredPane().getComponentCount() > 1) {
        	createNoteMainWindow.getLayeredPane().getComponent(1).setPreferredSize(new Dimension(0, 0));
        }

        createNoteMainWindow.getRootPane().setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
//        createNoteMainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        createNoteMainWindow.pack();
        createNoteMainWindow.setLocationRelativeTo(null);
        createNoteMainWindow.setVisible(true);
    }

    private JPanel createColorBlock(Color nBar , Color contentArea) {
        JPanel blockPanel = new JPanel();
        blockPanel.setBackground(nBar);
        
        blockPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textArea.setBackground(contentArea);
                textArea.setBorder(BorderFactory.createLineBorder(nBar, 2));
                buttonPanel.setBackground(nBar);
                
                userNBarColor = nBar;
                userContentAreaColor = contentArea;
            }
        });
        return blockPanel;
    }

    // Utility methods
    public String getUserNote() {
        return userNote;
    }
    
    public String getUserNoteId() {
        return id;
    }
    
    public Color getNavbarColor() {
        return userNBarColor;
    }
    
    public Color getContentColor() {
        return userContentAreaColor;
    }
    
    // Use to test Individual component
    public static void main(String[] args) {
        new TestWindow();
    }
}
