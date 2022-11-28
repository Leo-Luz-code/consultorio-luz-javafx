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
	private Serviço serviço;
	private Date dataAtendimento;
	
	public Atendimento(Integer id, String paciente, Double valorCobrado, Serviço serviço, Date dataAtendimento) {
		this.id = id;
		this.nomePaciente = paciente;
		this.valorCobrado=valorCobrado;
		this.serviço = serviço;
		this.dataAtendimento = dataAtendimento;
	}

	public Atendimento() {
	}

	public Atendimento(String paciente, Serviço serviço, Date date) {
		this.nomePaciente = paciente;
		this.serviço = serviço;
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

	public Serviço getServiço() {
		return serviço;
	}

	public void setServiço(Serviço serviço) {
		this.serviço = serviço;
	}

	public Date getDataAtendimento() {
		return dataAtendimento;
	}

	public void setDataAtendimento(Date string) {
		this.dataAtendimento = string;
	}

	public Double getValorCobrado() {
		return valorCobrado;
	}

	public void setValorCobrado(Double valorCobrado) {
		this.valorCobrado = valorCobrado;
	}
	
}
