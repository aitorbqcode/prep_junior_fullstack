

public class Dinglemouse {


    public static int countDeafRats(final String town) {
        //We delete the empty spaces
        String townWithoutSpaces = town.replace(" ", "");

        //Get the position of P
        int indexP = townWithoutSpaces.indexOf('P');
        //Set the counter to 0
        int count = 0;

        //Get the 2 string one before P and the other after P
        String firstHalf = townWithoutSpaces.substring(0, indexP);
        String secondHalf = townWithoutSpaces.substring(indexP + 1);

        // If we find O~ sum up
        for(int i = 0; i < firstHalf.length() / 2; i++){
            if(firstHalf.startsWith("O~", i * 2)) count++;
        }
        // If we find ~O sum up
        for(int i = 0; i < secondHalf.length() / 2; i++){
            if(secondHalf.startsWith("~O", i * 2)) count++;
        }
        // Return the count
        return count;
    }

    public static void main(String[] args) {
        System.out.println(countDeafRats("~O~O~O~OP~O~OO~"));
    }
}
