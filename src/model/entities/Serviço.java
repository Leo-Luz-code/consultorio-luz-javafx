package model.entities;

import java.io.Serializable;
import java.util.Date;

public abstract class Serviço implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private Double preco;
	private Date dataAtendimento;
	
	public Serviço(String nome) {
		this.nome=nome;
	}

	public abstract Double precoTotal();
	
	public Serviço(String nome, Double preco, Date dataAtendimento) {
		this.nome = nome;
		this.preco = preco;
		this.dataAtendimento = dataAtendimento;
	}
	
	public Serviço() {
		// TODO Auto-generated constructor stub
	}

	public Serviço(String nome2, Double preço) {
		this.nome=nome2;
		this.preco = preço;
	}

	public Date getDataAtendimento() {
		return dataAtendimento;
	}

	public void setDataAtendimento(Date dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	@Override
	public String toString() {
		return nome;
	}
	
	
	
}
