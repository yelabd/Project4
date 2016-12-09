import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by youssefelabd on 12/5/16.
 */
public class UpdateScores {
    public static void updateScores()  {

        File originalFile = new File("/Users/youssefelabd/Desktop/cs180/Project4/UserDatabase.txt");

        Scanner scanner;
        //scanner = new Scanner(originalFile);
        ArrayList<String> updatedScores = new ArrayList<>();
        try{
        BufferedReader userDatabaseIn = new BufferedReader(new FileReader(new File("/Users/youssefelabd/Desktop/cs180/project4/UserDatabase.txt")));

        //PrintWriter userDatabaseOut = new PrintWriter(new File("/Users/youssefelabd/Desktop/cs180/project4/UserDatabase.txt"));

        File tempFile = new File("/Users/youssefelabd/Desktop/cs180/Project4/UserDatabase.txt");

        String fileLine;
        //String newOut = name + ":" + password + ":" + cumulativeScore + ":" + numTimesFooledOthers + ":" + numTimesFooledByOthers;


        while ((fileLine = userDatabaseIn.readLine()) != null) {
            updatedScores.add(fileLine);
        }

        for (int i = 0; i < updatedScores.size(); i++) {
            //System.out.println(updatedScores.get(i));
            String[] splitFile = updatedScores.get(i).split(":");
            for (int j = 0; j < ServerListener.allUsers.size(); j++) {
                if (splitFile[0].equals(ServerListener.allUsers.get(j).substring(0, splitFile[0].length()))) {
                    //System.out.println(ServerListener.updatedScores.get(splitFile[0]));
                    updatedScores.set(i, ServerListener.updatedScores.get(splitFile[0]));
                    //System.out.println("Updated : " + updatedScores.get(i));
                }
            }
        }
            userDatabaseIn.close();
        }catch (IOException e) {
            System.err.println("IOException");
        }

        try {
            BufferedWriter userDatabaseOut = new BufferedWriter(new FileWriter(originalFile));


            for (int h = 0; h < updatedScores.size(); h++) {
                //System.out.println("Updated : " + updatedScores.get(h));
                userDatabaseOut.write(updatedScores.get(h));
                userDatabaseOut.newLine();
            }
            userDatabaseOut.flush();
            userDatabaseOut.close();

        } catch (IOException e) {
            System.err.println("IOException");

        }

            //userDatabaseOut.close();
            //userDatabaseIn.close();

            /*if (!originalFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            if (!tempFile.renameTo(originalFile))
                System.out.println("Could not rename file");
                */

        }
    }

