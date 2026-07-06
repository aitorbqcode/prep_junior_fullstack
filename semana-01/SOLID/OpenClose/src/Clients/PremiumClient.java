package Clients;

import Interfaces.DiscountPedido;
import Class.Pedido;

public class PremiumClient implements DiscountPedido {

    @Override
    public double calculateDiscount(Pedido p) {
        return p.getPrice() * 0.9;
    }
}
