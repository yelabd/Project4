import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by youssefelabd on 11/26/16.
 */
public class DataStorage{
    private static File wordle;
    private File userDatabase;
    private static BufferedReader wordleIn;
    //private static BufferedReader userDatabaseIn;
    public static BufferedWriter userDatabaseOut;

    public DataStorage() throws IOException{

            wordle = new File("/Users/youssefelabd/Desktop/cs180/project4/WordleDeck.txt");
            userDatabase = new File("/Users/youssefelabd/Desktop/cs180/project4/UserDatabase.txt");
            if (!wordle.exists()){
                wordle.createNewFile();
            }
            if (!userDatabase.exists()){
            userDatabase.createNewFile();
            }
            wordleIn = new BufferedReader(new FileReader(wordle));
            //userDatabaseIn = new BufferedReader(new FileReader(userDatabase));
            userDatabaseOut = new BufferedWriter(new FileWriter(userDatabase,true));

    }

    public boolean checkUserExists(String username) throws IOException{
        BufferedReader userDatabaseIn = new BufferedReader(new FileReader(userDatabase));
        String fileLine;
        while ((fileLine = userDatabaseIn.readLine()) != null){
            String[] splitFile = fileLine.split(":");
            if (splitFile[0].equals(username)){
                closer();
                return true;
            }
        }
        userDatabaseIn.close();
        closer();
        return false;
    }


    public boolean checkUsernamePasswordCorrect(String username,String password) throws IOException{
        BufferedReader userDatabaseIn = new BufferedReader(new FileReader(userDatabase));
        String fileLine;
        while ((fileLine = userDatabaseIn.readLine()) != null){
            String[] splitFile = fileLine.split(":");
            if (splitFile[0].equals(username)) {
                if (splitFile[1].equals(password)) {
                    closer();
                    return true;
                }
            }
        }
        userDatabaseIn.close();
        closer();
        return false;
    }

    public boolean passwordChecker(String password) throws IOException{
        BufferedReader userDatabaseIn = new BufferedReader(new FileReader(userDatabase));
        String fileLine;
        while ((fileLine = userDatabaseIn.readLine()) != null){
            String[] splitFile = fileLine.split(":");
                if (splitFile[1].equals(password)) {
                    closer();
                    return true;
                }
            }
        userDatabaseIn.close();
        closer();
        return false;
    }

    public synchronized void addUser(String username,String password) throws IOException {
        BufferedWriter userDatabaseOut = new BufferedWriter(new FileWriter(userDatabase,true));
        String fileInput = username+":"+password+":0:0:0";
        userDatabaseOut.write(fileInput);
        userDatabaseOut.newLine();
        userDatabaseOut.flush();
        userDatabaseOut.close();
        closer();
    }

    public static ArrayList<String> wordleDeckGetter() throws IOException{
        BufferedReader wordleDeckIn = new BufferedReader(new FileReader(wordle));

        ArrayList<String> wordleDeckList = new ArrayList<>();
        String fileLine = "";

        while ((fileLine = wordleDeckIn.readLine()) != null){
            //System.out.println(fileLine);
            wordleDeckList.add(fileLine);
        }

        wordleDeckIn.close();

        return wordleDeckList;
    }
    public synchronized int[] getScores(String name) throws IOException{
        int[] scores = new int[3];
        BufferedReader userDatabaseIn = new BufferedReader(new FileReader(userDatabase));
        String fileLine;
        while ((fileLine = userDatabaseIn.readLine()) != null){
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

    public synchronized void updateScores(String name,String oldScore,String password, int cumulativeScore, int numTimesFooledOthers, int numTimesFooledByOthers) throws IOException {
        BufferedReader userDatabaseIn = new BufferedReader(new FileReader(userDatabase));
        BufferedWriter userDatabaseOut = new BufferedWriter(new FileWriter(userDatabase));
        String fileLine, totalFile = "";
        String newOut = name + ":" + password + ":" + cumulativeScore + ":" + numTimesFooledOthers + ":" + numTimesFooledByOthers;


        /*Path path = Paths.get("/Users/youssefelabd/Desktop/cs180/project4/UserDatabase.txt");
        Charset charset = StandardCharsets.UTF_8;

        String content = new String(Files.readAllBytes(path), charset);
        content = content.replaceAll(oldScore, newOut);
        Files.write(path, content.getBytes(charset));


        while ((fileLine = userDatabaseIn.readLine()) != null){
            String[] splitFile = fileLine.split(":");
            if (splitFile[0].equals(name)) {
                String newOut1 = splitFile[0] + ":" + splitFile[1]  + ":" + cumulativeScore  + ":" + numTimesFooledOthers + ":" + numTimesFooledByOthers;
                totalFile += newOut1;
            }
            else{
                totalFile+= fileLine;
            }
        }

        userDatabaseOut.write(totalFile);
        userDatabaseOut.newLine();
        userDatabaseOut.flush();
        userDatabaseIn.close();
        userDatabaseOut.close();
        */


        Path filePath = Paths.get(userDatabase.getPath());
        List<String> fileContent = new ArrayList<>(Files.readAllLines(filePath, StandardCharsets.UTF_8));

        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).equals(oldScore)) {
                fileContent.set(i, newOut);
                break;
            }
        }

        Files.write(filePath, fileContent, StandardCharsets.UTF_8);
    }


    public void closer(){
        try {
            wordleIn.close();
            //userDatabaseIn.close();
            userDatabaseOut.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }


}
