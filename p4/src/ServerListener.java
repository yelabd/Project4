import com.sun.org.apache.xerces.internal.util.URI;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by youssefelabd on 11/20/16.
 */
public class ServerListener {

    public static HashMap<Integer, Socket> clientSocketStorage = new HashMap<Integer, Socket>();
    public static HashMap<Integer, ClientHandler> handlerStorage = new HashMap<Integer, ClientHandler>();
    public static HashMap <String,UserInfo> currentSession = new HashMap<>();
    public static HashMap<String, GameSession> gameSessionInfo = new HashMap<>();
    public static HashMap<String,ArrayList<String>> gameArray = new HashMap<>();
    public static HashMap<String, HashMap<String,String>> suggestionArray = new HashMap<>();
    public static HashMap<String, HashMap<String,String>> choiceArray = new HashMap<>();
    public static HashMap<String, Boolean> loggedOn = new HashMap<>();
    public static HashMap<String, String> updatedScores = new HashMap<>();
    public static HashMap<String, ArrayList<String>> scoreArray = new HashMap<>();
    public static ArrayList<String> entireDeck = new ArrayList<String>();
    //public static HashMap<String ,ArrayList<String>>
    public static HashMap<String,ArrayList<String>> deckCollections = new HashMap<>();
    //public static HashMap<String, ArrayList<String>> suggestionStorage = new HashMap<>();
    //public static HashMap<String, Boolean> gameStarted = new HashMap<>();
    public static ArrayList<String> allUsers = new ArrayList<>();
    public static ArrayList<String> gameTokens = new ArrayList<>();
    public static int userID = 0;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) throws IOException {
        try{
            startClient();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void startClient() throws IOException {
        ServerSocket serverSocket;

        try {
            System.out.println((char)27+"[33;1mCreating socket"+ANSI_RESET);
            serverSocket = new ServerSocket(5000);

            System.out.println((char)27+"[33;1mListening"+ANSI_RESET);
            while (true) {
                clientSocketStorage.put(userID, serverSocket.accept());
                System.out.println((char)27+"[32;1mGot a request from: "+ANSI_RESET+ clientSocketStorage.get(userID).getPort());
                handlerStorage.put(userID,new ClientHandler(clientSocketStorage.get(userID),userID));
                handlerStorage.get(userID).start();
                userID++;

            }
        } catch (URI.MalformedURIException e) {
            System.out.println("Could not conenct: "+ e.getMessage());
            System.exit(0);

        }


    }

}
