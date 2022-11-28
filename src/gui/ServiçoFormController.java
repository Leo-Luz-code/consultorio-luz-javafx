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
import model.entities.Serviço;
import model.exceptions.ValidationException;
import model.services.ServiçoService;

public class ServiçoFormController implements Initializable {

	private ServiçoService service;

	private Serviço entity;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtNome;

	@FXML
	private Label labelErrorNome;

	@FXML
	private Label labelErrorId;

	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btDeletar;

	@FXML
	private Button btCancelar;

	@FXML
	public void onBtSalvarAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Serviço nulo");
		}
		if (service == null) {
			throw new IllegalStateException("ServiçoService nulo");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		} catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		}
	}

	@FXML
	public void onBtDeletarAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Serviço nulo");
		}
		if (service == null) {
			throw new IllegalStateException("ServiçoService nulo");
		}
		try {
			entity = getFormData();
			service.remove(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		} catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		}
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChange();
		}
	}

	@FXML
	public void onBtCancelarAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	private Serviço getFormData() {
		Serviço atd = new Serviço();
		ValidationException exception = new ValidationException("Erro de validação");

		
		atd.setId(Utils.tryParseToInt(txtId.getText()));
		if (txtId.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("id", "O campo não pode ser vazio");
		}
		
		atd.setNome(txtNome.getText());
		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "O campo não pode ser vazio");
		}
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return atd;
	}

	public void setServiçoService(ServiçoService service) {
		this.service = service;
	}

	public void setServiço(Serviço entity) {
		this.entity = entity;
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
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
			throw new IllegalStateException("Serviço nulo");
		}
		
		txtId.setText(String.valueOf(entity.getId()));
		txtNome.setText(entity.getNome());
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		if (fields.contains("nome")) {
			labelErrorNome.setText(errors.get("nome"));
		}
		
		if (fields.contains("id")) {
			labelErrorId.setText(errors.get("id"));
		}

	}
}
