package Animals;

import Interfaces.FlyingAnimal;
import Interfaces.SwimmingAnimal;

public class Duck extends Animal implements FlyingAnimal, SwimmingAnimal {
    /* Var */
    public int height;
    public String color;

    public Duck(String name, int numPaws) {
        super(name, numPaws);
    }

    /* Methods */
    @Override
    public void swim() {
        System.out.println("The duck is swimming");
    }

    @Override
    public void fly() {
        System.out.println("The duck is flying");
    }

    @Override
    public void makeSound() {
        System.out.println("Cuac!");
    }
}
