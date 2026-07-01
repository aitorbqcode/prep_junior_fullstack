package Animals;

public class Dog extends Animal{

    /* Var */
    public String race;


    public Dog(String name, int numPaws, String race) {
        super(name, numPaws);
        setRace(race);
    }

    /* Setter */
    public void setRace(String race) { this.race = race; }

    /* Getter */
    public String getRace() { return race; }

    /* Methods */
    @Override
    public void makeSound() {
        System.out.println("Guau!");
    }
}
