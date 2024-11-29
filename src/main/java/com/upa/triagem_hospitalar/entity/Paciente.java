package com.upa.triagem_hospitalar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Paciente {

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;



		@Column
	    private String nome;
		
		@Column
	    private boolean preferencial;

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public boolean isPreferencial() {
			return preferencial;
		}

		public void setPreferencial(boolean preferencial) {
			this.preferencial = preferencial;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
		
		
		
}
