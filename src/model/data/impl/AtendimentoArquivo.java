package model.data.impl;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import gui.util.Alerts;
import javafx.scene.control.Alert.AlertType;
import model.data.IAtendimento;
import model.entities.Atendimento;

public class AtendimentoArquivo implements IAtendimento {

	String arquivo = "atendimentos.ser";
	
	@Override
	public List<Atendimento> getAllAtendimentos() {
		ArrayList<Atendimento> Atendimentos = new ArrayList<>();
		FileInputStream fluxo;
		ObjectInputStream lerObj = null;
		try {
			fluxo = new FileInputStream(arquivo);
			while (fluxo.available() > 0) {
				lerObj = new ObjectInputStream(fluxo);
				Atendimento p = (Atendimento) lerObj.readObject();
				Atendimentos.add(p);
			}
			lerObj.close();
		} catch (EOFException e) {
			Alerts.showAlert("Erro de fim de arquivo", null, e.getMessage(), AlertType.ERROR);
		} catch (FileNotFoundException e) {
			Alerts.showAlert("Erro ao listar os atendimentos", null, e.getMessage(), AlertType.ERROR);
		} catch (IOException | ClassNotFoundException e) {
			Alerts.showAlert("Erro ao listar os atendimentos", null, e.getMessage(), AlertType.ERROR);
		}
		return Atendimentos;
	}

	@Override
	public void createAtendimento(Atendimento Atendimento) {
		FileOutputStream fluxo;
		try {
			fluxo = new FileOutputStream(arquivo, true);
			ObjectOutputStream gravarObj = new ObjectOutputStream(fluxo);
			gravarObj.writeObject(Atendimento);
			gravarObj.close();
		} catch (FileNotFoundException e) {
			Alerts.showAlert("Erro no cadastro do atendimento", null, e.getMessage(), AlertType.ERROR);
		} catch (IOException e) {
			Alerts.showAlert("Erro no cadastro do atendimento", null, e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public Atendimento readAtendimento(Integer Id) {
		ArrayList<Atendimento> Atendimentos = (ArrayList<Atendimento>) getAllAtendimentos();
		Atendimento p = null;
		for (int i = 0; i < Atendimentos.size(); i++) {
			if (Id == Atendimentos.get(i).getId()) {
				p = Atendimentos.get(i);
				break;
			}
		}
		return p;
	}

	@Override
	public void updateAtendimento(Atendimento atendimento) {
		ArrayList<Atendimento> atendimentos;
		boolean achou = false;
		atendimentos = (ArrayList<Atendimento>) getAllAtendimentos();
		for (int i = 0; i < atendimentos.size(); i++) {
			if (atendimento.getId() == atendimentos.get(i).getId()) {
				atendimentos.remove(i);
				atendimentos.add(atendimento);
				achou = true;
				break;
			}
		}
		if (achou) {
			FileOutputStream fluxo;
			try {
				fluxo = new FileOutputStream(arquivo);
				ObjectOutputStream gravarObj = new ObjectOutputStream(fluxo);
				for (int i = 0; i < atendimentos.size(); i++) {
					gravarObj.writeObject(atendimentos.get(i));
				}
				gravarObj.close();
			} catch (FileNotFoundException e) {
				Alerts.showAlert("Erro na atualização do Atendimento", null, e.getMessage(), AlertType.ERROR);
			} catch (IOException e) {
				Alerts.showAlert("Erro na atualização do Atendimento", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}

	@Override
	public void deleteAtendimento(Atendimento Atendimento) {
		ArrayList<Atendimento> Atendimentos;
		boolean achou = false;
		Atendimentos = (ArrayList<Atendimento>) getAllAtendimentos();
		for (int i = 0; i < Atendimentos.size(); i++) {
			if (Atendimento.getId() == Atendimentos.get(i).getId()) {
				Atendimentos.remove(i);
				achou = true;
				break;
			}
		}
		if (achou) {
			FileOutputStream fluxo;
			try {
				fluxo = new FileOutputStream(arquivo);
				ObjectOutputStream gravarObj = new ObjectOutputStream(fluxo);
				for (int i = 0; i < Atendimentos.size(); i++) {
					gravarObj.writeObject(Atendimentos.get(i));
				}
				gravarObj.close();
			} catch (FileNotFoundException e) {
				Alerts.showAlert("Erro ao remover o Atendimento", null, e.getMessage(), AlertType.ERROR);
			} catch (IOException e) {
				Alerts.showAlert("Erro ao remover o Atendimento", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}

	@Override
	public boolean checkAtendimento(Atendimento atendimento) {
		for (int i = 0; i < getAllAtendimentos().size(); i++) {
			if (getAllAtendimentos().get(i).getPaciente().getNome()== atendimento.getPaciente().getNome()) {
				return true;
			}
		}
		return false;
	}

}
