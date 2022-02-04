package com.logistica.Logistica.exeptionhandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.logistica.Logistica.exception.NegocioException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@ControllerAdvice
public class ExeptionHandler extends ResponseEntityExceptionHandler{ 
	
	private MessageSource messageSource;
	
	// Metodo que trata a exception lançada após erro no cadastro.
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<Problem.Campo> campos = new ArrayList<>();
		 
		
		for (ObjectError error : ex.getBindingResult().getAllErrors()){
			String nome = ((FieldError) error).getField();
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			
			
			campos.add(new Problem.Campo(nome, mensagem));
		}

		
		Problem problem = new Problem();
		
		problem.setStatus(status.value());
		problem.setDataHora(LocalDateTime.now());
		problem.setTitulo("Um ou mais campos inválidos. Faça o preenchimento correto e envie novamente.");
		problem.setCampos(campos);
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	// Método que trata a NegocioException lançada ao se ter um email duplicado
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocioException(NegocioException ex, WebRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problem problem = new Problem();
		
		problem.setStatus(status.value());
		problem.setDataHora(LocalDateTime.now());
		problem.setTitulo(ex.getMessage());
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
		
		
		
		
	}
}
