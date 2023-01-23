package com.github.myqandrade.sales.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @GetMapping("/hello/{nome}")
    @ResponseBody
    public String helloClientes(@PathVariable("nome") String nomeCliente){
        return String.format("Hello %s", nomeCliente);
    }
}
