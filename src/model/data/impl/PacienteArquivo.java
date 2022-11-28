package model.data.impl;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import gui.util.Alerts;
import javafx.scene.control.Alert.AlertType;
import model.data.IPaciente;
import model.entities.Paciente;

public class PacienteArquivo implements IPaciente {

	String arquivo = "pacientes.ser";

	@Override
	public ArrayList<Paciente> getAllPacientes() {

		ArrayList<Paciente> Pacientes = new ArrayList<>();

		try {
			FileInputStream fluxo = new FileInputStream(arquivo);
			ObjectInputStream lerObj = null;
			System.out.println(fluxo.toString());

			while (fluxo.available() != 0) {
				lerObj = new ObjectInputStream(fluxo);
				Paciente p = (Paciente) lerObj.readObject();
				Pacientes.add(p);
			}
		} catch (EOFException e) {
			Alerts.showAlert("Erro de fim de arquivo", null, e.getMessage(), AlertType.ERROR);
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			Alerts.showAlert("Não foram listados os pacientes", null, "Obs: cadastre um paciente",
					AlertType.INFORMATION);
			e.printStackTrace();
		} catch (IOException | ClassNotFoundException e) {
			Alerts.showAlert("Erro ao listar os pacientes", null, e.getMessage(), AlertType.ERROR);
			e.printStackTrace();
		}

		return Pacientes;
	}

	@Override
	public void createPaciente(Paciente paciente) {
		FileOutputStream fluxo;
		try {
			ArrayList<Paciente> pacientes;
			boolean achou = false;
			pacientes = getAllPacientes();
			for (int i = 0; i < pacientes.size(); i++) {
				if (paciente.getId().equals(pacientes.get(i).getId())) {
					achou = true;
					break;
				}
			}
			if (achou) {
				throw new IOException("Paciente já está cadastrado");
			}

			fluxo = new FileOutputStream(arquivo, true);
			ObjectOutputStream gravarObj = new ObjectOutputStream(fluxo);
			gravarObj.writeObject(paciente);
			gravarObj.close();

			fluxo.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public Paciente readPaciente(Integer Id) {
		ArrayList<Paciente> Pacientes = getAllPacientes();
		Paciente p = null;
		for (int i = 0; i < Pacientes.size(); i++) {
			if (Id == Pacientes.get(i).getId()) {
				p = Pacientes.get(i);
				break;
			}
		}
		return p;
	}

	@Override
	public void updatePaciente(Paciente paciente) {
		ArrayList<Paciente> pacientes;
		boolean achou = false;
		pacientes = getAllPacientes();
		for (int i = 0; i < pacientes.size(); i++) {
			if (paciente.getId().equals(pacientes.get(i).getId())) {
				pacientes.remove(i);
				pacientes.add(paciente);
				achou = true;
				break;
			}
		}
		if (achou) {
			FileOutputStream fluxo;
			try {
				fluxo = new FileOutputStream(arquivo);
				ObjectOutputStream gravarObj = null;
				for (int i = 0; i < pacientes.size(); i++) {
					gravarObj = new ObjectOutputStream(fluxo);
					gravarObj.writeObject(pacientes.get(i));
				}
			} catch (FileNotFoundException e) {
				Alerts.showAlert("Erro na atualização do Paciente", null, e.getMessage(), AlertType.ERROR);
			} catch (IOException e) {
				Alerts.showAlert("Erro na atualização do Paciente", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}

	@Override
	public void deletePaciente(Paciente paciente) {
		ArrayList<Paciente> pacientes;
		boolean achou = false;
		pacientes = getAllPacientes();
		for (int i = 0; i < pacientes.size(); i++) {
			if (paciente.getId().equals(pacientes.get(i).getId())) {
				pacientes.remove(i);
				achou = true;
				break;
			}
		}
		if (achou) {
			FileOutputStream fluxo;
			try {
				fluxo = new FileOutputStream(arquivo);
				ObjectOutputStream gravarObj = null;
				for (int i = 0; i < pacientes.size(); i++) {
					gravarObj = new ObjectOutputStream(fluxo);
					pacientes.get(i).setId(i);
					gravarObj.writeObject(pacientes.get(i));
				}
			} catch (FileNotFoundException e) {
				Alerts.showAlert("Erro ao remover o Paciente", null, e.getMessage(), AlertType.ERROR);
				e.printStackTrace();
			} catch (IOException e) {
				Alerts.showAlert("Erro ao remover o Paciente", null, e.getMessage(), AlertType.ERROR);
				e.printStackTrace();
			}
		}
	}

}
