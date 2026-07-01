package Animals;

import Interfaces.SwimmingAnimal;

public class Shark implements SwimmingAnimal {

    /* Var */
    public int weight;
    public float swimVelocity;

    /* Methods */
    @Override
    public void swim() {
        System.out.println("The shark is swimming");
    }
}
