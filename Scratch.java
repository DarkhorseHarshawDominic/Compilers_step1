import java.io.*;
import java.util.Scanner;

class Scratch {
	public static void main(String[] args) throws IOException {


        try {

	    executeCommands();            

            //creating a fileWriter object that will write to a file given a file path
            FileWriter fileWriter = new FileWriter(System.getProperty("user.dir")+"/Output.txt");

            Process p;
            p = Runtime.getRuntime().exec("grun Little prog " + System.getProperty("user.dir") + "/Step1/inputs/s* " + "-tokens");//cmd /c <SomeCommand>
            p.waitFor();// causes the current thread to wait until this process has ended
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                fileWriter.write(line + "\n");
                System.out.println(line);
            }//while
            fileWriter.close();

        }catch (Exception e){//try
            e.printStackTrace();
        }//catch
    }//main


        public static void executeCommands() throws IOException {

            File tempScript = createTempScript();

            try {
                ProcessBuilder pb = new ProcessBuilder("bash", tempScript.toString());
                pb.inheritIO();
                Process process = pb.start();
                process.waitFor();
            } catch (InterruptedException e) {//try
                e.printStackTrace();
            } finally {//finally
                tempScript.delete();
            }//finally
        }//executeCommands

        public static File createTempScript() throws IOException {
            File tempScript = File.createTempFile("script", null);

            Writer streamWriter = new OutputStreamWriter(new FileOutputStream(tempScript));
            PrintWriter printWriter = new PrintWriter(streamWriter);
            printWriter.println("#!/bin/bash");
	//printWriter.println("source /home/58/n01382358/.bash_profile");
	    printWriter.println(System.getProperty("user.dir") + "/Output.txt");
          //printWriter.println("grun Little prog System.getProperty("user.dir") + "/*micro" -tokens > Output.txt");
		printWriter.println("ls > Output.txt");
            printWriter.close();

            return tempScript;
        }//createTempScript
    }//end of Scratch

