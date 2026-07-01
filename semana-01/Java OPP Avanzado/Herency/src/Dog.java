public class Dog extends Animal{

    /* Var */
    public String size;


    public Dog(String name, int numPaws, String race) {
        super(name, numPaws);
        setRace(race);
    }

    /* Setter */
    public void setRace(String race) { this.size = race; }

    /* Getter */
    public String getRace() { return size; }

    /* Methods */
    @Override
    public void makeSound() {
        System.out.println("Guau!");
    }
}
