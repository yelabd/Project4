/**
 * Created by youssefelabd on 11/24/16.
 */
public class tester {
    public static void main(String[] args) {
        splitter("this--is--not--one--word");

    }
    public static void splitter(String toSplit){
        String[] first;

        first = toSplit.split("--");

        for (int i = 0; i < first.length;i++){
            System.out.println(first[i]);
        }
}
}
