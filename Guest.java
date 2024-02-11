/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.System.exit;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;          
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 *
 * @author Sarah Wael
 */
public class Receptionist extends User implements Serializable {
   private static final long serialVersionUID = 4290697726183404729L;
   protected String fname;
    protected String lname;
    protected String email;
    protected int ID ;
    protected String phoNumber;
    protected String AUsername;
    protected String Apass;
    protected String Role;
    protected Status status;
    protected String ReceptionistName;
    protected int NumOfRes;
    private double revenue;
    public int getNumOfRes() {
        return NumOfRes;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public void setNumOfRes(int NumOfRes) {
        this.NumOfRes = NumOfRes;
    }
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
    public String getRole() {
        return Role;
    }
    public void setRole(String Role) {
        this.Role = Role;
    }
@Override
    public Status getStatus() {
        return status;
    }
@Override
    public void setStatus(Status status) {
        this.status = status;
    }
   public String getReceptionistName() 
    {
        return ReceptionistName;
    }

    public void setReceptionistName(String ReceptionistName) {
        this.ReceptionistName = ReceptionistName;
    }
   public void setRECStatus(Status status) 
    {
        this.status = status;
    }
   public Status getRECStatus() 
    {
        return status;
    }
  public Receptionist(String fname,String lname,String email,String phoNumber,String role,String username,String pass,int ID)
    {
        this.fname=fname;
        this.lname=lname;
        this.ID=ID;
        this.email=email;
        this.phoNumber=phoNumber;
        AUsername=username;
        Apass=pass;
        Role=role;
        status=Status.PENDING;
        revenue=0;
        NumOfRes=0;
   }  
Receptionist() { }
@Override
    public void saveToFile(Object user,String filepath) {
        List<Receptionist> userList = (List<Receptionist>) loadFromFile(filepath);
        userList.add((Receptionist) user);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath, false))) {
            oos.writeObject(userList);
            System.out.println("Receptionist saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving Receptionist to file: " + e.getMessage());
        }
    }
@Override
    public void signup(String firstName, String lastName, String email, String contactNumber, String role, String username1, String password, int id) throws IOException {
      Receptionist REC=new Receptionist(firstName,lastName,email,contactNumber,role,username1,password,id);
      saveToFile(REC,"Pending.bin");
    }
@Override
public String toString() {
    return "{" +"-----------------------------"+'\n'+
            "First name='" + fname + '\n' +
            ", Last name='" + lname + '\n'+
            ", email='" + email + '\n' +
            ", ID=" + ID +'\n'+
            ", PhoneNumber=" + phoNumber +'\n'+
            ", Username='" + AUsername + '\n' +
            ", Password='" + Apass + '\n' +
            ", Role='" + Role + '\n' +
            ", Status= "+status+", Number of reservations: "+NumOfRes+
            '}';
    }
protected void Checkin() throws IOException {
    Scanner scanner = new Scanner(System.in);
    System.out.println("1) New Reservation");
    System.out.println("2) Pending Reservation");
    int choice =scanner.nextInt();
    switch(choice)
    {
        case 1:
           Reservation res = new Reservation();  
          System.out.println("Enter Guest phone Number :");
          scanner.nextLine();
          String phoneNumber = scanner.nextLine();
              res.guestInfo(phoneNumber);
              boolean result = res.selectCategory();
              if (result) 
              {
              res.selectDate();
              if (res.getRoom1() != null) 
              {
              res.NumOfAdults(res.getRoom1().getCategory().toLowerCase());
              res.NumOfChildren();
              fname=getFname();
              lname=getLname();
              res.countResNum(fname,lname);
              System.out.println("Error: Room not selected.");
              }
              } 
            break;
        case 2:
            List<Reservation> reservationList= Reservation.loadReservationList("Reservations.bin");
            if (!reservationList.isEmpty()) {
                System.out.println("List of Reservations:");

                for (Reservation reservation : reservationList) {
                    if(reservation.getStatus().equals(Reservation.ResStatus.PENDING)) {
                        System.out.println(reservation);
                    }
                }
            } else {
                System.out.println("No reservations found.");
            }

            System.out.println("Enter the number of the phone number u booked with");
            String x = new Scanner(System.in).nextLine();
            for(Reservation reservation:reservationList){
                if(reservation.guestInfo.getPhoNumber().equals(x)){
                    fname=getFname();
                    lname=getLname();
                    reservation.countOnlineRes(fname,lname);
                    reservation.saveReservationList(reservationList, "Reservations.bin");
                    System.out.println("Reservation saved successfully.");
                }
            }
    }
} 


