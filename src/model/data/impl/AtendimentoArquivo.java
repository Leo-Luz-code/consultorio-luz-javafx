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
			System.out.println("Erro de fim de arquivo");
		} catch (FileNotFoundException e) {
			System.out.println("Erro ao listar as disciplinas");
		} catch (IOException | ClassNotFoundException ex) {
			System.out.println("Erro ao listar as disciplinas");
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
			System.out.println("Erro no cadastro da disciplina");
		} catch (IOException ex) {
			System.out.println("Erro no cadastro da disciplina");
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
	public void updateAtendimento(Atendimento Atendimento) {
		ArrayList<Atendimento> Atendimentos;
		boolean achou = false;
		Atendimentos = (ArrayList<Atendimento>) getAllAtendimentos();
		for (int i = 0; i < Atendimentos.size(); i++) {
			if (Atendimento.getId() == Atendimentos.get(i).getId()) {
				Atendimentos.remove(i);
				Atendimentos.add(Atendimento);
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
				System.out.println("Erro na atualização do Atendimento");
			} catch (IOException ex) {
				System.out.println("Erro na atualização do Atendimento");
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
				System.out.println("Erro ao remover o Atendimento");
			} catch (IOException ex) {
				System.out.println("Erro ao remover o Atendimento");
			}
		}
	}

}
