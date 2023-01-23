package com.github.myqandrade.sales.repository;

import com.github.myqandrade.sales.entity.Cliente;
import com.github.myqandrade.sales.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);
}
