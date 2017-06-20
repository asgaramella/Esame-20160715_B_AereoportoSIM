package it.polito.tdp.flight;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.flight.model.AirportAndNum;
import it.polito.tdp.flight.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FlightController {

	private Model model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField txtDistanzaInput;

	@FXML
	private TextField txtPasseggeriInput;

	@FXML
	private TextArea txtResult;

	@FXML
	void doCreaGrafo(ActionEvent event) {
		String ds=this.txtDistanzaInput.getText();
		if(ds.equals(" ")){
			txtResult.appendText("ERRORE: Inserire distanza\n");
			return;
		}
		int disMin;
		try{
			disMin=Integer.parseInt(ds);
		}catch(NumberFormatException e ){
			txtResult.appendText("ERRORE: Inserire un numero !");
			return;
		}
		boolean connesso=model.creaGrafo(disMin);
		if(connesso==true)
			txtResult.appendText("Il grafo creato è connesso\n");
		else
			txtResult.appendText("Il grafo creato non è connesso\n");
		
		txtResult.appendText(model.getVicinissimo(3484).toString()+"\n");
	}
	

	

	@FXML
	void doSimula(ActionEvent event) {
		String pass=this.txtPasseggeriInput.getText();
		if(pass.equals(" ")){
			txtResult.appendText("Inserire un valore!");
			return;
		}
		int N;
		try{
			N=Integer.parseInt(pass);
		}catch(NumberFormatException e){
			txtResult.appendText("Inserire un numero!\n");
			return;
		}
		int tot=0;
		for(AirportAndNum an: model.doSimula(N)){
			txtResult.appendText(an.getAirport().toString()+" "+an.getPass()+"\n");
			tot+=an.getPass();
		}
		txtResult.appendText("tot pass "+tot);
		
		
	}

	@FXML
	void initialize() {
		assert txtDistanzaInput != null : "fx:id=\"txtDistanzaInput\" was not injected: check your FXML file 'Untitled'.";
		assert txtPasseggeriInput != null : "fx:id=\"txtPasseggeriInput\" was not injected: check your FXML file 'Untitled'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Untitled'.";

	}

	public void setModel(Model model) {
		this.model = model;
	}
}
