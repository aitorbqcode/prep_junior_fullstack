import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ArrayDiff {

    public static int[] arrayDiff(int[] a, int[] b) {

        // We save the elements of b to have a lower cost of search in the loop
        Set<Integer> setB = new HashSet<>();
        for (int num : b) {
            setB.add(num);
        }

        ArrayList<Integer> list = new ArrayList<>();

        // 2. We check if the a values are in setB
        for (int i = 0; i < a.length; i++) {
            int key = a[i];
            // .contains() has a cost of O(1)
            if (!setB.contains(key)) {
                list.add(key);
            }
        }

        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    public static void main(String[] args) {

        int[] a = {1, 2};
        int[] b = {1};

        System.out.println(Arrays.toString(arrayDiff(a, b)));
    }
}
