import java.util.Arrays;

public class Xbonacci {

    public static double[] tribonacci(double[] s, int n) {


        double[] intArray = new double[n];


        for(int i = 0; i < n; i++){

            if(i < 3){

                intArray[i] = s[i];

            } else {

                intArray[i] = intArray[i - 1] + intArray[i - 2] + intArray[i - 3];

            }

        }


        return intArray;

    }

    public static void main(String[] args) {
        double[] array = {1,1,1};
        System.out.println(Arrays.toString(tribonacci(array, 10)));
    }
}