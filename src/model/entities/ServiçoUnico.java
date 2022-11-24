package model.entities;

import java.util.Date;

public class ServiçoUnico extends Serviço {
	
	private static final long serialVersionUID = 1L;
	
	private Date dataServiço;
	
	public ServiçoUnico(String nome, Double preco, Date dataAtendimento, Date dataServiço) {
		super(nome, preco);
		this.dataServiço = dataServiço;
	}

	public ServiçoUnico() {
		super();
	}

	public ServiçoUnico(String nome) {
		super(nome);
	}

	public ServiçoUnico(String nome, Double preço) {
		super(nome, preço);
	}

	public Date getDataServiço() {
		return dataServiço;
	}

	public void setDataServiço(Date dataServiço) {
		this.dataServiço = dataServiço;
	}

	@Override
	public Double precoTotal() {
		return getPreco();
	}

}
