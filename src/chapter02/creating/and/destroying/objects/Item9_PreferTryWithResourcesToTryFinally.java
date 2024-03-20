package chapter02.creating.and.destroying.objects;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Item9_PreferTryWithResourcesToTryFinally {
}


class ClosingResources {

    // try-with-resources - the best way to close resources!
    static String firstLineOfFileWithResource(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(
                new FileReader(path))) {
            return br.readLine();
        } catch (IOException e) {
            return "oops";
        }
    }

    // try-finally - No longer the best way to close resources!
    static String firstLineOfFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        try {
            return br.readLine();
        } finally {
            br.close();
        }
    }

    // try-with-resources on multiple resources - short and sweet
    static void copyWithResource(String src, String dst) throws IOException {
        try (InputStream in = Files.newInputStream(Paths.get(src));
             OutputStream out = Files.newOutputStream(Paths.get(dst))) {
            byte[] buf = new byte[10];
            int n;
            while ((n = in.read(buf)) >= 0)
                out.write(buf, 0, n);
        }
    }

    // try-finally is ugly when used with more than one resource!
    static void copy(String src, String dst) throws IOException {
        InputStream in = Files.newInputStream(Paths.get(src));
        try {
            OutputStream out = Files.newOutputStream(Paths.get(dst));
            try {
                byte[] buf = new byte[10];
                int n;
                while ((n = in.read(buf)) >= 0)
                    out.write(buf, 0, n);
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }


}