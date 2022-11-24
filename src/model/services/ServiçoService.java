package model.services;

import java.text.SimpleDateFormat;
import java.util.List;

import model.data.IServiço;
import model.data.impl.ServiçoArquivo;
import model.entities.Serviço;
import model.entities.ServiçoUnico;

public class ServiçoService {
	
	private IServiço service = new ServiçoArquivo();
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public List<Serviço> findAll() {
		return service.getAllServiços();
	}
}
