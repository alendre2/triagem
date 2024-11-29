package com.upa.triagem_hospitalar.controller.handlerException;

import com.upa.triagem_hospitalar.exception.FilaDeAtendimentoVaziaException;
import com.upa.triagem_hospitalar.exception.PacienteNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ManipuladorException {

    @ExceptionHandler(PacienteNaoEncontradoException.class)
    public ResponseEntity<PadraoDeErro> entidadeNaoEncontradoException(PacienteNaoEncontradoException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String erro = "Paciente não encontrado.";
        PadraoDeErro padraoDeErro = new PadraoDeErro(LocalDateTime.now(), status.value(), erro, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(padraoDeErro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<PadraoDeErro> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String erro = "Nome não pode ser null";
        PadraoDeErro padraoDeErro = new PadraoDeErro(LocalDateTime.now(), status.value(), erro, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(padraoDeErro);
    }

    @ExceptionHandler(FilaDeAtendimentoVaziaException.class)
    public ResponseEntity<PadraoDeErro> filadeAtendimentoVaziaException(FilaDeAtendimentoVaziaException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String erro = "Fila de Atendimento vazia.";
        PadraoDeErro padraoDeErro = new PadraoDeErro(LocalDateTime.now(), status.value(), erro, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(padraoDeErro);
    }
}
