package com.github.myqandrade.sales.repository;

import com.github.myqandrade.sales.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
