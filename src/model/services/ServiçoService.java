package model.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.entities.Serviço;
import model.entities.ServiçoSessionado;
import model.entities.ServiçoUnico;

public class ServiçoService {
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public List<Serviço> findAll() {
		List<Serviço> list = new ArrayList<>();
		try {
			list.add(new ServiçoSessionado("Ortodontia", 300.0, sdf.parse("26/06/2002"), 5, 100.0));
			list.add(new ServiçoUnico("Clareamento", 600.0, sdf.parse("25/10/2001"), sdf.parse("25/10/2003")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
}
