import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class test {
    public static Integer[] count_letters(String fname) throws FileNotFoundException, IOException {
        Integer[] result = new Integer[26];
        for (int i = 0; i < result.length; i++) {
            result[i] = 0;
        }
        BufferedReader reader;
        try {

            reader = new BufferedReader(new FileReader(fname));
            String line = reader.readLine();

            while (line != null) {
                String temp = line.toLowerCase();

                for (int i = 0; i < temp.length(); i++) {
                    int custom_ascii = (int) temp.charAt(i) - 97;
                    if (custom_ascii != -65) {
                        result[custom_ascii] = result[custom_ascii] + 1;
                    }
                }
                // read next line
                line = reader.readLine();

            }
            reader.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }

        return result;
    }

    public static void main(String[] args) {
        Integer[] answer = count_letters("dummy.txt");
        for (int i = 0; i < answer.length; i++) {
            System.out.println(answ);
        }
    }
}
