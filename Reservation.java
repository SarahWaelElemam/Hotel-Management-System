/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

//elly n3mlo rate yetshal
//el rate ysm3
package user;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import user.User;
/**
 *
 * @author Sarah Wael
 */
public class Reservation implements Serializable{
    
public enum ResStatus{
        PENDING,
        CHECKED_IN,
        CHECKED_OUT,
        CANCELLED
    }

   private static final long serialVersionUID = 1L;  
   public int ResNum;
   public LocalDate checkInDate;
   public LocalDate checkOutDate;
   public Room room1;
   public long totalDays;
   private ResStatus status;
   protected Guest guestInfo;
   protected double roomcost;
   protected String RecName;
   protected int rate;
   protected double totalcost;
   protected HashMap<AdditionalServices, Integer> AS;

    public HashMap<AdditionalServices, Integer> getAS() {
        return AS;
    }

    public void setAS(HashMap<AdditionalServices, Integer> AS) {
        this.AS = AS;
    }


    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public ResStatus getStatus() {
        return status;
    }

    public void setStatus(ResStatus status) {
        this.status = status;
    }

    public int getResNum() {
        return ResNum;
    }

    public void setResNum(int ResNum) {
        this.ResNum = ResNum;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public long getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(long totalDays) {
        this.totalDays = totalDays;
    }
    public Room getRoom1() {
        return room1;
    }
    protected boolean selectCategory() {
    boolean valid = false;
    Scanner scanner = new Scanner(System.in);
    System.out.println("Available Categories: single, double, triple, vip, suite");
    System.out.print("Choose a category: ");
    String chosenCategory = scanner.nextLine().toLowerCase(); 
    ArrayList<Room> availableRooms = getAvailableRooms(chosenCategory);
    if (availableRooms.isEmpty()) {
        System.out.println("No available rooms in the selected category.");
        return false;
    }
    System.out.println("Available Rooms in Category " + chosenCategory + ":");
    for (Room room : availableRooms) {
        System.out.println(room);
    }
    System.out.print("Enter the Room Number to book: ");
    int selectedRoomNum;
    while (true) {
        try {
            selectedRoomNum = Integer.parseInt(scanner.nextLine());
            break; 
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid integer for the Room Number.");
        }
    }

    Room selectedRoom = null;
    for (Room room : availableRooms) {
        if (room.getRoomNum() == selectedRoomNum) {
            selectedRoom = room;
            break;
        }
    }
    
    if (selectedRoom != null) {
       valid=true;
// Update the entire room list with the modified room
room1=selectedRoom;

    } else {
        System.out.println("Invalid room selection. Room not booked.");
    }
    return valid;
    }
    private ArrayList<Room> getAvailableRooms(String category) {
        ArrayList<Room> availableRooms = new ArrayList<>();
        ArrayList<Room> roomList = Room.loadRoomList("Rooms.bin");

        for (Room room : roomList) {
            if (room.getCategory().equalsIgnoreCase(category) && room.getStatus() == Room.RoomStatus.NOT_BOOKED) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }
    protected void selectDate() {
        Scanner scanner = new Scanner(System.in);
        LocalDate currentDate = LocalDate.now();

        // Prompt the user for the check-in date
        do {
            System.out.print("Enter the check-in date (YYYY-MM-DD): ");
            String checkInDateString = scanner.nextLine();

            try {
                checkInDate = LocalDate.parse(checkInDateString, DateTimeFormatter.ISO_DATE);

                if (checkInDate.isBefore(currentDate)) {
                    System.out.println("Invalid check-in date. Please enter a date on or after the current date.");
                }
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter the date in the format YYYY-MM-DD.");
                checkInDate = null; // Reset checkInDate to null in case of parsing errors
            }
        } while (checkInDate == null || checkInDate.isBefore(currentDate));

        // Prompt the user for the check-out date
        do {
            System.out.print("Enter the check-out date (YYYY-MM-DD): ");
            String checkOutDateString = scanner.nextLine();

            try {
                checkOutDate = LocalDate.parse(checkOutDateString, DateTimeFormatter.ISO_DATE);

                if (checkOutDate.isBefore(checkInDate)) {
                    System.out.println("Invalid check-out date. Please enter a date on or after the check-in date.");
                }
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter the date in the format YYYY-MM-DD.");
                checkOutDate = null; // Reset checkOutDate to null in case of parsing errors
            }
        } while (checkOutDate == null || checkOutDate.isBefore(checkInDate));

        // Calculate the total number of days
        totalDays = java.time.temporal.ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }  
    public void NumOfAdults(String category) {
        Scanner scanner = new Scanner(System.in);

        int maxAdults;

        switch (category.toLowerCase()) {
            case "single":
                maxAdults = 1;
                break;
            case "double":
                maxAdults = 2;
                break;
            case "triple":
                maxAdults = 3;
                break;
            case "vip":
                maxAdults = 4;
                break;
            case "suite":
                maxAdults = 5;
                break;
            default:
                System.out.println("Invalid room category.");
                return;
        }

        int numOfAdults;

        do {
            System.out.print("Enter the number of adults (up to " + maxAdults + "): ");
            try {
                numOfAdults = Integer.parseInt(scanner.nextLine());
                if (numOfAdults <= 0 || numOfAdults > maxAdults) {
                    System.out.println("Invalid number of adults. Please enter a valid number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                numOfAdults = -1; // Reset to trigger re-entry into the loop
            }
        } while (numOfAdults <= 0 || numOfAdults > maxAdults);

                System.out.println("Number of Adults: " + numOfAdults);
                room1.setNumOfAdults(numOfAdults);

    }

    public Guest getGuestInfo() {
        return guestInfo;
    }

    public void setGuestInfo(Guest guestInfo) {
        this.guestInfo = guestInfo;
    }

    public double getRoomcost() {
        return roomcost;
    }

    public void setRoomcost(double roomcost) {
        this.roomcost = roomcost;
    }

    public String getRecName() {
        return RecName;
    }

    public void setRecName(String RecName) {
        this.RecName = RecName;
    }
   void NumOfChildren() {
        Scanner scanner = new Scanner(System.in);

        int maxChildren = 0;

        switch (room1.getCategory().toLowerCase()) {
            case "single":
                maxChildren = 1;
                break;
            case "double":
                maxChildren = 2;
                break;
            case "triple":
                maxChildren = 3;
                break;
            case "vip":
                maxChildren = 4;
                break;
            case "suite":
                maxChildren = 5;
                break;
            default:
                System.out.println("Invalid room category.");
                return;
        }

        System.out.print("Enter the number of children (up to " + maxChildren + "): ");
        int numOfChildren;

        while (true) {
            try {
                numOfChildren = Integer.parseInt(scanner.nextLine());
                if (numOfChildren <= maxChildren) {
                    break;
                } else {
                    System.out.println("Exceeded the maximum number of children allowed for this room category. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }

        System.out.println("Number of Children: " + numOfChildren);
        room1.setNumOfChildren(numOfChildren);
    }
   public double Cost() throws IOException {
        roomcost = room1.getPrice() * totalDays;
       System.out.println("Room Cost: $" + roomcost);
        return roomcost;
    }
   
    public void countResNum (String fname,String lname)
   {
      try {
          Cost();
          room1.setStatus(Room.RoomStatus.BOOKED);
          double cost=room1.getRevenue();
          room1.setRevenue(cost+roomcost);
          int num = room1.getNumOfRes();
          room1.setNumOfRes(num+1);
          ArrayList<Room> roomList = Room.loadRoomList("Rooms.bin");
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).getRoomNum() == room1.getRoomNum()) {
                roomList.set(i, room1);
                break;
            }
        }
        Room.saveRoomList(roomList, "Rooms.bin");
          List<Reservation> reservationList = loadReservationList("Reservations.bin");
          int newResNum = reservationList.size() + 1;
            setResNum(newResNum);
            RecName=fname+" "+lname;
            setStatus(ResStatus.CHECKED_IN);
            reservationList.add(this);
            saveReservationList(reservationList, "Reservations.bin");  
            System.out.println("Reservation saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving reservation: " + e.getMessage());
        }
      }
   
protected static List<Reservation> loadReservationList(String filepath) 
{
List<Reservation> reservationList = null;
try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
            Object obj = ois.readObject();
if (obj instanceof List<?>) {
                reservationList = (List<Reservation>) obj;
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading reservation list from file: " + e.getMessage());
        }

        if (reservationList == null) {
            reservationList = new ArrayList<>();
        }

        return reservationList;
}
protected static void saveReservationList(List<Reservation> reservationList, String filepath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath, false))) {
            oos.writeObject(reservationList);
            System.out.println("Reservations saved to file.");
        }
    }
public void guestInfo(String phoneNumber) throws IOException {
   
        List<? extends User> userList = User.loadFromFile("Users.bin");
        Guest g1 = null;
        for (User user : userList) {
    if (user instanceof Guest && user.getPhoNumber() != null && user.getPhoNumber().equals(phoneNumber)
            && user.getRole().equalsIgnoreCase("guest")) {
        g1 = (Guest) user;
        break;
    }
        }

        if(g1==null)
        {
            Scanner scanner=new Scanner(System.in);
        guestInfo = new Guest();
        System.out.println("Enter your first name: ");
        String firstName = scanner.next();
        System.out.println("Enter your last name: ");
        String lastName = scanner.next();
        System.out.println("Enter your email: ");
        String email = scanner.next();
        System.out.println("Enter your username: ");
        String username = scanner.next();
        System.out.println("Enter your password: ");
        String password = scanner.next();
        System.out.println("Enter your ID: ");
        int id = scanner.nextInt();
        guestInfo.signup(firstName, lastName, email, phoneNumber, "guest", username, password, id);
        System.out.println("New guest created and information saved.");
        List<? extends User> uList = User.loadFromFile("Users.bin");
        Guest g2 = null;
        for (User userr : uList) {
        if (userr instanceof Guest && userr.getPhoNumber().equals(phoneNumber)
            && userr.getRole().equalsIgnoreCase("guest")) {
        g2 = (Guest) userr;
        break;
        }
        }
        guestInfo=g2;
        }
        else 
        {
            guestInfo=g1;
        }
        
    }
 public void countOnlineRes (String fname,String lname)
    {
        totalResCost();
        RecName=fname+" "+lname;
        setStatus(ResStatus.CHECKED_IN);
        room1.setStatus(Room.RoomStatus.BOOKED);
          ArrayList<Room> roomList = Room.loadRoomList("Rooms.bin");
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).getRoomNum() == room1.getRoomNum()) {
                roomList.set(i, room1);
                break;
            }
        }
        Room.saveRoomList(roomList, "Rooms.bin");
    }
 public void onlineResNum() throws IOException {
       try{
         Cost();
         room1.setStatus(Room.RoomStatus.PENDING);
          double cost=room1.getRevenue();
          room1.setRevenue(cost+roomcost);
          int num = room1.getNumOfRes();
          room1.setNumOfRes(num+1);
          ArrayList<Room> roomList = Room.loadRoomList("Rooms.bin");
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).getRoomNum() == room1.getRoomNum()) {
                roomList.set(i, room1);
                break;
            }
        }
        Room.saveRoomList(roomList, "Rooms.bin");
          List<Reservation> reservationList = loadReservationList("Reservations.bin");
          int newResNum = reservationList.size() + 1;
            setResNum(newResNum);
            setStatus(ResStatus.PENDING);
            reservationList.add(this);
            saveReservationList(reservationList, "Reservations.bin");
            System.out.println("Reservation saved successfully.");
       }catch(IOException e){
           System.out.println("Error saving reservation: " + e.getMessage());
       }
   }
  public void cancellation(String fname,String lname) throws IOException {
      room1.setStatus(Room.RoomStatus.NOT_BOOKED);
      double cost=room1.getRevenue();
      room1.setRevenue(roomcost-cost);
      room1.setNumOfAdults(0);
      room1.setNumOfChildren(0);
      int num = room1.getNumOfRes();
      room1.setNumOfRes(num-1);
      ArrayList<Room> roomList = Room.loadRoomList("Rooms.bin");
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).getRoomNum() == room1.getRoomNum()) {
                roomList.set(i, room1);
                break;
            }
        }
        Room.saveRoomList(roomList, "Rooms.bin");
        RecName=fname+" "+lname;
        List<Reservation> RecycliceBinList = loadReservationList("RecycleBin.bin");
        setStatus(ResStatus.CANCELLED);
        RecycliceBinList.add(this);
        saveReservationList(RecycliceBinList, "RecycleBin.bin");
        List<Reservation> reservationList = loadReservationList("Reservations.bin");
    Iterator<Reservation> iterator = reservationList.iterator();
    while (iterator.hasNext()) {
        Reservation reservation = iterator.next();
        if (reservation.getResNum()== this.getResNum()) {
            iterator.remove();
            break;
        }
    }
    saveReservationList(reservationList, "Reservations.bin");

        System.out.println("Reservation saved successfully.");
     }
  protected void totalResCost()
  {
   double additionalServicesRevenue = ASRevenue(); // Calculate additional services revenue
    roomcost = room1.getPrice() * totalDays;
    System.out.println("Room Cost: $" + roomcost);
    roomcost += additionalServicesRevenue; // Add additional services revenue to total cost
    System.out.println("Total Reservation Cost: $" + (roomcost));
  }
  protected double ASRevenue()
  {
  double totalASRevenue = 0;

    if (AS != null && !AS.isEmpty()) {
        for (Map.Entry<AdditionalServices, Integer> entry : AS.entrySet()) {
            AdditionalServices additionalService = entry.getKey();
            int quantity = entry.getValue();
            double serviceRevenue = additionalService.getPrice() * quantity;
            totalASRevenue += serviceRevenue;
        }
    }

    System.out.println("Additional Services Revenue: $" + totalASRevenue);
    return totalASRevenue;
  }
  public HashMap<String, Integer> print()
  {
      HashMap<String, Integer> FAD=new HashMap<>();
      if (AS == null) {
            AS = new HashMap<>();
        }
      for(Map.Entry<AdditionalServices, Integer> entry : AS.entrySet())
      {
         AdditionalServices additionalService = entry.getKey();
        String serviceName = additionalService.getAdditionalSer();
        int quantity = entry.getValue();
        FAD.put(serviceName, quantity); 
      }
      return FAD;
  }
  
   public void confirmReservation() {
        if (status == ResStatus.PENDING) {
            setStatus(ResStatus.CHECKED_IN);
            System.out.println("Reservation confirmed. Checked-in status set.");
        } else {
            System.out.println("Reservation cannot be confirmed. Invalid status.");
        }
    }
@Override
public String toString() {
    return "-----------------------"+'\n'
            + "Reservation {" +
           "Reservation Number: " + ResNum +'\n'+
            ",Receptionist Name: "+RecName+'\n'+
           ", Check-In Date: " + checkInDate +'\n'+
           ", Check-Out Date: " + checkOutDate +'\n'+
           ", Room: " + room1 +'\n'+
            ",Guest Info : "+guestInfo.getFname()+" "+guestInfo.getLname()+guestInfo.getPhoNumber()+'\n'+
           ", Status: " + status + ",Rate: "+rate+'\n'+
            ",Services: "+print()+'\n'+
            ",Total Cost: "+roomcost+
           '}';
}

}
