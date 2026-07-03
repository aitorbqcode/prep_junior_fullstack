import java.util.TreeMap;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static TreeMap<String, Integer> getTextNumAparences(String text){

        /* Create the TreeMap*/
        TreeMap<String, Integer> map = new TreeMap<>();
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