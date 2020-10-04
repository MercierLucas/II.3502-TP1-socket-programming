package Part_I_SimpleServer;

import java.io.*;
import java.net.*;


public class FTPClient
{
    private static String getFileContent(String fileName)
    {
        try
        {
            InputStream stream = FTPClient.class.getResourceAsStream(fileName);
            StringBuilder builder = new StringBuilder();

            int i =0;
            while ((i = stream.read()) != -1)
            {
                builder.append((char)i);
            }

            return  builder.toString();
        }
        catch (Exception ex)
        {
            System.out.println("Exception while getting file "+ex);
        }

        return null;
    }
    public static void main (String argv[])
    {
        String fileName = "/file.txt";
        String host = "locahost";
        int port = 5555;

        String content = getFileContent(fileName);
        //String content = "test";
        try
        {
            Socket socketClient= new Socket( "localhost" , 5555 );
            BufferedReader reader = new BufferedReader( new
                    InputStreamReader(socketClient.getInputStream()));

            BufferedWriter writer= new BufferedWriter( new
                    OutputStreamWriter(socketClient.getOutputStream()));


            writer.write(content +" \n");
            writer.flush();
            System.out.println("File sent");

            String serverResponse;
            while ((serverResponse = reader.readLine())!= null ) {
                System.out.println( "Client:" + serverResponse);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
