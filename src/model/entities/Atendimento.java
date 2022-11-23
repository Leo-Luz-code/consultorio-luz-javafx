package model.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Atendimento implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Paciente paciente;
	private Serviço serviço;
	private Date dataAtendimento;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public Atendimento(Integer id, Paciente paciente, Serviço serviço, Date dataAtendimento) {
		this.id = id;
		this.paciente = paciente;
		this.serviço = serviço;
		this.dataAtendimento = dataAtendimento;
	}

	public Atendimento() {
	}

	public Atendimento(Paciente paciente, ServiçoUnico serviçoUnico, Date date) {
		this.paciente = paciente;
		this.serviço = serviçoUnico;
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

	public void setDataAtendimento(Date dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
	}
	
}
