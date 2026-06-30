public class SumOfDigits {

    public static int digital_root(int n) {
        while(n > 9){
            String number = String.valueOf(n);
            int lenght = number.length();
            int value = 0;

            for(int i = 0; i < lenght; i++){
                value += (n % 10);
                n = n / 10;
            }
            n = value;
        }
        return n;
    }

    public static void main(String[] args){
        System.out.println(digital_root(493193));
    }
}
