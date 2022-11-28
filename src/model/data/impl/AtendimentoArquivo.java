package model.data.impl;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import gui.util.Alerts;
import javafx.scene.control.Alert.AlertType;
import model.data.IAtendimento;
import model.entities.Atendimento;

public class AtendimentoArquivo implements IAtendimento {

	String arquivo = "atendimentos.ser";

	@Override
	public ArrayList<Atendimento> getAllAtendimentos() {

		ArrayList<Atendimento> Atendimentos = new ArrayList<>();

		try {
			FileInputStream fluxo = new FileInputStream(arquivo);
			ObjectInputStream lerObj = null;
			System.out.println(fluxo.toString());

			while (fluxo.available() != 0) {
				lerObj = new ObjectInputStream(fluxo);
				Atendimento p = (Atendimento) lerObj.readObject();
				Atendimentos.add(p);
			}
		} catch (EOFException e) {
			Alerts.showAlert("Erro de fim de arquivo", null, e.getMessage(), AlertType.ERROR);
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			Alerts.showAlert("Não foram listados os atendimentos", null, "Obs: cadastre um atendimento",
					AlertType.INFORMATION);
			e.printStackTrace();
		} catch (IOException | ClassNotFoundException e) {
			Alerts.showAlert("Erro ao listar os atendimentos", null, e.getMessage(), AlertType.ERROR);
			e.printStackTrace();
		}

		return Atendimentos;
	}

	@Override
	public void createAtendimento(Atendimento atendimento) {
		FileOutputStream fluxo;
		try {
			ArrayList<Atendimento> atendimentos;
			boolean achou = false;
			atendimentos = getAllAtendimentos();
			for (int i = 0; i < atendimentos.size(); i++) {
				if (atendimento.getId().equals(atendimentos.get(i).getId())) {
					achou = true;
					break;
				}
			}
			if (achou) {
				throw new IOException("Atendimento já está cadastrado");
			}

			fluxo = new FileOutputStream(arquivo, true);
			ObjectOutputStream gravarObj = new ObjectOutputStream(fluxo);
			gravarObj.writeObject(atendimento);
			gravarObj.close();

			fluxo.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public Atendimento readAtendimento(Integer Id) {
		ArrayList<Atendimento> Atendimentos = getAllAtendimentos();
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
		atendimentos = getAllAtendimentos();
		for (int i = 0; i < atendimentos.size(); i++) {
			if (atendimento.getId().equals(atendimentos.get(i).getId())) {
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
				ObjectOutputStream gravarObj = null;
				for (int i = 0; i < atendimentos.size(); i++) {
					gravarObj = new ObjectOutputStream(fluxo);
					gravarObj.writeObject(atendimentos.get(i));
				}
			} catch (FileNotFoundException e) {
				Alerts.showAlert("Erro na atualização do Atendimento", null, e.getMessage(), AlertType.ERROR);
			} catch (IOException e) {
				Alerts.showAlert("Erro na atualização do Atendimento", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}

	@Override
	public void deleteAtendimento(Atendimento atendimento) {
		ArrayList<Atendimento> atendimentos;
		boolean achou = false;
		atendimentos = getAllAtendimentos();
		for (int i = 0; i < atendimentos.size(); i++) {
			if (atendimento.getId().equals(atendimentos.get(i).getId())) {
				atendimentos.remove(i);
				achou = true;
				break;
			}
		}
		if (achou) {
			FileOutputStream fluxo;
			try {
				fluxo = new FileOutputStream(arquivo);
				ObjectOutputStream gravarObj = null;
				for (int i = 0; i < atendimentos.size(); i++) {
					gravarObj = new ObjectOutputStream(fluxo);
					atendimentos.get(i).setId(i);
					gravarObj.writeObject(atendimentos.get(i));
				}
			} catch (FileNotFoundException e) {
				Alerts.showAlert("Erro ao remover o Atendimento", null, e.getMessage(), AlertType.ERROR);
				e.printStackTrace();
			} catch (IOException e) {
				Alerts.showAlert("Erro ao remover o Atendimento", null, e.getMessage(), AlertType.ERROR);
				e.printStackTrace();
			}
		}
	}

}
