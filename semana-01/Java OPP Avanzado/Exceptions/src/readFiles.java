import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class readFiles {

    public static void main(String[] args) {
        /* Intentamos abrir el archivo */
        try (BufferedReader br = new BufferedReader(new FileReader("example.txt"))) {
            /* Var */
            String line;

            /* Read all the line of the file */
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

        /* In case, it doesn't exist you will have an error */
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
