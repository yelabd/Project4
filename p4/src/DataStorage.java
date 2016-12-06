import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by youssefelabd on 11/26/16.
 */
public class DataStorage {
    private static File wordle;
    private File userDatabase;
    private static BufferedReader wordleIn;
    //private static BufferedReader userDatabaseIn;
    public static BufferedWriter userDatabaseOut;

    public DataStorage() throws IOException {

        //wordle = new File("/Users/youssefelabd/Desktop/cs180/Project4/WordleDeck.txt");
        userDatabase = new File("./UserDatabase");

        //userDatabase = new File("/Users/youssefelabd/Desktop/cs180/Project4/UserDatabase.txt");
        wordle = new File("./WordleDeck");

        if (!wordle.exists()) {
            wordle.createNewFile();
        }
        if (!userDatabase.exists()) {
            userDatabase.createNewFile();
        }
        //wordleIn = new BufferedReader(new FileReader(wordle));
        //userDatabaseIn = new BufferedReader(new FileReader(userDatabase));
        //userDatabaseOut = new BufferedWriter(new FileWriter(userDatabase, true));

    }

    public boolean checkUserExists(String username) throws IOException {
        BufferedReader userDatabaseIn = new BufferedReader(new FileReader(userDatabase));
        String fileLine;
        while ((fileLine = userDatabaseIn.readLine()) != null) {
            String[] splitFile = fileLine.split(":");
            if (splitFile[0].equals(username)) {
                //closer();
                return true;
            }
        }
        userDatabaseIn.close();
        //closer();
        return false;
    }


    public boolean checkUsernamePasswordCorrect(String username, String password) throws IOException {
        BufferedReader userDatabaseIn = new BufferedReader(new FileReader(userDatabase));
        String fileLine;
        while ((fileLine = userDatabaseIn.readLine()) != null) {
            String[] splitFile = fileLine.split(":");
            if (splitFile[0].equals(username)) {
                if (splitFile[1].equals(password)) {
                    //closer();
                    return true;
                }
            }
        }
        userDatabaseIn.close();
        //closer();
        return false;
    }

    public boolean passwordChecker(String password) throws IOException {
        BufferedReader userDatabaseIn = new BufferedReader(new FileReader(userDatabase));
        String fileLine;
        while ((fileLine = userDatabaseIn.readLine()) != null) {
            String[] splitFile = fileLine.split(":");
            if (splitFile[1].equals(password)) {
                //closer();
                return true;
            }
        }
        userDatabaseIn.close();
        //closer();
        return false;
    }

    public synchronized void addUser(String username, String password) throws IOException {
        BufferedWriter userDatabaseOut = new BufferedWriter(new FileWriter(userDatabase, true));
        String fileInput = username + ":" + password + ":0:0:0";
        userDatabaseOut.write(fileInput);
        userDatabaseOut.newLine();
        userDatabaseOut.flush();
        userDatabaseOut.close();
        //closer();
    }

    public static ArrayList<String> wordleDeckGetter() throws IOException {
        BufferedReader wordleDeckIn = new BufferedReader(new FileReader(wordle));

        ArrayList<String> wordleDeckList = new ArrayList<>();
        String fileLine = "";

        while ((fileLine = wordleDeckIn.readLine()) != null) {
            //System.out.println(fileLine);
            wordleDeckList.add(fileLine);
        }

        wordleDeckIn.close();

        return wordleDeckList;
    }

    public synchronized int[] getScores(String name) throws IOException {
        int[] scores = new int[3];
        BufferedReader userDatabaseIn = new BufferedReader(new FileReader(userDatabase));
        String fileLine;
        while ((fileLine = userDatabaseIn.readLine()) != null) {
            String[] splitFile = fileLine.split(":");
            if (splitFile[0].equals(name)) {
                scores[0] = Integer.parseInt(splitFile[2]);
                scores[1] = Integer.parseInt(splitFile[3]);
                scores[2] = Integer.parseInt(splitFile[4]);

            }
        }
        userDatabaseIn.close();
        return scores;

    }

    public static void updateScores()  {

        //File originalFile = new File("/Users/youssefelabd/Desktop/cs180/Project4/UserDatabase.txt");
        File originalFile = new File("./UserDatabase");



        Scanner scanner;
        //scanner = new Scanner(originalFile);
        ArrayList<String> updatedScores = new ArrayList<>();
        try{
            BufferedReader userDatabaseIn = new BufferedReader(new FileReader(new File("./UserDatabase")));
            //BufferedReader userDatabaseIn = new BufferedReader(new FileReader(new File("/Users/youssefelabd/Desktop/cs180/Project4/UserDatabase.txt")));
            //PrintWriter userDatabaseOut = new PrintWriter(new File("/Users/youssefelabd/Desktop/cs180/project4/UserDatabase.txt"));
            //File tempFile = new File("/Users/youssefelabd/Desktop/cs180/Project4/UserDatabase.txt");
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


    public void closer() {
        try {
            wordleIn.close();
            //userDatabaseIn.close();
            userDatabaseOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
