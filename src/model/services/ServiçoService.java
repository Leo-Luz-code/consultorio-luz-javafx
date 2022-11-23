package model.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import model.data.IServiço;
import model.data.impl.ServiçoArquivo;
import model.entities.Serviço;
import model.entities.ServiçoSessionado;

public class ServiçoService {
	
	private IServiço service = new ServiçoArquivo();
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public List<Serviço> findAll() {
		
		try {
			service.createServiço(new ServiçoSessionado("Ortodontia", 300.0, sdf.parse("26/12/2002"), 5, 100.0));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return service.getAllServiços();
	}
}
