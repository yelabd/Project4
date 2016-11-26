import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by youssefelabd on 11/21/16.
 */
public class ClientHandler extends Thread{
    Socket userSocket;
    int userID;

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
        PrintWriter out;
        BufferedReader in;
        String inFromClient;
        String[] splitInput;
        String createResponse = "RESPONSE--CREATENEWUSER--";
        String loignResponse = "RESPONSE--LOGIN--";
        String startResponse = "RESPONSE--STARTNEWGAME --";
        String joinResponse = "RESPONSE--JOINGAME--";
        String launchResponse = "RESPONSE--ALLPARTICIPANTSHAVEJOINED--";
        String playerSuggestion = "RESPONSE--PLAYERSUGGESTION--";
        while (true){
            out = new PrintWriter(userSocket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(userSocket.getInputStream()));

            while (true){
                inFromClient = in.readLine();
                splitInput = inFromClient.split("--");
                //check if user already exists (1) and invalid message format (2)
                String firstInput = splitInput[0];
                switch (firstInput){
                    case "CREATENEWUSER":
                        if(!credentialChecker.usernameChecker(splitInput[1])){
                        out.println(createResponse+"INVALIDUSERNAME");
                        }else if (!credentialChecker.passwordChecker(splitInput[2])){
                        out.println(createResponse+"INVALIDPASSWORD");
                        }else {
                        out.println(createResponse+"SUCCESS");
                            //put user info in the file
                        }
                        break;
                    case "LOGIN":

                        break;
                    case "STARTNEWGAME":
                        break;
                    case "JOINGAME":
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


}