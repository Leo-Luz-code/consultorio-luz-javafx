package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Paciente;
import model.entities.Serviço;

public class ListaPacienteController implements Initializable{

	@FXML
	private TableView<Paciente> tableViewPaciente;
	
	@FXML
	private TableColumn<Paciente, String> tableColumnNome;

	@FXML
	private TableColumn<Paciente, Long> tableColumnCPF;
	
	@FXML
	private TableColumn<Paciente, Long> tableColumnTelefone;
	
	@FXML
	private TableColumn<Paciente, Serviço> tableColumnServiços;
	
	@FXML 
	private Button btNovo;
	
	@FXML
	public void onBtNovoAction() {
		System.out.println("YES!");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("Preço"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewPaciente.prefHeightProperty().bind(stage.heightProperty());
	}
}
