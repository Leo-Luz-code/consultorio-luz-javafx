package model.services;

import java.text.SimpleDateFormat;
import java.util.List;

import model.data.IPaciente;
import model.data.impl.PacienteArquivo;
import model.entities.Paciente;

public class PacienteService {
	
	private IPaciente service = new PacienteArquivo();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public List<Paciente> findAll() {
		return service.getAllPacientes();
	}
	
	public void remove(Paciente Paciente) {
		service.deletePaciente(Paciente);
	}
	
	
	public void saveOrUpdate(Paciente Paciente) {
		if(Paciente.getCpf() == null) {
			service.createPaciente(Paciente);
		} else {
			service.updatePaciente(Paciente);
		}
	}
	
}
