import javax.imageio.ImageIO;
import javax.swing.*;

import org.json.simple.parser.ParseException;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class StickyNotesManager {
	
	// Components
    private JWindow settingPanelWindow;  // setting btn
    private JPanel additionalBtnSettingPanelpanel;	// extra btn (add, cross..)
    
    private JButton settingButton;
    private JButton stickButton;
    private JButton addButton;
    private JButton visibleButton;
    private JButton crossButton;
    private JButton infoButton;
    
    // Variables
    private boolean isVisible = true;
    private static int xPosSettingBtn, yPosSettingBtn;		// Use for initial point of notes setting panel
    
    private static final int NOTE_WIDGET_WIDTH = 40;
    private static final int NOTE_WIDGET_HEIGHT = 37;
    
    private static final int ICON_WIDTH = 30;
    private static final int ICON_HEIGHT = 30;
    
    private static final int ICON_H_GAP  = 20;
    private static final int ICON_V_GAP  = 20;
    
    private static boolean isInfoClick = false;
    
    // Objects
    protected static SaveNotesInFile saveNotesInFileObj;
    protected  static StickyNotesWindow stickyNoteObj;
    private static InfoWindow infoWindowObj; 
    
    // Color
    private Color purpulCol = Color.decode("#ce81ff");
    private Color greenCol = Color.decode("#cdfc93");
    private Color yellowCol = Color.decode("#fff68b");
    

    public StickyNotesManager() {
    	saveNotesInFileObj = new SaveNotesInFile();
    	stickyNoteObj = new StickyNotesWindow();
    	
        notesSettingPanel();
    }
    
    // Use for visible btn
    private void updateButtonIcon() {
        // Load icons for open and close eye
        ImageIcon openEyeIcon = getImageToIcon("openEye.png",30,30);
        ImageIcon closeEyeIcon = getImageToIcon("closeEye.png",30,30);

        // Set button icon based on visibility state
        visibleButton.setIcon(isVisible ? openEyeIcon : closeEyeIcon);
        visibleButton.setPreferredSize(new Dimension(ICON_WIDTH, ICON_HEIGHT));
    }
    
    // Use to get scaled icon for btn
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
    
    // Use for btn which has icon in it
    private void prepareBtnForIcon(JButton btn) {
    	btn.setContentAreaFilled(false);
    	btn.setBorderPainted(false);
    	btn.setFocusPainted(false);
    	btn.setOpaque(false);
    	btn.setMargin(new Insets(0, 0, 0, 0));
    }
    
    // Listener for stick notes btn
    private void stickButtonEventListneres() {
    	stickButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	additionalBtnSettingPanelpanel.setVisible(false);
                settingPanelWindow.pack();
                
                try {
                	// Stick all notes to side
                	stickyNoteObj.stick();
				} catch (Exception e2) {
					System.out.println(e2);
				}


                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                settingPanelWindow.setLocation(screenSize.width - settingPanelWindow.getWidth(), screenSize.height - settingPanelWindow.getHeight() - 40);
                settingPanelWindow.validate();
                settingPanelWindow.repaint();
            }
        });
    }
    
    // Listener for visible btn : change icon and make notes visible and hide
    public void visibleBtnEventListners() {
        visibleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isVisible = !isVisible; // Toggle visibility
                updateButtonIcon();
                try {
                	if (isVisible) {
                		stickyNoteObj.start();  // Displays all notes        		 
                	}else {
                		stickyNoteObj.makeInvisible(); // Hides all notes
                	}
        		} catch (IOException | ParseException e1) {
        			e1.printStackTrace();
        		}
            }
        });
        
        try {
        	if (isVisible) {
        		stickyNoteObj.start();        		
        	}else {
        		stickyNoteObj.makeInvisible();
        	}
		} catch (IOException | ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
//        System.out.println(isVisible);
        
    }
    
    private void shutdownApp() {
    	settingPanelWindow.dispose();
    	
    	stickyNoteObj.makeInvisible();
    }
    
    private void createNote() {
    	CreateNoteWindow createNoteWindow = new CreateNoteWindow();
    	createNoteWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                String noteContent = createNoteWindow.getUserNote();
//                System.out.println(noteContent);
                if (noteContent != null) {
                	
                    
                	saveNotesInFileObj.addNote(noteContent, createNoteWindow.getNavbarColor(),createNoteWindow.getContentColor());
                	saveNotesInFileObj.saveJsonToFile();
                	
                	try {
						stickyNoteObj.addNewNote(noteContent,createNoteWindow.getUserNoteId(),createNoteWindow.getNavbarColor(),createNoteWindow.getContentColor());
					} catch (IOException | ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
//                	getAllNotes();
                	
//                	try {
//                		StickyNotesWidget.start();
//                	} catch (IOException | ParseException err) {
//                		err.printStackTrace();
//                	}
                }
            }
        });
    }
    
