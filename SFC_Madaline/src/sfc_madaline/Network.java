/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfc_madaline;

import static java.lang.Math.abs;
import java.util.Arrays;
import java.util.Random;
import static java.lang.Math.abs;

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
    private Boolean[] functions;    // TRUE = AND, FALSE = O   
    private double error;
    private Settings set;
    
    public Network(Settings s){
        inVect = new double[s.getInCnt()];
        adaU = new double[s.getAdaN()];
        adaOut = new double[s.getAdaN()];
        outVect = new double[s.getOutCnt()];       
        desired = new double[s.getOutCnt()];      
        weights = new double[s.getAdaN()*s.getInCnt()];
        functions = new Boolean[s.getOutCnt()];
        set = s;
        
        Random r = new Random(System.currentTimeMillis());
        for(int i = 0; i < (s.getAdaN()*s.getInCnt()); i++){
             weights[i] = randomDbl(r);
        }
        
        for(int i = 0; i < s.getOutCnt(); i++){
            functions[i] = randomBool();
        }
        printWeights();
    }
    
    public double getInput(int i){
        return inVect[i];
    }
    
    public double getDesired(){
        return desired[0];
    }
    
    public double getAda(int i){
        return adaU[i];
    }
    
    public void setAdaI(int i, double val){
        adaU[i] = val;
    }
    
    public double getAdaOut(int i){
        return adaOut[i];
    }
    
    public void setAdaOut(int i, double val){
        weights[i] = val;
    }
    
    public double getOut(int i){
        return outVect[i];
    }
    
    public void setWeight(int i, double val){
        weights[i] = val;
    }
    
    public double getWeight(int i){
        return weights[i];
    }
    
    public double randomDbl(Random r){
        return r.nextDouble();
    }
    
    public Boolean randomBool(){
        Random r = new Random(System.currentTimeMillis());
        return r.nextBoolean();
    }
    
    public void loadInput(int n){
        int startPos = n*(set.getInCnt() + set.getOutCnt());
        for(int i = 0; i < set.getInCnt(); i++){
            inVect[i] = set.getNthInput(startPos + i);
        }    
    }
    
    public void loadDesired(int n){
       int startPos = n*(set.getInCnt() + set.getOutCnt()) + set.getInCnt();
       for(int i = 0; i < set.getOutCnt(); i++){
            desired[i] = set.getNthInput(startPos + i);
        }    
    }

    public void forwardPass(){
        countAda();
        countOutput();
        printVector(outVect);
    }  
    
    public Boolean isItOK(){
        error = countError();
        
        if(error < set.getTolerance()){
            return true;
        }
        return false;
    }
    
    public SmallestAda findSmallestAda(double prevmin, int prevind){
        SmallestAda result = new SmallestAda(-1, 15);
        double value;
        double thresh = 1.0;
        
        for(int j = 0; j < adaU.length; j++){
            value = abs(adaU[j]);
            if((value <= result.getVal()) && (value < thresh) && (value >= prevmin) && (j != prevind)){
                result.setVal(value);
                result.setI(j);
            }
        }
        return result;
    }
    
    public void backwardPass(){
        //seradit - do druhyho pole
        double[] tmpAda = new double[adaU.length];
        System.arraycopy(adaU, 0, tmpAda, 0, adaU.length);
        double prevmin = 0.0;
        int prevind = -1;
        SmallestAda min;
        
        for(int i = 0; i < adaU.length; i++){
            //najit nejmensi u
            min = findSmallestAda(prevmin, prevind);
            
            //zmenit znaminko
            adaOut[min.getI()] *= (-1);

            //vypocitat vystup
            countOutput();
            double newError = countError();

            //pokud se vystup zlepsil, pridat zmenu do vahove matice
            if(newError < error){
                System.out.println("Vysledek " + newError + " je lepsi nez " + error);
                weights[min.getI()*inVect.length] *= (-1);
                error = newError;
            }
            
            if(newError < set.getTolerance()){
                break;
            }
            prevmin = min.getVal();
            prevind = min.getI();
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
        }//for all adalines      
    }
    
    public void countAdaOut(){
        for(int i = 0; i < adaOut.length; i++){
            adaOut[i] = Math.signum(adaU[i]);
        }
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
   
    
    public void printVector(double[] vect){
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
