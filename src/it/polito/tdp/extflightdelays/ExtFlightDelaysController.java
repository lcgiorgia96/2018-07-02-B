/**
 * Sample Skeleton for 'ExtFlightDelays.fxml' Controller Class
 */

package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.List;

import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Model;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ExtFlightDelaysController {

	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="voliMinimo"
    private TextField voliMinimo; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalizza"
    private Button btnAnalizza; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxAeroportoPartenza"
    private ComboBox<Airport> cmbBoxAeroportoPartenza; // Value injected by FXMLLoader

    @FXML // fx:id="btnAeroportiConnessi"
    private Button btnAeroportiConnessi; // Value injected by FXMLLoader

    @FXML // fx:id="numeroOreTxtInput"
    private TextField numeroOreTxtInput; // Value injected by FXMLLoader

    @FXML // fx:id="btnOttimizza"
    private Button btnOttimizza; // Value injected by FXMLLoader

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {
    	
    	txtResult.clear();
    	cmbBoxAeroportoPartenza.getItems().clear();
    	try {
    	
    	int x = Integer.parseInt(voliMinimo.getText());
    	
    	this.model.creaGrafo(x);
    	
    	cmbBoxAeroportoPartenza.getItems().addAll(this.model.getAer());
    	
    	
    	} catch (NumberFormatException e) {
    		txtResult.appendText("Inserisci un numero"+"\n");
    	}
    	catch (RuntimeException e ) {
    		txtResult.appendText("ERRORE");
    	}
    }

    @FXML
    void doCalcolaAeroportiConnessi(ActionEvent event) {
    	txtResult.clear();
    	
    	try {
    		Airport a =  cmbBoxAeroportoPartenza.getValue();
    		List<Airport> connessi = this.model.getConnessi(a);
    		
    		for (Airport a1: connessi) {
    			txtResult.appendText(a1+"\n");
    		}
    		
    	}
    	catch (RuntimeException e) {
    		txtResult.appendText("ERRORE");
    	}
    	
    }

    @FXML
    void doCercaItinerario(ActionEvent event) {
    	try {
    		int ore = Integer.parseInt(numeroOreTxtInput.getText());
    		Airport a =  cmbBoxAeroportoPartenza.getValue();
    		List<Airport> it = this.model.getItinerario(a,ore);
    		txtResult.appendText("\nItinerario:\n");
    		for (Airport a1: it) {
    			txtResult.appendText(a1+"\n");
    		}
    		
    		txtResult.appendText("\nTOT ORE: "+this.model.getTotOre()+"\n");
    	}
    		catch (NumberFormatException e) {
        		txtResult.appendText("Inserisci un numero"+"\n");
        	}
        	catch (RuntimeException e ) {
        		txtResult.appendText("ERRORE");
        	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert voliMinimo != null : "fx:id=\"voliMinimo\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAeroportiConnessi != null : "fx:id=\"btnAeroportiConnessi\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert numeroOreTxtInput != null : "fx:id=\"numeroOreTxtInput\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnOttimizza != null : "fx:id=\"btnOttimizza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";

    }
    
    public void setModel(Model model) {
  		this.model = model;
  		
  	}
}
