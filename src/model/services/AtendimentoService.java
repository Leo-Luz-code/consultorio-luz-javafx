package model.services;

import java.text.SimpleDateFormat;
import java.util.List;

import model.data.IAtendimento;
import model.data.impl.AtendimentoArquivo;
import model.entities.Atendimento;

public class AtendimentoService {
	
	private IAtendimento service = new AtendimentoArquivo();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public List<Atendimento> findAll() {
		return service.getAllAtendimentos();
	}
	
	public void saveOrUpdate(Atendimento atendimento) {
		if(atendimento.getId() == null) {
			service.createAtendimento(atendimento);
		} else {
			service.updateAtendimento(atendimento);
		}
	}
	
}
