package client.view;

import client.conn.Client;
import com.google.gson.Gson;
import intf.MessageInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientPanel extends JFrame implements ActionListener, MessageInterface{

    private int width = 800;
    private int height = 600;

    private JPanel mainPanel;
    private JPanel centerPanel;
    private JPanel bottomPanel;

    private JTextArea outputMessagesClient;
    private JTextArea outputMessages;
    private JScrollPane scrollMessages;
    private JTextField inputMessage;
    private JButton sendMessageBtn;
    private JButton connectServerBtn;
    private Gson json = new Gson();

    public ClientPanel(){
        super("Client Panel");

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

        outputMessagesClient = new JTextArea();
        outputMessagesClient.setBackground(Color.WHITE);
        outputMessagesClient.setForeground(Color.BLACK);
        scrollMessages = new JScrollPane(outputMessagesClient);
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
        sendMessageBtn.setBounds(inputMessage.getWidth() + 10, 10, 100, 30);
        sendMessageBtn.addActionListener(this);
        bottomPanel.add(sendMessageBtn);


        connectServerBtn = new JButton("Connect");
        connectServerBtn.setBackground(Color.BLUE);
        connectServerBtn.setForeground(Color.WHITE);
        connectServerBtn.setBounds(bottomPanel.getWidth() - 200, 10, 100, 30);
        connectServerBtn.addActionListener(this);
        bottomPanel.add(connectServerBtn);

        mainPanel.add(centerPanel);
        mainPanel.add(bottomPanel);

        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setVisible(true);
    }

    public static void main(String[] args){
        new ClientPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == connectServerBtn){
            try{
                Client.getInstance().connectToServer(this);
            }catch(Exception ex){
                System.err.println(ex.getMessage());
            }
        }

        if(e.getSource() == sendMessageBtn){
            try{
                Client.getInstance().sendMessage(inputMessage.getText());
                outputMessagesClient.append("Client: "+ inputMessage.getText() + "\n");
            }catch(Exception ex){
                System.err.println(ex.getMessage());
            }
        }
    }

    @Override
    public void onMessageReceived(String message) {
        outputMessages.append("Server: " + message + "\n");
    }
}