public void cancelReservation() throws FileNotFoundException, IOException {
        List<Reservation> reservationList=Reservation.loadReservationList("Reservations.bin");
        if (!reservationList.isEmpty()) {
            System.out.println("List of Reservations:");

            for (Reservation reservation : reservationList) {
                if(reservation.getStatus().equals(Reservation.ResStatus.PENDING)){
                    System.out.println(reservation);
                }

            }
        } else {
            System.out.println("No reservations found.");
        }

          System.out.println("Enter the number of the Phone you booked with:");
            String x = new Scanner(System.in).nextLine();
            Reservation ress = new Reservation();  
            ress.guestInfo(x);
            
        for (Reservation reservation : reservationList) {
            if (reservation.guestInfo.getPhoNumber() != null && reservation.guestInfo.getPhoNumber().equals(x)&&reservation.getStatus().equals(Reservation.ResStatus.PENDING)) {
                reservation.cancellation(fname,lname);
                System.out.println("Reservation Canceled successfully.");
            }
        }
        
    }
public void displayRecycleBinContents() {
        List<Reservation> recycleBinList = Reservation.loadReservationList("RecycleBin.bin");

        if (!recycleBinList.isEmpty()) {
            System.out.println("Contents of Recycle Bin:");
            for (Reservation reservation : recycleBinList) {
                System.out.println(reservation);
            }
        } else {
            System.out.println("Recycle Bin is empty.");
        }
    }
public void checkout() throws FileNotFoundException, IOException, ClassNotFoundException {
        List<Reservation> reservationList=Reservation.loadReservationList("Reservations.bin");
        if (!reservationList.isEmpty()) {
            System.out.println("List of Reservations:");
            for (Reservation reservation : reservationList) {
                if (reservation.getStatus().equals(Reservation.ResStatus.CHECKED_IN)){
                       System.out.println(reservation);
                }
            }
        } else {
            System.out.println("No reservations found.");
        }

        System.out.println("Enter the number of the room number u want checkout");
        Scanner scanner=new Scanner(System.in);
        int selectedRoomNum = Integer.parseInt(scanner.nextLine());
        for (Reservation reservation : reservationList) {
            if (reservation.room1.getRoomNum() == selectedRoomNum) {
                {
                reservation.setStatus(Reservation.ResStatus.CHECKED_OUT);
                reservation.room1.setStatus(Room.RoomStatus.NOT_BOOKED);
                reservation.room1.setNumOfAdults(0);
                reservation.room1.setNumOfChildren(0);
                reservation.saveReservationList(reservationList, "Reservations.bin");
                }
        ArrayList<Room> roomList = Room.loadRoomList("Rooms.bin");
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).getRoomNum() == reservation.room1.getRoomNum()) {
                roomList.set(i, reservation.room1);
                break;
            }
        }
        Room.saveRoomList(roomList, "Rooms.bin");
                System.out.println("Reservation saved successfully.");
                break;
            }
        }
    }
  public void addAdditionalService() throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        List<Reservation> reservationList=Reservation.loadReservationList("Reservations.bin");
        List<AdditionalServices> ASList=AdditionalServices.loadFromFile("AllServices.bin");
        System.out.print("Enter the Room Number: ");
        int selectedRoomNum;
        while (true) {
            try {
                selectedRoomNum = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for the Room Number.");
            }
        }
        System.out.println("Write Additional Service Name you want to add");
        for (AdditionalServices additionalServices : ASList) {
            System.out.println("Service: " + additionalServices.getAdditionalSer()+ "\tPrice: " + additionalServices.getPrice());
        }
        String chosenService = scanner.nextLine().toLowerCase();
        System.out.println("How many?");
        int serviceNum = Integer.parseInt(scanner.nextLine());
        for (Reservation reservation : reservationList) {
        if (reservation.room1.getRoomNum() == selectedRoomNum) {

           HashMap<AdditionalServices, Integer> currentAS = reservation.getAS();
            if (currentAS == null) {
                currentAS = new HashMap<>();
            }
            boolean containsService = false;
            for (Map.Entry<AdditionalServices, Integer> entry : currentAS.entrySet()) {
                if (entry.getKey().getAdditionalSer().equalsIgnoreCase(chosenService)) {
                    containsService = true;
                    entry.setValue(entry.getValue() + serviceNum);
                    AdditionalServices additionalService = entry.getKey();
                    int num = additionalService.getNumOfReq();
                    additionalService.setNumOfReq(num + serviceNum);
                    additionalService.setRevenue(additionalService.getPrice() * additionalService.getNumOfReq());
                    additionalService.saveToFile((ArrayList<AdditionalServices>) ASList, "AllServices.bin");
                    break;
                }
            }
            if (!containsService) {
                for (AdditionalServices additionalService : ASList) {
                    if (additionalService.getAdditionalSer().equalsIgnoreCase(chosenService)) {
                        currentAS.put(additionalService, serviceNum);
                        int num = additionalService.getNumOfReq();
                        additionalService.setNumOfReq(num + serviceNum);
                        additionalService.setRevenue(additionalService.getPrice() * additionalService.getNumOfReq());
                        additionalService.saveToFile((ArrayList<AdditionalServices>) ASList, "AllServices.bin");
                        break;
                    }
                }
            }
            reservation.setAS(currentAS);
            reservation.totalResCost();
            reservation.saveReservationList(reservationList, "Reservations.bin");
            System.out.println("Additional Service added successfully.");
            break;
            }
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
