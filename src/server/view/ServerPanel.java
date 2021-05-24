package server.view;

import com.google.gson.Gson;
import intf.MessageInterface;
import server.conn.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author IDEA Developers
 */
public class ServerPanel extends JFrame implements ActionListener, MessageInterface{


    private int width = 800;
    private int height = 600;

    private JPanel mainPanel;
    private JPanel centerPanel;
    private JPanel bottomPanel;

    private JTextArea outputMessagesServer;
    private JTextArea outputMessages;
    private JScrollPane scrollMessages;
    private JTextField inputMessage;
    private JButton sendMessageBtn;
    private JButton startServerBtn;
    private Gson json= new Gson();

    public ServerPanel(){
        super("Server Panel");

        mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);


        centerPanel = new JPanel(null);
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBounds(0, 0, width, height - 100);


        bottomPanel = new JPanel(null);
        bottomPanel.setBackground(Color.GRAY);
        bottomPanel.setBounds(0, centerPanel.getHeight(), width, 100);

        outputMessages = new JTextArea();
        outputMessages.setBackground(Color.WHITE);
        outputMessages.setForeground(Color.BLACK);
        scrollMessages = new JScrollPane(outputMessages);
        scrollMessages.setBounds(5, 5, centerPanel.getWidth() - 20, centerPanel.getHeight() - 20);
        centerPanel.add(scrollMessages);

        outputMessagesServer = new JTextArea();
        outputMessagesServer.setBackground(Color.WHITE);
        outputMessagesServer.setForeground(Color.BLACK);
        scrollMessages = new JScrollPane(outputMessagesServer);
        scrollMessages.setBounds(5, 5, centerPanel.getWidth() - 20, centerPanel.getHeight() - 20);
        centerPanel.add(scrollMessages);

        inputMessage = new JTextField();
        inputMessage.setBackground(Color.WHITE);
        inputMessage.setForeground(Color.BLACK);
        inputMessage.setBounds(10, 10, 450, 30);
        bottomPanel.add(inputMessage);

        sendMessageBtn = new JButton("Send");
        sendMessageBtn.setBackground(Color.BLUE);
        sendMessageBtn.setForeground(Color.WHITE);
        sendMessageBtn.setBounds(inputMessage.getWidth() + 20, 10, 100, 30);
        sendMessageBtn.addActionListener(this);
        bottomPanel.add(sendMessageBtn);


        startServerBtn = new JButton("Start");
        startServerBtn.setBackground(Color.BLUE);
        startServerBtn.setForeground(Color.WHITE);
        startServerBtn.setBounds(bottomPanel.getWidth() - 200, 10, 100, 30);
        startServerBtn.addActionListener(this);
        bottomPanel.add(startServerBtn);

        mainPanel.add(centerPanel);
        mainPanel.add(bottomPanel);

        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setVisible(true);
    }

    public static void main(String[] args){
        new ServerPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startServerBtn){
            try {
                Server.getInstance().startServer(this);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }

        if(e.getSource() == sendMessageBtn){
            try {
                Server.getInstance().sendMessage(inputMessage.getText());
                outputMessagesServer.append("Server: "+ inputMessage.getText() + "\n");

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
    }


    @Override
    public void onMessageReceived(String message) {
        outputMessages.append("Client: " + message + "\n");
    }

}
