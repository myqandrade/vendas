package com.github.myqandrade.sales.service.impl;

import com.github.myqandrade.sales.entity.Cliente;
import com.github.myqandrade.sales.entity.ItemPedido;
import com.github.myqandrade.sales.entity.Pedido;
import com.github.myqandrade.sales.entity.Produto;
import com.github.myqandrade.sales.entity.dto.ItemPedidoDTO;
import com.github.myqandrade.sales.entity.dto.PedidoDTO;
import com.github.myqandrade.sales.exception.RegraNegocioException;
import com.github.myqandrade.sales.repository.ClienteRepository;
import com.github.myqandrade.sales.repository.ItemPedidoRepository;
import com.github.myqandrade.sales.repository.PedidoRepository;
import com.github.myqandrade.sales.repository.ProdutoRepository;
import com.github.myqandrade.sales.service.PedidoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer clienteId = dto.getCliente();

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);

        List<ItemPedido> itemPedidos = converterItens(pedido, dto.getItens());
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itemPedidos);
        pedido.setItens(itemPedidos);

        return pedido;
    }

    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens){
        if(itens.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar pedido sem itens");
        }

        return itens
                .stream()
                .map(dto -> {
                    Integer produtoId = dto.getProduto();

                Produto produto = produtoRepository.findById(produtoId)
                            .orElseThrow(() ->
                                    new RegraNegocioException(
                                            "Código de produto inválido " + produtoId));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());
    }
}
