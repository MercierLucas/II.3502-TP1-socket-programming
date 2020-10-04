package Part_III_GroupChat;

import javafx.stage.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ChatClient
{
    private static JTextArea _chat;
    private static JTextField _input;
    private static Socket _socket;

    private static StringBuilder _chatHistory;

    private static void setupFrame()
    {
        JFrame f=new JFrame("my chat");
        f.setSize(400,400);

        JPanel p1=new JPanel();
        p1.setLayout(new BorderLayout());

        JPanel p2 = new JPanel();
        p2.setLayout(new BorderLayout());

        _input = new JTextField();
        p1.add(_input, BorderLayout.CENTER);

        JButton sendButton =new JButton("Send");
        p1.add(sendButton, BorderLayout.EAST);

        _chat =new JTextArea();
        p2.add(_chat, BorderLayout.CENTER);
        p2.add(p1, BorderLayout.SOUTH);

        f.setContentPane(p2);
        f.setLocationRelativeTo(null); // center the window

        f.setVisible(true);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                sendMessage();
                System.out.println("sending");
            }
        });
    }

    private static void sendMessage()
    {
        try
        {
            BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(_socket.getOutputStream()));
            String message = _input.getText();

            if(message.length() > 0)
            {
                writer.write(message+"\n");
                writer.flush();
                _input.setText("");
            }
        }
        catch (Exception ex)
        {
            System.out.println(" /!\\ Error while sending message : "+ex);
        }

    }

    private static void updateChat(String message)
    {
        _chatHistory.append(message);
        _chatHistory.append("\n");
        _chat.setText(_chatHistory.toString());
    }

    public static void main (String argv[])
    {
        String host = "locahost";
        int port = 5555;

        _chatHistory = new StringBuilder();
        try
        {
            _socket = new Socket( "localhost" , 5555 );
            setupFrame();

            BufferedReader reader = new BufferedReader( new InputStreamReader(_socket.getInputStream()));

            BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(_socket.getOutputStream()));

            String serverResponse;
            while ((serverResponse = reader.readLine())!= null )
            {
                updateChat(serverResponse);
            }
        }
        catch (Exception ex)
        {
            System.out.println("Exception "+ex);
        }
    }
}
