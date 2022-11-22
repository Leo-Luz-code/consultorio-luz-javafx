package model.entities;

import java.util.Date;

public abstract class Serviço {
	
	private String nome;
	private Double preco;
	private Date dataAtendimento;
	
	public Serviço() {
	}

	public abstract Double precoTotal();
	
	public Serviço(String nome, Double preco, Date dataAtendimento) {
		this.nome = nome;
		this.preco = preco;
		this.dataAtendimento = dataAtendimento;
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
	
}
