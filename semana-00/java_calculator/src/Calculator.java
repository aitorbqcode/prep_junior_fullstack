package src;
public class Calculator {

    public int sum(int a, int b){
        return a + b;
    }

    public int substraction(int a, int b){
        return a - b;
    }

    public int multiply(int a, int b){
        return a * b;
    }

    public float divide(int a, int b){
        if(b == 0){
            throw new ArithmeticException("You can divide by 0");
        }
        return (float) a / b;
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();
        System.out.println("The sum of 10 + 5 = " + calc.sum(10,5));
        System.out.println("The substraction of 10 - 5 = " + calc.substraction(10,5));
        System.out.println("The multiply of 10 * 5 = " + calc.multiply(10,5));
        System.out.println("The divide of 10 / 5 = " + calc.divide(10,5));
    }
}