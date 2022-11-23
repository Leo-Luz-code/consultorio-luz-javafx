package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Atendimento;
import model.entities.Paciente;
import model.entities.Serviço;
import model.entities.ServiçoUnico;
import model.services.AtendimentoService;

public class ListaAtendimentoController implements Initializable{

	private AtendimentoService service;
	
	@FXML
	private TableView<Atendimento> tableViewAtendimento;
	
	@FXML
	private TableColumn<Atendimento, Integer> tableColumnId;

	@FXML
	private TableColumn<Atendimento, Paciente> tableColumnPaciente;
	
	@FXML
	private TableColumn<Atendimento, Serviço> tableColumnServiço;
	
	@FXML
	private TableColumn<Atendimento, Date> tableColumnData;
	
	@FXML 
	private Button btNovo;
	
	private ObservableList<Atendimento> obsList;
	
	@FXML
	public void onBtNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Atendimento obj = new Atendimento(new Paciente(""), new ServiçoUnico(""), new Date());
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
		tableColumnPaciente.setCellValueFactory(new PropertyValueFactory<>("paciente"));
		tableColumnServiço.setCellValueFactory(new PropertyValueFactory<>("serviço"));
		tableColumnData.setCellValueFactory(new PropertyValueFactory<>("dataAtendimento"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewAtendimento.prefHeightProperty().bind(stage.heightProperty());
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
			controller.setAtendimentoService(new AtendimentoService());
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
		}
	}
	
}
