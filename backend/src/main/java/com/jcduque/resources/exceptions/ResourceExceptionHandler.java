package com.jcduque.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jcduque.services.exceptions.EntidadeNaoEncontrada;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(EntidadeNaoEncontrada.class)
	public ResponseEntity<StandardError> idNotFound(EntidadeNaoEncontrada e, HttpServletRequest request) {
		
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setErros("Recurso não encontrado");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		error.setParaTeste("Somente testando");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
}
