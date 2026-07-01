public class Shark{

    /* Var */
    private SwimmingBehavior swimmingBehavior = new SwimmingBehavior();

    /* Getter & Setter */
    public SwimmingBehavior getSwimmingBehavior() { return swimmingBehavior; }

    public void setSwimmingBehavior(SwimmingBehavior swimmingBehavior) { this.swimmingBehavior = swimmingBehavior; }

    /* Methods */
    public void swim() {
        swimmingBehavior.swim();
    }
}
