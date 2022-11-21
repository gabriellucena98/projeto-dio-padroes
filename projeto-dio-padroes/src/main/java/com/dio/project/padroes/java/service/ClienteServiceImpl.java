package com.dio.project.padroes.java.service;

import com.dio.project.padroes.java.error.ClienteAlreadyExistsException;
import com.dio.project.padroes.java.error.NotFoundException;
import com.dio.project.padroes.java.model.Cliente;
import com.dio.project.padroes.java.model.Endereco;
import com.dio.project.padroes.java.repository.ClienteRepository;
import com.dio.project.padroes.java.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;


    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);

        if (cliente.isPresent()) {
            return cliente.get();
        } else {
            throw new NotFoundException("Cliente não encontrado!");
        }
    }

    @Override
    public void inserir(Cliente cliente) {
        salvarClienteComCep(cliente);
    }
    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> clienteBd = clienteRepository.findById(id);
        if (clienteBd.isPresent()) {
            salvarClienteComCep(cliente);
        } else {
            throw new NotFoundException("Cliente não encontrado!");
        }

    }
    @Override
    public void deletar(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if(cliente.isPresent()) {
            clienteRepository.deleteById(id);
        } else {
            throw new NotFoundException("Cliente não encontrado!");
        }

    }
    private void salvarClienteComCep(Cliente cliente) {
        Boolean clienteDb = validarPeloNome(cliente.getNome());
        if(!clienteDb){
            String cep = cliente.getEndereco().getCep();
            Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
                Endereco novoEndereco = viaCepService.consultarCep(cep);
                enderecoRepository.save(novoEndereco);
                return novoEndereco;
            });
            cliente.setEndereco(endereco);
            clienteRepository.save(cliente);
        } else {
            throw new ClienteAlreadyExistsException("O cliente %s já existe no Banco de Dados".formatted(cliente.getNome()));
        }
    }

    private Boolean validarPeloNome(String nome) {
        Boolean validacao = false;
        for(Cliente cliente: clienteRepository.findAll()) {
            if (cliente.getNome().equals(nome)) {
                validacao = true;
            }
        }
        return validacao;
    }

    public Cliente buscarPorNome(String nome) {
        Iterable<Cliente> clientes = clienteRepository.findAll();
        for (Cliente cliente : clientes) {
            if (cliente.getNome().equals(nome)) {
                return cliente;
            }
        } throw new NotFoundException("Cliente não encontrado!");
    }

    public List<Cliente> buscarPorCep(String cep) {
        Iterable<Cliente> clientes = clienteRepository.findAll();
        List<Cliente> clientesFiltradosPorCep = new LinkedList<Cliente>();;
        for (Cliente cliente : clientes) {
            if (cliente.getEndereco().getCep().equals(cep)) {
                clientesFiltradosPorCep.add(cliente);
            }
        }
        if (!clientesFiltradosPorCep.isEmpty())
            return clientesFiltradosPorCep;
        else
            throw new NotFoundException("Cliente não encontrado!");

    }
}
