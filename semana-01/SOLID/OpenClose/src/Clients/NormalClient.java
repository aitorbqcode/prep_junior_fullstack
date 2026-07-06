package Clients;

import Interfaces.DiscountPedido;
import Class.Pedido;

public class NormalClient implements DiscountPedido {

    @Override
    public double calculateDiscount(Pedido p) {
        return p.getPrice();
    }
}
