public class FindNextSquare {

    /*
    You might know some pretty large perfect squares. But what about the NEXT one?

    Complete the findNextSquare method that finds the next integral perfect square after the one passed as a parameter.
    Recall that an integral perfect square is an integer n such that sqrt(n) is also an integer.

    If the argument is itself not a perfect square then return either -1 or an empty value like None or null,
    depending on your language. You may assume the argument is non-negative.
     */

    public static long findNextSquare(long sq){
        //Get the squared number without decimals
        long num = (long) Math.sqrt(sq);

        //If this num equals the sq we sum up to num and we pow 2 to get the next square if not return -1
        if (num * num == sq) {
            return (num + 1) * (num + 1);
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(findNextSquare(114));
    }
}
