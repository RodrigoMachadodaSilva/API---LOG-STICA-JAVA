package com.logistica.Logistica.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.logistica.Logistica.domain.model.Cliente;
import com.logistica.Logistica.exception.NegocioException;
import com.logistica.Logistica.repository.ClienteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClinteServiceImplematacao  {
	
	private ClienteRepository clienteRepository;
	
	@org.springframework.transaction.annotation.Transactional
	public Cliente salvar(Cliente cliente) {
		boolean emailEmUso = clienteRepository.findByEmail(cliente.getEmail()).
			stream().anyMatch(clienteExistente -> !clienteExistente.equals(cliente));
		
		if(emailEmUso) {
			throw new NegocioException("Já existe um cliente cadastrado com esse e-mail");
		}
		
		return clienteRepository.save(cliente);
		
	}
	
	@org.springframework.transaction.annotation.Transactional
	public void excluir(Long Id) {
		clienteRepository.deleteById(Id);
		
		
	}

	
	
	



}
