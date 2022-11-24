package model.entities;
import java.io.Serializable;

public class Paciente implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long cpf;
	private String nome;
	private Long telefone;
	private String data;
	
	public Paciente(String nome) {
		this.nome=nome;
	}

	public Paciente(Long cpf, String nome, Long telefone) {
		this.cpf = cpf;
		this.nome = nome;
		this.telefone = telefone;
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}