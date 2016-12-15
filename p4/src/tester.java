import java.io.IOException;

/**
 * Created by youssefelabd on 11/24/16.
 */
public class tester {
    public static void main(String[] args) throws IOException {
           /* DataStorage triy = new DataStorage();

        triy.addUser("halo","dfdf");

        //splitter("this--is--not--one--word");
        */
           //System.out.println(2.734%2);
            boolean a = true || false;
            System.out.println(a);
    }
    public static void splitter(String toSplit){
        String[] first;

        first = toSplit.split("--");

        for (int i = 0; i < first.length;i++){
            System.out.println(first[i]);
        }
}
}
