/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Sarah Wael
 */
public class Guest extends User implements Serializable{
private static final long serialVersionUID = 8554799732525085769L;
  protected String fname;
  protected String lname;
  protected String email;
  protected int ID ;
  protected String phoNumber;
  protected String AUsername;
  protected String Apass;
  protected Status status;
  protected String Role;

@Override
  public String getFname() {
        return fname;
    }
  public void setFname(String fname) {
        this.fname = fname;
    }
@Override
  public String getLname() {
        return lname;
    }
  public void setLname(String lname) {
        this.lname = lname;
    }
  public String getEmail() {
        return email;
    }
@Override
  public void setEmail(String email) {
        this.email = email;
    }
  public int getID() {
        return ID;
    }
  public void setID(int ID) {
        this.ID = ID;
    }
@Override
  public String getPhoNumber() {
        return phoNumber;
    }
@Override
  public void setPhoNumber(String phoNumber) {
        this.phoNumber = phoNumber;
    }
@Override
  public String getAUsername() {
        return AUsername;
    }
@Override
  public void setAUsername(String AUsername) {
        this.AUsername = AUsername;
    }
@Override
  public String getApass() {
        return Apass;
    }
@Override
  public void setApass(String Apass) {
        this.Apass = Apass;
    }
@Override
  public Status getStatus() {
        return status;
    }
@Override
  public void setStatus(Status status) {
        this.status = status;
    }
  @Override
  public String getRole() {
        return Role;
    }
  public void setRole(String Role) {
        this.Role = Role;
    }
    
    public Guest() {}
    public Guest(String firstName, String lastName, String email, String contactNumber, String role, String username1, String password, int id) {
        fname=firstName;
        lname=lastName;
        ID=id;
        this.email=email;
        phoNumber=contactNumber;
        AUsername=username1;
        Apass=password;
        Role=role;
        status=status.CONFIRMED;
    }
    @Override
    public void signup(String firstName, String lastName, String email, String contactNumber, String role, String username1, String password, int id) throws IOException {
    Guest G1=new Guest(firstName,lastName,email,contactNumber,role,username1,password,id);  
    saveToFile(G1,"Users.bin");
    }
    @Override
public String toString() {
    return "Guest{" +'\n'+
            "First name='" + fname + '\n' +
            ", Last name='" + lname + '\n'+
            ", email='" + email + '\n' +
            ", ID=" + ID +'\n'+
            ", PhoneNumber=" + phoNumber +'\n'+
            ", Username='" + AUsername + '\n' +
            ", Password='" + Apass + '\n' +
            ", Role='" + Role + '\n' +
            ", Status= "+status+
            '}';
}
    @Override
    public void saveToFile(Object user,String filepath) {
        List<Guest> userList = (List<Guest>) loadFromFile(filepath);
        userList.add((Guest) user);
       try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath, false))) {
            oos.writeObject(userList);
            System.out.println("Guest saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving Guest to file: " + e.getMessage());
        }
    } 
protected void bookOnline() throws IOException {
        Reservation res = new Reservation();
        res.setGuestInfo(this);
        boolean result = res.selectCategory();
        if (result) {
            res.selectDate();
            if (res.getRoom1() != null) {
                res.NumOfAdults(res.getRoom1().getCategory().toLowerCase());
                res.NumOfChildren();
                res.setGuestInfo(this);
                fname=getFname();
                lname=getLname();
               res.onlineResNum();
            } else {
                System.out.println("Error: Room not selected.");
            }
        }
    }
protected void rateRes() throws IOException, ClassNotFoundException {
        List<Reservation> reservationList;
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Reservations.bin"));
        Object obj = ois.readObject();
        reservationList = (List<Reservation>) obj;
        if (!reservationList.isEmpty()) {
            System.out.println("List of Reservations:");

            for (Reservation reservation : reservationList) {
                if(reservation.guestInfo.getAUsername().equals(getAUsername())&&reservation.getStatus().equals(Reservation.ResStatus.CHECKED_OUT))
                    System.out.println(reservation);
            }
        } else {
            System.out.println("No reservations found.");
        }
        System.out.println("Enter Number of Reservation you want to rate");
        int x = new Scanner(System.in).nextInt();
        for (Reservation reservation : reservationList) {
            if(reservation.getResNum()==x) {
                System.out.println("Please Rate from 1 to 5");
                int guestRate = new Scanner(System.in).nextInt();
                reservation.setRate(guestRate);
                reservation.saveReservationList(reservationList, "Reservations.bin");
                System.out.println("Rate saved successfully.");
                break;
            }
        }
    }
protected void viewHistory() throws IOException, ClassNotFoundException {
        List<Reservation> reservationList;
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Reservations.bin"));
        Object obj = ois.readObject();
        reservationList = (List<Reservation>) obj;
        if (!reservationList.isEmpty()) {
            System.out.println("List of Reservations:");

            for (Reservation reservation : reservationList) {
                if(reservation.guestInfo.getAUsername().equals(getAUsername())&&reservation.getStatus().equals(Reservation.ResStatus.CHECKED_OUT))
                    System.out.println(reservation);
            }
        } else {
            System.out.println("No reservations found.");
        }
    }
protected void viewres() throws IOException, ClassNotFoundException {
        List<Reservation> reservationList;
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Reservations.bin"));
        Object obj = ois.readObject();
        reservationList = (List<Reservation>) obj;
        if (!reservationList.isEmpty()) {
            System.out.println("List of Reservations:");

            for (Reservation reservation : reservationList) {
                if(reservation.guestInfo.getAUsername().equals(getAUsername())&&reservation.getStatus().equals(Reservation.ResStatus.CHECKED_IN))
                    System.out.println(reservation);
            }
        } else {
            System.out.println("No reservations found.");
        }
    }

    @Override
    protected String fname() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String lname() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String email() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected int ID() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String phoNumber() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String AUsername() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String Apass() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String Role() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected Status status() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
