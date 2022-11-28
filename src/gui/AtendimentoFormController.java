package gui;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Atendimento;
import model.exceptions.ValidationException;
import model.services.AtendimentoService;

public class AtendimentoFormController implements Initializable {

	private AtendimentoService service;

	private Atendimento entity;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtValorCobrado;

	@FXML
	private TextField txtServiço;

	@FXML
	private DatePicker dpDataAtendimento;

	@FXML
	private Label labelErrorNome;

	@FXML
	private Label labelErrorServiço;

	@FXML
	private Label labelErrorData;

	@FXML
	private Label labelErrorId;

	@FXML
	private Label labelErrorValorCobrado;

	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancelar;

	@FXML
	public void onBtSalvarAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Atendimento nulo");
		}
		if (service == null) {
			throw new IllegalStateException("AtendimentoService nulo");
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
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChange();
		}
	}

	@FXML
	public void onBtCancelarAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	private Atendimento getFormData() {
		Atendimento atd = new Atendimento();
		ValidationException exception = new ValidationException("Erro de validação");

		
		atd.setId(Utils.tryParseToInt(txtId.getText()));
		if (txtId.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("id", "O campo não pode ser vazio");
		}
		
		atd.setPaciente(txtNome.getText());
		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "O campo não pode ser vazio");
		}
		
		atd.setValorCobrado(Utils.tryParseToDouble(txtValorCobrado.getText()));
		if (txtValorCobrado.getText() == null || txtValorCobrado.getText().trim().equals("")) {
			exception.addError("valorCobrado", "O campo não pode ser vazio");
		}
		
//		atd.getServiço().setNome((txtServiço.getText()));;
//		if (txtServiço.getText() == null || txtServiço.getText().trim().equals("")) {
//			exception.addError("serviço", "O campo não pode ser vazio");
//		}
		
		if (dpDataAtendimento.getValue() == null) {
			exception.addError("dataCadastro", "O campo não pode ser vazio");
		}
		else {
			Instant instant = Instant.from(dpDataAtendimento.getValue().atStartOfDay(ZoneId.systemDefault()));
			atd.setDataAtendimento((Date.from(instant)));
		}
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return atd;
	}

	public void setAtendimentoService(AtendimentoService service) {
		this.service = service;
	}

	public void setAtendimento(Atendimento entity) {
		this.entity = entity;
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	public void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtNome, 30);
		Constraints.setTextFieldDouble(txtValorCobrado);
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
		txtNome.setText(entity.getPaciente());
//		txtServiço.setText(entity.getServiço().getNome());
		if (entity.getDataAtendimento() != null) {
			dpDataAtendimento.setValue(LocalDate.ofInstant(entity.getDataAtendimento().toInstant(), ZoneId.systemDefault()));
		}
		txtValorCobrado.setText(String.format("%.2f", entity.getValorCobrado()));
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		if (fields.contains("nome")) {
			labelErrorNome.setText(errors.get("nome"));
		}
		if (fields.contains("serviço")) {
			labelErrorServiço.setText(errors.get("serviço"));
		}
		if (fields.contains("dataCadastro")) {
			labelErrorData.setText(errors.get("dataCadastro"));
		}
		if (fields.contains("id")) {
			labelErrorId.setText(errors.get("id"));
		}
		if (fields.contains("valorCobrado")) {
			labelErrorValorCobrado.setText(errors.get("valorCobrado"));

		}
	}
}
