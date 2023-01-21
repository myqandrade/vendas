package com.github.myqandrade.sales.entity;

import lombok.Data;

@Data
public class ItemPedido {

    private Integer id;
    private Pedido pedido;
    private Produto produto;
    private Integer quantidade;
}
