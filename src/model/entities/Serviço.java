package model.entities;

import java.io.Serializable;
import java.util.Date;

public class Serviço implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private Integer id;
	
	public Serviço(String nome, Integer id) {
		this.nome = nome;
		this.id = id;
	}
	
	public Serviço() {
	
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return nome;
	}
	
}
