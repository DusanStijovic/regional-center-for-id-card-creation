package basicRegionalniCentar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Response;
import org.eclipse.persistence.exceptions.i18n.JMSProcessingExceptionResource;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 *
 * @author ducati
 */
public class RegionalniCentarLogics implements Initializable{
    
    private static int regionalniCentarIdentiy = 17145;
    
    @FXML
    private TextField ime;
     
    @FXML
    private ChoiceBox bracnoStanje;
    
    @FXML
    private TextField pol;
    
    @FXML
    private TextField prezime;
    
    @FXML
    private TextField jmbg;
    
    @FXML
    private TextField profesija;
    
    @FXML
    private TextField imeMajke;
    
    @FXML
    private TextField prezimeMajke;
    
    @FXML
    private TextField imeOca;
    
    @FXML
    private TextField prezimeOca;
    
    @FXML
    private TextField nacionalnost;
   
    @FXML
    private TextField opstinaPrebivalista;
    
    @FXML
    private TextField ulicaPrebivalista;
    
    @FXML
    private TextField BrojPrebivalista;
    
    @FXML
    private TextField datumRodjenja;
    
    
    @FXML
    private TextField ImeOut;
     
    @FXML
    private TextField bracnoStanjeOut;
    
    @FXML
    private TextField polOut;
    
    @FXML
    private TextField prezimeOut;
    
    @FXML
    private TextField jmbgOut;
    
    @FXML
    private TextField profesijaOut;
    
    @FXML
    private TextField imeMajkeOut;
    
    @FXML
    private TextField prezimeMajkeOut;
    
    @FXML
    private TextField imeOcaOut;
    
    @FXML
    private TextField prezimeOcaOut;
    
    @FXML
    private TextField nacionalnostOut;
   
    @FXML
    private TextField opstinaPrebivalistaOut;
    
    @FXML
    private TextField ulicaPrebivalistaOut;
    
    @FXML
    private TextField BrojPrebivalistaOut;
    
    @FXML
    private TextField datumRodjenjaOut;
    
    @FXML
    Button kreirajZahtev;
    
    @FXML
    Button proveriStatus;
    
    @FXML
    TextField brojZahteva;
    
    @FXML
    TextField statusZahteva;
    
    @FXML
    Button uruci;
    
   
    
   private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("basicRegionalniCentarPU");
   private static EntityManager entityManager = entityManagerFactory.createEntityManager();    
   private JMSContext context = Main.connectionFactory.createContext();
   private JMSProducer producer = context.createProducer();
    
    @FXML 
    private TextField statusKreiranjaZahteva;
    
   
    
    private final String serverURL = "http://collabnet.netset.rs:8081/is";
    private final static int TERMIN_AVAILABLE = 200;
    private final static int NOT_VALID_REQUEST_ID = 400;
    private final static int REQUEST_DOES_NOT_EXIST = 404;
    Thread timerListener;
    private final static int REQUEST_FOUND = 200;
    private String U_PRODUKCIJI ="uProdukciji";
    private String CEKA_NA_URUCENJE = "proizveden";
    private String KREIRAN = "kreiran";
    private String URUCEN = "urucen";
    
    @FXML
    private void ponistiFormuOut(){
        clearOutFields();
        statusZahteva.clear();
        brojZahteva.setEditable(true);
        brojZahteva.clear();
        proveriStatus.setVisible(true);
        proveriStatus.setDisable(false);
        proveriStatus.setText("Proveri status");
        uruci.setVisible(false);
        uruci.setDisable(true);
    }
    
