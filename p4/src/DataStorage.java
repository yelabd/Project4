import java.io.*;

/**
 * Created by youssefelabd on 11/26/16.
 */
public class DataStorage{
    private File wordle;
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

    public synchronized void addUser(String username,String password) throws IOException {
        BufferedWriter userDatabaseOut = new BufferedWriter(new FileWriter(userDatabase,true));
        String fileInput = username+":"+password+":0:0:0";
        userDatabaseOut.write(fileInput);
        userDatabaseOut.newLine();
        userDatabaseOut.flush();
        userDatabaseOut.close();
        closer();
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
