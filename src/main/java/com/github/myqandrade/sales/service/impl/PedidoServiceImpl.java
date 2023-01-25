package com.github.myqandrade.sales.service.impl;

import com.github.myqandrade.sales.repository.PedidoRepository;
import com.github.myqandrade.sales.service.PedidoService;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl implements PedidoService {

    private PedidoRepository pedidoRepository;

    public PedidoServiceImpl(PedidoRepository pedidoRepository){
        this.pedidoRepository = pedidoRepository;
    }
}
