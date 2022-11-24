package model.data.impl;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.List;

import model.data.IServiço;
import model.entities.Serviço;

public class ServiçoArquivo implements IServiço {
	String arquivo="serviços.ser";
	
	@Override
	public List<Serviço> getAllServiços() {
		 ArrayList <Serviço> serviços = new ArrayList<>();
	        FileInputStream fluxo;
	        ObjectInputStream lerObj = null;
	        try {
	            fluxo = new FileInputStream(arquivo);
	            while (fluxo.available()>0){
	                lerObj = new ObjectInputStream (fluxo);
	                Serviço s = (Serviço) lerObj.readObject();
	                serviços.add(s);
	            }
	            lerObj.close();
	       }catch (EOFException e){
	            System.out.println("Erro de fim de arquivo");
	       }
	        catch (FileNotFoundException e){
	            System.out.println ("Erro ao listar os serviços");
	        }
	        catch (IOException | ClassNotFoundException ex){
	            System.out.println("Erro ao listar os serviços");
	        }
	        return serviços;
	}

	@Override
	public void createServiço(Serviço serviço) { 
		FileOutputStream fluxo;
        try {
        	ArrayList <Serviço> serviços;
            boolean achou=false;
            serviços = (ArrayList<Serviço>) getAllServiços();
             for (int i=0; i<serviços.size(); i++){
                 if (serviço.getPreco()== serviços.get(i).getPreco()){
                     achou=true;
                     break;
                 }
             }
            if (achou){
                throw new IOException("Serviço já está cadastrado");
            }
        	
            fluxo = new FileOutputStream(arquivo, true);
            ObjectOutputStream gravarObj = new ObjectOutputStream(fluxo);
            gravarObj.writeObject(serviço);
            gravarObj.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
	}

	@Override
	public Serviço readServiço(String nome) {
		ArrayList <Serviço> serviços = (ArrayList<Serviço>) getAllServiços();
	       Serviço s = null;
	         for (int i=0; i<serviços.size(); i++){
	             if (nome == serviços.get(i).getNome()){
	                 s=serviços.get(i);
	                 break;
	             }
	         }
	         return s;
	}

	@Override
	public void updateServiço(Serviço serviço) {
		ArrayList <Serviço> serviços;
        boolean achou=false;
        serviços = (ArrayList<Serviço>) getAllServiços();
         for (int i=0; i<serviços.size(); i++){
             if (serviço.getNome()== serviços.get(i).getNome()){
                 serviços.remove(i);
                 serviços.add(serviço);
                 achou=true;
                 break;
             }
         }
        if (achou){
            FileOutputStream fluxo;
            try {
                fluxo = new FileOutputStream(arquivo);
                ObjectOutputStream gravarObj = new ObjectOutputStream(fluxo);
                for (int i=0; i<serviços.size(); i++){
                    gravarObj.writeObject(serviços.get(i));
                }
                gravarObj.close();
            }
            catch (FileNotFoundException e){
                System.out.println("Erro na atualização do serviço");
            }
            catch (IOException ex){
                System.out.println ("Erro na atualização do serviço");
            }
        }
	}

	@Override
	public void deleteServiço(Serviço serviço) {
		ArrayList <Serviço> serviços;
        boolean achou=false;
        serviços = (ArrayList<Serviço>) getAllServiços();
        for (int i=0; i<serviços.size(); i++){
            if (serviço.getNome()== serviços.get(i).getNome()){
                serviços.remove(i);
                achou=true;
                break;
            }
        }
       if (achou){
           FileOutputStream fluxo;
           try {
               fluxo = new FileOutputStream(arquivo);
               ObjectOutputStream gravarObj = new ObjectOutputStream(fluxo);
               for (int i=0; i<serviços.size(); i++){
                   gravarObj.writeObject(serviços.get(i));
               }
               gravarObj.close();
           }
           catch (FileNotFoundException e){
               System.out.println("Erro ao remover o serviço");
           }
           catch (IOException ex){
               System.out.println ("Erro ao remover o serviço");
           }
       }
	}

	@Override
	public boolean checaServiço(Serviço serviço) {
		for (int i = 0; i < getAllServiços().size(); i++) {
			if (getAllServiços().get(i).getNome() == serviço.getNome()) {
				return true;
			}
		}
		return false;
	}

}
