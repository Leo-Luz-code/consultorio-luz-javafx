package model.entities;

import java.io.Serializable;
import java.util.Date;

public abstract class Serviço implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
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
