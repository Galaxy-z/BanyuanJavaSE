import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Test1 {
    public static void main(String[] args) throws UnknownHostException {
//        String s = "AbCdEf";
//        char[] chars = s.toCharArray();
//        List<Character> list = new ArrayList<>();
//        for (char aChar : chars) {
//            list.add(aChar);
//        }
//        list.stream().filter(c -> c > 97).forEach(System.out::println);
//
//        Scanner in = new Scanner("sad|dsads|fsd");
//        in.useDelimiter("\\PL+");
//        Stream<String> tokens = in.tokens();
//        tokens.forEach(System.out::println);
        InetAddress addr = InetAddress.getLocalHost();
        System.out.println("Local HostAddress: "+addr.getHostAddress());

    }
}
