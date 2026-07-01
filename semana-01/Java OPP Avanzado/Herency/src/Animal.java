public abstract class Animal {

    /* Var */
    public String name;
    public int numPaws;

    /* Constructor */
    public Animal(String name, int numPaws){
        setName(name);
        setNumPaws(numPaws);
    }

    /* Setters */
    public void setName(String name) { this.name = name; }

    public void setNumPaws(int numPaws) { this.numPaws = numPaws;}

    /* Getters */
    public String getName() { return name; }

    public int getNumPaws() { return numPaws; }

    /* Methods */
    public abstract void makeSound();
}
