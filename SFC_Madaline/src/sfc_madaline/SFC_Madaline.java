/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfc_madaline;

import java.io.IOException;

/**
 *
 * @author barush
 */
public class SFC_Madaline {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        System.out.println("SFC_Madaline");
       /* UI frame = new UI();
        frame.setDefaultCloseOperation(UI.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        */
        
        Settings params = new Settings("data.txt");
        Network n = new Network(params.getInCnt(), params.getAdaN(), params.getOutCnt());
        for(int i = 0; i < params.getDataLen(); i++){
            System.out.println("****** Input vector " + i + " **********");
            //load input
            n.loadInput(params, i);
            n.loadDesired(params, i);
            
            //count values
            n.forwardPass();
            
            //decide if learned already
            if(n.isItLearned(params)){
                System.out.println("vector" + i + " OK.");
                continue;
            }
            else{
                System.out.println("vector " + i + " needs help.");
            }
            
            //learn
            n.backwardPass();
        }
    }
    
}
