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
	
	public void remove(Atendimento atendimento) {
		service.deleteAtendimento(atendimento);
	}
	
	public void saveOrUpdate(Atendimento atendimento) {
		if(atendimento.getId() == null) {
			atendimento.setId(findAll().size());
			service.createAtendimento(atendimento);
		} else {
			service.updateAtendimento(atendimento);
		}
	}
	
}
