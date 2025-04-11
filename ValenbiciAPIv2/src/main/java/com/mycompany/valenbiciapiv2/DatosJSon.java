package com.mycompany.valenbiciapiv2;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
/**
 *
 * @author diego
 */
public class DatosJSon {
    private static String API_URL;
    private String datos = ""; //para mostrar en el jTextArea los datos de las estaciones

    protected String [] values; //para añadir los datos de las estaciones Valenbici a la BDD
    private int numEst;

    public DatosJSon(int nE){
        numEst = nE;
        datos = "";
        API_URL = "https://valencia.opendatasoft.com/api/explore/v2.1/catalog/datasets/valenbisi-disponibilitat-valenbisi-dsiponibilidad/records?f=json&location=39.46447,-0.39308&distance=10&limit=" + nE;

        values = new String [numEst];

        for (int i = 0; i < numEst; i++ )
            
            values[i] = "";
    
         }

    public void mostrarDatos(int nE){

        numEst = nE;
        datos="";
        API_URL = "https://valencia.opendatasoft.com/api/explore/v2.1/catalog/datasets/valenbisi-disponibilitat-valenbisi-dsiponibilidad/records?f=json&location=39.46447,-0.39308&distance=10&limit=" + nE;

        values = new String [numEst];
        for (int i = 0; i < numEst; i++ )
            values[i] = "";

        double lon,lat;

            if (API_URL.isEmpty()) {
            //System.err.println("La URL de la API no está especificada.");
            setDatos(getDatos().concat("La URL de la API no está especificada."));
            return;
            }
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpGet request = new HttpGet(API_URL);
                HttpResponse response = httpClient.execute(request);
                HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);

            // Intentamos procesar la respuesta como JSON
            try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray resultsArray = jsonObject.getJSONArray("results");
                    
                    for (int i = 0; i < resultsArray.length(); i++){
                        
                        
                        
                        System.out.println(resultsArray.getJSONObject(i).toString());
                        JSONObject element = resultsArray.getJSONObject(i);
                        JSONObject geopoint = element.getJSONObject("geo_point_2d");
                        lon = geopoint.getDouble("lon");
                        lat = geopoint.getDouble("lat");

                        values[i] = resultsArray.getJSONObject(i).get("number").toString() + "<" +
                                    resultsArray.getJSONObject(i).get("address") + "<" +
                                    resultsArray.getJSONObject(i).get("available") + "<" +
                                    resultsArray.getJSONObject(i).get("free") + "<" +
                                    resultsArray.getJSONObject(i).get("open") + "<" +
                                    lat + "<" + lon;
                        
                        
                        
                        
                        datos += "Estación Nº: "+resultsArray.getJSONObject(i).get("number")+"\n"
                                +"Direccion: "+resultsArray.getJSONObject(i).get("address")+"\t"
                                +"Bicicletas disponibles: "+resultsArray.getJSONObject(i).get("available")+"\t"
                                +"Espacios disponibles: "+resultsArray.getJSONObject(i).get("free")+"\t"
                                +"Operativo: "+resultsArray.getJSONObject(i).get("open")+"\t\n"
                                +"------------------------------------------------"+"\n";
                        
                    }
                   
                    
                    
                // Añade aquí el Código para recorrer el vector de objetos JSON, con los datos de las estaciones y preparar el vector de
                // valores (atributo values de esta clase)
           
            } catch (org.json.JSONException e) {
            // Si la respuesta no es un array JSON, imprimimos el mensaje de error
            setDatos(getDatos().concat("Error al procesar los datos JSON: " + e.getMessage()));
            }
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
                return;
           }
        /**
        * @return the datos
        */
        public String getDatos() {
            return datos;
        }
        /**
        * @param datos the datos to set
        */
        public void setDatos(String datos) {
            this.datos = datos;
        }
        /**
        * @return the values
        */
        public String[] getValues() {
            return values;
        }
        /**
        * @param values the values to set
        */
        public void setValues(String[] values) {
            this.values = values;
        }
        /**
        * @return the numEst
        */
        public int getNumEst() {
            return numEst;
        }
        /**
        * @param numEst the numEst to set
        */
        public void setNumEst(int numEst) {
            this.numEst = numEst;
 }
}



