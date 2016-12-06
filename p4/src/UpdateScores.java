import java.io.*;
import java.util.ArrayList;

/**
 * Created by youssefelabd on 12/5/16.
 */
public class UpdateScores {
    public static void updateScores() throws IOException {
        File originalFile = new File("/Users/farhanshafi/Desktop/cs rough/Project4/UserDatabase.txt");
        BufferedReader userDatabaseIn = new BufferedReader(new FileReader(originalFile/*new File("/Users/youssefelabd/Desktop/cs180/project4/UserDatabase.txt"*/));
        //PrintWriter userDatabaseOut = new PrintWriter(new File("/Users/youssefelabd/Desktop/cs180/project4/UserDatabase.txt"));

        File tempFile = new File("/Users/youssefelabd/Desktop/cs180/project4/tempfile.txt");
        PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

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
                pw.println(updatedScores.get(h));
                pw.flush();
            }
            //userDatabaseOut.flush();
            pw.close();

            //userDatabaseOut.close();
            userDatabaseIn.close();

            if (!originalFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            if (!tempFile.renameTo(originalFile))
                System.out.println("Could not rename file");

        }
    }
}
