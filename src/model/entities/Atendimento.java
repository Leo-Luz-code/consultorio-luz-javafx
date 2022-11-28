package model.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.data.IAtendimento;
import model.data.impl.AtendimentoArquivo;

public class Atendimento implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nomePaciente;
	private Double valorCobrado;
	private String nomeServiço;
	private String dataAtendimento;
	
	public Atendimento(Integer id, String paciente, Double valorCobrado, String serviço, String dataAtendimento) {
		this.id = id;
		this.nomePaciente = paciente;
		this.valorCobrado=valorCobrado;
		this.nomeServiço = serviço;
		this.dataAtendimento = dataAtendimento;
	}

	public Atendimento() {
	}

	public Atendimento(String paciente, String serviçoUnico, String date) {
		this.nomePaciente = paciente;
		this.nomeServiço = serviçoUnico;
		this.dataAtendimento = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPaciente() {
		return nomePaciente;
	}

	public void setPaciente(String paciente) {
		this.nomePaciente = paciente;
	}

	public String getServiço() {
		return nomeServiço;
	}

	public void setServiço(String serviço) {
		this.nomeServiço = serviço;
	}

	public String getDataAtendimento() {
		return dataAtendimento;
	}

	public void setDataAtendimento(String string) {
		this.dataAtendimento = string;
	}

	public Double getValorCobrado() {
		return valorCobrado;
	}

	public void setValorCobrado(Double valorCobrado) {
		this.valorCobrado = valorCobrado;
	}
	
}
