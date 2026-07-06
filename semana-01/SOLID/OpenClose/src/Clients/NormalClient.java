public class NormalClient implements DiscountPedido{

    @Override
    public float calculateDiscount(Pedido p) {
        return p.getPrice();
    }
}
