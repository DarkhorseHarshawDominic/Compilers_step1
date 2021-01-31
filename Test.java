import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Test {

    public static void main(String[] args)  {

        try {

            Scanner scanner = new Scanner(System.in);

            //creating a fileWriter object that will write to a file given a file path
           
	    FileWriter fileWriter = new FileWriter("/home/58/n01382358/COP4620/Project1/Compilers_step1/Output.txt");



            Process p;
            p = Runtime.getRuntime().exec(cat *.micro | grun Little prog -tokens);//cmd /c <SomeCommand>
            p.waitFor();// causes the current thread to wait until this process has ended
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                fileWriter.write(line + "\n");
                System.out.println(line);
            }
            fileWriter.close();
            scanner.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

