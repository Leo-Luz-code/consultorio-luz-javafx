package model.services;

import java.util.List;

import model.data.IServiço;
import model.data.impl.ServiçoArquivo;
import model.entities.Serviço;

public class ServiçoService {
	
	private IServiço service = new ServiçoArquivo();

	public List<Serviço> findAll() {
		return service.getAllServiços();
	}
	
	public void remove(Serviço serviço) {
		service.deleteServiço(serviço);
	}
	
	public void saveOrUpdate(Serviço serviço) {
		if(serviço.getId() == null) {
			serviço.setId(findAll().size());
			service.createServiço(serviço);
		} else {
			service.updateServiço(serviço);
		}
	}
	
}
