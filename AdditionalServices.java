/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


/**
 *
 * @author Sarah Wael
 */
public class AdditionalServices implements Serializable{
    private static final long serialVersionUID = 50166542487616L;

    private String additionalSer;
    private int price;
    private int NumOfReq;
    private int Revenue;
    public String getAdditionalSer() {
        return additionalSer;
    }
    public void setAdditionalSer(String additionalSer) {
        this.additionalSer = additionalSer;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getNumOfReq() {
        return NumOfReq;
    }
    public void setNumOfReq(int NumOfReq) {
        this.NumOfReq = NumOfReq;
    }
    public int getRevenue() {
        return Revenue;
    }
    public void setRevenue(int Revenue) {
        this.Revenue = NumOfReq*price;
    }
    
    protected AdditionalServices(String AD,int price) throws IOException{
        additionalSer=AD;
        this.price=price;
        Revenue=0;
        NumOfReq=0;
        addToAS(this);
        }
    @Override
    public String toString() {
     return String.format("Service: %s, Price: %d, Number of Requests: %d, Revenue: %d",
            additionalSer, price, NumOfReq, Revenue);
    }
    public void saveToFile(ArrayList<AdditionalServices> AS,String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
        oos.writeObject(AS);
        System.out.println("Object saved to " + filePath);
    } catch (IOException e) {
        System.out.println(e);
    }
    }
   public static ArrayList<AdditionalServices> loadFromFile(String filePath) {
    ArrayList<AdditionalServices> additionalServicesList = new ArrayList<>();

    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
        additionalServicesList=(ArrayList<AdditionalServices>)ois.readObject();
        System.out.println("Object loaded from " + filePath);
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
        // Handle the case when the file is empty or does not exist
        System.out.println("No Services loaded from " + filePath);
    }

    return additionalServicesList;
} 
   private void addToAS (AdditionalServices as)
   {
       ArrayList<AdditionalServices> AS=loadFromFile("AllServices.bin");
       AS.add(as);
       saveToFile(AS,"AllServices.bin");
   }
    
}
