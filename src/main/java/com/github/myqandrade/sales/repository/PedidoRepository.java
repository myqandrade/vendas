package com.github.myqandrade.sales.repository;

import com.github.myqandrade.sales.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