//    settingPanelWindow[ container { mainPanel [settingButton] , additionalBtnSettingPanelpanel [stickButton, .... ,infoButton ] } ]

    protected void notesSettingPanel(){
        settingPanelWindow = new JWindow();
        settingPanelWindow.setSize(NOTE_WIDGET_WIDTH, NOTE_WIDGET_HEIGHT); // Set the size of the window
        settingPanelWindow.setAlwaysOnTop(true);

        settingButton = new JButton("");
        stickButton = new JButton("");
        addButton = new JButton("");
        visibleButton = new JButton("");
        crossButton = new JButton("");
        infoButton = new JButton("");
        
        
        // preparing btns for icon
        prepareBtnForIcon(settingButton);
        prepareBtnForIcon(stickButton);
        prepareBtnForIcon(addButton);
        prepareBtnForIcon(visibleButton);
        prepareBtnForIcon(crossButton);
        prepareBtnForIcon(infoButton);
    
//        add icon
        settingButton.setIcon(getImageToIcon("setting.png",30,30));
        settingButton.setPreferredSize(new Dimension(ICON_WIDTH, ICON_HEIGHT));
        
        stickButton.setIcon(getImageToIcon("magnet.png",30,30));
        stickButton.setPreferredSize(new Dimension(ICON_WIDTH, ICON_HEIGHT));
        stickButtonEventListneres();
        
        addButton.setIcon(getImageToIcon("add.png",30,30));
        addButton.setPreferredSize(new Dimension(ICON_WIDTH, ICON_HEIGHT));
        addButton.addActionListener(e -> createNote());
        
        visibleButton.setIcon(getImageToIcon("openEye.png",30,30));
        visibleButton.setPreferredSize(new Dimension(ICON_WIDTH, ICON_HEIGHT));
        visibleBtnEventListners();
        
        crossButton.setIcon(getImageToIcon("cross.png",30,30));
        crossButton.setPreferredSize(new Dimension(ICON_WIDTH, ICON_HEIGHT));
        crossButton.addActionListener(e -> shutdownApp());
        
        infoButton.setIcon(getImageToIcon("info.png",30,30));
        infoButton.setPreferredSize(new Dimension(ICON_WIDTH, ICON_HEIGHT));
        infoButton.addActionListener(e -> {
        	 
        	// Used if else to avoid spawning new Info Window if btn double click
        	if (isInfoClick == false) {
        		infoWindowObj = new InfoWindow(); 
        		isInfoClick = true; 
        	}else {
        		infoWindowObj.closeWindow();
        		isInfoClick = false;
        	}
        	
        });
        

        // Create a additionalBtnSettingPanelpanel for the main button
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        mainPanel.add(settingButton);
        mainPanel.setBackground(yellowCol);

        // Create a additionalBtnSettingPanelpanel for the additional buttons (stick, add, cross, eye..)
        additionalBtnSettingPanelpanel = new JPanel();
        additionalBtnSettingPanelpanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        additionalBtnSettingPanelpanel.setVisible(false); // Hide the additionalBtnSettingPanelpanel initially
        additionalBtnSettingPanelpanel.setBackground(greenCol);
        // Add btns to panel
        additionalBtnSettingPanelpanel.add(Box.createRigidArea(new Dimension(10, 0)));
        additionalBtnSettingPanelpanel.add(stickButton);
        additionalBtnSettingPanelpanel.add(Box.createRigidArea(new Dimension(10, 0)));
        additionalBtnSettingPanelpanel.add(addButton);
        additionalBtnSettingPanelpanel.add(Box.createRigidArea(new Dimension(10, 0)));
        additionalBtnSettingPanelpanel.add(visibleButton);
        additionalBtnSettingPanelpanel.add(Box.createRigidArea(new Dimension(10, 0)));
        additionalBtnSettingPanelpanel.add(infoButton);
        additionalBtnSettingPanelpanel.add(Box.createRigidArea(new Dimension(10, 0)));
        additionalBtnSettingPanelpanel.add(Box.createRigidArea(new Dimension(10, 0)));
        additionalBtnSettingPanelpanel.add(Box.createRigidArea(new Dimension(10, 0)));
        additionalBtnSettingPanelpanel.add(crossButton);
        additionalBtnSettingPanelpanel.add(Box.createRigidArea(new Dimension(10, 0)));
        

        // Add an ActionListener to the main button to toggle the visibility of the additional buttons
        settingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                System.out.println(settingPanelWindow.getSize());
                if (additionalBtnSettingPanelpanel.isVisible()) {
                    // contentPanel.setVisible(false);
                } else {
                additionalBtnSettingPanelpanel.setVisible(!additionalBtnSettingPanelpanel.isVisible());
                settingPanelWindow.pack();


                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//                settingPanelWindow.setLocation(screenSize.width - settingPanelWindow.getWidth(), screenSize.height - settingPanelWindow.getHeight() - 40);
//                settingPanelWindow.setLocation(xPosSettingBtn, yPosSettingBtn);
                int newX = e.getXOnScreen() - xPosSettingBtn;
                int newY = e.getYOnScreen() - yPosSettingBtn;
                settingPanelWindow.setLocation(newX, newY);
                settingPanelWindow.validate();
                settingPanelWindow.repaint();
                }
            }
        });

        // Container panel contains mainPanel and additionalBtnSettingPanelpanel
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        
        // Insert component
        container.add(mainPanel);
        container.add(additionalBtnSettingPanelpanel);

        // Insert container to main window
        settingPanelWindow.add(container);

        // Position the window at the bottom right of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        settingPanelWindow.setLocation(screenSize.width - settingPanelWindow.getWidth(), screenSize.height - settingPanelWindow.getHeight() - 40);

        settingPanelWindow.setVisible(true);

        // Listener to drag setting panel
        settingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                // Get the position of the mouse relative to the window
                xPosSettingBtn = me.getXOnScreen() - settingPanelWindow.getX();
                yPosSettingBtn = me.getYOnScreen() - settingPanelWindow.getY();
            }
        });

        settingButton.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                int newX = me.getXOnScreen() - xPosSettingBtn;
                int newY = me.getYOnScreen() - yPosSettingBtn;
                settingPanelWindow.setLocation(newX, newY);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StickyNotesManager();
            }
        });
    }
}
