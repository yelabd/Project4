import com.sun.corba.se.spi.activation.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by youssefelabd on 11/21/16.
 */
public class ClientHandler extends Thread{
    Socket userSocket;
    int userID;
    PrintWriter out;
    BufferedReader in;
    private String playerToken;
    private String gameToken;
    static ArrayList<UserInfo> usersInSession;
    private String[] currentCard;
    private String currentSuggestion;
    private String currentAnswer;
    public static final String ANSI_RESET = "\u001B[0m";


    public ClientHandler(Socket socket, int userID){
        this.userSocket = socket;
        this.userID = userID;
    }

    public void run(){
        try{
            clientCommunication();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void clientCommunication() throws IOException{
        DataStorage trial = new DataStorage();

        String inFromClient;
        String[] splitInput;
        String createResponse = "RESPONSE--CREATENEWUSER--";
        String loginResponse = "RESPONSE--LOGIN--";
        String startResponse = "RESPONSE--STARTNEWGAME--";
        String joinResponse = "RESPONSE--JOINGAME--";
        String launchResponse = "RESPONSE--ALLPARTICIPANTSHAVEJOINED--";
        String playerSuggestion = "RESPONSE--PLAYERSUGGESTION--";

            out = new PrintWriter(userSocket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(userSocket.getInputStream()));

            mainLoop : while (true){
                inFromClient = in.readLine();
                System.out.println((char)27+"[34;1mClient "+userID+": "+ ANSI_RESET+inFromClient);
                splitInput = inFromClient.split("--");
                //check if user already exists (1) and invalid message format (2)
                String firstInput = splitInput[0];
                switch (firstInput){
                    case "CREATENEWUSER":
                        if(!CredentialChecker.usernameChecker(splitInput[1])){
                        out.println(createResponse+"INVALIDUSERNAME");
                        }else if (!CredentialChecker.passwordChecker(splitInput[2])){
                        out.println(createResponse+"INVALIDPASSWORD");
                        }else {
                            if (!trial.checkUserExists(splitInput[1])) {
                                trial.addUser(splitInput[1], splitInput[2]);
                                out.println(createResponse+"SUCCESS");
                            }else{
                                out.println(createResponse+"USERALREADYEXISTS");
                            }
                        }
                        break;
                    case "LOGIN":
                        if(trial.checkUserExists(splitInput[1])){
                            if (trial.checkUsernamePasswordCorrect(splitInput[1], splitInput[2])){
                                 playerToken = TokenGenerators.userToken();
                                ServerListener.currentSession.put(playerToken,new UserInfo(splitInput[1],playerToken,userID));
                                out.println(loginResponse+"SUCCESS--"+playerToken);
                            }else{
                                out.println(loginResponse+"INVALIDUSERPASSWORD");
                            }
                        }else{
                            out.println(createResponse+"UNKNOWNUSER");
                        }
                        break;
                    case "STARTNEWGAME":
                        //TODO: add other status cases
                        String gameToken = TokenGenerators.gameToken();
                        if (ServerListener.gameTokens.size() > 0){
                            while (!checkGameToken(gameToken)){
                            gameToken = TokenGenerators.gameToken();
                            }
                        }
                        //ArrayList<UserInfo> userInfoArrayList = new ArrayList<UserInfo>();
                        //userInfoArrayList.add(ServerListener.currentSession.get(splitInput[1]));
                        //ServerListener.gameSession.put(gameToken,ServerListener.sessionStorage.add());
                        this.gameToken = gameToken;
                        //ServerListener.gameSessionInfo.put(gameToken,new GameSession(gameToken,splitInput[1],userID));
                        //leaderControl = new GameSession(gameToken,splitInput[1],userID);
                        ServerListener.gameArray.put(gameToken,new ArrayList<String>());
                        //ServerListener.suggestionArray.put(gameToken,new ArrayList<String>());
                        ServerListener.gameArray.get(gameToken).add(splitInput[1]);
                        ServerListener.suggestionArray.put(gameToken,new HashMap<String,String>());
                        ServerListener.gameTokens.add(gameToken);
                        ServerListener.currentSession.get(splitInput[1]).setGameToken(gameToken);
                        ServerListener.currentSession.get(splitInput[1]).setLeader(true);
                        ServerListener.allUsers.add(ServerListener.currentSession.get(splitInput[1]));
                        out.println(startResponse+"SUCCESS--"+gameToken);
                        break;
                    case "JOINGAME":
                        //TODO: create another hashmap and find way to get leader id
                        if (!gameTokenExists(splitInput[2])) {
                            out.println(joinResponse + "GAMEKEYNOTFOUND");
                        }else if (!ServerListener.currentSession.containsKey(splitInput[1])){
                            out.println(joinResponse+"USERNOTLOGGEDIN");
                        }else {
                            this.gameToken = splitInput[2];
                            ServerListener.allUsers.add(ServerListener.currentSession.get(splitInput[1]));
                            ServerListener.gameArray.get(this.gameToken).add(splitInput[1]);
                            //TODO: Find shorter way to do the line below
                            ServerListener.handlerStorage.get(ServerListener.currentSession.get(ServerListener.gameArray.get(this.gameToken).get(0)).getUserID()).out.println("NEWPARTICIPANT--"+ServerListener.currentSession.get(playerToken).getUsername()+"--0");
                            out.println(joinResponse+"SUCCESS--"+splitInput[2]);
                            //if (ServerListener.gameSessionInfo.get(splitInput[2]).gameToken.equals(splitInput[2])){
                            /*if(leaderControl.gameToken.equals(splitInput[2])){
                                //ServerListener.gameSessionInfo.get(splitInput[2]).players.add(splitInput[1]);
                                ServerListener.handlerStorage.get(ServerListener.gameSessionInfo.get(splitInput[2]).leaderID).out.println("NEWPARTICIPANT--"+ServerListener.currentSession.get(playerToken).getUsername()+"--0");
                                out.println(joinResponse+"SUCCESS--"+splitInput[2]);
                            }else {
                                System.out.println("\u001B[35;1mExpected error: Could not send message to user; Attempt Number: ");//+(i+1)+"/"+ServerListener.allUsers.size());
                            }
                            /*
                            subloop : for(int i = 0; i < ServerListener.allUsers.size();i++){
                                if (ServerListener.allUsers.get(i).isLeader() && ServerListener.allUsers.get(i).getGameToken().equals(splitInput[2])){
                                    ServerListener.handlerStorage.get(ServerListener.allUsers.get(i).getUserID()).out.println("NEWPARTICIPANT--"+ServerListener.currentSession.get(playerToken).getUsername()+"--0"); //TODO: add correct score
                                    System.out.println("\u001B[36;1mSent message to Leader with ID: "+ANSI_RESET+ServerListener.allUsers.get(i).getUserID());
                                    ServerListener.currentSession.get(splitInput[1]).setGameToken(splitInput[2]);
                                    break subloop;
                                }else{
                                    System.out.println("\u001B[35;1mExpected error: Could not send message to user; Attempt Number: "+(i+1)+"/"+ServerListener.allUsers.size());
                                }
                                */
                            //out.println("NEWPARTICIPANT--"+ServerListener.currentSession.get(splitInput[1]).getUsername()+"--0");
                        }
                        break;
                    case "ALLPARTICIPANTSHAVEJOINED":
                        if (!ServerListener.currentSession.containsKey(splitInput[1])){
                            out.println("RESPONSE--ALLPARTICIPANTSHAVEJOINED--USERNOTLOGGEDIN");
                        } else if (!gameTokenExists(splitInput[2])){
                            out.println("RESPONSE--ALLPARTICIPANTSHAVEJOINED--INVALIDGAMETOKEN");
                        } else if(!ServerListener.currentSession.get(splitInput[1]).isLeader()){
                            out.println("RESPONSE--ALLPARTICIPANTSHAVEJOINED--USERNOTGAMELEADER");
                        }else{
                            startGame(splitInput[2],splitInput[1]);
                            ArrayList<String> temp = ServerListener.gameArray.get(splitInput[2]);
                            //ServerListener.gameSessionInfo.get(splitInput[2]).collectPlayers();
                            for (int i = 0; i < temp.size();i++){
                                ServerListener.handlerStorage.get(ServerListener.currentSession.get(temp.get(i)).getUserID()).out.println("NEWGAMEWORD--"+currentCard[0]+"--"+currentCard[1]);
                                //System.out.println(ServerListener.gameSessionInfo.get(splitInput[2]).players.size());
                            }

                            //out.println("NEWGAMEWORD--"+currentCard[0]+"--"+currentCard[1]);
                        }
                        break;
                    case "PLAYERSUGGESTION":
                        if (!ServerListener.currentSession.containsKey(splitInput[1])){
                            out.println(playerSuggestion+"USERNOTLOGGEDIN");
                        } else if (!gameTokenExists(splitInput[2])){//TODO: Check if it belongs to user
                            out.println(playerSuggestion+"INVALIDGAMETOKEN");
                        }else{
                            currentSuggestion = splitInput[3];
                            //ServerListener.suggestionArray.put(splitInput[2],new HashMap<String,String>());
                            ServerListener.suggestionArray.get(splitInput[2]).put(splitInput[1],currentSuggestion);

                            while (ServerListener.suggestionArray.get(splitInput[2]).size() != ServerListener.gameArray.get(splitInput[2]).size()){
                            }
                            //System.out.println("Made it");
                        }
                        break;
                    case "PLAYERCHOICE":
                        break;
                    default:
                        out.println();
                }

            }

    }

    public boolean checkGameToken(String gameToken) {
        for (int i = 0; i < ServerListener.gameTokens.size(); i++) {
            if (ServerListener.gameTokens.get(i).equals(gameToken)) {
                return false;
            }
        }
        return true;
    }
    public boolean gameTokenExists(String gameToken) {
        for (int i = 0; i < ServerListener.gameTokens.size(); i++) {
            if (ServerListener.gameTokens.get(i).equals(gameToken)) {
                return true;
            }
        }
        return false;
    }

    public String getPlayerToken(){
        return this.playerToken;
    }

    public void startGame(String gameToken,String leaderToken){
        //ServerListener.gameStarted.put(gameToken,true);
        ArrayList<String> wordleDeck = null;
        try {
            wordleDeck = DataStorage.wordleDeckGetter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentCard = wordleDeck.get(0).split(":");
        //TODO: check if it is the final round


    }



}