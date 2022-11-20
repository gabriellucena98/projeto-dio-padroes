package com.dio.project.padroes.java.service;

import com.dio.project.padroes.java.model.Cliente;

public interface ClienteService {

    Iterable<Cliente> buscarTodos();

    Cliente buscarPorId(Long id);

    void inserir(Cliente cliente);

    void atualizar(Long id, Cliente cliente);

    void deletar(Long id);
}

