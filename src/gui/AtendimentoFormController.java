package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.listener.DataChangeListener;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Atendimento;
import model.entities.Paciente;
import model.entities.Serviço;
import model.entities.ServiçoUnico;
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
	public void onBtSalvarAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Atendimento nulo");
		}
		if (service == null) {
			throw new IllegalStateException("AtendimentoService nulo");
		}
		entity = getFormData();
		service.saveOrUpdate(entity);
		notifyDataChangeListeners();
		Utils.currentStage(event).close();
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

	private Atendimento getFormData() {
		Atendimento atd = new Atendimento();
		atd.setId(Utils.tryParseToInt(txtId.getText()));
		Paciente pct = new Paciente(txtNome.getText());
		Serviço svc = new ServiçoUnico(txtServiço.getText());
		atd.setPaciente(pct);
		atd.setServiço(svc);
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
