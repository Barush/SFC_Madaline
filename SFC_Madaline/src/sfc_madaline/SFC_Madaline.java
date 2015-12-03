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
        int ind = 0;
        for(int i = 0; i < 5*params.getDataLen(); i++){
            if(ind == params.getDataLen()){
                ind = 0;
            }
            System.out.println("****** Input vector " + ind + " **********");
            //load input
            n.loadInput(params, ind);
            n.loadDesired(params, ind);
            
            //count values
            n.forwardPass();
            
            //decide if learned already
            if(n.isItLearned(params)){
                System.out.println("vector " + ind + " OK.");
                ind++;
                continue;
            }
            else{
                System.out.println("vector " + ind + " needs help.");
            }
            
            //learn
            n.backwardPass(params);
            ind++;
        }
    }
    
}
