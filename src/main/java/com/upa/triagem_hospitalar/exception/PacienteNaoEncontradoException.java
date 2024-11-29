package com.upa.triagem_hospitalar.exception;

public class PacienteNaoEncontradoException extends RuntimeException {

private static final long serialVersionUID = 1L;
	
	public PacienteNaoEncontradoException (String msg) {
		super(msg);
	}
}
