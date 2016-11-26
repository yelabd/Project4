import java.lang.*;
import java.util.Random;

/**
 * Created by youssefelabd on 11/22/16.
 */
public class tokenGenerators {
    public static void main(String[] args) {
        gameToken();
        userToken();
    }

    public static String gameToken(){
        String gameToken;
        char a,b,c;
        String lowerCaseSet = "abcdefghijklmnopqrstuvwxyz";
        Random rand = new Random();

        gameToken = ""+lowerCaseSet.charAt(rand.nextInt(26))+lowerCaseSet.charAt(rand.nextInt(26))+lowerCaseSet.charAt(rand.nextInt(26));
        System.out.println(gameToken);

        return gameToken;

    }
    public static String userToken(){
        String userToken = "";
        String set = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rand = new Random();

        for (int i = 0; i < 10;i++){
            userToken += set.charAt(rand.nextInt(52));
        }

        System.out.println(userToken);



        return userToken;
    }
}
