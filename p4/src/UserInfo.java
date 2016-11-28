/**
 * Created by youssefelabd on 11/26/16.
 */
public class UserInfo {
    private String username;
    private String userToken;
    private String gameToken;
    private boolean isLeader = false;
    private int userID;

    public UserInfo(String username,String userToken,int userID){
        this.username = username;
        this.userToken = userToken;
        this.userID = userID;
    }
    public String getUserToken() {
        return userToken;
    }
    public String getGameToken() {
        return gameToken;
    }

    public void setGameToken(String gameToken) {
        this.gameToken = gameToken;
    }

    public String getUsername() {
        return username;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
    }

    public int getUserID(){
        return userID;
    }
}
