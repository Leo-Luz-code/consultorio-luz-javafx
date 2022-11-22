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
import model.entities.Serviço;

public class ListaServiçoController implements Initializable{

	@FXML
	private TableView<Serviço> tableViewServiço;
	
	@FXML
	private TableColumn<Serviço, String> tableColumnNome;
	
	@FXML
	private TableColumn<Serviço, Double> tableColumnPreço;
	
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
		tableViewServiço.prefHeightProperty().bind(stage.heightProperty());
	}
}
