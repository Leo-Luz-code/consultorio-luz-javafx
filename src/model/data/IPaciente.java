package model.data;

import java.util.List;

import model.entities.Paciente;

public interface IPaciente {
    public List<Paciente> getAllPacientes();
    public void createPaciente (Paciente paciente);
    public Paciente readPaciente(Long cpf);
    public void updatePaciente(Paciente Paciente);
    public void deletePaciente(Paciente Paciente);
}

