
package it.polito.tdp.borders;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML
	void doCalcolaConfini(ActionEvent event) {
		txtResult.clear();
		int anno = Integer.parseInt(txtAnno.getText());

		if(anno < 1816 || anno > 2016) {
			txtResult.setText("Inserire un anno compreso tra 1816 e 2016");
			return;
		}

		try {
			this.model.creaGrafo(anno);	
			List<Country> countries = model.getCountries();

			//*************Numero di componenti connesse al grafo*************
			txtResult.appendText(String.format("Numero componenti connesse %d\n", this.model.getNumbersOfConnectedComponents()));

			//*************Elenco degli stati con numero di stati confinanti*************
			Map<Country, Integer> stats = model.getCountryCounts();
			for(Country c : stats.keySet()) {
				txtResult.appendText(String.format("%s con %d stati confinanti\n", c, stats.get(c)));
			}
		}  catch (RuntimeException e) {
			txtResult.setText("Errore: " + e.getMessage() + "\n");
			return;
		}

	}


	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
	}
}
