import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Initialy I had used JFrame but i got problem when ever it opens it creates its icon in taskbar ( i dont know what to call this :) ) 
// i refere https://stackoverflow.com/questions/14394384/can-not-edit-the-text-of-a-jtextfield-inside-a-jpanel-within-a-jwindow
public class CreateNoteWindow extends JDialog {
    private JTextArea textArea;
    private JPanel buttonPanel;
    private String userNote;
    private Color userNBarColor =  ColorClass.yellow1;
    private Color userContentAreaColor = ColorClass.yellow2;
    private int xPos, yPos;		// Use co ordinates for frame while dragging
    private String id = null;

    // Constructor 1 : use in create note
    public CreateNoteWindow() {
//        JFrame.setDefaultLookAndFeelDecorated(true);
        noteWindow();
    }
    
    // Constructor 2 : use in edit
    public CreateNoteWindow(String note,String id) {
//        JFrame.setDefaultLookAndFeelDecorated(true);
        noteWindow();
        textArea.setText(note);
        this.id = id;
    }
    
    
    
    public void noteWindow() {
    	setUndecorated(true);

        JPanel mainPanel = new JPanel(new BorderLayout()); // Main panel

        // Panel for color blocks
        JPanel colorPanel = new JPanel(new GridLayout(1, 5)); 
        colorPanel.setPreferredSize(new Dimension(400, 20));
        // Add color blocks
        colorPanel.add(createColorBlock(ColorClass.pink1, ColorClass.pink2)); 
        colorPanel.add(createColorBlock(ColorClass.cyan1,ColorClass.cyan2));
        colorPanel.add(createColorBlock(ColorClass.blue1,ColorClass.blue2));
        colorPanel.add(createColorBlock(ColorClass.yellow1,ColorClass.yellow2));
        colorPanel.add(createColorBlock(ColorClass.orange1,ColorClass.orange2));
        colorPanel.add(createColorBlock(ColorClass.purple1,ColorClass.purple2));
        colorPanel.add(createColorBlock(ColorClass.grenish1,ColorClass.grenish2));
        
        // Panel for text area
        JPanel textAreaPanel = new JPanel(new BorderLayout()); 
        textAreaPanel.setPreferredSize(new Dimension(400, 300));
        
        // Textarea component
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        
        // Scrollpane component
        JScrollPane scrollPane = new JScrollPane(textArea);
        textAreaPanel.add(scrollPane, BorderLayout.CENTER);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        
        

        // Btn panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        // Btns
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        // save btn listener
        saveButton.addActionListener(e -> {
            userNote = textArea.getText();
            dispose(); 
        });

        // cancel btn listener
        cancelButton.addActionListener(e -> {
            dispose(); // Close the note window without saving
        });
        
        // Add btns to button panel
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        // Add color panel, textarea panel, button panel in main panel 
        mainPanel.add(colorPanel, BorderLayout.NORTH);
        mainPanel.add(textAreaPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame
        getContentPane().add(mainPanel);
        
        // Listener for frame use while dragging to drag smoothly
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                xPos = me.getX();
                yPos = me.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                int deltaX = me.getXOnScreen() - xPos;
                int deltaY = me.getYOnScreen() - yPos;
                setLocation(deltaX, deltaY);
            }
        });

        if (getLayeredPane().getComponentCount() > 1) {
            getLayeredPane().getComponent(1).setPreferredSize(new Dimension(0, 0));
        }

        getRootPane().setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        textArea.setBackground(userContentAreaColor);
        textArea.setBorder(BorderFactory.createLineBorder(userNBarColor, 2));
        buttonPanel.setBackground(userNBarColor);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
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
//    public static void main(String[] args) {
//        new CreateNoteWindow();
//    }
}
