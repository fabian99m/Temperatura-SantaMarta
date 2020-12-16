package logica;


import java.util.Arrays;
import java.util.Vector;
import weka.classifiers.functions.LinearRegression;
import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class ProcesaAlgoritmo {

    private Instances dataset;
    private Vector datos;

    public ProcesaAlgoritmo(Vector datos) {
        this.datos = datos;
    }

    public ProcesaAlgoritmo(int[] x, float[] y) {
        FastVector vector = new FastVector(2);
        vector.addElement(new Attribute("x"));
        vector.addElement(new Attribute("y"));
        dataset = new Instances("dataset", vector, x.length);

        for (int i = 0; i < x.length; i++) {
            Instance instance = new Instance(2);
            instance.setValue((Attribute) vector.elementAt(0), x[i]);
            instance.setValue((Attribute) vector.elementAt(1), y[i]);
            dataset.add(instance);
        }
    }

    public ProcesaAlgoritmo(float[] x, float[] y) {
        FastVector vector = new FastVector(2);
        vector.addElement(new Attribute("x"));
        vector.addElement(new Attribute("y"));
        dataset = new Instances("dataset", vector, x.length);

        for (int i = 0; i < x.length; i++) {
            Instance instance = new Instance(2);
            instance.setValue((Attribute) vector.elementAt(0), x[i]);
            instance.setValue((Attribute) vector.elementAt(1), y[i]);
            dataset.add(instance);
        }
    }

    public String regresionLineal() throws Exception {
        dataset.setClassIndex(dataset.numAttributes() - 1); //elegir la columna clase
        LinearRegression lire = new LinearRegression();
        lire.buildClassifier(dataset);
        double cf[] = lire.coefficients();
        String ecuation = "(" + String.format("%.4f", cf[0]) + ") * X + (" + String.format("%.4f", cf[2]) + ")";
        //String ecuation = "("+cf[0]+") * X + ("+cf[2] + ")";
        return ecuation.replace(",", ".");

    }

    public SimpleKMeans clustering(String tipo) throws Exception {

        FastVector vector = new FastVector(2);
        vector.addElement(new Attribute(tipo));
        vector.addElement(new Attribute("Temperatura"));

        Instances data = new Instances("dataset", vector, datos.size());

        Punto aux;
        for (int i = 0; i < datos.size(); i++) {
            aux = (Punto) datos.get(i);
            Instance instance = new Instance(2);
            instance.setValue((Attribute) vector.elementAt(0), aux.getX());
            instance.setValue((Attribute) vector.elementAt(1), aux.getY());
            data.add(instance);
        }

        SimpleKMeans skm = new SimpleKMeans();
        skm.setPreserveInstancesOrder(true);
        skm.setNumClusters(3);
        skm.buildClusterer(data);

        int[] arr = skm.getAssignments();
        Punto p;
        for (int i = 0; i < datos.size(); i++) {
            p = (Punto) datos.get(i);
            p.setCluster(arr[i]);
        }
        return skm;
    }

}
