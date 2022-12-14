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
import model.entities.Atendimento;
import model.entities.Paciente;
import model.entities.Serviço;
import model.services.AtendimentoService;
import model.services.PacienteService;
import model.services.ServiçoService;

public class ListaAtendimentoController implements Initializable, DataChangeListener {

	private AtendimentoService service;

	@FXML
	private TableView<Atendimento> tableViewAtendimento;

	@FXML
	private TableColumn<Atendimento, Integer> tableColumnId;

	@FXML
	private TableColumn<Atendimento, Double> tableColumnValorCobrado;

	@FXML
	private TableColumn<Atendimento, Atendimento> tableColumnREMOVE;

	@FXML
	private TableColumn<Atendimento, Paciente> tableColumnPaciente;

	@FXML
	private TableColumn<Atendimento, Serviço> tableColumnServiço;

	@FXML
	private TableColumn<Atendimento, Date> tableColumnData;

	@FXML
	private TableColumn<Atendimento, Atendimento> tableColumnEDIT;

	@FXML
	private Button btNovo;

	private ObservableList<Atendimento> obsList;

	@FXML
	public void onBtNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Atendimento obj = new Atendimento(null, null, 0.0, new Serviço("", null), new Date());
		createDialogForm(obj, "/gui/AtendimentoForm.fxml", parentStage);

	}

	public void setAtendimentoService(AtendimentoService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnValorCobrado.setCellValueFactory(new PropertyValueFactory<>("valorCobrado"));
		tableColumnPaciente.setCellValueFactory(new PropertyValueFactory<>("paciente"));
		tableColumnServiço.setCellValueFactory(new PropertyValueFactory<>("serviço"));
		tableColumnData.setCellValueFactory(new PropertyValueFactory<>("dataAtendimento"));
		Utils.formatTableColumnDate(tableColumnData, "dd/MM/yyyy");
		Utils.formatTableColumnDouble(tableColumnValorCobrado, 2);
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewAtendimento.prefHeightProperty().bind(stage.heightProperty());
		initEditButtons();
		initRemoveButtons();
	}

	public void updateTableView() {
		List<Atendimento> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewAtendimento.setItems(obsList);
	}

	private void createDialogForm(Atendimento obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			AtendimentoFormController controller = loader.getController();
			controller.setAtendimento(obj);
			controller.setServices(new AtendimentoService(), new ServiçoService(), new PacienteService());
			controller.loadAssociatedObjectsPaciente();
			controller.loadAssociatedObjects();
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Digite os dados do atendimento");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Erro carregando página", e.getMessage(), AlertType.ERROR);
			e.printStackTrace();
		}
	}

	@Override
	public void onDataChange() {
		updateTableView();
	}

	protected void removeEntity(Atendimento obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Tem certeza que quer fazer isso?");

		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Serviço de atendimento nulo");
			}
			service.remove(obj);
			updateTableView();
		}
	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Atendimento, Atendimento>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Atendimento obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/AtendimentoForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Atendimento, Atendimento>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Atendimento obj, boolean empty) {
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
