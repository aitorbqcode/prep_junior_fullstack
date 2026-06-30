import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Pick_Peaks {

    public static Map<String, List<Integer>> getPeaks(int[] arr) {
        //List for the values
        List<Integer> pos = new ArrayList<Integer>();
        List<Integer> peaks = new ArrayList<Integer>();
        int peakStart = -1;

        for (int i = 1; i < arr.length - 1; i++) {
            //Check if we are repeating a value
            if (arr[i] > arr[i-1]) {
                peakStart = i; // empieza un posible pico o plateau
            }
            if (peakStart != -1 && arr[i + 1] < arr[i]) {
                // confirmamos el pico
                pos.add(peakStart);
                peaks.add(arr[peakStart]);
                peakStart = -1;
            }
        }

        Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
        map.put ("pos", pos);
        map.put ("peaks", peaks);

        return map;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 2, 2, 3};

        System.out.println(getPeaks(array));
    }
}
