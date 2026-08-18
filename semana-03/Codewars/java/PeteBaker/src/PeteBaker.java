import java.util.Map;

public class PeteBaker {
    public static int cakes(Map<String, Integer> recipe, Map<String, Integer> available) {

        // We initialize the min value and the firstLap to save the min value in the first loop
        int minValue = Integer.MAX_VALUE;

        //Start the loop
        for(String key : recipe.keySet()){
            //We check if the element of the recipe it's in the available ingredients, if not return 0
            if(available.containsKey(key)){
                //Get the value of cake we can make with the available ingredients
                int value = (int) Math.floor((double) available.get(key) / recipe.get(key));
                //If it's the first lap of the loop we save the value
                if(minValue > value) {
                    //If the min value it's bigger than value we save the value
                    minValue = value;
                }
            } else {
                return 0;
            }
        }
        return minValue;
    }

    /*
    Con Stream Lambda
    public static int cakes(Map<String, Integer> recipe, Map<String, Integer> available) {
    return recipe.entrySet().stream()
            .mapToInt(entry -> available.getOrDefault(entry.getKey(), 0) / entry.getValue())
            .min()
            .orElse(0);
}
     */

    public static void main(String[] args) {
        Map<String, Integer> recipe = Map.of(
                "flour", 500,
                "sugar", 200,
                "eggs", 1,
                "cinnamon", 300);
        Map<String, Integer> available = Map.of(
                "flour", 1200,
                "sugar", 1200,
                "eggs", 5,
                "milk", 200);

        System.out.println(cakes(recipe, available));
    }
}
