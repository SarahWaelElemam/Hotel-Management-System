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
public class Room implements Serializable{
   private static final long serialVersionUID = 337660697169910223L;
   public enum RoomStatus {
        BOOKED,
        NOT_BOOKED,
        PENDING
    }
   private int roomNum;
   private String category;
   private double price;
   private RoomStatus status;
   private int NumOfAdults;
   private int NumOfChildren; 
   private int NumOfRes;
   private double Revenue;
    public int getNumOfRes() {
        return NumOfRes;
    }
    public void setNumOfRes(int NumOfRes) {
        this.NumOfRes = NumOfRes;
    }
    public double getRevenue() {
        return Revenue;
    }
    public void setRevenue(double Revenue) {
        this.Revenue = Revenue;
    }
    public int getRoomNum() {
        return roomNum;
    }
    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public RoomStatus getStatus() {
        return status;
    }
    public void setStatus(RoomStatus status) {
        this.status = status;
    }
    public int getNumOfAdults() {
        return NumOfAdults;
    }
    public void setNumOfAdults(int NumOfAdults) {
        this.NumOfAdults = NumOfAdults;
    }
    public int getNumOfChildren() {
        return NumOfChildren;
    }
    public void setNumOfChildren(int NumOfChildren) {
        this.NumOfChildren = NumOfChildren;
    }
    public Room(int roomNum,String category,double price){
        this.roomNum=roomNum;
        this.category=category;
        this.price=price;
        this.status = RoomStatus.NOT_BOOKED;
        this.NumOfAdults=0;
        this.NumOfChildren=0;
        this.Revenue=0;
        this.NumOfRes=0;
        this.NumOfRes=0;
        this.Revenue=0.0;
        addToRoomList(this);
    }
     @Override
   public String toString() {
    return String.format("Room Number: %-10d Category: %-15s Status: %-10s Price: $%.2f NumOfAdults: %-5d NumOfChildren: %-5d NumOfRes: %-5d Revenue: $%.2f",
            roomNum, category, status, price, NumOfAdults, NumOfChildren, NumOfRes, Revenue);
}
   public static void saveRoomList(ArrayList<Room> roomList, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(roomList);
            System.out.println("Object saved to " + filePath);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
   public static ArrayList<Room> loadRoomList(String filePath) {
    ArrayList<Room> roomList = new ArrayList<>();
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
        roomList = (ArrayList<Room>) ois.readObject();
        System.out.println("Object loaded from " + filePath);
    } catch (IOException | ClassNotFoundException | ClassCastException e) {
        // Handle the case when the file is empty or does not exist
        System.out.println("No rooms loaded from " + filePath);
    }
    return roomList;
}
  private void addToRoomList(Room room) {
    ArrayList<Room> roomList = loadRoomList("Rooms.bin");
    roomList.add(room);
    saveRoomList(roomList, "Rooms.bin");
}
}
