package Animals;

import Interfaces.FlyingAnimal;

public class Eagle implements FlyingAnimal {
    /* Var */
    public int weight;
    public String eagleRace;

    /* Methods */
    @Override
    public void fly() {
        System.out.println("The eagle is flying");
    }
}
