/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfc_madaline;

import static java.lang.Math.abs;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author barush
 */
public class Network {
    
    private double[] inVect;
    private double[] adaU;    
    private double[] adaOut;
    private double[] outVect;
    
    private double[] desired;
    
    private double[] weights;
    private Boolean[] functions;    // TRUE = AND, FALSE = OR
    
    private double error;
    
    public Network(int inSize, int adaCnt, int outSize){
        inVect = new double[inSize];
        adaU = new double[adaCnt];
        adaOut = new double[adaCnt];
        outVect = new double[outSize];
        
        desired = new double[outSize];
        
        weights = new double[adaCnt*inSize];
        functions = new Boolean[outSize];
        
        for(int i = 0; i < adaCnt; i++){
             weights[i] = randomDbl();
        }
        
        for(int i = 0; i < outSize; i++){
            functions[i] = randomBool();
        }
        //printWeights();
    }
    
    public double randomDbl(){
        Random r = new Random(System.currentTimeMillis());
        return r.nextDouble();
    }
    
    public Boolean randomBool(){
        Random r = new Random(System.currentTimeMillis());
        return r.nextBoolean();
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
    
    public Boolean isItLearned(Settings s){
        error = countError();
        
        if(error < s.getTolerance()){
            return true;
        }
        return false;
    }
    
    public void backwardPass(Settings s){
        //seradit - do druhyho pole
        double[] tmpAda = new double[adaU.length];
        System.arraycopy(adaU, 0, tmpAda, 0, adaU.length);
        double thresh = 1.0;
        double min = 1.5;
        double prevmin = 0.0;
        double value;
        int mini = 0;
        
        for(int i = 0; i < adaU.length; i++){
            //najit nejmensi u
            for(int j = 0; j < adaU.length; j++){
                value = abs(adaU[j]);
                if((value < min) && (value < thresh) && (value > prevmin)){
                    min = value;
                    mini = j;
                }
            }
            
            //zmenit znaminko
            adaOut[mini] *= (-1);

            //vypocitat vystup
            countOutput();
            double newError = countError();

            //pokud se vystup zlepsil, pridat zmenu do vahove matice
            if(newError < error){
                System.out.println("Vysledek " + newError + " je lepsi nez " + error);
                weights[mini*inVect.length] *= (-1);
                error = newError;
            }
            
            if(newError < s.getTolerance()){
                break;
            }
            prevmin = min;
        }
        
        
    }
    
    public void countAda(){
        double val;
        for(int i = 0; i < adaU.length; i++){
            val = 0;
            for(int j = 0; j < inVect.length; j++){
                //System.out.println(weights1to2[i*inVect.length + j] + " * " + inVect[j]);
                val += weights[i*inVect.length + j] * inVect[j];
            }//for all prev neurons
            adaU[i] = val;
            adaOut[i] = Math.signum(val);
        }//for all adalines      
    }
    
    public void countOutput(){
        int cnt;
        for(int i = 0; i < outVect.length; i++){
            cnt = 0;
            for(int j = 0; j < adaOut.length; j++){
                if(adaOut[j] > 0.0){
                    cnt++;
                }
            }//for all prev neurons
                        
            if(functions[i]){
                //AND func
                if(cnt == adaOut.length){
                    outVect[i] = 1.0;
                }
                else{
                    outVect[i] = -1.0;
                }
            }
            else{
                //OR func
                if(cnt > 0){
                    outVect[i] = 1.0;
                }
                else{
                    outVect[i] = -1.0;
                }            
            }
            
        }//for all outputs    
    }
    
    public double countError(){
        double err = 0.0;
        for(int i = 0; i < outVect.length; i++){
               //System.out.println("Value " + outVect[i] + " compared to " + desired[i]);
               err += abs(outVect[i] - desired[i]);
               //System.out.println("err += " + abs(outVect[i] - desired[i]));
        }
        
        return err;
    }
   
    
    public void printVector( double[] vect){
        for(int i = 0; i < vect.length; i++){
            System.out.print(vect[i] + " ");
        }
        System.out.println();
    }
    
    public void printWeights(){       
        System.out.println("Weights value: ");
        for(int i = 0; i < weights.length; i++){
            if(i < weights.length){
                System.out.println(weights[i] + " ");
            }
        }
    }
    
}
