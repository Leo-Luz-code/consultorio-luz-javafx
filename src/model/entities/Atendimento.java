package model.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.data.IAtendimento;
import model.data.impl.AtendimentoArquivo;

public class Atendimento implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Paciente paciente;
	private Double valorCobrado;
	private Serviço serviço;
	private Date dataAtendimento;
	
	public Atendimento(Integer id, Paciente paciente, Double valorCobrado, Serviço serviço, Date dataAtendimento) {
		this.id = id;
		this.paciente = paciente;
		this.valorCobrado=valorCobrado;
		this.serviço = serviço;
		this.dataAtendimento = dataAtendimento;
	}

	public Atendimento() {
	}

	public Atendimento(Paciente paciente, Serviço serviço, Date date) {
		this.paciente = paciente;
		this.serviço = serviço;
		this.dataAtendimento = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
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
