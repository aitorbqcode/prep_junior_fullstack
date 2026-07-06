public class Cuadrado implements Forma{

    int lado;

    //Constructor
    public Cuadrado(int lado){
        setLado(lado);
    }

    //Setter
    public void setLado(int lado) {
        this.lado = lado;
    }

    //Getter
    public float getLado() {
        return lado;
    }

    //Method
    @Override
    public double area() {
        return getLado() * getLado();
    }
}
