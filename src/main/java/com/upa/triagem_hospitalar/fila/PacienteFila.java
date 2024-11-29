package com.upa.triagem_hospitalar.fila;

import com.upa.triagem_hospitalar.entity.Paciente;

public class PacienteFila {
	
	private Paciente valor;
	private PacienteFila proximo;
	
	
	public PacienteFila(Paciente valor) {
		this.valor = valor;
		this.proximo = null;
	}


	public Paciente getValor() {
		return valor;
	}


	public void setValor(Paciente valor) {
		this.valor = valor;
	}


	public PacienteFila getProximo() {
		return proximo;
	}


	public void setProximo(PacienteFila proximo) {
		this.proximo = proximo;
	}
	
	
	
}
