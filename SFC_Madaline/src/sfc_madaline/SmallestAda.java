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
public class SmallestAda {
    private int ind;
    private double val;
    
    public SmallestAda(int i, double v){
        ind = i;
        val = v;
    }
    
    public int getI(){ return ind;}
    public void setI(int i){ ind = i;}
    public double getVal() {return val;}
    public void setVal(double v){ val = v;}
}
