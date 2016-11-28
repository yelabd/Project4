import java.io.IOException;
import java.util.HashMap;

/**
 * Created by youssefelabd on 11/26/16.
 */
public class GameSession extends Thread {
    private String leaderToken;
    private String gameToken;
    private HashMap <String,UserInfo> gameSession = new HashMap<String,UserInfo>();

    public GameSession(String gameToken,UserInfo userInfo,String leaderToken){
        this.leaderToken = leaderToken;
        this.gameToken = gameToken;
        gameSession.put(leaderToken,userInfo);

    }

    public void run(){
        try{
            gameSetup();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void gameSetup() throws IOException{
        while(true){

    }
}
}