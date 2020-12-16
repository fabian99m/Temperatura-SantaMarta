package Vista;

import weka.clusterers.SimpleKMeans;

/*
 @author Fabb
 */
public class Centroides extends javax.swing.JFrame {

    SimpleKMeans skm, skm1;

    public Centroides(SimpleKMeans skm, SimpleKMeans skm1) {

        initComponents();
        this.skm = skm;
        this.skm1 = skm1;

        if (ht.isSelected()) {
            textcent.setText("" + this.skm);
        } else {
            textcent.setText("" + this.skm1);
        }
    }

    private Centroides() {
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textcent = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        ht = new javax.swing.JRadioButton();
        vt = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel1.setText("Centroides");

        textcent.setEditable(false);
        textcent.setColumns(20);
        textcent.setRows(5);
        jScrollPane1.setViewportView(textcent);

        jButton1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton1.setText("Cerrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(ht);
        ht.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        ht.setSelected(true);
        ht.setText("Humedad-Temperatura");
        ht.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                htActionPerformed(evt);
            }
        });

        buttonGroup1.add(vt);
        vt.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        vt.setText("Velocidad-Temperatura");
        vt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(ht)
                        .addGap(65, 65, 65)
                        .addComponent(vt))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(241, 241, 241)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(254, 254, 254)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ht)
                    .addComponent(vt))
                .addGap(34, 34, 34)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void vtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vtActionPerformed
        textcent.setText("");
        textcent.setText("" + this.skm1);
    }//GEN-LAST:event_vtActionPerformed

    private void htActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_htActionPerformed
        textcent.setText("");
        textcent.setText("" + this.skm);
    }//GEN-LAST:event_htActionPerformed

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton ht;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea textcent;
    private javax.swing.JRadioButton vt;
    // End of variables declaration//GEN-END:variables
}
