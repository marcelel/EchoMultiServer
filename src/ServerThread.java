import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Marcel on 20.12.2016.
 */

public class ServerThread extends Thread {
    private Socket socket = null;
    String inputLine;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(
                             socket.getInputStream()))
        ) {

            String connectedTo = "Connected to " + Thread.currentThread().getName();
            out.println(connectedTo);
            System.out.println(connectedTo);


            try {
                while ((inputLine = in.readLine()) != null) {
                    out.println(inputLine);
                }
            } catch (SocketException e) {
                System.out.println("Disconnected from thread: " + Thread.currentThread().getName());
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

