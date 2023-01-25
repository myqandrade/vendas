package com.github.myqandrade.sales.controller;

import com.github.myqandrade.sales.entity.Produto;
import com.github.myqandrade.sales.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    @ResponseStatus(OK)
    public List<Produto> findAll(){
        return produtoRepository.findAll();
    }

    @GetMapping("/find")
    @ResponseStatus(OK)
    public List<Produto> find(Produto filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return produtoRepository.findAll(example);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public Produto getProdutoById(@PathVariable Integer id){
        return produtoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException
                                (NOT_FOUND, "Produto não encontrado"));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Produto saveProduto(@RequestBody Produto produto){
        return produtoRepository.save(produto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateProduto(@RequestBody Produto produtoAtualizado,@PathVariable Integer id){
        produtoRepository.findById(id)
                .map(produto -> {
                    produtoAtualizado.setId(produto.getId());
                    produtoRepository.save(produtoAtualizado);
                    return produtoAtualizado;
                }).orElseThrow(() ->
                        new ResponseStatusException
                                (NOT_FOUND, "Produto não encontrado"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id){
        produtoRepository.findById(id)
                .map(produto -> {
                    produtoRepository.delete(produto);
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado"));
    }
}
