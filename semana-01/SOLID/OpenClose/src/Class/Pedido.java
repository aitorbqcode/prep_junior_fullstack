package Class;

public class Pedido {

    float price;

    public Pedido(int price){
        setPrice(price);
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
