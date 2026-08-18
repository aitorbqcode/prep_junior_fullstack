import java.util.HashMap;

public class DuplicateEncoder {
    static String encode(String word){

        word = word.toLowerCase();

        HashMap<Character, Integer> letters = new HashMap<>();

        for(int i = 0; i < word.length(); i++){
            letters.put(word.charAt(i), letters.getOrDefault(word.charAt(i), 0) + 1);
        }

        StringBuilder result = new StringBuilder();

        for(int i = 0; i < word.length(); i++){
            if(letters.get(word.charAt(i)) != 1){
                result.append(")");
            } else {
                result.append("(");
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(encode("(( @"));
    }
}
