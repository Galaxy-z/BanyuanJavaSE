import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ServerTest {
    public static void main(String[] args) throws IOException {
        try (ServerSocket ss = new ServerSocket(8189);) {
            int i = 1;
            while (true){
                Socket incoming = ss.accept();
                System.out.println("Spawning" + i);
                Runnable r = new ThreadedEchoHandler(incoming);
                Thread t = new Thread(r);
                t.start();
                i++;
            }
        }

    }
}

class ThreadedEchoHandler implements Runnable {

    private Socket incoming;

    public ThreadedEchoHandler(Socket incoming) {
        this.incoming = incoming;
    }

    @Override
    public void run() {

        try (InputStream inputStream = incoming.getInputStream();
             OutputStream outputStream = incoming.getOutputStream();
             Scanner in = new Scanner(inputStream, StandardCharsets.UTF_8);
             PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true)) {

            out.println("Hello! Enter BYE to exit");

            boolean done = false;
            while (!done && in.hasNextLine()) {
                String line = in.nextLine();
                out.println("Echo: " + line);
                if (line.trim().equals("BYE")) done = true;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
