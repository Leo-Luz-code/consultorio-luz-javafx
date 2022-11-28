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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Atendimento;
import model.entities.Paciente;
import model.entities.Serviço;
import model.exceptions.ValidationException;
import model.services.AtendimentoService;
import model.services.PacienteService;
import model.services.ServiçoService;

public class AtendimentoFormController implements Initializable {

	private AtendimentoService service;

	private ServiçoService serviçoService;
	
	private PacienteService pacienteService;
	
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
	private ComboBox<Serviço> comboBoxServiço;
	
	@FXML
	private ComboBox<Paciente> comboBoxPaciente;
	
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
	
	private ObservableList<Serviço> obsListServiço;
	private ObservableList<Paciente> obsListPaciente;

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
		if (txtId.getText() == null || txtId.getText().trim().equals("")) {
			exception.addError("id", "O campo não pode ser vazio");
		}
		
		atd.setValorCobrado(Utils.tryParseToDouble(txtValorCobrado.getText()));
		if (txtValorCobrado.getText() == null || txtValorCobrado.getText().trim().equals("")) {
			exception.addError("valorCobrado", "O campo não pode ser vazio");
		}
		
		
		if (dpDataAtendimento.getValue() == null) {
			exception.addError("dataCadastro", "O campo não pode ser vazio");
		}
		else {
			Instant instant = Instant.from(dpDataAtendimento.getValue().atStartOfDay(ZoneId.systemDefault()));
			atd.setDataAtendimento((Date.from(instant)));
		}
		
		atd.setServiço(comboBoxServiço.getValue());
		atd.setPaciente(comboBoxPaciente.getValue());
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return atd;
	}

	public void setServices(AtendimentoService service, ServiçoService serviçoService, PacienteService pacienteService) {
		this.service = service;
		this.serviçoService = serviçoService;
		this.pacienteService = pacienteService;
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
		
		initializeComboBoxServiço();
		initializeComboBoxPaciente();
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
		
		if (entity.getPaciente() == null) {
			comboBoxPaciente.getSelectionModel().selectFirst();
		} else {
			comboBoxPaciente.setValue(entity.getPaciente());
		} 
		
		if (entity.getServiço() == null) {
			comboBoxServiço.getSelectionModel().selectFirst();
		} else {
			comboBoxServiço.setValue(entity.getServiço());
		} 
		
		if (entity.getDataAtendimento() != null) {
			dpDataAtendimento.setValue(LocalDate.ofInstant(entity.getDataAtendimento().toInstant(), ZoneId.systemDefault()));
		}
		txtValorCobrado.setText(String.format("%.2f", entity.getValorCobrado()));
	}

	public void loadAssociatedObjects() {
		if (serviçoService == null)
			throw new IllegalStateException("ServiçoService nulo");

		List<Serviço> list = serviçoService.findAll();
		obsListServiço = FXCollections.observableArrayList(list);
		comboBoxServiço.setItems(obsListServiço);
	}
	
	public void loadAssociatedObjectsPaciente() {
		if (serviçoService == null)
			throw new IllegalStateException("PacienteService nulo");

		List<Paciente> list = pacienteService.findAll();
		obsListPaciente = FXCollections.observableArrayList(list);
		comboBoxPaciente.setItems(obsListPaciente);
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
	
	private void initializeComboBoxServiço() {
		Callback<ListView<Serviço>, ListCell<Serviço>> factory = lv -> new ListCell<Serviço>() {
			@Override
			protected void updateItem(Serviço item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNome());
			}
		};
		comboBoxServiço.setCellFactory(factory);
		comboBoxServiço.setButtonCell(factory.call(null));
	}
	
	private void initializeComboBoxPaciente() {
		Callback<ListView<Paciente>, ListCell<Paciente>> factory = lv -> new ListCell<Paciente>() {
			@Override
			protected void updateItem(Paciente item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNome());
			}
		};
		comboBoxPaciente.setCellFactory(factory);
		comboBoxPaciente.setButtonCell(factory.call(null));
	}
	
}
