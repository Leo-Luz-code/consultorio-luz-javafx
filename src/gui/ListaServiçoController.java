package gui;

import java.io.IOException;
import java.net.URL;
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
import model.entities.Serviço;
import model.services.ServiçoService;

public class ListaServiçoController implements Initializable, DataChangeListener {

	private ServiçoService service;

	@FXML
	private TableView<Serviço> tableViewServiço;

	@FXML
	private TableColumn<Serviço, Integer> tableColumnId;

	@FXML
	private TableColumn<Serviço, Serviço> tableColumnREMOVE;

	@FXML
	private TableColumn<Serviço, String> tableColumnNome;

	@FXML
	private Button btNovo;

	private ObservableList<Serviço> obsList;

	@FXML
	public void onBtNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Serviço obj = new Serviço("", null);
		createDialogForm(obj, "/gui/ServiçoForm.fxml", parentStage);

	}

	public void setServiçoService(ServiçoService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewServiço.prefHeightProperty().bind(stage.heightProperty());
		initRemoveButtons();
	}

	public void updateTableView() {
		List<Serviço> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewServiço.setItems(obsList);
	}

	private void createDialogForm(Serviço obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			ServiçoFormController controller = loader.getController();
			controller.setServiço(obj);
			controller.setServiçoService(new ServiçoService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Digite os dados do serviço");
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

	protected void removeEntity(Serviço obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Tem certeza que quer fazer isso?");

		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Serviço de atendimento nulo");
			}
			service.remove(obj);
			updateTableView();
		}
	}


	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Serviço, Serviço>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Serviço obj, boolean empty) {
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
