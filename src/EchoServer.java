import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Marcel on 20.12.2016.
 */
public class EchoServer {
    private int port;
    private ThreadPool executor;

    public EchoServer(int port, int noOfThreads, int maxNoOfTasks) throws IOException {
        this.executor = new ThreadPool(3, 3);
        this.port = port;
    }

    public static void stop(ThreadPool executor) {
        executor.stop();
    }

    private void startServer() {

        try(ServerSocket serverSocket = new ServerSocket(port)) {
            while(true) {
                try {
                    executor.execute(new ServerThread(serverSocket.accept()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            System.err.println("Could not listen on port " + port);
            System.exit(-1);
        }
        finally {
            EchoServer.stop(executor);
        }
    }

    public static void main(String[] args) throws IOException {
        EchoServer server = new EchoServer(6666, 3, 3);
        server.startServer();
    }

    private void closeServer() {}


}