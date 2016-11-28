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
    static ArrayList<UserInfo> usersInSession;
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
        while (true){
            out = new PrintWriter(userSocket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(userSocket.getInputStream()));

            while (true){
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
                            ServerListener.allUsers.add(ServerListener.currentSession.get(splitInput[1]));
                            subloop : for(int i = 0; i < ServerListener.allUsers.size();i++){
                                if (ServerListener.allUsers.get(i).isLeader() && ServerListener.allUsers.get(i).getGameToken().equals(splitInput[2])){
                                    ServerListener.handlerStorage.get(ServerListener.allUsers.get(i).getUserID()).out.println("NEWPARTICIPANT--"+ServerListener.currentSession.get(playerToken).getUsername()+"--0");
                                    System.out.println("\u001B[36;1mSent message to Leader with ID: "+ANSI_RESET+ServerListener.allUsers.get(i).getUserID());
                                    break subloop;
                                }else{
                                    System.out.println("\u001B[35;1mExpected error: Could not send message to user; Attempt Number: "+(i+1)+"/"+ServerListener.allUsers.size());
                                }
                            }
                            //out.println("NEWPARTICIPANT--"+ServerListener.currentSession.get(splitInput[1]).getUsername()+"--0");
                        }
                        break;
                    case "ALLPARTICIPANTSHAVEJOINED":
                        break;
                    case "PLAYERSUGGESTION":
                        break;
                    case "PLAYERCHOICE":
                        break;
                    default:
                        out.println();
                }

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



}