import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class InfoWindow extends JFrame {
    public InfoWindow() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("My Info - Application Information");
        setSize(500, 550);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
//        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

     // Info Panel
        JPanel infoPanel = createInfoPanel();
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        

        JPanel iconDescriptionPanel = new JPanel();
        iconDescriptionPanel.setLayout(new GridLayout(10, 1));
        mainPanel.add(iconDescriptionPanel, BorderLayout.CENTER);
        
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

        getContentPane().add(mainPanel);
        setVisible(true);
    }
    
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

        JLabel githubLabel = new JLabel("<html><a href=''>Contribute to this project on GitHub</a></html>");
        githubLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        githubLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        githubLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/your-repository-link"));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });

        infoPanel.add(titleLabel);
//        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(infoText);
//        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(githubLabel);

        return infoPanel;
    }


    
    private JPanel createIconDescriptionPanel(String iconPath, String description) {
    	JPanel panel = new JPanel();
//    	panel.setBorder(BorderFactory.createLineBorder(Color.RED)); // Add border
        
    	panel.setLayout(new  BoxLayout(panel,BoxLayout.X_AXIS));
    	panel.add(Box.createRigidArea(new Dimension(10, 10)));  // Gives inital space then we can add new component after this 
        
        // Icon label
    	java.net.URL imgURL = getClass().getClassLoader().getResource(iconPath);
        // Read the image
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
        
        return panel;
    }


    public static void main(String[] args) {
        new InfoWindow();
    }
}
