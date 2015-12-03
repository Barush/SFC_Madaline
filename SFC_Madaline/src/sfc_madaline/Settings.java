/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfc_madaline;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author barush
 */
public class Settings {
    
    private int adalinesN;
    private int inCnt;
    private int outCnt;
    private String inFile;
    private int dataLen;
    private double[] data;
    private double tolerance;
    
    public Settings(String filename) throws IOException{
        inFile = filename;
        inCnt = 2;
        adalinesN = 2;
        outCnt = 1;
        tolerance = 0.05;
        readFile();
    }
    
    public int getAdaN(){ return adalinesN;}
    public void setAdaN(int n){ adalinesN = n;}
    public int getInCnt(){ return inCnt;}
    public void setInCnt(int cnt){ inCnt = cnt;}
    public int getOutCnt(){ return outCnt;}
    public void setOutCnt(int cnt){ outCnt = cnt;}
    public String getInFile(){ return inFile;}
    public void setInFile(String filename){ inFile = filename;}
    public int getDataLen(){ return dataLen;}
    public void setDataLen(int n){ dataLen = n;}
    public double getTolerance(){ return tolerance;}
    public void setTolerance(double t){ tolerance = t;}
    
    public double getNthInput(int n){
        return data[n];
    }
    
    public void loadSettings(){
        
    }
    
    public int countLines() throws IOException {   
        FileReader fr = new FileReader(inFile);
        BufferedReader textReader = new BufferedReader(fr);
        
        int cnt = 0;
        
        while(textReader.readLine() != null){
            cnt++;
        }
        
        textReader.close();
        return cnt;
    }
    
    public void readFile() throws IOException {
        FileReader fr = new FileReader(inFile);
        BufferedReader textReader = new BufferedReader(fr);
        
        dataLen = countLines();
        int dblsPerLine = inCnt + outCnt;
        String[] textData = new String[dataLen];
        
        //load all lines of file
        for(int i = 0; i < dataLen; i++){
            textData[i] = textReader.readLine();
        }       
        textReader.close();
        
        //cut lines into data array
        data = new double[dataLen*dblsPerLine];  
        for(int i = 0; i < dataLen; i++){
            Scanner sc = new Scanner(textData[i]);
            int j = 0;
            while(sc.hasNextDouble()){
                data[i*dblsPerLine + j] = sc.nextDouble();
                j++;
            }//for all doubles of a line
            
            //validity check 
            if (j != dblsPerLine){
                System.out.println("Na řádku " + i + " nesedí počet I/O se zadáním.");
            } 
            
        }//for all lines     
    }
    
    public void printData(){
        for(int i = 0; i < data.length; i++){
            System.out.println(data[i]);
        }
    }
   
}
