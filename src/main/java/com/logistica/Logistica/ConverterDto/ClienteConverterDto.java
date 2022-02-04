package com.logistica.Logistica.ConverterDto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.logistica.Logistica.Dto.ClienteDto;
import com.logistica.Logistica.domain.model.Cliente;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ClienteConverterDto {
	
	private ModelMapper modelMapper;
		
	public ClienteDto toDto(Cliente cliente) {
		return modelMapper.map(cliente, ClienteDto.class);
		
	}
	
	public List<ClienteDto> toListClienteDto(List<Cliente> clientes){
		return clientes.stream().map(this::toDto).collect(Collectors.toList());
		
	}

}

