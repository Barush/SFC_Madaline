/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfc_madaline;

import static java.lang.Math.abs;
import java.util.Arrays;

/**
 *
 * @author barush
 */
public class Network {
    
    private double[] inVect;
    private double[] adaVect;    
    private double[] outVect;
    
    private double[] desired;
    
    private double[] weights1to2;
    private double[] weights2to3;
    
    private double error;
    
    public Network(int inSize, int adaCnt, int outSize){
        inVect = new double[inSize];
        adaVect = new double[adaCnt];
        outVect = new double[outSize];
        
        desired = new double[outSize];
        
        weights1to2 = new double[adaCnt*inSize];
        weights2to3 = new double[outSize*adaCnt];
        
        Arrays.fill(weights1to2, 0.5);
        Arrays.fill(weights2to3, 0.3);
    }
    
    public void loadInput(Settings s, int n){
        int startPos = n*(s.getInCnt() + s.getOutCnt());
        for(int i = 0; i < s.getInCnt(); i++){
            inVect[i] = s.getNthInput(startPos + i);
        }    
    }
    
    public void loadDesired(Settings s, int n){
       int startPos = n*(s.getInCnt() + s.getOutCnt()) + s.getInCnt();
       for(int i = 0; i < s.getOutCnt(); i++){
            desired[i] = s.getNthInput(startPos + i);
        }    
    }

    public void forwardPass(){
        countAda();
        countOutput();
        printVector(outVect);
    }
    
    public void countAda(){
        double val;
        for(int i = 0; i < adaVect.length; i++){
            val = 0;
            for(int j = 0; j < inVect.length; j++){
                //System.out.println(weights1to2[i*inVect.length + j] + " * " + inVect[j]);
                val += weights1to2[i*inVect.length + j] * inVect[j];
            }//for all prev neurons
            adaVect[i] = val;
        }//for all adalines      
    }
    
    public void countOutput(){
        double val;
        for(int i = 0; i < outVect.length; i++){
            val = 0;
            for(int j = 0; j < adaVect.length; j++){
                val += weights2to3[i*adaVect.length + j] * adaVect[j];
            }//for all prev neurons
            outVect[i] = val;
        }//for all adalines    
    }
    
    public Boolean isItLearned(Settings s){
        error = 0.0;
        for(int i = 0; i < outVect.length; i++){
            System.out.println("Value " + outVect[i] + " compared to " + desired[i]);
            error += abs(outVect[i] - desired[i]);
        }
        
        if(error < s.getTolerance()){
            return true;
        }
        return false;
    }
    
    public void backwardPass(){
        //seradit - do druhyho pole
        double[] tmpAda = new double[adaVect.length];
        System.arraycopy(adaVect, 0, tmpAda, 0, adaVect.length);
        
        for(int i = 0; i < adaVect.length; i++){
            //vzit nejmensi

            //zmenit znaminko

            //vypocitat vystup

            //je lepsi?ponechat do hlavniho pole:jit na dalsi
        }
        
        
    }
    
    public void printVector( double[] vect){
        for(int i = 0; i < vect.length; i++){
            System.out.println(vect[i]);
        }
    }
    
}
