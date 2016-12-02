import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by youssefelabd on 11/26/16.
 */
public class GameSession {
    public String leaderToken;
    public int leaderID;
    public static String gameToken;
    public static ArrayList<String> players = new ArrayList<>();
    private ArrayList<String> playerSuggestions = new ArrayList<>();
    public int round = 0;
    private static int gameWordsLeft = 0;
    public static ArrayList<String> wordleDeck = new ArrayList<>();
    private HashMap <String,UserInfo> gameSession = new HashMap<String,UserInfo>();



    public GameSession(String gameToken,String leaderToken,int leaderID){
        this.gameToken = gameToken;
        this.leaderToken = leaderToken;
        this.leaderID = leaderID;
        //players.add(leaderToken);
        getWordleInfo();

    }
    public GameSession(String gameToken,UserInfo userInfo,String leaderToken){
        this.leaderToken = leaderToken;
        this.gameToken = gameToken;
        gameSession.put(leaderToken,userInfo);

    }
    public static void getWordleInfo(){
        try {
            DataStorage ds = new DataStorage();
            wordleDeck = ds.wordleDeckGetter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameWordsLeft = wordleDeck.size();
    }
    public static void gameWordDecerementer(){
        gameWordsLeft--;
    }
    public static int getGameWordsLeftGetter(){
        return gameWordsLeft;
    }
    public static void collectPlayers(){
        for (int i = 0;i < ServerListener.allUsers.size();i++){
                   if (ServerListener.allUsers.get(i).getGameToken().equals(gameToken)){
                players.add(ServerListener.allUsers.get(i).getUserToken());
            }
        }
    }

}
