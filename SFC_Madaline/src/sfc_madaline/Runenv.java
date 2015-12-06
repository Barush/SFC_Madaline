/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfc_madaline;

import java.util.ArrayList;

/**
 *
 * @author barush
 */
public class Runenv extends javax.swing.JFrame {

    /**
     * Creates new form runenv
     */
    public Runenv(Settings set) {
        initComponents(); 
        this.frame = new javax.swing.JFrame();
        this.adas = new ArrayList<>();
        s = set;
        step = 0;
        ind = 0;
        correct = 0;
        learned = false;
        n = null;
        this.setTitle("Demonstrace učení Madaline I - SFC - B. Skřivánková");
    }
    
    public void nextStep(){
        int i;
        System.out.println("FA state: " + step);
        if(!learned){
            switch (step){

                case 0: //initiate network
                    n = new Network(s);
                    step = 1;
                    state.setText("Inicializace sítě.");
                    i = 0;
                    break;
                
                case 1: //load input
                    System.out.println("****** Input vector " + ind + " **********");
                    n.loadInput(ind);
                    n.loadDesired(ind);
                    state.setText("Načtení vstupu " + ind);
                    i0.setText(Double.toString(n.getInput(0)));
                    i1.setText(Double.toString(n.getInput(1)));
                    desired.setText(Double.toString(n.getDesired()));
                    ada0.setText("");
                    ada1.setText("");
                    ada2.setText("");
                    ada3.setText("");
                    outVal.setText("");
                    step = 2;
                    countedAda = 0;
                    break;
                    
                case 2: //count values
                    n.countAda();
                    state.setText("u = sum(xi * wi)");
                    ada0.setText(String.format("%.4f", n.getAda(0)));
                    ada1.setText(String.format("%.4f", n.getAda(1)));
                    ada2.setText(String.format("%.4f", n.getAda(2)));
                    ada3.setText(String.format("%.4f", n.getAda(3)));
                    step = 3;
                    break;
                
                case 3: //print ada outputs
                    n.countAdaOut();
                    state.setText("out = sign(u)");
                    ada0.setText(Double.toString(n.getAdaOut(0)));
                    ada1.setText(Double.toString(n.getAdaOut(1)));
                    ada2.setText(Double.toString(n.getAdaOut(2)));
                    ada3.setText(Double.toString(n.getAdaOut(3)));
                    step = 4;
                    break;
                    
                case 4: //count output
                    n.countOutput();
                    state.setText("output = OR/AND_i=0to4(adai)");
                    outVal.setText(Double.toString(n.getOut(0)));
                    step = 5;
                    break;
                
                case 5: //check if output is correct
                    if(n.isItOK()){
                        state.setText(Double.toString(n.getOut(0)) + " = " + Double.toString(n.getDesired()));
                        correct++;
                        if(countedAda > 0){
                            n.setWeight(prevind, n.getWeight(prevind)*(-1));
                        }
                        step = 9;
                    }
                    else if(countedAda == s.getAdaN()){
                        state.setText("Vyzkoušena změna znamínka u všech adalin.");
                        ind++;
                        step = 1;
                    }
                    else{
                        if(countedAda > 0){
                            n.setAdaOut(prevind, prevmin);
                        }
                        state.setText(Double.toString(n.getOut(0)) + " != " + Double.toString(n.getDesired()));
                        correct = 0;
                        step = 6;
                    }
                    break;
                    
                case 6: // select smallest 
                    min = n.findSmallestAda(prevmin, prevind);
                    state.setText("Adaline s nejmenším u je " + String.format("%.4f", min.getVal()));
                    ada0.setText(String.format("%.4f", n.getAda(0)));
                    ada1.setText(String.format("%.4f", n.getAda(1)));
                    ada2.setText(String.format("%.4f", n.getAda(2)));
                    ada3.setText(String.format("%.4f", n.getAda(3)));
                    prevmin = min.getVal();
                    prevind = min.getI();
                    countedAda++;
                    step = 7;
                    break;
                 
                case 7: //change value
                    n.setAdaI(min.getI(), min.getVal()*(-1));
                    state.setText("Znamínko nejmenší adaline změněno");
                    ada0.setText(String.format("%.4f", n.getAda(0)));
                    ada1.setText(String.format("%.4f", n.getAda(1)));
                    ada2.setText(String.format("%.4f", n.getAda(2)));
                    ada3.setText(String.format("%.4f", n.getAda(3)));
                    n.countAdaOut();
                    step = 8;
                    break;
                
                case 8: //recount output
                    n.countOutput();
                    state.setText("Vypočten nový výstup");
                    outVal.setText(Double.toString(n.getOut(0)));
                    ada0.setText(String.format("%.4f", n.getAdaOut(0)));
                    ada1.setText(String.format("%.4f", n.getAdaOut(1)));
                    ada2.setText(String.format("%.4f", n.getAdaOut(2)));
                    ada3.setText(String.format("%.4f", n.getAdaOut(3)));
                    step = 5;
                    break;
                
                case 9: //check if learned already 
                    ind++;
                    if(ind == s.getDataLen()){
                        ind = 0;
                    }
                    if(correct == s.getDataLen()){
                        state.setText("Síť dala korektní výstupy pro všechny vstupy.");
                        learned = true;
                        break;
                    }
                    state.setText("Síť dala korektní vstupy pro " + (double)correct/s.getDataLen()*100 + "% vstupů.");
                    step = 1;
                    break;
            }
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ada0Scroll = new javax.swing.JScrollPane();
        ada0 = new javax.swing.JTextPane();
        ada1Scroll = new javax.swing.JScrollPane();
        ada1 = new javax.swing.JTextPane();
        ada2Scroll = new javax.swing.JScrollPane();
        ada2 = new javax.swing.JTextPane();
        ada3Scroll = new javax.swing.JScrollPane();
        ada3 = new javax.swing.JTextPane();
        outFuncScroll = new javax.swing.JScrollPane();
        outFunc = new javax.swing.JTextPane();
        outValScroll = new javax.swing.JScrollPane();
        outVal = new javax.swing.JTextPane();
        desiredScroll = new javax.swing.JScrollPane();
        desired = new javax.swing.JTextPane();
        desLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        i0 = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        i1 = new javax.swing.JTextPane();
        jButton1 = new javax.swing.JButton();
        state = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ada0Scroll.setViewportView(ada0);

        ada1Scroll.setViewportView(ada1);

        ada2Scroll.setViewportView(ada2);

        ada3Scroll.setViewportView(ada3);

        outFuncScroll.setViewportView(outFunc);

        outValScroll.setViewportView(outVal);

        desiredScroll.setViewportView(desired);

        desLabel.setText("Očekávaný výstup");

        jScrollPane3.setViewportView(i0);

        jScrollPane4.setViewportView(i1);

        jButton1.setText("KROK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        state.setText("Status");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(outFuncScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(outValScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78))
            .addGroup(layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                        .addComponent(jScrollPane3))
                    .addComponent(state))
                .addGap(81, 81, 81)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(ada2Scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                            .addComponent(ada1Scroll)
                            .addComponent(ada0Scroll)
                            .addComponent(ada3Scroll, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(120, 120, 120)
                        .addComponent(desLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(desiredScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(desLabel)
                                    .addComponent(desiredScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(49, 49, 49))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ada0Scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ada1Scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(outFuncScroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(outValScroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ada2Scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(ada3Scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(state)
                    .addComponent(jButton1))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        nextStep();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(Runenv.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Runenv.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Runenv.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Runenv.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane ada0;
    private javax.swing.JScrollPane ada0Scroll;
    private javax.swing.JTextPane ada1;
    private javax.swing.JScrollPane ada1Scroll;
    private javax.swing.JTextPane ada2;
    private javax.swing.JScrollPane ada2Scroll;
    private javax.swing.JTextPane ada3;
    private javax.swing.JScrollPane ada3Scroll;
    private javax.swing.JLabel desLabel;
    private javax.swing.JTextPane desired;
    private javax.swing.JScrollPane desiredScroll;
    private javax.swing.JTextPane i0;
    private javax.swing.JTextPane i1;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextPane outFunc;
    private javax.swing.JScrollPane outFuncScroll;
    private javax.swing.JTextPane outVal;
    private javax.swing.JScrollPane outValScroll;
    private javax.swing.JLabel state;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JFrame frame;
    private ArrayList<javax.swing.JTextArea> adas;
    private Settings s;
    private int step;
    private int ind;
    private int correct;
    private Boolean learned;
    private Network n;
    private SmallestAda min;
    private int countedAda;
    private double prevmin;
    private int prevind;
}
