package chat;

import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import encryption.Encryption;

import java.security.Key;
import java.security.PrivateKey;
import java.util.Base64;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ChatServer extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextArea TextArea = new JTextArea();
	private ServerSocket serverSock;
	private PrivateKey privateKey;
    private final List<ClientHandler> clientHandlers = new CopyOnWriteArrayList<>();  
    private static int clientCount = 0; 

    // UI setup method
    private void uiServer() {
        setLayout(new BorderLayout());
        add(new JScrollPane(TextArea), BorderLayout.CENTER);
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


    public ChatServer() {
        super("Chat Server");
        readPrivateKey();
        uiServer();      
        initiateServer();
    }
    
    // Initiating server
    private void initiateServer() {
        try {
            setupServerSocket();
            logServerStart();
            waitForClientConnections();
        } catch (IOException e) {
            logServerError(e);
        }
    }

    private void setupServerSocket() throws IOException {
        serverSock = new ServerSocket(9898);
    }

    private void logServerStart() {
        String formattedDate = getCurrentDateTime();
        updateTextArea("Chat Server started on port 9898 at " + formattedDate + "\n");
    }
    
    // Method to wait for client connections
    private void waitForClientConnections() throws IOException {
        while (true) {
            Socket clientSocket = serverSock.accept();
            ClientHandler clientHandler = new ClientHandler(clientSocket);
            clientHandlers.add(clientHandler);
            clientHandler.start();
        }
    }

    private void logServerError(IOException e) {
        updateTextArea("Server failed to start: " + e.getMessage() + "\n");
        e.printStackTrace();
    }

    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        return dateFormat.format(new Date());
    }

    
    private void readPrivateKey() {
        try {
            privateKey = Encryption.readPrivateKey("keypairs/pkcs8_key");
        } catch (Exception e) {
            e.printStackTrace();
            updateTextArea("Problem loading private key: " + e.getMessage());
            System.exit(1);
        }
    }
    
    private void updateTextArea(String text) {
        SwingUtilities.invokeLater(() -> TextArea.append(text));
    }


    public class ClientHandler extends Thread {
    	// Socket and I/O streams
        private Socket socket;
        private DataOutputStream out;
        private DataInputStream in;
        // AES key and client number
        private Key aesKey;
        private int clientNum;  

        public ClientHandler(Socket socket) {
            this.socket = socket;
            this.clientNum = ++clientCount;  
            try {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
            	updateTextArea("Error setting up stream: " + e.getMessage() + "\n");
                e.printStackTrace();
            }
        }
        
        // Run method for thread execution
        public void run() {
            logClientConnectionDetails();
            try {
                String clientMessage = in.readUTF();
                if (isHelloMessage(clientMessage)) {
                    acknowledgeClientConnection();
                    handleClientMessages();
                }
            } catch (IOException e) {
                updateTextArea("Client disconnected: " + socket + "\n");
            } catch (Exception e) {
                updateTextArea("Error while communication: " + e.getMessage() + "\n");
            } finally {
                clientHandlers.remove(this);
            }
        }

        // Method to log client connection details
        private void logClientConnectionDetails() {
            String currentTime = getCurrentDateTime();
            updateTextArea(String.format("Starting thread for client %d at %s\n", clientNum, currentTime));
            updateTextArea(String.format("Client %d's host name is %s\n", clientNum, socket.getInetAddress().getHostName()));
            updateTextArea(String.format("Client %d's IP Address is %s\n", clientNum, socket.getInetAddress().getHostAddress()));
        }
        

        private boolean isHelloMessage(String message) {
            return "HELLO".equalsIgnoreCase(message);
        }

        private void acknowledgeClientConnection() throws IOException {
            out.writeUTF("CONNECTED");
        }

        private void handleClientMessages() throws Exception {
            byte[] encryptedSeed = Base64.getDecoder().decode(in.readUTF());
            byte[] seed = Encryption.pkDecrypt(privateKey, encryptedSeed);
            aesKey = Encryption.generateAESKey(seed);

            while (true) {
                String encryptedMessage = in.readUTF();
                String message = Encryption.decrypt(aesKey, encryptedMessage);
                messageBroadcast(message, this);
            }
        }


        private void messageBroadcast(String message, ClientHandler sender) {
            for (ClientHandler client : clientHandlers) {
                try {
                    
                    String messageOut;
                    if (client == sender) {
                      
                    	messageOut = message;
                    } else {
                        
                    	messageOut = String.format("%d: %s", sender.clientNum, message);
                    }
                    String encryptedMessage = Encryption.encrypt(client.aesKey, messageOut);
                    client.out.writeUTF(encryptedMessage);
                } catch (Exception e) {
                	updateTextArea("Failed to encrypt or send message: " + e.getMessage() + "\n");
                }
            }
        }


        }

    public static void main(String[] args) {
		ChatServer chatServer = new ChatServer();
    }
}