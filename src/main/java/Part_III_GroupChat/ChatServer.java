package Part_III_GroupChat;

import Part_II_ThreadedServer.ThreadedServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatServer implements Runnable
{
    private Socket _socket;
    private static List<Socket> _clients;

    public ChatServer(Socket socket)
    {
        _socket = socket;
    }

    public void run()
    {
        try
        {
            BufferedReader reader = new BufferedReader( new InputStreamReader(_socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(_socket.getOutputStream()));
            writer.write( "*** Welcome to the chat ***\r\n" );
            writer.flush();

            while (true)
            {
                String message = reader.readLine().trim();
                broadCast(message, _socket);
                //_socket.close();
            }

        }
        catch (Exception ex)
        {
            System.out.println("Exception "+ex);
        }

    }
    private void broadCast(String message, Socket sender)
    {
        for (Socket socket: _clients)
        {
            try
            {
                BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
                StringBuilder builder = new StringBuilder();

                builder.append("["+new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime())+"] ");
                builder.append(message);
                builder.append("\n");

                writer.write(builder.toString());
                writer.flush();
            }
            catch (Exception ex){System.out.println("Error while sending message "+ex);};

        }
    }

    public static void main (String argv[]) throws Exception
    {
        System.out.println( " Server is Running " );
        ServerSocket mysocket = new ServerSocket( 5555 );
        _clients = new ArrayList<>();

        while (true)
        {
            Socket socket = mysocket.accept();
            ChatServer server = new ChatServer(socket);
            _clients.add(socket);

            Thread serverThread = new Thread(server);
            serverThread.start();
            System.out.println("New user connected");
        }

    }
}
