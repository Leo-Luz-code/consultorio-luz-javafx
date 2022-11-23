package model.data;

import java.util.List;

import model.entities.Serviço;

public interface IServiço{
    public List<Serviço> getAllServiços();
    public void createServiço (Serviço serviço);
    public Serviço readServiço(String nome);
    public void updateServiço(Serviço serviço);
    public void deleteServiço(Serviço serviço);
    public boolean checaServiço(Serviço serviço);
}