package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Atendimento;

public class AtendimentoFormController implements Initializable {

	private Atendimento entity;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private TextField txtServiço;
	
	@FXML
	private TextField txtData;
	
	@FXML
	private Label labelErrorNome;
	
	@FXML
	private Label labelErrorServiço;
	
	@FXML
	private Label labelErrorData;
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancelar;
	
	@FXML
	public void onBtSalvarAction() {
		
	}
	
	@FXML
	public void onBtCancelarAction() {
		
	}
	
	public void setAtendimento(Atendimento entity) {
		this.entity=entity;
	}
	
	public void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtNome, 30);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Atendimento nulo");
		}
		
		txtId.setText(String.valueOf(entity.getId()));
		txtNome.setText(entity.getPaciente().getNome());
	}

}
