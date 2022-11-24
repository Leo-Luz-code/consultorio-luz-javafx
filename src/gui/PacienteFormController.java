package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import gui.listener.DataChangeListener;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Paciente;
import model.exceptions.ValidationException;
import model.services.PacienteService;

public class PacienteFormController implements Initializable {

	private PacienteService service;

	private Paciente entity;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtCpf;

	@FXML
	private TextField txtNome;
	
	@FXML
	private TextField txtTelefone;

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
	public void onBtSalvarAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Paciente nulo");
		}
		if (service == null) {
			throw new IllegalStateException("PacienteService nulo");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		} catch(ValidationException e) {
			setErrorMessages(e.getErrors());
		}
	}
	
	private void notifyDataChangeListeners() {
		for(DataChangeListener listener: dataChangeListeners) {
			listener.onDataChange();
		}
	}

	@FXML
	public void onBtCancelarAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	private Paciente getFormData() {
		Paciente atd = new Paciente();
		
		ValidationException exception = new ValidationException("Erro de validação");
		
		if(txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "O campo não pode ser vazio");
		}
		
		atd.setNome(txtNome.getText());
		
		if(exception.getErrors().size() > 0) {
			throw exception;
		}
		
		atd.setCpf(Long.parseLong(txtCpf.getText()));
		atd.setTelefone(Long.parseLong(txtTelefone.getText()));
		atd.setData(txtData.getText());
		
		return atd;
	}

	public void setPacienteService(PacienteService service) {
		this.service = service;
	}

	public void setPaciente(Paciente entity) {
		this.entity = entity;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	public void initializeNodes() {
		Constraints.setTextFieldMaxLength(txtNome, 30);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub

	}

	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Paciente nulo");
		}
		txtCpf.setText(String.valueOf(entity.getCpf()));
		txtNome.setText(entity.getNome());
		txtData.setText(entity.getData());
		txtTelefone.setText(String.valueOf(entity.getTelefone()));
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if(fields.contains("nome")) {
			labelErrorNome.setText(errors.get("nome"));
		}
		if(fields.contains("nome")) {
			labelErrorServiço.setText(errors.get("nome"));
		}
		if(fields.contains("nome")) {
			labelErrorData.setText(errors.get("nome"));
		}
		
	}

}
