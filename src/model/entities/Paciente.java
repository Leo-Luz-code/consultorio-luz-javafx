package model.entities;
import java.io.Serializable;
import java.util.Date;

public class Paciente implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String cpf;
	private String nome;
	private Long telefone;
	private Date dataCadastro;
	private Serviço serviço;
	private Double cobrança;
	
	public Paciente(Integer id, String cpf, String nome, Long telefone, Date dataCadastro, Serviço serviço,
			Double cobrança) {
		super();
		this.id = id;
		this.cpf = cpf;
		this.nome = nome;
		this.telefone = telefone;
		this.dataCadastro = dataCadastro;
		this.serviço = serviço;
		this.cobrança = cobrança;
	}

	public Paciente(Integer id, String cpf, String nome, Long telefone, Date dataCadastro) {
		this.id = id;
		this.cpf = cpf;
		this.nome = nome;
		this.telefone = telefone;
		this.dataCadastro = dataCadastro;
	}

	public Paciente(String nome) {
		this.nome=nome;
	}

	public Paciente(String cpf, String nome, Long telefone) {
		this.cpf = cpf;
		this.nome = nome;
		this.telefone = telefone;
	}

	public Paciente() {
		// TODO Auto-generated constructor stub
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Serviço getServiço() {
		return serviço;
	}

	public void setServiço(Serviço serviço) {
		this.serviço = serviço;
	}

	public Double getCobrança() {
		return cobrança;
	}

	public void setCobrança(Double cobrança) {
		this.cobrança = cobrança;
	}
}