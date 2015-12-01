/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfc_madaline;

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
    
    public Network(int inSize, int adaCnt, int outSize){
        inVect = new double[inSize];
        adaVect = new double[adaCnt];
        outVect = new double[outSize];
        
        desired = new double[outSize];
        
        weights1to2 = new double[adaCnt*inSize];
        weights2to3 = new double[outSize*adaCnt];
        
        Arrays.fill(weights1to2, 0.5);
        Arrays.fill(weights2to3, 0.5);
    }
    
    public void loadInput(Settings s, int n){
        int startPos = n*(s.getInCnt() + s.getOutCnt());
        for(int i = 0; i < s.getInCnt(); i++){
            inVect[i] = s.getNthInput(startPos + i);
        }    
    }
    
    public void printInput(){
        for(int i = 0; i < inVect.length; i++){
            System.out.println(inVect[i]);
        }
    }
    
    public void loadDesired(Settings s, int n){
       int startPos = n*(s.getInCnt() + s.getOutCnt()) + s.getInCnt();
       for(int i = 0; i < s.getOutCnt(); i++){
            desired[i] = s.getNthInput(startPos + i);
        }    
    }
    
    public void printDesired(){
        for(int i = 0; i < desired.length; i++){
            System.out.println(desired[i]);
        }
    }
}
