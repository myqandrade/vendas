package com.github.myqandrade.sales.controller;

import com.github.myqandrade.sales.entity.Produto;
import com.github.myqandrade.sales.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Produto> findAll(){
        return produtoRepository.findAll();
    }

    @GetMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    public List<Produto> find(Produto filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return produtoRepository.findAll(example);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Produto getProdutoById(@PathVariable Integer id){
        return produtoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException
                                (HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto saveProduto(@RequestBody Produto produto){
        return produtoRepository.save(produto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        Optional<Produto> produto = produtoRepository.findById(id);
        if(produto.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
        }
        produtoRepository.delete(produto.get());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduto(@RequestBody Produto produtoAtualizado,@PathVariable Integer id){
        produtoRepository.findById(id)
                .map(produto -> {
                    produtoAtualizado.setId(produto.getId());
                    produtoRepository.save(produtoAtualizado);
                    return produtoAtualizado;
                }).orElseThrow(() ->
                        new ResponseStatusException
                                (HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }
}
