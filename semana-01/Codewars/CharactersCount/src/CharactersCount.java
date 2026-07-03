import java.util.HashMap;
import java.util.Map;

public class CharactersCount {

    public static Map<Character, Integer> count(String str) {
        //We create the hashmap struct following the order, that the values are added in the hashmap
        Map<Character, Integer> map = new HashMap<>();

        //Loop to add the character and the number of times that they are in the following word
        for(int i = 0; i < str.length(); i++){
            map.put(str.charAt(i), map.getOrDefault(str.charAt(i), 0) + 1);
        }

        return map;
    }

    public static void main(String[] args) {
        System.out.println(count(""));
    }
}
