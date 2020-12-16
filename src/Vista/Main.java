package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import logica.LecturaCSV;
import logica.ProcesaAlgoritmo;
import logica.Punto;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.jfree.chart.ChartPanel;
import utils.Dispersion;
import utils.XYChart;
import utils.XYChartFactory;
import weka.clusterers.SimpleKMeans;

public class Main extends JFrame {

    private XYChart lineChart;
    private Expression regresionTemp;
    private JFrame ecuFrame;
    private JFrame CentrFrame;

    private float[] evaluateRegretion(Expression evaluator, int[] toPredict) {
        float predicted[] = new float[toPredict.length];
        for (int i = 0; i < toPredict.length; i++) {
            evaluator.setVariable("X", toPredict[i]);
            predicted[i] = (float) evaluator.evaluate();
        }
        return predicted;
    }

    private void evaluate() {
        try {
            Integer value = Integer.parseInt(testing.getText());
            if (value > 0) {
                Expression evaluator;
                evaluator = regresionTemp;
                evaluator.setVariable("X", value);
                float predicted = (float) evaluator.evaluate();
                prediction.setText("La temperatura para la hora " + value + " se proyecta en " + String.format("%.2f", predicted) + " grados (°C)");
            }
        } catch (NumberFormatException nfe) {
            prediction.setText("");
        }
    }

