import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientTest {
    public static void main(String[] args) throws IOException {

        try (Socket s = new Socket("127.0.0.1", 8189)) {
            OutputStream outputStream = s.getOutputStream();
            InputStream inputStream = s.getInputStream();

            try (PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true)) {
                Scanner in = new Scanner(inputStream, StandardCharsets.UTF_8);
                Scanner text = new Scanner(System.in);

                while (true){
                    if (in.hasNextLine()){
                        System.out.println(in.nextLine());
                    }
                    out.println(text.next());

                }

            }

        }

    }

}
