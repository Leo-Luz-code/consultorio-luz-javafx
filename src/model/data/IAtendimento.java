package model.data;

import java.util.List;

import model.entities.Atendimento;

public interface IAtendimento {

	public List<Atendimento> getAllAtendimentos();

	public void createAtendimento(Atendimento atendimento);

	public Atendimento readAtendimento(Integer id);

	public void updateAtendimento(Atendimento atendimento);

	public void deleteAtendimento(Atendimento atendimento);

}
