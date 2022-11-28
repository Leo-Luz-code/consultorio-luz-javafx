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
	private TextField txtId;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtTelefone;

	@FXML
	private TextField txtCpf;

	@FXML
	private TextField txtDataCadastro;

	@FXML
	private TextField txtServiço;

	@FXML
	private TextField txtCobrança;

	@FXML
	private Label labelErrorNome;

	@FXML
	private Label labelErrorDataCadastro;

	@FXML
	private Label labelErrorId;

	@FXML
	private Label labelErrorCpf;

	@FXML
	private Label labelErrorCobrança;

	@FXML
	private Label labelErrorTelefone;

	@FXML
	private Label labelErrorServiço;

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
			entity = getFormDataCadastro();
			service.saveOrUpdate(entity);
			notifyDataCadastroChangeListeners();
			Utils.currentStage(event).close();
		} catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		}
	}

	@FXML
	public void onBtDeletarAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Paciente nulo");
		}
		if (service == null) {
			throw new IllegalStateException("PacienteService nulo");
		}
		try {
			entity = getFormDataCadastro();
			service.remove(entity);
			notifyDataCadastroChangeListeners();
			Utils.currentStage(event).close();
		} catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		}
	}

	private void notifyDataCadastroChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChange();
		}
	}

	@FXML
	public void onBtCancelarAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	private Paciente getFormDataCadastro() {
		Paciente pct = new Paciente();
		ValidationException exception = new ValidationException("Erro de validação");

		pct.setId(Utils.tryParseToInt(txtId.getText()));
		if (txtId.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("id", "O campo não pode ser vazio");
		}

		pct.setNome(txtNome.getText());
		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "O campo não pode ser vazio");
		}

		pct.setCobrança(Utils.tryParseToDouble(txtCobrança.getText()));
		if (txtId.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("cobrança", "O campo não pode ser vazio");
		}

		pct.setTelefone(Utils.tryParseToLong(txtTelefone.getText()));
		if (txtCpf.getText() == null || txtCpf.getText().trim().equals("")) {
			exception.addError("telefone", "O campo não pode ser vazio");
		}

		pct.setCpf(txtCpf.getText());
		if (txtCpf.getText() == null || txtCpf.getText().trim().equals("")) {
			exception.addError("cpf", "O campo não pode ser vazio");
		}

//		pct.setDataCadastro(txtDataCadastro.getText());
//		if (txtDataCadastro.getText() == null || txtDataCadastro.getText().trim().equals("")) {
//			exception.addError("dataCadastro", "O campo não pode ser vazio");
//		}

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return pct;
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
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtNome, 30);
		Constraints.setTextFieldDouble(txtCpf);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub

	}

	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Paciente nulo");
		}

		txtId.setText(String.valueOf(entity.getId()));
		txtNome.setText(entity.getNome());
		txtTelefone.setText(String.valueOf(entity.getTelefone()));
//		txtDataCadastro.setText(entity.getDataCadastro());
		txtCpf.setText(entity.getCpf());
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		if (fields.contains("nome")) {
			labelErrorNome.setText(errors.get("nome"));
		}

		if (fields.contains("dataCadastro")) {
			labelErrorDataCadastro.setText(errors.get("dataCadastro"));
		}
		if (fields.contains("id")) {
			labelErrorId.setText(errors.get("id"));
		}
		if (fields.contains("cobrança")) {
			labelErrorCobrança.setText(errors.get("cobrança"));
		}
		if (fields.contains("telefone")) {
			labelErrorTelefone.setText(errors.get("telefone"));
		}
		if (fields.contains("cpf")) {
			labelErrorCpf.setText(errors.get("cpf"));
		}
		if (fields.contains("serviço")) {
			labelErrorServiço.setText(errors.get("serviço"));
		}

	}
}
