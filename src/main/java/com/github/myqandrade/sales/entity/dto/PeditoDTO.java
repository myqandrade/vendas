package com.github.myqandrade.sales.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeditoDTO {

    private Integer cliente;
    private BigDecimal total;
    private List<ItemPedidoDTO> itens;
}
