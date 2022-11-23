package model.services;

import java.util.List;

import model.data.IAtendimento;
import model.data.impl.AtendimentoArquivo;
import model.entities.Atendimento;

public class AtendimentoService {
	
	private IAtendimento service = new AtendimentoArquivo();

	public List<Atendimento> findAll() {
		return service.getAllAtendimentos();
	}
}