   private boolean formIsFilled(){
       
       if(ime.getText().length() == 0) return false;
       if(prezime.getText().length() == 0) return false;
       if(datumRodjenja.getText().length() == 0) return false;
       if(nacionalnost.getText().length() == 0) return false;
       if(profesija.getText().length() == 0) return  false;
       if(bracnoStanje.getSelectionModel().getSelectedItem() == null) return false;
       if(!pol.getText().equals("muski") && !pol.getText().equals("muški") && !pol.getText().equals("zenski") && !pol.getText().equals("ženski")) return false;
       if(imeMajke.getText().length() == 0) return false;
       if(imeOca.getText().length() == 0) return false;
       if(prezimeOca.getText().length() == 0) return false;
       if(prezimeMajke.getText().length() == 0)return false;
       if(opstinaPrebivalista.getText().length() == 0) return false;
       if(ulicaPrebivalista.getText().length() == 0) return false;
       if(BrojPrebivalista.getText().length() == 0) return false;
       return jmbg.getText().length() == 13;              
   }    
    
   private boolean jmbgFormatIsCorrect(){
       if(jmbg.getText().length() != 13) {
           return false;
       } 
       String datumRodj = datumRodjenja.getText();    
       String year = datumRodj.substring(1, 4);
       String month = datumRodj.substring(5,7);
       String day = datumRodj.substring(8);
       // System.out.println(jmbg.getText().substring(0,8).equals(day+month+year));
       //System.out.println(day + month + year + "   " + jmbg.getText().substring(0,7));
       //System.out.println(jmbg.getText().matches("[0-9]+"));
       return jmbg.getText().matches("[0-9]+") && jmbg.getText().substring(0,7).equals(day+month+year);
   }
   private boolean dataOfBirthFormatIsCorrect(){
       String datumRodj = datumRodjenja.getText();
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
       try{
           Date date = simpleDateFormat.parse(datumRodj);
           return true;
       } catch (java.text.ParseException ex) {
            
       }
       return false;
   }
   
   
    @FXML
    private void kreirajZahtev(){
        
       if(!formIsFilled()) {
           statusKreiranjaZahteva.setText("Forma nije ispravno popunjena!");
           return;
       }
       if(!dataOfBirthFormatIsCorrect()){
         statusKreiranjaZahteva.setText("Datum rodjenja niju u odgovarajucem formatu");
         return;
     }
     if(!jmbgFormatIsCorrect()){
         statusKreiranjaZahteva.setText("JMBG nije u dobrom formatu");
         return;
     }
     
       if(!terminIsAvailable()){
           statusKreiranjaZahteva.setText("Termin nije dostupan!");
           return;
       }
     
       Zahtev zahtev = new Zahtev(jmbg.getText(), ime.getText(), prezime.getText(), imeMajke.getText(), prezimeMajke.getText(),imeOca.getText(), prezimeOca.getText(),
         pol.getText(), datumRodjenja.getText(),nacionalnost.getText(), profesija.getText(), (String)bracnoStanje.getSelectionModel().getSelectedItem(), 
         opstinaPrebivalista.getText(), ulicaPrebivalista.getText(), BrojPrebivalista.getText());
       
        saveZahetvToDataBase(zahtev);
        
        ObjectMessage objectMessage =  context.createObjectMessage(zahtev);
        producer.send(Main.zahtevi, objectMessage);   
        statusKreiranjaZahteva.setText("Zahtev je uspesno kreiran");
    }
    
    @FXML
    private void ponistiFormu(){
        ime.clear();
        prezime.clear();
        jmbg.clear();
        datumRodjenja.clear();
        nacionalnost.clear();
        profesija.clear();
        pol.clear();
        imeOca.clear();
        prezimeOca.clear();
        imeMajke.clear();
        prezimeMajke.clear();
        opstinaPrebivalista.clear();
        ulicaPrebivalista.clear();
        BrojPrebivalista.clear();
        bracnoStanje.getSelectionModel().clearSelection();
        statusKreiranjaZahteva.clear();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> availableChoices = FXCollections.observableArrayList("ozenjen/udata", "razveden/a", "neozenjen/a", "udovac/udovica"); 
        bracnoStanje.setItems(availableChoices);
    }

