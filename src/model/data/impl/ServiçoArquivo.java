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
import model.data.IServiço;
import model.entities.Serviço;

public class ServiçoArquivo implements IServiço {

	String arquivo = "serviços.ser";

	@Override
	public ArrayList<Serviço> getAllServiços() {

		ArrayList<Serviço> Serviços = new ArrayList<>();

		try {
			FileInputStream fluxo = new FileInputStream(arquivo);
			ObjectInputStream lerObj = null;
			System.out.println(fluxo.toString());

			while (fluxo.available() != 0) {
				lerObj = new ObjectInputStream(fluxo);
				Serviço p = (Serviço) lerObj.readObject();
				Serviços.add(p);
			}
		} catch (EOFException e) {
			Alerts.showAlert("Erro de fim de arquivo", null, e.getMessage(), AlertType.ERROR);
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			Alerts.showAlert("Não foram listados os serviços", null, "Obs: cadastre um serviço",
					AlertType.INFORMATION);
			e.printStackTrace();
		} catch (IOException | ClassNotFoundException e) {
			Alerts.showAlert("Erro ao listar os serviços", null, e.getMessage(), AlertType.ERROR);
			e.printStackTrace();
		}

		return Serviços;
	}

	@Override
	public void createServiço(Serviço serviço) {
		FileOutputStream fluxo;
		try {
			ArrayList<Serviço> serviços;
			boolean achou = false;
			serviços = getAllServiços();
			for (int i = 0; i < serviços.size(); i++) {
				if (serviço.getId().equals(serviços.get(i).getId())) {
					achou = true;
					break;
				}
			}
			if (achou) {
				throw new IOException("Serviço já está cadastrado");
			}

			fluxo = new FileOutputStream(arquivo, true);
			ObjectOutputStream gravarObj = new ObjectOutputStream(fluxo);
			gravarObj.writeObject(serviço);
			gravarObj.close();

			fluxo.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public Serviço readServiço(Integer Id) {
		ArrayList<Serviço> Serviços = getAllServiços();
		Serviço p = null;
		for (int i = 0; i < Serviços.size(); i++) {
			if (Id == Serviços.get(i).getId()) {
				p = Serviços.get(i);
				break;
			}
		}
		return p;
	}

	@Override
	public void updateServiço(Serviço serviço) {
		ArrayList<Serviço> serviços;
		boolean achou = false;
		serviços = getAllServiços();
		for (int i = 0; i < serviços.size(); i++) {
			if (serviço.getId().equals(serviços.get(i).getId())) {
				serviços.remove(i);
				serviços.add(serviço);
				achou = true;
				break;
			}
		}
		if (achou) {
			FileOutputStream fluxo;
			try {
				fluxo = new FileOutputStream(arquivo);
				ObjectOutputStream gravarObj = null;
				for (int i = 0; i < serviços.size(); i++) {
					gravarObj = new ObjectOutputStream(fluxo);
					gravarObj.writeObject(serviços.get(i));
				}
			} catch (FileNotFoundException e) {
				Alerts.showAlert("Erro na atualização do Serviço", null, e.getMessage(), AlertType.ERROR);
			} catch (IOException e) {
				Alerts.showAlert("Erro na atualização do Serviço", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}

	@Override
	public void deleteServiço(Serviço serviço) {
		ArrayList<Serviço> serviços;
		boolean achou = false;
		serviços = getAllServiços();
		for (int i = 0; i < serviços.size(); i++) {
			if (serviço.getId().equals(serviços.get(i).getId())) {
				serviços.remove(i);
				achou = true;
				break;
			}
		}
		if (achou) {
			FileOutputStream fluxo;
			try {
				fluxo = new FileOutputStream(arquivo);
				ObjectOutputStream gravarObj = null;
				for (int i = 0; i < serviços.size(); i++) {
					gravarObj = new ObjectOutputStream(fluxo);
					serviços.get(i).setId(i);
					gravarObj.writeObject(serviços.get(i));
				}
			} catch (FileNotFoundException e) {
				Alerts.showAlert("Erro ao remover o Serviço", null, e.getMessage(), AlertType.ERROR);
				e.printStackTrace();
			} catch (IOException e) {
				Alerts.showAlert("Erro ao remover o Serviço", null, e.getMessage(), AlertType.ERROR);
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean checaServiço(Serviço serviço) {
		// TODO Auto-generated method stub
		return false;
	}

}
