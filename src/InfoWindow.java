import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class InfoWindow  {
	private static JWindow mainWindow ;
	private  int xPos, yPos;
	private boolean isInfoWindowClose = false;
	
	protected void closeWindow() {
		mainWindow.dispose();
	}
	
	public InfoWindow() {
    	mainWindow = new JWindow();
        mainWindow.setAlwaysOnTop(true);
        mainWindow.setSize(500, 550);
        mainWindow.setBackground(Color.BLUE);
        mainWindow.setLocationRelativeTo(null);


        // Panel
        JPanel mainPanel = new JPanel(new BorderLayout());


     // Info Panel
        JPanel infoPanel = createInfoPanel();
        infoPanel.setBackground(ColorClass.yellow2);
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        

        JPanel iconDescriptionPanel = new JPanel();
        iconDescriptionPanel.setLayout(new GridLayout(10, 1));
        iconDescriptionPanel.setBackground(ColorClass.yellow2);
        mainPanel.add(iconDescriptionPanel, BorderLayout.CENTER);
        
        JButton closeBtn = new JButton("Close");
        closeBtn.setBackground(ColorClass.cyan2);
        closeBtn.addActionListener(e -> {
        	isInfoWindowClose = true;
        	mainWindow.dispose();
        	});
        mainPanel.add(closeBtn,BorderLayout.SOUTH);
        
        // Icons and descriptions
        String[] iconPaths = {"setting.png", "magnet.png", "add.png","openEye.png","closeEye.png","cross.png","info.png","paperClip.png","pencil.png","trash.png"};
        String[] descriptions = {": Opens the notes manager",
                ": Pins all notes to their initial positions",
                ": Creates a new note",
                ": Displays all notes",
                ": Hides all notes",
                ": Stops the whole application",
                ": Provides information about the app and usage of application buttons",
                ": Displays an individual note",
                ": Allows you to edit the note",
                ": Deletes the note"};
        
        for (int i = 0; i < iconPaths.length; i++) {
            JPanel panel = createIconDescriptionPanel(iconPaths[i], descriptions[i]);
            iconDescriptionPanel.add(panel);
        }
        
        
        
     // Listeners useful in dragging notes
        mainWindow.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                xPos = me.getX();
                yPos = me.getY();
            }
        });

        mainWindow.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                int deltaX = me.getXOnScreen() - xPos;
                int deltaY = me.getYOnScreen() - yPos;
                mainWindow.setLocation(deltaX, deltaY);
            }
        });

        mainWindow.getContentPane().add(mainPanel);
        mainWindow.setVisible(true);
    }
    
    // Method to create info section
    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Application Info");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea infoText = new JTextArea(
            "This is a Java-based floating sticky note application. You can create, edit, and delete notes. "
            + "Unlike other note applications that stay open on your desktop, this app sticks to the side of your desktop "
            + "with a small icon. When you click the icon, it opens the note. You can also customize the color of your sticky notes."
        );
        infoText.setEditable(false);
        infoText.setOpaque(false);
        infoText.setLineWrap(true);
        infoText.setWrapStyleWord(true);
        infoText.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoText.setFont(new Font("Arial", Font.BOLD,12));

        JLabel githubLabel = new JLabel("<html><a href='https://github.com/hackerspider09/FloatingNotes'>Contribute to this project on GitHub</a></html>");
        githubLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        githubLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        githubLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/hackerspider09/FloatingNotes"));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Add component in panel
        infoPanel.add(titleLabel);
        infoPanel.add(infoText);
        infoPanel.add(githubLabel);

        return infoPanel;
    }


    // Method to create icon and description
    private JPanel createIconDescriptionPanel(String iconPath, String description) {
    	JPanel panel = new JPanel();
    	panel.setLayout(new  BoxLayout(panel,BoxLayout.X_AXIS));
    	panel.add(Box.createRigidArea(new Dimension(10, 10)));  // Gives inital space then we can add new component after this 
        
        // Icon label
    	java.net.URL imgURL = getClass().getClassLoader().getResource(iconPath);
		try {
			Image photo;
			photo = ImageIO.read(imgURL);
			Image scaledImage = photo.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
			panel.add(iconLabel, BorderLayout.WEST);
		} catch (IOException e) {
			e.printStackTrace();
		}        
		
        
        // Description 
		JLabel textLabel = new JLabel(description);
		
		panel.add(Box.createRigidArea(new Dimension(10, 0)));  // This position matters to give space after 1st component
		panel.add(textLabel);
        panel.setBackground(ColorClass.yellow2);
        return panel;
    }


//    public static void main(String[] args) {
//        new InfoWindow();
//    }
}
