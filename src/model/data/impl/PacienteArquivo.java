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

import model.data.IPaciente;
import model.entities.Paciente;

public class PacienteArquivo implements IPaciente {
    String arquivo="pacientes.ser";
    
    @Override
    public List<Paciente> getAllPacientes() {
        ArrayList <Paciente> pacientes = new ArrayList<>();
        FileInputStream fluxo;
        ObjectInputStream lerObj = null;
        try {
            fluxo = new FileInputStream(arquivo);
            while (fluxo.available()>0){
                lerObj = new ObjectInputStream (fluxo);
                Paciente p = (Paciente) lerObj.readObject();
                pacientes.add(p);
            }
            lerObj.close();
       }catch (EOFException e){
            System.out.println("Erro de fim de arquivo");
       }
        catch (FileNotFoundException e){
            System.out.println ("Erro ao listar as disciplinas");
        }
        catch (IOException | ClassNotFoundException ex){
            System.out.println("Erro ao listar as disciplinas");
        }
        return pacientes;
    }

    @Override
    public void createPaciente(Paciente paciente) {
        FileOutputStream fluxo;
        try {
            fluxo = new FileOutputStream(arquivo, true);
            ObjectOutputStream gravarObj = new ObjectOutputStream(fluxo);
            gravarObj.writeObject(paciente);
            gravarObj.close();
        } catch (FileNotFoundException e){
            System.out.println("Erro no cadastro do paciente");
        }
        catch (IOException ex){
            System.out.println ("Erro no cadastro do paciente");
        }
    }

	@Override
	public Paciente readPaciente(Long cpf) {
		ArrayList <Paciente> pacientes = (ArrayList<Paciente>) getAllPacientes();
	       Paciente p = null;
	         for (int i=0; i<pacientes.size(); i++){
	             if (cpf == pacientes.get(i).getCpf()){
	                 p=pacientes.get(i);
	                 break;
	             }
	         }
	         return p;
	}

	@Override
	public void updatePaciente(Paciente paciente) {
		 ArrayList <Paciente> pacientes;
	        boolean achou=false;
	        pacientes = (ArrayList<Paciente>) getAllPacientes();
	         for (int i=0; i<pacientes.size(); i++){
	             if (paciente.getCpf()== pacientes.get(i).getCpf()){
	                 pacientes.remove(i);
	                 pacientes.add(paciente);
	                 achou=true;
	                 break;
	             }
	         }
	        if (achou){
	            FileOutputStream fluxo;
	            try {
	                fluxo = new FileOutputStream(arquivo);
	                ObjectOutputStream gravarObj = new ObjectOutputStream(fluxo);
	                for (int i=0; i<pacientes.size(); i++){
	                    gravarObj.writeObject(pacientes.get(i));
	                }
	                gravarObj.close();
	            }
	            catch (FileNotFoundException e){
	                System.out.println("Erro na atualização do paciente");
	            }
	            catch (IOException ex){
	                System.out.println ("Erro na atualização do paciente");
	            }
	        }
	}

	@Override
	public void deletePaciente(Paciente paciente) {
		 ArrayList <Paciente> pacientes;
         boolean achou=false;
         pacientes = (ArrayList<Paciente>) getAllPacientes();
         for (int i=0; i<pacientes.size(); i++){
             if (paciente.getCpf()== pacientes.get(i).getCpf()){
                 pacientes.remove(i);
                 achou=true;
                 break;
             }
         }
        if (achou){
            FileOutputStream fluxo;
            try {
                fluxo = new FileOutputStream(arquivo);
                ObjectOutputStream gravarObj = new ObjectOutputStream(fluxo);
                for (int i=0; i<pacientes.size(); i++){
                    gravarObj.writeObject(pacientes.get(i));
                }
                gravarObj.close();
            }
            catch (FileNotFoundException e){
                System.out.println("Erro ao remover o paciente");
            }
            catch (IOException ex){
                System.out.println ("Erro ao remover o paciente");
            }
        }
	}
    
}