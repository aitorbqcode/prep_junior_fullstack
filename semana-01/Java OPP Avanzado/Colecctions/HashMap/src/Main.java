import java.util.HashMap;
public class Main {

    public static  HashMap<String, Integer> getTextNumAparences(String text){

        /* Create the hashMap*/
        HashMap<String, Integer> map = new HashMap<>();
        String[] words = text.toLowerCase().split("\\W+");

        /* Loop to add everyone either exist or not to add it to the count */
        for(String word : words){
            map.put(word, map.getOrDefault(word, 0) + 1);

        }

        //Return the map
        return map;
    }


    public static void main(String[] args) {

        String text = "Hello Hello world world, this is a test hello.";

        System.out.println(getTextNumAparences(text));
    }
}