    private int getNextId(){
    
        List resultList = entityManager.createNamedQuery("Zahtev.findAll").getResultList();
        if(!resultList.isEmpty()){
        Zahtev z =(Zahtev) (resultList.get(resultList.size()-1));
        return z.getIdzahtev()+22;
        }
    return 1;
 }
        
    private void saveZahetvToDataBase(Zahtev zahtev){
        zahtev.setIdzahtev(getNextId());
        entityManager.getTransaction().begin();
        entityManager.persist(zahtev);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }
    
    
    private boolean httpRequestCheckIfTerminIsAvailable(){
        try {
            
            URL url = new URL(serverURL + "/terminCentar/checkTimeslotAvailability" + "?regionalniCentarId=" + regionalniCentarIdentiy + "&" + "termin=" + "2020-02-04T09:10:00");//java.time.LocalDate.now().toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            System.out.println(connection.getResponseCode());
            if(connection.getResponseCode() != TERMIN_AVAILABLE) return false;
            return (boolean) readAndParseRequestResult(connection.getInputStream(),"dostupnost");  
        } catch (MalformedURLException ex) {
            Logger.getLogger(RegionalniCentarLogics.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(RegionalniCentarLogics.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RegionalniCentarLogics.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }
    
    private Object readAndParseRequestResult(InputStream inputStream, String stringToParse){
        try {
            StringBuilder response;
            try (BufferedReader requestResult = new BufferedReader(new InputStreamReader(inputStream))) {
                response = new StringBuilder();
                while(true){
                    String lineOfResponse = requestResult.readLine();
                    if(lineOfResponse == null) break;
                    response.append(lineOfResponse);
                }
            }         
            System.out.println(response.toString());
            JSONParser parseJSONObjects = new JSONParser();
            JSONObject parsedJSONObject = (JSONObject) parseJSONObjects.parse(response.toString());
            return parsedJSONObject.get(stringToParse);
        } catch (ParseException | IOException ex) {
            Logger.getLogger(RegionalniCentarLogics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    private String getResponseString(InputStream inputStream){
         try {
            StringBuilder response;
            try (BufferedReader requestResult = new BufferedReader(new InputStreamReader(inputStream))) {
                response = new StringBuilder();
                while(true){
                    String lineOfResponse = requestResult.readLine();
                    if(lineOfResponse == null) break;
                    response.append(lineOfResponse);
                }
            }
            
            return response.toString();
        } catch (IOException ex) {
            Logger.getLogger(RegionalniCentarLogics.class.getName()).log(Level.SEVERE, null, ex);
        }
         return null;
    }
    
    
    private Object readAndParseRequestResult(InputStream inputStream){
         try {
            StringBuilder response;
            try (BufferedReader requestResult = new BufferedReader(new InputStreamReader(inputStream))) {
                response = new StringBuilder();
                while(true){
                    String lineOfResponse = requestResult.readLine();
                    if(lineOfResponse == null) break;
                    response.append(lineOfResponse);
                }
            }
            
            JSONParser parseJSONObjects = new JSONParser();
            JSONObject parsedJSONObject = (JSONObject) parseJSONObjects.parse(response.toString());
            return parsedJSONObject;
        } catch (ParseException | IOException ex) {
            Logger.getLogger(RegionalniCentarLogics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private boolean terminIsAvailable() {
        
        return httpRequestCheckIfTerminIsAvailable();
    }

    @FXML
    private void checkRequestStatus(){ 
         if(proveriStatus.getText().equals("Proveri status")){
            if(brojZahteva.getText().length()!=12){
                statusZahteva.setText("Broj zahteva nije okej!");
                return;
            }
            statusZahteva.clear();
            brojZahteva.setEditable(false);
            if(noNeedForServerRequest()){
                return;
            }
            //Ne idi na server ako nema u bazi ,dodati
            if(!httpRequestCheckIfRequestExists()){
                brojZahteva.setEditable(true);
                return;
            } 
             readRequestDetailsAndStatus(true);
             proveriStatus.setText("Osvezi Status");
        } else{
             statusZahteva.clear();                   
             readRequestDetailsAndStatus(false);
         }
         
        
    }

    private boolean brojZahtevaisCorrect() {
        return brojZahteva.getText().substring(0, 5).equals(""+regionalniCentarIdentiy);
    
    }
    
    private boolean noNeedForServerRequest(){
            List<Zahtev> resultList = entityManager.createNamedQuery("Zahtev.findByIdzahtev", Zahtev.class).setParameter("idzahtev", Integer.parseInt(brojZahteva.getText().substring(5))).getResultList();
            if(resultList.isEmpty()){
               statusZahteva.setText("Zahtev sa datim ID-je ne postoji");
                return true;
            
            }           
            Zahtev zahtev = resultList.get(0);
            if(zahtev.getStanje().equals(CEKA_NA_URUCENJE)){
             setRequestDetails(zahtev);
             uruci.setVisible(true);
             uruci.setDisable(false);
             statusZahteva.setText(CEKA_NA_URUCENJE);
             proveriStatus.setText("Proveri status");
             proveriStatus.setVisible(false);
             proveriStatus.setDisable(true);
             return true;
            }
            if(zahtev.getStanje().equals("urucen")){
                setRequestDetails(zahtev);
                statusZahteva.setText(URUCEN);
                
                return true;
            }
         
            return false;
    }
    
    
    
    private void readRequestDetailsAndStatus(boolean needAllData){
        try {  
            URL url = new URL(serverURL + "/persoCentar/" + brojZahteva.getText());
            HttpURLConnection connection  = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
         
              
            JSONObject parsedResponse = (JSONObject)readAndParseRequestResult(connection.getInputStream());
            String requestStatus = (String)parsedResponse.get("status");
            
            if(needAllData){
                setRequestDetails(parsedResponse);  
            }
         
            if(requestStatus.equals(U_PRODUKCIJI)){
              statusZahteva.setText("U Produkciji");
              proveriStatus.setText("Osvezi Status");  
              return;
            }
           
            if(requestStatus.equals(CEKA_NA_URUCENJE)){
             uruci.setVisible(true);
             uruci.setDisable(false);
             statusZahteva.setText(CEKA_NA_URUCENJE);
             proveriStatus.setText("Proveri status");
             proveriStatus.setVisible(false);
             proveriStatus.setDisable(true);
            }
           
            
        } catch (IOException ex) {
            Logger.getLogger(RegionalniCentarLogics.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
   
    private void updateZahtevInDataBase(Zahtev zahtev, String newState){
     zahtev.setStanje(newState);
     entityManager.getTransaction().begin();
     entityManager.merge(zahtev);
     entityManager.flush();
     entityManager.getTransaction().commit();
        
        
    }
    
    
    @FXML
    private void uruciDokument(){
            List<Zahtev> resultList = entityManager.createNamedQuery("Zahtev.findByIdzahtev", Zahtev.class).setParameter("idzahtev", Integer.parseInt(brojZahteva.getText().substring(5))).getResultList();
            if(resultList.isEmpty()){
               statusZahteva.setText("Greska ne nalazi se u bazi");
               return;
            } 
            Zahtev zahtev = resultList.get(0);
            updateZahtevInDataBase(zahtev, URUCEN);
            uruci.setVisible(false);
            uruci.setDisable(true);
            statusZahteva.setText(URUCEN);
    }
    
    private boolean httpRequestCheckIfRequestExists(){          
        try {
            URL url = new URL(serverURL + "/persoCentar/" + brojZahteva.getText());
            HttpURLConnection connection  = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
           
            int responseStatus = connection.getResponseCode();
            
            //System.out.println(responseStatus);
            
            String response = getResponseString(connection.getInputStream());
            
            if(responseStatus == NOT_VALID_REQUEST_ID){
                statusZahteva.setText("ID zahteva nije u ispravnom formatu!");
                return false;
            }
            if(responseStatus == REQUEST_DOES_NOT_EXIST){
                statusZahteva.setText("Zahtev sa datim id-jem ne postoji!");
                return false;
            }
            if(responseStatus == REQUEST_FOUND) {
                return true;
            }
            statusZahteva.setText(response);
            
        } catch (ProtocolException ex) {
            Logger.getLogger(RegionalniCentarLogics.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(RegionalniCentarLogics.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RegionalniCentarLogics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;   
        
    }

    private String getJSONField(JSONObject object, String fieldToGet){
        return (String)object.get(fieldToGet);
    }
    
    private void setRequestDetails(JSONObject parsedResponse) {
       ImeOut.setText(getJSONField(parsedResponse, "ime"));
       prezimeOut.setText(getJSONField(parsedResponse, "prezime"));
       jmbgOut.setText(getJSONField(parsedResponse, "JMBG"));
       imeMajkeOut.setText(getJSONField(parsedResponse, "imeMajke"));
       prezimeMajkeOut.setText(getJSONField(parsedResponse, "prezimeMajke"));
       imeOcaOut.setText(getJSONField(parsedResponse, "imeOca"));
       prezimeOcaOut.setText(getJSONField(parsedResponse, "prezimeOca"));
       polOut.setText(getJSONField(parsedResponse, "pol"));
       datumRodjenjaOut.setText(getJSONField(parsedResponse, "datumRodjenja"));
       nacionalnostOut.setText(getJSONField(parsedResponse, "nacionalnost"));
       profesijaOut.setText(getJSONField(parsedResponse, "profesija"));
       bracnoStanjeOut.setText(getJSONField(parsedResponse, "bracnoStanje"));
       opstinaPrebivalistaOut.setText(getJSONField(parsedResponse, "opstinaPrebivalista"));
       ulicaPrebivalistaOut.setText(getJSONField(parsedResponse, "ulicaPrebivalista"));
       BrojPrebivalistaOut.setText(getJSONField(parsedResponse, "brojPrebivalista")); 
    }
    
    
     private void setRequestDetails(Zahtev zahtev) {
       ImeOut.setText(zahtev.getIme());
       prezimeOut.setText(zahtev.getPrezime());
       jmbgOut.setText(zahtev.getJmbg());
       imeMajkeOut.setText(zahtev.getImemajke());
       prezimeMajkeOut.setText(zahtev.getPrezimemajke());
       imeOcaOut.setText(zahtev.getImeoca());
       prezimeOcaOut.setText(zahtev.getImeoca());
       polOut.setText(zahtev.getPol());
       datumRodjenjaOut.setText(zahtev.getDatumrodjenja());
       nacionalnostOut.setText(zahtev.getNacionalnost());
       profesijaOut.setText(zahtev.getProfesija());
       bracnoStanjeOut.setText(zahtev.getBracnostanje());
       opstinaPrebivalistaOut.setText(zahtev.getOpstinaprebivaliste());
       ulicaPrebivalistaOut.setText(zahtev.getUlicaprebivalista());
       BrojPrebivalistaOut.setText(zahtev.getBrojulice()); 
    }

    private void clearOutFields() {
       ImeOut.clear();
       prezimeOut.clear();
       imeMajkeOut.clear();
       prezimeMajkeOut.clear();
       imeOcaOut.clear();
       prezimeOcaOut.clear();
       polOut.clear();
       bracnoStanjeOut.clear();
       datumRodjenjaOut.clear();
       nacionalnostOut.clear();
       profesijaOut.clear();
       opstinaPrebivalistaOut.clear();
       ulicaPrebivalistaOut.clear();
       jmbgOut.clear();
       BrojPrebivalistaOut.clear();
       brojZahteva.clear();
       statusZahteva.clear();
       
    }
    
     
    
}
