package Part_I_SimpleServer;
import javax.swing.text.StyledEditorKit;
import java.io.*;
import java.net.*;
/**
 *
 * @author macbookpro
 */
public class FTPServer
{
    public static void main (String argv[]) throws Exception
    {
        System.out.println( " Server is Running " );
        ServerSocket mysocket = new ServerSocket( 5555 );

        while ( true )
        {
            Socket connectionSocket = mysocket.accept();
            BufferedReader reader = new BufferedReader( new InputStreamReader(connectionSocket.getInputStream()));
            BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(connectionSocket.getOutputStream()));
            writer.write("Connection successful, please send your file \r\n");
            writer.flush();

            String fileContent = reader.readLine();

            writer.write("Received content, trying to write in the file ...");
            //System.in.read();
            writer.flush();
            writer.write(writeToFile(fileContent) ? "[SUCCESS]" : "[ERROR]");
            writer.flush();
            connectionSocket.close();
        }
    }

    private static Boolean writeToFile(String message)
    {
        try
        {
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("received.txt"), "utf-8"));
            writer.write(message);
            writer.close();
            return true;
        }
        catch (Exception ex)
        {
            System.out.println("Exception while writing to file: "+ex );
        }

        return false;
    }
}