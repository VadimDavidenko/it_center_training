package chat;

import javafx.application.Application;
import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by Вадим on 05.01.2016.
 */
public class AsyncChat extends Application {
    @FXML
    private TextField fieldIP;
    @FXML
    private TextField fieldClientPort;
    @FXML
    private TextField fieldListenPort;
    @FXML
    private TextArea fieldUserMessage;
    @FXML
    private TextArea fieldChatText;

    private int clientPort;
    private int listenPort;
    private String ip;
    private StringBuffer chatText;
    private SocketChannel clientSocketChannel;
    private ChatThread chatThread;
    private boolean isRefreshStarted;
    private boolean isClientConnected;
    private String chatTitle;
    private static final String ENCODING = "UTF-8";

    public AsyncChat(String chatTitle) {
        this.chatTitle = chatTitle;
    }

    public AsyncChat(){
        chatTitle = "P2P Chat";
    }

    @Override
    public void start(Stage stage) throws Exception {
        createStage(stage);
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    public void createStage(Stage stage) throws IOException {
        String fxmlFile = "/chat/chat.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
        stage.setTitle(chatTitle);
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void process() {
        // text area refreshing thread starts
        chatText = new StringBuffer();
        if (!isRefreshStarted) {
            refreshChatWindow();
            isRefreshStarted = true;
        }
        // client attempts to connect to other server
        if (!isClientConnected) {
            connectToServer();
        }
        // server thread starts
        if (chatThread == null) {
            chatThread = new ChatThread(ip, listenPort);
            chatThread.start();
        }
    }

    public void onClickConnect() {
        if (!fieldIP.getText().isEmpty() && !fieldClientPort.getText().isEmpty() &&
                !fieldListenPort.getText().isEmpty()) {

            listenPort = Integer.parseInt(fieldListenPort.getText());
            clientPort = Integer.parseInt(fieldClientPort.getText());
            ip = fieldIP.getText();
            fieldListenPort.setDisable(true);
            fieldClientPort.setDisable(true);
            fieldIP.setDisable(true);

            process();
        }
    }

    public void onClickSend() throws Exception {
        if (!fieldUserMessage.getText().isEmpty()) {
            String msg = fieldUserMessage.getText();
            if (msg.trim().equalsIgnoreCase("exit")) {
                stop();
            }
            if (isClientConnected) {
                sendMessage(msg);
                fieldUserMessage.clear();
                updateChatText("<< This user >>\n" + msg + "\n");
            }
        }
    }

    public void connectToServer() {
        try {
            clientSocketChannel = SocketChannel.open(new InetSocketAddress(ip, clientPort));
            updateChatText("Chat client connected to remote server " + clientSocketChannel.getRemoteAddress().toString() + "\n");
            isClientConnected = true;
        } catch (IOException e) {
            disconnectClient();
            isClientConnected = false;
        }
    }

    public void sendMessage(String msg) throws UnsupportedEncodingException {
        String sendMsg = "<< Other user >>\n" + msg + "\n";
        ByteBuffer buf = ByteBuffer.allocate(sendMsg.getBytes(ENCODING).length);
        buf.put(sendMsg.getBytes(ENCODING));
        buf.flip();
        try {
            clientSocketChannel.write(buf);
        } catch (IOException e) {
            disconnectClient();
        }
    }

    public synchronized void updateChatText(String msg) {
        if (!msg.isEmpty()) {
            chatText.append(msg);
        }
    }

    public String getChatText() {
        if (chatText != null){
            return chatText.toString();
        }
        return "";
    }

    public void refreshChatWindow() {
        final long PAUSE = 200; // in ms

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(PAUSE);
                    } catch (InterruptedException ignored) {
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            double scrollPos = fieldChatText.getScrollTop();
                            fieldChatText.setText(getChatText());
                            fieldChatText.setScrollTop(scrollPos);
                            fieldChatText.appendText("");   // just to move cursor to the bottom of text
                        }
                    });
                }
            }
        }).start();
    }

    public void disconnectClient() {
        try {
            if (clientSocketChannel != null) {
                clientSocketChannel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateChatText("Remote chat disconnected\n");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private class ChatThread extends Thread {

        private ServerSocketChannel serverSocketChannel;
        private SocketChannel listenSocketChannel;
        private String ip;
        private int port;
        private boolean isServerConnected;

        public ChatThread(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        @Override
        public void run() {
            final int BUFFER_SIZE = 50;

            if (!isServerConnected) {
                connectToClient();
            }
            if (!isClientConnected) {
                connectToServer();
            }
            if (isClientConnected && isServerConnected) {
                updateChatText("Chat started...\n");
            }
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            while (true) {
                try {
                    buffer.clear();
                    listenSocketChannel.read(buffer);
                    buffer.flip();
                    String msg = new String(buffer.array(), ENCODING).substring(0, buffer.limit());
                    if (!msg.isEmpty()) {
                        updateChatText(msg);
                    }
                } catch (IOException ignored) {
                    try {
                        disconnectServer();
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void connectToClient() {
            try {
                serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.socket().bind(new InetSocketAddress(ip, port));
                updateChatText("Chat server waiting for connection from other chat...\n");

                listenSocketChannel = serverSocketChannel.accept();
                updateChatText("Client " + listenSocketChannel.getLocalAddress().toString() +
                        " connected to this chat\n");
                isServerConnected = true;
            } catch (IOException e) {
                disconnectServer();
                isServerConnected = false;
            }
        }

        public void disconnectServer() {
            try{
                if(listenSocketChannel != null) {
                    listenSocketChannel.close();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
            try{
                if(serverSocketChannel != null) {
                    serverSocketChannel.close();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
            updateChatText("Remote chat disconnected\n");
        }
    }

}