    public Main() {
        try {
            initComponents();
            this.setLocationRelativeTo(null); // centrar ventana

            // Evento para predicción de temperatura
            testing.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char input = e.getKeyChar();
                    if ((input < '0' || input > '9') && input != '\b') {
                        e.consume();
                    } else {
                        SwingUtilities.invokeLater(() -> {
                            evaluate();
                        });
                    }
                }
            });

            // gráfica de datos reales y regresión
            this.lineChart = XYChartFactory.createXYLineChart("Temperatura Santa Marta", "Horas", "Grados (°C)");
            ChartPanel chartPanel = this.lineChart.getPanel();
            chartPanel.setPreferredSize(this.graph.getPreferredSize());
            this.graph.removeAll();
            this.graph.setLayout(new BorderLayout());
            this.graph.add(chartPanel, BorderLayout.CENTER);
            this.graph.validate();

            LecturaCSV csvReader = new LecturaCSV("datos_sf.csv");
            csvReader.LeerCSV();
            float[] temp = csvReader.getTemperaturaN();
            float[] hum = csvReader.getHumedadN();
            float[] vel = csvReader.getVelocidadN();

            this.lineChart.prepareSerie("Casos(Datos reales)", Color.BLACK, csvReader.getHoras(), temp);
            ProcesaAlgoritmo dvCasos = new ProcesaAlgoritmo(csvReader.getHoras(), temp);
            String regTemp = dvCasos.regresionLineal();
            this.regresionTemp = new ExpressionBuilder(regTemp)
                    .variables("X")
                    .build();
            float[] regEvalCasos = evaluateRegretion(regresionTemp, csvReader.getHoras());
            this.lineChart.prepareSerie("Casos(Regresión)", Color.RED, csvReader.getHoras(), regEvalCasos);

            // cluster temp vs humedad
            Vector datos = new Vector();
            Punto p;
            for (int i = 0; i < temp.length; i++) {
                p = new Punto(hum[i], temp[i]);
                datos.add(p);
            }
                ProcesaAlgoritmo analisis = new ProcesaAlgoritmo(datos);
                SimpleKMeans skm = analisis.clustering("Humedad");

                // gráficar cluster y centroide
                Dispersion disp = new Dispersion(datos);
                ChartPanel chart = disp.generarGrafica("Humedad (%)", "Temperatura (°C)", skm);
                this.cent_graph.removeAll();
                this.cent_graph.setLayout(new BorderLayout());
                this.cent_graph.add(chart, BorderLayout.CENTER);
                this.cent_graph.validate();

            // cluster temp vs velocidad viento
            Vector datos1 = new Vector();
            Punto p1 = null;
            for (int i = 0; i < temp.length; i++) {
                p1 = new Punto(vel[i], temp[i]);
                datos1.add(p1);
            }
                ProcesaAlgoritmo analisis1 = new ProcesaAlgoritmo(datos1);
                SimpleKMeans skm1 = analisis1.clustering("Velocidad del viento");

                // gráficar cluster y centroide
                Dispersion disp1 = new Dispersion(datos1);
                ChartPanel chart1 = disp1.generarGrafica("Velocidad del viento (Km/h)", "Temperatura (°C)", skm1);
                this.cent2.removeAll();
                this.cent2.setLayout(new BorderLayout());
                this.cent2.add(chart1, BorderLayout.CENTER);
                this.cent2.validate();

            // Ventana para info de centroides
            CentrFrame = new Centroides(skm, skm1);

            // Ventana para info de ecuación de regresión    
            ecuFrame = new Ecuaciones(regTemp);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        title = new javax.swing.JLabel();
        info_panel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        prediction = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        testing = new javax.swing.JTextField();
        verRegEC = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        Regres = new javax.swing.JTabbedPane();
        graph = new javax.swing.JPanel();
        cenpred = new javax.swing.JPanel();
        cent_graph = new javax.swing.JPanel();
        cent2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Santa Marta");
        setBackground(new java.awt.Color(255, 255, 255));

        title.setFont(new java.awt.Font("Garamond", 1, 24)); // NOI18N
        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title.setText("TEMPERATURA SANTA MARTA");
        title.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        info_panel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), null));
        info_panel.setMaximumSize(new java.awt.Dimension(32767, 100));
        info_panel.setMinimumSize(new java.awt.Dimension(0, 100));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setText("Hora");

        prediction.setEditable(false);
        prediction.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setText("Proyección");

        testing.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        verRegEC.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        verRegEC.setText("Ecuaciones");
        verRegEC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verRegECActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton1.setText("Centroides");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout info_panelLayout = new javax.swing.GroupLayout(info_panel);
        info_panel.setLayout(info_panelLayout);
        info_panelLayout.setHorizontalGroup(
            info_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(info_panelLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(testing, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(prediction, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(info_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(verRegEC, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        info_panelLayout.setVerticalGroup(
            info_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(info_panelLayout.createSequentialGroup()
                .addGroup(info_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(info_panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(verRegEC)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1))
                    .addGroup(info_panelLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(info_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(testing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(prediction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        Regres.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        graph.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout graphLayout = new javax.swing.GroupLayout(graph);
        graph.setLayout(graphLayout);
        graphLayout.setHorizontalGroup(
            graphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        graphLayout.setVerticalGroup(
            graphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)
        );

        Regres.addTab("Grafica", graph);

        javax.swing.GroupLayout cent_graphLayout = new javax.swing.GroupLayout(cent_graph);
        cent_graph.setLayout(cent_graphLayout);
        cent_graphLayout.setHorizontalGroup(
            cent_graphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 926, Short.MAX_VALUE)
        );
        cent_graphLayout.setVerticalGroup(
            cent_graphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 482, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout cenpredLayout = new javax.swing.GroupLayout(cenpred);
        cenpred.setLayout(cenpredLayout);
        cenpredLayout.setHorizontalGroup(
            cenpredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cenpredLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cent_graph, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        cenpredLayout.setVerticalGroup(
            cenpredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cenpredLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cent_graph, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Regres.addTab("Centroide 1", cenpred);

        javax.swing.GroupLayout cent2Layout = new javax.swing.GroupLayout(cent2);
        cent2.setLayout(cent2Layout);
        cent2Layout.setHorizontalGroup(
            cent2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 938, Short.MAX_VALUE)
        );
        cent2Layout.setVerticalGroup(
            cent2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 494, Short.MAX_VALUE)
        );

        Regres.addTab("Centroide 2", cent2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(70, 70, 70))
                    .addComponent(info_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Regres))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(info_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Regres)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void verRegECActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verRegECActionPerformed
        // TODO add your handling code here:
        ecuFrame.setVisible(true);
    }//GEN-LAST:event_verRegECActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        CentrFrame.setVisible(true);
        CentrFrame.setLocationRelativeTo(null);
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {

                    JFrame.setDefaultLookAndFeelDecorated(true);
                    JDialog.setDefaultLookAndFeelDecorated(true);
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
                }
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane Regres;
    private javax.swing.JPanel cenpred;
    private javax.swing.JPanel cent2;
    private javax.swing.JPanel cent_graph;
    private javax.swing.JPanel graph;
    private javax.swing.JPanel info_panel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField prediction;
    private javax.swing.JTextField testing;
    private javax.swing.JLabel title;
    private javax.swing.JButton verRegEC;
    // End of variables declaration//GEN-END:variables
}
