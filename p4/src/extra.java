/**
 * Created by farhanshafi on 11/29/16.
 */
public class extra {

    public static boolean formatCheck(String request){
        if (request== "CREATENEWUSER" || request== "LOGIN" || request== "STARTNEWGAME" || request== "JOINGAME" || request== "ALLPARTICIPANTSHAVEJOINED"
            || request== "PLAYERSUGGESTION" || request== "PLAYERCHOICE"){
            return true;
        }

        else return false;
    }

    /*public static void main(String[] args) {
        boolean x;
        x = formatCheck("test");
        System.out.println(x);
    }*/


}
