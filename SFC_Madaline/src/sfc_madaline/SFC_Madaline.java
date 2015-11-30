/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfc_madaline;

/**
 *
 * @author barush
 */
public class SFC_Madaline {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("SFC_Madaline");
        UI frame = new UI();
        frame.setDefaultCloseOperation(UI.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        
    }
    
}
