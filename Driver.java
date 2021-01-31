package ANTLR_Project1;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args)  {

        Scanner scanner = new Scanner(System.in);

        //gathering the inputs from the user


        System.out.print("Please enter a destination path for your text file: ");
        String filePath = scanner.nextLine();//where you want to write to

        System.out.println("Please enter a command for command line");
        String newCommand = scanner.nextLine();

        //creating a fileWriter object that will write to a file given a file path
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //prints command so the user can see what was entered
        System.out.println("\nThe executed command is---->  " + newCommand + "  <----\n");

        Process p;

        try {
            p = Runtime.getRuntime().exec("cmd /c " + newCommand);//cmd /c <SomeCommand>
            p.waitFor();// causes the current thread to wait until this process has ended
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            fileWriter.close();
            while ((line = reader.readLine()) != null) {
                fileWriter.write(line + "\n");
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        scanner.close();
    }
}
