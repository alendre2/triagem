package com.upa.triagem_hospitalar.fila;

import java.util.ArrayList;
import java.util.List;

import com.upa.triagem_hospitalar.entity.Paciente;

public class Lista {
  
    private PacienteFila primeiro;

    public Lista() {
        this.primeiro = null;
    }

    public void inserePaciente(Paciente paciente) {

    	if (buscarPaciente(paciente.getNome()) != null) {
            System.out.println("Paciente com o mesmo nome já existe na fila.");
            return;
        }

        PacienteFila novo = new PacienteFila(paciente);

        // Caso a lista esteja vazia ou o novo paciente deva ser o primeiro
        if (primeiro == null || (paciente.isPreferencial() && !primeiro.getValor().isPreferencial())) {
            novo.setProximo(primeiro);
            primeiro = novo;
            return;
        }

        // Percorrer a lista para encontrar a posição correta
        PacienteFila atual = primeiro;

        while (atual.getProximo() != null && 
              (!paciente.isPreferencial() || atual.getProximo().getValor().isPreferencial())) {
            atual = atual.getProximo();
        }

        // Inserir o novo nó na posição encontrada
        novo.setProximo(atual.getProximo());
        atual.setProximo(novo);
    }

    public Paciente buscarPaciente(String nome) {
        PacienteFila atual = primeiro;

        while (atual != null) {
            if (atual.getValor().getNome().equalsIgnoreCase(nome)) {
                return atual.getValor();
            }
            atual = atual.getProximo();
        }

        return null;
    }

    public Paciente removerPrimeiro() {
        if (primeiro == null) {
            return null;
        }

        Paciente removido = primeiro.getValor();
        primeiro = primeiro.getProximo();
        return removido;
    }

    public void removerPaciente(String nome) {
        if (primeiro == null) {
            return;
        }

        if (primeiro.getValor().getNome().equalsIgnoreCase(nome)) {
            primeiro = primeiro.getProximo();
            return;
        }

        PacienteFila atual = primeiro;

        while (atual.getProximo() != null) {
            if (atual.getProximo().getValor().getNome().equalsIgnoreCase(nome)) {
                atual.setProximo(atual.getProximo().getProximo());
                return;
            }
            atual = atual.getProximo();
        }
    }

    public List<Paciente> toList() {
        List<Paciente> lista = new ArrayList<>();
        PacienteFila atual = primeiro;

        while (atual != null) {
            lista.add(atual.getValor());
            atual = atual.getProximo();
        }

        return lista;
    }
}
