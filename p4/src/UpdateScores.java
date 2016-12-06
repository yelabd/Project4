import java.io.*;
import java.util.ArrayList;

/**
 * Created by youssefelabd on 12/5/16.
 */
public class UpdateScores {
    public static void updateScores() throws IOException {
        BufferedReader userDatabaseIn = new BufferedReader(new FileReader(new File("/Users/youssefelabd/Desktop/cs180/project4/UserDatabase.txt")));
        PrintWriter userDatabaseOut = new PrintWriter(new File("/Users/youssefelabd/Desktop/cs180/project4/UserDatabase.txt"));
        String fileLine;
        //String newOut = name + ":" + password + ":" + cumulativeScore + ":" + numTimesFooledOthers + ":" + numTimesFooledByOthers;
        ArrayList<String> updatedScores = new ArrayList<>();

        while ((fileLine = userDatabaseIn.readLine()) != null) {
            updatedScores.add(fileLine);
        }
        for (int i = 0; i < updatedScores.size(); i++) {
            String[] splitFile = updatedScores.get(i).split(":");
            for (int j = 0; i < ServerListener.allUsers.size(); j++) {
                if (splitFile[0].equals(ServerListener.allUsers.get(j).substring(0, splitFile[0].length()))) {
                    updatedScores.set(i, ServerListener.updatedScores.get(splitFile[0]));
                }
            }

            for (int h = 0; h < updatedScores.size(); h++) {
                userDatabaseOut.println(updatedScores.get(h));
            }
            //userDatabaseOut.flush();
            userDatabaseOut.close();
            userDatabaseIn.close();
        }
    }
}
