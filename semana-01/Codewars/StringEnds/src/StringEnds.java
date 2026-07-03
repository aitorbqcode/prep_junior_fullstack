public class StringEnds {

    public static boolean solution(String str, String ending) {
        //Checks if ends or not
        return str.endsWith(ending);
    }

    public static void main(String[] args) {
        System.out.println(solution("sumo", ""));
    }
}
