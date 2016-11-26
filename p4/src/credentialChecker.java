/**
 * Created by youssefelabd on 11/22/16.
 */
public class credentialChecker {
    public static void main(String[] args) {
        System.out.println(passwordChecker("sjhgao"));
    }

    public static boolean usernameChecker(String username){
        String set = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_0123456789";
        if (username.length() == 0 || username.length() > 10){
            return false;
        }else {
            for (int i = 0; i < username.length();i++){
                boolean isExists = false;
                for (int j = 0; j < set.length();j++){
                    if (username.charAt(i) == set.charAt(j)){
                        isExists = true;
                    }
                }
                if (!isExists){
                    return false;
                }
            }

        }
        return true;

    }
    public static boolean passwordChecker(String password){
        String set = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ#$&*0123456789";
        String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        boolean isUppercase = false, isDigit = false;

        if (password.length() == 0 || password.length() > 10){
            return false;
        }else {
            for (int i = 0; i < password.length();i++){
                boolean isExists = false;
                for (int j = 0; j < set.length();j++){
                    if (password.charAt(i) == set.charAt(j)){
                        isExists = true;
                    }

                }
                for (int f = 0; f < uppercase.length();f++){
                    if (password.charAt(i) == uppercase.charAt(f)){
                        isUppercase = true;
                    }
                }
                for (int z = 0; z < numbers.length();z++){
                    if (password.charAt(i) == numbers.charAt(z)){
                        isDigit = true;
                    }
                }
                if ((!isExists)){
                    return false;
                }
            }
            if (!isUppercase || !isDigit){
                return false;
            }

        }
        return true;

    }

}
