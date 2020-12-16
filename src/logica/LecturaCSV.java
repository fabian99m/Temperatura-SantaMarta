package logica;

import java.util.ArrayList;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class LecturaCSV {

    private final String dirCSV;
    private final ArrayList<String> temperatura;
    private final ArrayList<String> humedad;
    private final ArrayList<String> velocidad; // velocidad de viento
    private float[] temperaturaN, humedadN, velocidadN;
    private int[] horas;

    public LecturaCSV(String path) {
        dirCSV = path;
        temperatura = new ArrayList<>();
        humedad = new ArrayList<>();
        velocidad = new ArrayList<>();
    }

    public void LeerCSV() throws IOException {

        try (Reader reader = Files.newBufferedReader(Paths.get(dirCSV))) {
           
            // usando a org.apache.commons.csv
            //Iterable<CSVRecord> datos = CSVFormat.DEFAULT.parse(reader);
          
           // leer csv saltando el header
          Iterable<CSVRecord> datos = CSVFormat.DEFAULT.withHeader().withSkipHeaderRecord().parse(reader);
          
            datos.forEach(dato -> {
                temperatura.add(dato.get(0));
                humedad.add(dato.get(1));
                velocidad.add(dato.get(2));
                }
            );
            reader.close();
        }

        temperaturaN = new float[temperatura.size()];
        humedadN = new float[humedad.size()];
        velocidadN = new float[velocidad.size()];
        horas = new int[temperatura.size()];

        for (int i = 0; i < temperatura.size(); i++) {
            temperaturaN[i] = Float.parseFloat(temperatura.get(i));
            humedadN[i] = Float.parseFloat(humedad.get(i));
            velocidadN[i] = Float.parseFloat(velocidad.get(i));
            horas[i] = i + 1; // horas por temperatura tomada
        }

        temperatura.clear();
        humedad.clear();
        velocidad.clear();
    }

    public float[] getTemperaturaN() {
        return temperaturaN;
    }

    public void setTemperaturaN(float[] temperaturaN) {
        this.temperaturaN = temperaturaN;
    }

    public float[] getHumedadN() {
        return humedadN;
    }

    public void setHumedadN(float[] humedadN) {
        this.humedadN = humedadN;
    }

    public float[] getVelocidadN() {
        return velocidadN;
    }

    public void setVelocidadN(float[] velocidadN) {
        this.velocidadN = velocidadN;
    }

    public int[] getHoras() {
        return horas;
    }

    public void setHoras(int[] horas) {
        this.horas = horas;
    }

}
