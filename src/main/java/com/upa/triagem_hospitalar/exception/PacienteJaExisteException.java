package com.upa.triagem_hospitalar.exception;


import java.io.Serial;

public class PacienteJaExisteException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public PacienteJaExisteException(String msg) {
        super(msg);
    }
}
