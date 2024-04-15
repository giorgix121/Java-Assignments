package chat;

import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;

import encryption.Encryption;

public class ChatClient extends JFrame {
    private static final long serialVersionUID = 1L;
	private static final String SERVER_PUBLIC_KEY = "MIGeMA0GCSqGSIb3DQEBAQUAA4GMADCBiAKBgGk9wUQ4G9PChyL5SUkCyuHjTNOglEy5h4KEi0xpgjxi/UbIH27NXLXOr94JP1N5pa1BbaVSxlvpuCDF0jF9jlZw5IbBg1OW2R1zUACK+NrUIAYHWtagG7KB/YcyNXHOZ6Icv2lXXd7MbIao3ShrUVXo3u+5BJFCEibd8a/JD/KpAgMBAAE=";  
    private JTextArea jtextArea;
    private Socket socket;   
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private JTextField jtextField;
    private PublicKey serverPublicKey;
    private Key communicationKey;

    public ChatClient() {
        super("Chat Client");
        uiInit();
        readServerKey();
    }

    // Initialize the GUI
    private void uiInit() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");

        JMenuItem menuItemConnect = new JMenuItem("Connect");
        menuItemConnect.addActionListener(e -> serverConnection());

        JMenuItem menuExit = new JMenuItem("Exit");
        menuExit.addActionListener(e -> {
            closeConnection();
            System.exit(0);
        });

        menu.add(menuItemConnect);
        menu.add(menuExit);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        jtextArea = new JTextArea(20, 40);
        jtextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(jtextArea);

        jtextField = new JTextField(40);
        jtextField.addActionListener(e -> {
            sendMessage(jtextField.getText());
            jtextField.setText("");
        });

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(jtextField, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setVisible(true);
    }

    // Establishing connection with server
    private void readServerKey() {
        try {
            serverPublicKey = Encryption.readPublicKey(SERVER_PUBLIC_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error getting server public key: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void serverConnection() {
        try {
            socket = new Socket("localhost", 9898);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            jtextArea.append("Connected to the server\n");

            dataOutputStream.writeUTF("HELLO");
            String outResponse = dataInputStream.readUTF();
            if ("CONNECTED".equals(outResponse)) {
                byte[] seed = Encryption.generateSeed();
                byte[] encryptedSeed = Encryption.pkEncrypt(serverPublicKey, seed);
                dataOutputStream.writeUTF(Base64.getEncoder().encodeToString(encryptedSeed));
                communicationKey = Encryption.generateAESKey(seed);
                
                listenForServerMessages();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Connection error: " + e.getMessage(), "Connection Error", JOptionPane.ERROR_MESSAGE);
            
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            
            JOptionPane.showMessageDialog(this, "Encryption error: " + e.getMessage(), "Encryption Error", JOptionPane.ERROR_MESSAGE);
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            
            JOptionPane.showMessageDialog(this, "Encryption error, please check your keys: " + e.getMessage(), "Encryption Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void listenForServerMessages() {
        new Thread(() -> {
            try {
                while (true) {
                    String encryptedMessage = dataInputStream.readUTF();
                    String message = Encryption.decrypt(communicationKey, encryptedMessage);
                    SwingUtilities.invokeLater(() -> jtextArea.append(message + "\n"));
                }
            } catch (IOException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException e) {
                SwingUtilities.invokeLater(() -> jtextArea.append("Server has disconnected.\n"));
                closeConnection();
            }
        }).start();
    }

    private void sendMessage(String message) {
        try {
            String encryptedMessage = Encryption.encrypt(communicationKey, message);
            dataOutputStream.writeUTF(encryptedMessage);
        } catch (IOException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error sending message to server: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

 
    private void closeResources(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            try {
                if (resource != null) {
                    resource.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void closeConnection() {
        closeResources(dataInputStream, dataOutputStream, socket);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatClient::new);
    }
}
