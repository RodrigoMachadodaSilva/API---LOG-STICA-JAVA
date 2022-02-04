package com.logistica.Logistica.ConverterDto;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.logistica.Logistica.FormDto.ClienteFormDto;
import com.logistica.Logistica.domain.model.Cliente;

@Component

public class ClienteConverterFormDto {
	
	private ModelMapper modelMapper;
	
	public Cliente toEntity(ClienteFormDto clienteFormDto) {
		return modelMapper.map(clienteFormDto, Cliente.class);
		
	}

}
