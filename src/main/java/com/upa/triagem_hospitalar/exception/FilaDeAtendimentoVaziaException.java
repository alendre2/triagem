// Exceção para fila de atendimento vazia
package com.upa.triagem_hospitalar.exception;

public class FilaDeAtendimentoVaziaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FilaDeAtendimentoVaziaException(String msg) {
        super(msg);
    }
}
