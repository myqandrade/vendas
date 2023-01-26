package com.github.myqandrade.sales.service;

import com.github.myqandrade.sales.entity.Pedido;
import com.github.myqandrade.sales.entity.dto.PedidoDTO;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);
}
