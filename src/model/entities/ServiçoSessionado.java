package model.entities;

import java.util.ArrayList;
import java.util.Date;

public class ServiçoSessionado extends Serviço {

	private Integer sessoes;
	private Double precoSessao;
	private ArrayList<Date> dataSessoes;

	public ServiçoSessionado(String nome, Double precoInicial, Date dataAtendimento, Integer sessoes, Double precoSessao) {
		super(nome, precoInicial, dataAtendimento);
		dataSessoes = new ArrayList<>();
		this.sessoes = sessoes;
		this.precoSessao = precoSessao;
	}

	public Integer getSessoes() {
		return sessoes;
	}

	public void addSessao(Date sessao) {
		dataSessoes.add(sessao);
	}

	public Double getPrecoSessao() {
		return precoSessao;
	}

	public void setPrecoSessao(Double precoSessao) {
		this.precoSessao = precoSessao;
	}

	@Override
	public Double precoTotal() {
		return getPreco() + (getSessoes() * getPrecoSessao());
	}
	
}
