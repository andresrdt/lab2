/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.networking;
import java.io.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author 2112076
 */
 public class Ejercicios {
   public static void main(String[] args) throws Exception {
   System.out.println("Escriba una url");
   Scanner sc = new Scanner(System.in);
   String link = sc.nextLine();
   URLObject urlO =new URLObject(link);
   URL url =urlO.GetStringUrl();
   
   try (
    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
    String inputLine = null;
    BufferedWriter text= new BufferedWriter(new FileWriter("resultado.html")); 
    while ((inputLine = reader.readLine()) != null) {
     text.write(inputLine);
    }
    reader.close();
    
   } catch (IOException x) {
    System.err.println(x);
   }
  }
  
 }
