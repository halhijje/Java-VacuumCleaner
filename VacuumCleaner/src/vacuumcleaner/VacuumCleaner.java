/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vacuumcleaner;

import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Collections.sort;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author hussainal-hijje
 */
public class VacuumCleaner {

    public static void main(String[] args) {
        
     Scanner s=new Scanner(System.in);
     
        
        System.out.println("Welcome with the smart vacuum cleaner");
        System.out.print("Enter Number of Rows: ");
        int Rows = s.nextInt();
        System.out.print("Enter Number of column: ");
        int column = s.nextInt();
        System.out.print("Enter Number of dirty: ");
        int dirty= s.nextInt(); 
        
        
        Environment en = new Environment(Rows, column, dirty);
   
        int count=0;
        
        int ARow = en.getRow();
        int Acolumn = en.getColumn();
        
        ArrayList<Integer> listX=new ArrayList<Integer>();
        ArrayList<Integer> listY=new ArrayList<Integer>();
        
       Iterator itr=listX.iterator();

        for(int i = 0; i<Rows;i++){
           for(int y = 0; y<column;y++){
           if(en.isDirty(i, y)){
               listX.add(i);
               listY.add(y);
           }
          }
           
           i = i+1;
    
           for(int y = column - 1 ; y>=0 && i<Rows;y--){
           if(en.isDirty(i, y)){
               listX.add(i);
               listY.add(y);
           
           }
           }
        }
         System.out.println("---------------------------------");      
         for(int i = 0; i < listX.size(); i++) { 
                System.out.println("Dirty Point is: ("+listX.get(i)+","+listY.get(i)+")");
                count = count + 1;
}
 
         System.out.println("---------------------------------");
         System.out.println(en.toString());
         
         
         for(int i = 0; i < listX.size(); i++) {
             int x = 0;
         while(x == 0){
         if(en.getRow() == listX.get(i) && en.getColumn() == listY.get(i)){ //if the agent stand on the dirty cell then clean.
             System.out.println("is Dirty");
             en.suck();
             System.out.println(en.toString());
             System.out.println("Clean");
             x=x+1;
         }
         
         if(en.getColumn() < listY.get(i)){
             en.moveRight();
             System.out.println(en.toString());
             
         }
         if(en.getColumn() > listY.get(i)){
             en.moveLeft();
             System.out.println(en.toString());
             
         }
         
         if(en.getRow() < listX.get(i)){
             en.moveDown();
             System.out.println(en.toString());
             
         }
         }
         }
         
        System.out.println("The Score is: "+en.getScore());
    }
    
    
}
