package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import gui.listener.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Paciente;
import model.entities.Serviço;
import model.services.PacienteService;

public class ListaPacienteController implements Initializable, DataChangeListener {

	private PacienteService service;

	@FXML
	private TableView<Paciente> tableViewPaciente;

	@FXML
	private TableColumn<Paciente, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Paciente, Serviço> tableColumnServiço;
	
	@FXML
	private TableColumn<Paciente, Double> tableColumnCobrança;

	@FXML
	private TableColumn<Paciente, String> tableColumnNome;

	@FXML
	private TableColumn<Paciente, String> tableColumnCpf;

	@FXML
	private TableColumn<Paciente, String> tableColumnTelefone;

	@FXML
	private TableColumn<Paciente, Date> tableColumnDataCadastro;

	@FXML
	private TableColumn<Paciente, Paciente> tableColumnEDIT;
	
	@FXML
	private TableColumn<Paciente, Paciente> tableColumnREMOVE;

	@FXML
	private Button btNovo;

	private ObservableList<Paciente> obsList;

	@FXML
	public void onBtNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Paciente obj = new Paciente(null, "", "", "", new Date(), new Serviço("", null), 0.00);
		createDialogForm(obj, "/gui/PacienteForm.fxml", parentStage);
	}

	public void setPacienteService(PacienteService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnCobrança.setCellValueFactory(new PropertyValueFactory<>("cobrança"));
		tableColumnServiço.setCellValueFactory(new PropertyValueFactory<>("serviço"));
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
		tableColumnDataCadastro.setCellValueFactory(new PropertyValueFactory<>("dataCadastro"));
		Utils.formatTableColumnDouble(tableColumnCobrança, 2);
		Utils.formatTableColumnDate(tableColumnDataCadastro, "dd/MM/yyyy");
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewPaciente.prefHeightProperty().bind(stage.heightProperty());
		initEditButtons();
		initRemoveButtons();
	}

	public void updateTableView() {
		List<Paciente> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewPaciente.setItems(obsList);
	}

	private void createDialogForm(Paciente obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			PacienteFormController controller = loader.getController();
			controller.setPaciente(obj);
			controller.setPacienteService(new PacienteService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Digite os dados do paciente");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Erro carregando página", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChange() {
		updateTableView();
	}

	protected void removeEntity(Paciente obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Tem certeza que quer fazer isso?");

		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Serviço de paciente nulo");
			}
			service.remove(obj);
			updateTableView();
		}
	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Paciente, Paciente>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Paciente obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/PacienteForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Paciente, Paciente>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Paciente obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

}
