public class Rectangulo implements Forma {

    //Var
    int ancho, alto;

    //Constructor
    public Rectangulo(int ancho, int alto){
        setAlto(alto);
        setAncho(ancho);
    }

    //Setter
    public void setAncho(int a) { ancho = a; }
    public void setAlto(int h) { alto = h; }

    //Getter
    public int getAlto() { return alto; }
    public int getAncho() { return ancho; }

    //Method
    public double area() { return ancho * alto; }
}
