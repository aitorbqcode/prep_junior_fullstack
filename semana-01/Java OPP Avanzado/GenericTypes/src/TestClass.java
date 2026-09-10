import java.util.ArrayList;
import java.util.List;

public class TestClass {

    public static <T> void fill(List<T> list, T val){
        for(int i = 0; i < list.size(); i++){
            //El add añade, pero el set remplaza, en el caso de que exista ya un valor en esa posición
            list.set(i, val);
        }
    }

    public static void main(String[] args) {

        List<String> nameList = new ArrayList<>();

        nameList.add("John");
        nameList.add("Mark");

        System.out.println(nameList);

        fill(nameList, "");

        System.out.println(nameList);

        List<Integer> numberList = new ArrayList<>();

        numberList.add(1);
        numberList.add(2);
        numberList.add(3);

        System.out.println(numberList);

        fill(numberList, 888);
        System.out.println(numberList);
    }
}
