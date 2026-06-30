public class FindOutlier {

    static int find(int[] integers) {
        int odds_counter = 0;
        int even_counter = 0;
        int odd = -1;
        int even = -1;

        for(int i = 0; i < integers.length; i ++){

            if(integers[i] % 2 == 0){
                even_counter++;
                even = integers[i];
            } else {
                odds_counter++;
                odd = integers[i];
            }
        }

        if(even_counter == 1){
            return even;
        } else {
            return odd;
        }
    }

    public static void main(String[] args) {
        int[] integers = {2, 4, -3};

        System.out.println(find(integers));
    }
}
