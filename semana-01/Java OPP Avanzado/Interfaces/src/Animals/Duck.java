package Animals;

import Interfaces.FlyingAnimal;
import Interfaces.SwimmingAnimal;

public class Duck implements FlyingAnimal, SwimmingAnimal {

    /* Var */
    public int height;
    public String color;

    /* Methods */
    @Override
    public void swim() {
        System.out.println("The duck is swimming");
    }

    @Override
    public void fly() {
        System.out.println("The duck is flying");
    }
}
