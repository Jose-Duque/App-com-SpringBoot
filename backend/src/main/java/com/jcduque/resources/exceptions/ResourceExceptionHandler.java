package com.jcduque.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jcduque.services.exceptions.DataBaseException;
import com.jcduque.services.exceptions.EntidadeNaoEncontrada;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	HttpStatus statusNF = HttpStatus.NOT_FOUND;
	HttpStatus statusBR = HttpStatus.BAD_REQUEST;

	@ExceptionHandler(EntidadeNaoEncontrada.class)
	public ResponseEntity<StandardError> idNotFound(EntidadeNaoEncontrada e, HttpServletRequest request) {
		
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(statusNF.value());
		error.setErros("Recurso não encontrado");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		error.setParaTeste("Somente testando");
		return ResponseEntity.status(statusNF).body(error);
	}
	
	@ExceptionHandler(DataBaseException.class)
	public ResponseEntity<StandardError> database(DataBaseException e, HttpServletRequest request) {
		
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(statusBR.value());
		error.setErros("Excessão no Banco de Dados");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		error.setParaTeste("Somente testando");
		return ResponseEntity.status(statusBR).body(error);
	}
}
