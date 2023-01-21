package com.github.myqandrade.sales.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Produto {

    private Integer id;
    private String descricao;
    private BigDecimal preco;
}
