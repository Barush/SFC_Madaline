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
public class Settings {
    
    private int adalinesN;
    private int andorN;
    private int inCnt;
    private int outCnt;
    private String inFile;
    
    public Settings(String filename){
        inFile = filename;
    }
    
    public int getAdaN(){ return adalinesN;}
    public void setAdaN(int n){ adalinesN = n;}
    public int getAndOrN(){ return andorN;}
    public void setAndOrN(int n){ andorN = n;}
    public int getInCnt(){ return inCnt;}
    public void setInCnt(int cnt){ inCnt = cnt;}
    public int getOutCnt(){ return outCnt;}
    public void setOutCnt(int cnt){ outCnt = cnt;}
    public String getInFile(){ return inFile;}
    public void setInFile(String filename){ inFile = filename;}
    
    public void loadSettings(){
        
    }
}
