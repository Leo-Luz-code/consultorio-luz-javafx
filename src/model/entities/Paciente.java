package model.entities;
import java.io.Serializable;
import java.util.ArrayList;

public class Paciente implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long cpf;
	private String nome;
	private Long telefone;
	private ArrayList<Serviço> serviços;
	
	public Paciente(String nome) {
		this.nome=nome;
	}

	public Paciente(Long cpf, String nome) {
		serviços = new ArrayList<>();
		this.cpf = cpf;
		this.nome = nome;
	}

	public Paciente() {
		// TODO Auto-generated constructor stub
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getTelefone() {
		return telefone;
	}

	public void setTelefone(Long telefone) {
		this.telefone = telefone;
	}

	public ArrayList<Serviço> getServiços() {
		return serviços;
	}

	public void addServiço(Serviço serviço) {
		serviços.add(serviço);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paciente other = (Paciente) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome;
	}
}