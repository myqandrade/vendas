package com.github.myqandrade.sales.controller;

import com.github.myqandrade.sales.entity.Cliente;
import com.github.myqandrade.sales.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;


    @GetMapping
    @ResponseBody
    public ResponseEntity getAllClientes(){
        return ResponseEntity.ok(clienteRepository.findAll());
    }

    @GetMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente> find(Cliente filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return clienteRepository.findAll(example);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Cliente getClienteById(@PathVariable Integer id){
        return clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException
                                (HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente saveCliente(@RequestBody Cliente cliente){
        return clienteRepository.save(cliente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCliente(@PathVariable Integer id){
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if(cliente.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }
        clienteRepository.delete(cliente.get());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCliente(@RequestBody Cliente clienteAtualizado,
                                        @PathVariable Integer id){
        clienteRepository.findById(id)
                .map(cliente -> {
                    clienteAtualizado.setId(cliente.getId());
                    clienteRepository.save(clienteAtualizado);
                    return clienteAtualizado;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }
}
