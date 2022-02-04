package com.logistica.Logistica.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.logistica.Logistica.ConverterDto.ClienteConverterDto;
import com.logistica.Logistica.Dto.ClienteDto;
import com.logistica.Logistica.Service.ClinteServiceImplematacao;
import com.logistica.Logistica.domain.model.Cliente;
import com.logistica.Logistica.repository.ClienteRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class Clientecontroller {
	
	private ClienteRepository clienteRepository;
	private ClienteConverterDto clienteConverterDto;
	private ClinteServiceImplematacao cadastroClienteService;
	
	@GetMapping
	public List<ClienteDto> listar(){
		return clienteConverterDto.toListClienteDto(clienteRepository.findAll());
	}
	
	//@GetMapping("/{nome}")
	//public List<Cliente> listarPorNome(@PathVariable String nome){ 
	//	return clienteRepository.findByNomeContaining(nome); 
		  
	 // }
	 
	
	@GetMapping("/{id}")
	public ResponseEntity<ClienteDto> buscarPorId(@PathVariable Long id) {
		return  clienteRepository.findById(id).map(cliente -> ResponseEntity.ok(clienteConverterDto.toDto(cliente))).
											//.map(ResponseEntity::ok) Poderia usar Metod Reference
				orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		return cadastroClienteService.salvar(cliente);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> atualizar ( @Valid @PathVariable Long id,@RequestBody Cliente cliente) {
		if(!clienteRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		cliente.setId(id);
		cliente = cadastroClienteService.salvar(cliente);
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover (@PathVariable Long id){
		if(!clienteRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		cadastroClienteService.excluir(id);
		return ResponseEntity.noContent().build();
		
	}
	
}
