package ANTLR_Project1;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner scanner = new Scanner(System.in);

        //gathering the inputs from the user


        System.out.print("Please enter a destination path for your text file: ");
        String filePath = scanner.nextLine();//where you want to write to

        System.out.println("Please enter a command for command line");
        String newCommand = scanner.nextLine();

        //creating a fileWriter object that will write to a file given a file path
        FileWriter fileWriter = new FileWriter(filePath);

        //prints command so the user can see what was entered
        System.out.println("\nThe executed command is---->  " + newCommand + "  <----\n");

        Process p;
        p = Runtime.getRuntime().exec(newCommand);//cmd /c <SomeCommand>
        p.waitFor();// causes the current thread to wait until this process has ended
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;

        while ((line = reader.readLine()) != null) {
            fileWriter.write(line + "\n");
            System.out.println(line);
        }
        fileWriter.close();
        scanner.close();
    }
}
