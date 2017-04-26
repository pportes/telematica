package citibike;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.*; /* Usa la libreria: http://central.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.8.8.1/jackson-databind-2.8.8.1.jar
 Que necesita estas dependencias: http://central.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/2.8.8/jackson-core-2.8.8.jar
 http://central.maven.org/maven2/com/fasterxml/jackson/core/jackson-annotations/2.8.8/jackson-annotations-2.8.8.jar
*/
public class CitiBike {

    public static void main(String[] args) {

        try {
            URL url = new URL("https://feeds.citibikenyc.com/stations/stations.json"); //Creo un nuevo objeto URL.
            ObjectMapper mapper = new ObjectMapper(); // Este objeto convierte entre objetos JAVA y JSON
            JsonNode leerURL = mapper.readTree(url); // Leo el objeto URL creado
            JsonNode exTime = leerURL.get("executionTime"); // Obtengo tiempo de ejecucion
            System.out.println("Execution Time= " + exTime.asText()); // Imprimo tiempo de ejecucion
            JsonNode bList = leerURL.get("stationBeanList"); // Obtengo lista de estaciones
            ArrayList<JsonNode> buscoMax = new ArrayList<>(); // Creo un ArrayList para guardar los valores maximos que voy encontrando
            int maxBicis = 0; // Auxiliar para buscar el valor maximo de bicis

            for (int i1 = 0; i1 < bList.size(); i1++) { // Recorro lista de estaciones
                JsonNode iiNode = bList.get(i1); // Creo un objeto auxiliar para la iteracion actual
                int idNum = iiNode.get("id").asInt(); // Guardo el ID de la estacion actual
                int biciNum = iiNode.get("availableBikes").asInt(); // Guardo la cantidad de bicis disponibles en la estacion actual
                if (biciNum > maxBicis) { // Comparo con el valor auxiliar, si es mayor, es el maximo hasta ahora
                    buscoMax.clear(); // Limpio la lista, pues anteriores valores no eran realmente maximos
                    buscoMax.add(iiNode); // Agrego actual estacion a la lista
                    maxBicis = biciNum; // Asigno nuevo valor al auxiliar de valor maximo
                } else if (biciNum == maxBicis) { // Si no es mayor pero es igual al maximo anteriormente registrado
                    buscoMax.add(iiNode); // Agrego la estacion actual sin limpiar la lista, las estaciones anteriores del mismo valor se mantienen
                }
            }
            System.out.println("El máximo de bicis disponibles es " + maxBicis + " y se encuentra en " + buscoMax.size() + " estacion(es):"); // Imprimo numero de bicis maximo

            for (int i2 = 0; i2 < buscoMax.size(); i2++) { // Recorro lista de estaciones con mas bicis disponibles
                System.out.println("ID= " + buscoMax.get(i2).get("id").asInt() + " - Station name= " + buscoMax.get(i2).get("stationName").asText()); // Imprimo ID y nombre de cada estacion
            }
            
        } catch (MalformedURLException ex) { // Atrapo excepciones.
            System.out.println("MalformedURL");
        } catch (IOException ex) {
            System.out.println("IOException");
        }
    }
}