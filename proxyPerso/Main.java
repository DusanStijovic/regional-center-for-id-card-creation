/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basicRegionalniCentar;
 
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.json.simple.JSONObject;

/**
 *
 * @author ducati
 */
public class Main {
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup = "zahtevi")
    private static Queue zahtevi; 
    
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("proxyPU");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();
    private static final int REGIONALNNI_CENTAR_IDENTITY = 17141;
    private static final String serverURL = "http://collabnet.netset.rs:8081/is";
    private static int REQUEST_SEND_AND_RECEIVED = 200;
   
    

     private static String getResponseString(InputStream inputStream){
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
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
         return null;
    }
    
    public static void updateRequestToDataBase(Zahtev zahtev){
        entityManager.getTransaction().begin();
        entityManager.merge(zahtev);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }
    public static void main(String[] args) {
    //Proveriti
   
    JMSContext  context = connectionFactory.createContext();
    JMSConsumer consumer = context.createConsumer(zahtevi);
        while(true){
            Message message = consumer.receive();
            if(message instanceof ObjectMessage){
              
                try {
                    Zahtev zahtev = (Zahtev) ((ObjectMessage) message).getObject();
                    while(true){
                        try {
                            URL url = new URL(serverURL + "/persoCentar/submit");
                            HttpURLConnection connection  = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("POST");
                            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                            connection.setRequestProperty("Content-Type", "application/json");

                            connection.setDoOutput(true);
                            try (DataOutputStream writer = new DataOutputStream(connection.getOutputStream())) {
                                     writer.writeBytes(makeJSONObject(zahtev).toJSONString());
                                     writer.flush();
                            }
                            
                            System.out.println(connection.getResponseCode());
                            System.out.println(makeJSONObject(zahtev).toJSONString()); 
                             if(connection.getResponseCode() == REQUEST_SEND_AND_RECEIVED){
                                 System.out.println(getResponseString(connection.getInputStream()));
                                 zahtev.setStanje("uProdukciji");
                                 updateRequestToDataBase(zahtev);
                                 break;
                             }
                            //System.out.println(getResponseString(connection.getInputStream()));
                        } catch (IOException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    
                    }
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
               
            }
        }       
    }
    
    


    private static JSONObject makeJSONObject(Zahtev zahtev){
 
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("datumRodjenja", zahtev.getDatumrodjenja());
        jsonObject.put("nacionalnost", zahtev.getNacionalnost());
        jsonObject.put("bracnoStanje", zahtev.getBracnostanje());
        jsonObject.put("opstinaPrebivalista", zahtev.getOpstinaprebivaliste());
        String padding = zahtev.getIdzahtev().toString();
        String before = "";
        int size = 12 - 5 - padding.length();
        for(int i = 0;i<size;i++){
            before+="0";
        }
        jsonObject.put("id", REGIONALNNI_CENTAR_IDENTITY + before + padding);  
        jsonObject.put("JMBG", zahtev.getJmbg());
        jsonObject.put("pol",zahtev.getPol());
        jsonObject.put("ulicaPrebivalista", zahtev.getUlicaprebivalista());
        jsonObject.put("brojPrebivalista", zahtev.getBrojulice());
        jsonObject.put("profesija", zahtev.getProfesija());
        jsonObject.put("ime", zahtev.getIme());
        jsonObject.put("prezime", zahtev.getPrezime());
        jsonObject.put("imeMajke", zahtev.getImemajke());
        jsonObject.put("imeOca", zahtev.getImeoca());
        jsonObject.put("prezimeMajke", zahtev.getPrezimemajke());
        jsonObject.put("prezimeOca", zahtev.getPrezimeoca());
        return jsonObject;


}
    
}