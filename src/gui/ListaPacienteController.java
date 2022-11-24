package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.listener.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Paciente;
import model.entities.Paciente;
import model.services.PacienteService;
import model.services.PacienteService;

public class ListaPacienteController implements Initializable, DataChangeListener {

	private PacienteService service = new PacienteService();

	@FXML
	private TableView<Paciente> tableViewPaciente;

	@FXML
	private TableColumn<Paciente, String> tableColumnData;

	@FXML
	private TableColumn<Paciente, String> tableColumnNome;

	@FXML
	private TableColumn<Paciente, Paciente> tableColumnREMOVE;

	@FXML
	private TableColumn<Paciente, Paciente> tableColumnEDIT;

	@FXML
	private TableColumn<Paciente, Long> tableColumnCpf;

	@FXML
	private TableColumn<Paciente, Long> tableColumnTelefone;

	@FXML
	private Button btNovo;

	@FXML
	public void onBtNovoAction() {
		System.out.println("YES!");
	}

	private ObservableList<Paciente> obsList;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnData.setCellValueFactory(new PropertyValueFactory<>("data"));
		tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewPaciente.prefHeightProperty().bind(stage.heightProperty());
		initRemoveButtons();
	}

	public void updateTableView() {
		List<Paciente> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewPaciente.setItems(obsList);
	}

	@Override
	public void onDataChange() {
		updateTableView();
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Paciente, Paciente>() {
			private final Button button = new Button("remover");

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

	protected void removeEntity(Paciente obj) {
		service.remove(obj);
	}

}