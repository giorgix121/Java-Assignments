package encode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import files.FileUtils;


//Creates GUI for encoding text files using specified encoding strategies
public class FileEncoder extends JFrame {

    private JTextArea documentTextArea;
    private JComboBox<String> encodingStrategyComboBox;

    //Constructor that initializes the user interface
    
    public FileEncoder() {
        initializeUI();
        setTitle("File Encoder");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    //Initializes all UI components


    private void initializeUI() {
        setupTextArea();
        setupEncodingControls();
        setupMenuBar();
    }

    //Sets up text area where file content is displayed and edited
    
    private void setupTextArea() {
        documentTextArea = new JTextArea(20, 40); // Set dimensions as required
        add(new JScrollPane(documentTextArea), BorderLayout.CENTER);
    }

    //Sets up control panel at the bottom with encoding options and encode button
    
    private void setupEncodingControls() {
        JPanel bottomPanel = new JPanel();
        encodingStrategyComboBox = new JComboBox<>(new String[]{"ROT13", "Numeric"});
        JButton encodeTextButton = new JButton("Encode");
        encodeTextButton.addActionListener(this::encodeText);

        bottomPanel.add(new JLabel("Encoding Method:"));
        bottomPanel.add(encodingStrategyComboBox);
        bottomPanel.add(encodeTextButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    //Creates and sets up the menu bar with file operations

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(createMenuItem("Open File", this::loadFile));
        fileMenu.add(createMenuItem("Save File", this::saveFile));
        fileMenu.addSeparator();
        fileMenu.add(createMenuItem("Exit", e -> System.exit(0)));
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    //Helper method to create menu items
    
    private JMenuItem createMenuItem(String title, ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.addActionListener(actionListener);
        return menuItem;
    }

    //Opens file chooser to load file and displays its content in the text area
    
    private void loadFile(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            loadFileContent(fileChooser.getSelectedFile());
        }
    }
    
    //Loads content of the selected file into the text area

    private void loadFileContent(File file) {
        try {
            String content = FileUtils.readFile(file.getAbsolutePath());
            documentTextArea.setText(content);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error opening file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Opens file chooser to save the current text area content to file
    private void saveFile(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            saveFileContent(fileChooser.getSelectedFile());
        }
    }
    
    //Saves current text area content to selected file

    private void saveFileContent(File file) {
        try {
            FileUtils.saveFile(file.getAbsolutePath(), documentTextArea.getText());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to save file.", "File Write Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //Encodes text in text area using selected encoding strategy

    private void encodeText(ActionEvent e) {
        String selectedStrategy = (String) encodingStrategyComboBox.getSelectedItem();
        String encodedContent = Encoder.encode(documentTextArea.getText(), selectedStrategy);
        documentTextArea.setText(encodedContent);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FileEncoder::new);
    }
}
