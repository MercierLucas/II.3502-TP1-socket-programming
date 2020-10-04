package Part_II_ThreadedServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadedServer implements Runnable
{
    private Socket _socket;

    public ThreadedServer(Socket socket)
    {
        _socket = socket;
    }


    public void run()
    {
        try
        {
            while (true)
            {
                BufferedReader reader = new BufferedReader( new InputStreamReader(_socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(_socket.getOutputStream()));

                writer.write( "*** Welcome to the Calculation Server (Addition Only) ***\r\n" );
                writer.write( "*** Please type in the first number and press Enter : \n" );
                writer.flush();
                String data1 = reader.readLine().trim();
                writer.write( "*** Please type in the second number and press Enter :\n" );
                writer.flush();
                String data2 = reader.readLine().trim();
                int num1=Integer.parseInt(data1);
                int num2=Integer.parseInt(data2);
                int result=num1+num2;
                System.out.println( "Addition operation done " );
                writer.write( "\r\n=== Result is : \n" +result+ "\n" );

                writer.flush();
            }

        }
        catch (Exception ex)
        {
            System.out.println("Exception "+ex);
        }

    }

    public static void main (String argv[]) throws Exception
    {
        System.out.println( " Server is Running " );
        ServerSocket mysocket = new ServerSocket( 5555 );

        while (true)
        {

            ThreadedServer server = new ThreadedServer(mysocket.accept());

            Thread serverThread = new Thread(server);
            serverThread.start();
            System.out.println("New user connected");
        }

    }
}
