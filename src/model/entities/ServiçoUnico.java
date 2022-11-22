package model.entities;

import java.util.Date;

public class ServiçoUnico extends Serviço {

	private Date dataServiço;
	
	public ServiçoUnico(String nome, Double preco, Date dataAtendimento, Date dataServiço) {
		super(nome, preco, dataAtendimento);
		this.dataServiço = dataServiço;
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
