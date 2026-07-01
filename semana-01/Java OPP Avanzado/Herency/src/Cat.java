public class Cat extends Animal {

    /* Var */
    public boolean hasOwner;

    /* Constructor */
    public Cat(String name, int numPaws, boolean hasOwner){
        super(name, numPaws);

    }

    /* Setter */
    public void setHasOwner(boolean hasOwner) { this.hasOwner = hasOwner; }

    /* Getter */
    public boolean isHasOwner() { return hasOwner; }

    /* Methods */
    @Override
    public void makeSound() {
        System.out.println("Miau!");
    }
}
