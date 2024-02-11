/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
 
/**
 *
 * @author Sarah Wael
 */

public class Admin extends User implements Serializable {
private static final long serialVersionUID = -4081699847689303500L;  // Update with the correct value
//////////Admin Indo data Fields ///////////
    private String fname;
    private String lname;
    private String email;
    private int ID ;
    private String phoNumber;
    private String AUsername;
    private String Apass;
    private String Role;
    private Status status;
///////////////////////////////////////
    private static ArrayList<Room> rList;
    private ArrayList<AdditionalServices> AS;
    private static LocalDate analysisStartDate;
    private static LocalDate analysisEndDate;
//////////Setters and getters//////////////
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
        return "Admin";
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
/////////////Constractors/////////////////////
    public Admin(String fname,String lname,String email,String phoNumber,String role,String username,String pass,int ID)
    {
        this.fname=fname;
        this.lname=lname;
        this.ID=ID;
        this.email=email;
        this.phoNumber=phoNumber;
        AUsername=username;
        Apass=pass;
        Role=role;
    }
    public Admin() {
    this.rList = new ArrayList<Room>();
    this.AS = new ArrayList<AdditionalServices>();
}

//////////abstract functions from User///////
@Override
    public void saveToFile(Object user,String filepath) {
        List<Admin> userList = (List<Admin>) loadFromFile(filepath);
        userList.add((Admin) user);
       try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath, false))) {
            oos.writeObject(userList);
            System.out.println("Admin saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving admin to file: " + e.getMessage());
        }
    }
@Override
public void signup(String firstName, String lastName, String email, String contactNumber, String role, String username1, String password, int id) throws IOException {
     List<? extends User> adminList =loadFromFile("Users.bin");

    // Check if there is already an admin in the Users file
    boolean isAdminExists = adminList.stream()
            .anyMatch(admin -> admin.getRole().equalsIgnoreCase("Admin")&&admin.getStatus().equals(Status.CONFIRMED));

    if (isAdminExists) {
        // Save the new admin in the Pending file
        Admin admin = new Admin(firstName, lastName, email, contactNumber, role, username1, password, id);
        admin.setStatus(Status.PENDING);
        admin.saveToFile(admin,"Pending.bin");
    } else {
        // Save the new admin in the Users file
        Admin admin = new Admin(firstName, lastName, email, contactNumber, role, username1, password, id);
         admin.setStatus(Status.CONFIRMED);
        admin.saveToFile(admin,"Users.bin");
    }
}
@Override
public String toString() {
    return "Admin{" +
            "fname='" + fname + '\'' +
            ", lname='" + lname + '\'' +
            ", email='" + email + '\'' +
            ", ID=" + ID +
            ", phoNumber=" + phoNumber +
            ", AUsername='" + AUsername + '\'' +
            ", Apass='" + Apass + '\'' +
            ", Role='" + Role + '\'' +
            ", Status= "+status+
            '}';
}
///////////Room Functions ///////////
 public void addRoom(int roomNum,String category,double price) throws IOException
    {
       ArrayList<Room> roomList = Room.loadRoomList("Rooms.bin");
       Room room1 = new Room(roomNum, category, price);
        roomList.add(room1);
        Room.saveRoomList(roomList, "Rooms.bin");
       
    }
    protected boolean searchRoom(int roomNum) 
    {
    ArrayList<Room> roomList = Room.loadRoomList("Rooms.bin");

    for (Room room : roomList) {
        if (room.getRoomNum() == roomNum) {
            return true;
        }
    }
    return false;
    }
    public void editRoom(int roomOld, String categoryNew,double price) throws IOException, ClassNotFoundException {
    if (searchRoom(roomOld)) {
        ArrayList<Room> roomList = Room.loadRoomList("Rooms.bin");

        for (Room room : roomList) {
            if (room.getRoomNum() == roomOld) {
                room.setCategory(categoryNew);
                room.setPrice(price);
                break;
            }
        }

         Room.saveRoomList(roomList, "Rooms.bin");
    } else {
        System.out.println("Room not found.");
    }
}
    public ArrayList<Room> viewroom() {
    ArrayList<Room> roomList = Room.loadRoomList("Rooms.bin");

    if (!roomList.isEmpty()) {
        for (Room room : roomList) {
            System.out.println(room);
        }
    } else {
        System.out.println("No rooms available.");
    }

    return roomList;
}
    public void removeRoom(int roomNum) throws IOException {
    if (searchRoom(roomNum)) {
        ArrayList<Room> roomList = Room.loadRoomList("Rooms.bin");

        Room roomToRemove = null;
        for (Room room : roomList) {
            if (room.getRoomNum() == roomNum) {
                roomToRemove = room;
                break;
            }
        }
        if (roomToRemove != null) {
            roomList.remove(roomToRemove);
            System.out.println("Room removed successfully.");
            Room.saveRoomList(roomList, "Rooms.bin");
        } else {
            System.out.println("Room not found.");
        }
    } else {
        System.out.println("Room not found.");
    }
}   
    public Room getRoom(int roomNumber) {
    ArrayList<Room> roomList = Room.loadRoomList("Rooms.bin");

    for (Room room : roomList) {
        if (room.getRoomNum() == roomNumber) {
            return room;
        }
    }
    return null; // Return null if the room is not found
}
////////////Service Functions/////////
public ArrayList<AdditionalServices> viewAS() {
         ArrayList<AdditionalServices> AS = AdditionalServices.loadFromFile("AllServices.bin");
    if (!AS.isEmpty()) {
        // Display the loaded AdditionalServices objects
        System.out.println("Array size= " + AS.size());
        System.out.println("The services available: ");
        if (!AS.isEmpty()) {
        for (AdditionalServices a : AS) {
            System.out.println(a);
        }
        }
    }
            return AS;
    
    }
public void ASAdd(String AD, int price) throws IOException {
         AdditionalServices AD1 = new AdditionalServices(AD, price);
         ArrayList<AdditionalServices> AS = AdditionalServices.loadFromFile("AllServices.bin");
         AS.add(AD1);
        
    }
    protected boolean ASSearch(String AD){
     ArrayList<AdditionalServices> AS = AdditionalServices.loadFromFile("AllServices.bin");
            for (AdditionalServices AD1 : AS)
    {
        if (AD1.getAdditionalSer().equalsIgnoreCase(AD) )
        {
           return true;
        }    
   }
   return false;
        }
    protected void ASRemove(String AD) throws IOException{
          ArrayList<AdditionalServices> AS = AdditionalServices.loadFromFile("AllServices.bin");   
            AdditionalServices ADToRemove = null;
    for (AdditionalServices AD1 : AS) {
        if (AD1.getAdditionalSer() .equalsIgnoreCase(AD)) {
            ADToRemove = AD1;
            break;  
        }
    }

    if (ADToRemove != null) {
        AS.remove(ADToRemove);
        System.out.println("Service removed successfully.");
    } else {
        System.out.println("Service not found.");
    }
   try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("AllServices.bin"))) {
            oos.writeObject(AS);
            System.out.println("Updated services saved to AllServices.bin");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    protected void ASEdit(String AD3, int newPrice) throws IOException {
        ArrayList<AdditionalServices> AS = AdditionalServices.loadFromFile("AllServices.bin");
        boolean serviceFound = false;

        for (AdditionalServices AD1 : AS) {
            if (AD1.getAdditionalSer().equalsIgnoreCase(AD3)) {
                AD1.setPrice(newPrice);
                serviceFound = true;
                System.out.println("Service price edited successfully.");
                break;
            }
        }

        if (!serviceFound) {
            System.out.println("Service not available.");
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("AllServices.bin"))) {
            oos.writeObject(AS);
            System.out.println("Updated services saved to AllServices.bin");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    /*AdditionalServices getAS(String searchValue) {
        ArrayList<AdditionalServices> AS = AdditionalServices.loadFromFile("AllServices.bin");

    for (AdditionalServices AA : AS) {
        if (AA.getAdditionalSer().equalsIgnoreCase(searchValue) ) {
            return AA;
        }
    }
    return null; // Return null if the room is not found
    } */
     protected boolean addnew(String firstName,String lastName,String eemail, String contactNumber,String type,String username,String password,int id)
    {
        if(type.equalsIgnoreCase("receptionist"))
        {
            Receptionist rec = new Receptionist(firstName,lastName,eemail,contactNumber,type,username,password,id);
            rec.setStatus(status.CONFIRMED);
                rec.saveToFile(rec, "Users.bin");
                return true;
        }
        else if(type.equalsIgnoreCase("guest"))
        {
            Guest guest = new Guest(firstName,lastName,eemail,contactNumber,type,username,password,id);
                guest.saveToFile(guest, "Users.bin");
                return true;
        }
        else if(type.equalsIgnoreCase("admin"))
        {
            Admin add = new Admin(firstName,lastName,eemail,contactNumber,type,username,password,id);
                add.saveToFile(add, "Users.bin");
                return true;
        }
        else
            return false;
                
    }
    protected List<User> loadPendingUsersFromFile()
    {
        List<User> pendingUsersList = (List<User>) loadFromFile("Pending.bin");
                if (pendingUsersList.isEmpty()) {
                System.out.println("No pending users.");
                
                }
                return pendingUsersList;
    }
    protected boolean addPending(List<User> pendingUsersList,User userToAdd)
    {  boolean done=false;
        if (userToAdd != null) {
        userToAdd.setStatus(Status.CONFIRMED);
        pendingUsersList.remove(userToAdd);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Pending.bin", false))) {
            oos.writeObject(pendingUsersList);
            System.out.println("Pending users list updated.");
        } catch (IOException e) {
            System.out.println("Error updating pending users list: " + e.getMessage());
        }

        userToAdd.saveToFile(userToAdd, "Users.bin");
        done=true;
        return done;
    } else {
        System.out.println("User with the entered phone number not found in pending users.");
        return done;
    }
    }
    
    protected void addUser() throws IOException{
         Scanner scanner = new Scanner(System.in);

        System.out.println("Select user type to add (1. Guest, 2. Receptionist, 3. Admin):");
        System.out.println(" Or 4)add a pending user ");
        int userTypeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        switch (userTypeChoice) {
            case 1:
                // Adding a guest
                System.out.println("Enter guest details:");
                System.out.println("Enter your first name: ");
                String firstName = scanner.next();
                System.out.println("Enter your last name: ");
                String lastName = scanner.next();
                System.out.println("Enter your contact number: ");
                String contactNumber = scanner.next();
                System.out.println("Enter your email: ");
                String email = scanner.next();
                System.out.println("Enter your username: ");
                String username = scanner.next();
                System.out.println("Enter your password: ");
                String password = scanner.next();
                System.out.println("Enter your ID: ");
                int id = scanner.nextInt();
                Guest guest = new Guest(firstName,lastName,email,contactNumber,"guest",username,password,id);
                guest.saveToFile(guest, "Users.bin");
                System.out.println("Guest added successfully.");
                break;

            case 2:
                System.out.println("Enter receptionist details:");
                System.out.println("Enter your first name: ");
                String firstName2 = scanner.next();
                System.out.println("Enter your last name: ");
                String lastName2 = scanner.next();
                System.out.println("Enter your contact number: ");
                String contactNumber2 = scanner.next();
                System.out.println("Enter your email: ");
                String email2= scanner.next();
                System.out.println("Enter your username: ");
                String username2 = scanner.next();
                System.out.println("Enter your password: ");
                String password2 = scanner.next();
                System.out.println("Enter your ID: ");
                int id2 = scanner.nextInt();
                Receptionist Rec1 =new Receptionist(firstName2, lastName2, email2, contactNumber2, "Receptionist", username2, password2, id2);
                Rec1.setStatus(Status.CONFIRMED);
                Rec1.saveToFile(Rec1, "Users.bin");
                System.out.println("Receptionist added successfully!");
            break;
            case 3:
                System.out.println("Enter Admin details:");
                System.out.println("Enter your first name: ");
                String firstName1 = scanner.next();
                System.out.println("Enter your last name: ");
                String lastName1 = scanner.next();
                System.out.println("Enter your contact number: ");
                String contactNumber1 = scanner.next();
                System.out.println("Enter your email: ");
                String email1= scanner.next();
                System.out.println("Enter your username: ");
                String username1 = scanner.next();
                System.out.println("Enter your password: ");
                String password1 = scanner.next();
                System.out.println("Enter your ID: ");
                int id1 = scanner.nextInt();
                Admin adminn=new Admin(firstName1, lastName1, email1, contactNumber1, "Admin", username1, password1, id1);
                adminn.setStatus(Status.CONFIRMED);
                adminn.saveToFile(adminn, "Users.bin");
                System.out.println("Admin added successfully!");
                break;
            case 4:
                List<User> pendingUsersList = (List<User>) loadFromFile("Pending.bin");
                if (pendingUsersList.isEmpty()) {
                System.out.println("No pending users.");
                return;
                }
                System.out.println("Pending Users:");
                for (User pendingUser : pendingUsersList) {
                System.out.println(pendingUser);
                }
                System.out.println("Enter the phone number of the user to add (or enter 0 to exit):");
                String phoneNumberToAdd = scanner.next();
                if (phoneNumberToAdd.equals("0")) {
                 break;
                }
                User userToAdd = null;
               for (User pendingUser : pendingUsersList) {
               if (pendingUser.getPhoNumber().equals(phoneNumberToAdd)) {
               userToAdd = pendingUser;
            break;
        }
    }

    if (userToAdd != null) {
        // Change the status of the user to CONFIRMED
        userToAdd.setStatus(Status.CONFIRMED);

        // Remove the user from the pending list
        pendingUsersList.remove(userToAdd);

        // Save the changes to the Pending.bin file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Pending.bin", false))) {
            oos.writeObject(pendingUsersList);
            System.out.println("Pending users list updated.");
        } catch (IOException e) {
            System.out.println("Error updating pending users list: " + e.getMessage());
        }

        // Save the user to the Users.bin file
        userToAdd.saveToFile(userToAdd, "Users.bin");

        System.out.println("User added successfully!");
    } else {
        System.out.println("User with the entered phone number not found in pending users.");
    }

            break;
            default:
                System.out.println("Invalid user type choice.");
        }
    }  
    protected void removeUser() 
    {
          Scanner scanner = new Scanner(System.in);
    List<User> userList = (List<User>) loadFromFile("Users.bin");

    System.out.println("List of Users:");
    for (User user : userList) {
        System.out.println(user);
    }

    System.out.println("Enter the phone number of the user to remove (or enter 0 to exit):");
    String phoneNumberToRemove = scanner.next();

    if (phoneNumberToRemove.equals("0")) {
        return;
    }

    User userToRemove = null;
    for (User user : userList) {
        if (user.getPhoNumber().equals(phoneNumberToRemove)) {
            userToRemove = user;
            break;
        }
    }
    if (userToRemove != null) {
        // Change the status of the user to REMOVED
        userToRemove.setStatus(Status.REMOVED);
        // Save the updated user list to file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Users.bin", false))) {
            oos.writeObject(userList);
            System.out.println("User removed successfully!");
        } catch (IOException e) {
            System.out.println("Error saving user list: " + e.getMessage());
        }
    } else {
        System.out.println("User with the entered phone number not found.");
    }
    }
    protected boolean removeUserGUI(User userToRemove,List<User> userList) 
    {
    boolean done=false;
    if (userToRemove != null) {
        userToRemove.setStatus(Status.REMOVED);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Users.bin", false))) {
            oos.writeObject(userList);
            done=true;
            System.out.println("Done");
            return done;
        } catch (IOException e) {
            System.out.println("Failed");
            return done;
        }
    } else {
       return done;
    }
    }
    
    protected void saveeditUser(List<User> userList,User userToEdit) {
      // Save the updated user list to file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Users.bin", false))) {
            oos.writeObject(userList);
            System.out.println("User updated successfully!");
        } catch (IOException e) {
            System.out.println("Error saving user list: " + e.getMessage());
        }
}
    protected void editUser() {
    Scanner scanner = new Scanner(System.in);
    List<User> userList = (List<User>) loadFromFile("Users.bin");

    System.out.println("List of Users:");
    for (User user : userList) {
        System.out.println(user);
    }

    System.out.println("Enter first name of the user to edit: ");
    String editFirstName = scanner.next();
    System.out.println("Enter last name of the user to edit: ");
    String editLastName = scanner.next();
    System.out.println("Enter role of the user to edit: ");
    String editRole = scanner.next();

    User userToEdit = null;
    for (User user : userList) {
        if (user.getFname().equalsIgnoreCase(editFirstName) &&
            user.getLname().equalsIgnoreCase(editLastName) &&
            user.getRole().equalsIgnoreCase(editRole)) {
            userToEdit = user;
            break;
        }
    }

    if (userToEdit != null) {
        // Ask what to edit
        int editChoice;
        do {
            System.out.println("Select what to edit:");
            System.out.println("1. Phone Number");
            System.out.println("2. Email");
            System.out.println("3. Username");
            System.out.println("4. Password");
            System.out.println("0. Exit");

            try {
                editChoice = scanner.nextInt();

                switch (editChoice) {
                    case 1:
                        System.out.println("Enter new phone number: ");
                        String newPhoneNumber = scanner.next();
                        userToEdit.setPhoNumber(newPhoneNumber);
                        break;
                    case 2:
                        System.out.println("Enter new email: ");
                        String newEmail = scanner.next();
                        userToEdit.setEmail(newEmail);
                        break;
                    case 3:
                        System.out.println("Enter new username: ");
                        String newUsername = scanner.next();
                        userToEdit.setAUsername(newUsername);
                        break;
                    case 4:
                        System.out.println("Enter new password: ");
                        String newPassword = scanner.next();
                        userToEdit.setApass(newPassword);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
                editChoice = -1;
            }
        } while (editChoice != 0);

        // Save the updated user list to file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Users.bin", false))) {
            oos.writeObject(userList);
            System.out.println("User updated successfully!");
        } catch (IOException e) {
            System.out.println("Error saving user list: " + e.getMessage());
        }
    } else {
        System.out.println("User not found.");
    }

    // Do not close the scanner to keep System.in open for further input
}
   protected User searchUser(String role, String phoneNumber) {
    List<User> userList = (List<User>) loadFromFile("Users.bin");

    if (role == null || phoneNumber == null) {
        System.out.println("Invalid input. Role and phone number cannot be null.");
        return null;
    }

    for (User user : userList) {
        if (role.equalsIgnoreCase(user.getRole()) && phoneNumber.equals(user.getPhoNumber())) {
            return user;
        }
    }

    return null; // Return null if the user is not found
}
  public void viewRes() throws FileNotFoundException, IOException
  {
      List<Reservation> reservationList;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Reservations.bin"))) {
            Object obj = ois.readObject();

            if (obj instanceof List<?>) {
                reservationList = (List<Reservation>) obj;
            } else {
                reservationList = new ArrayList<>();
            }

        } catch (ClassNotFoundException e) {
            throw new IOException("Error loading reservations: " + e.getMessage());
        }
        if (!reservationList.isEmpty()) {
            System.out.println("List of Reservations:");
            
            for (Reservation reservation : reservationList) {
                System.out.println(reservation);
            }
        } else {
            System.out.println("No reservations found.");
        }
  }
   protected int NumOfRes() {
       List<Reservation> reservationList = Reservation.loadReservationList("Reservations.bin");
       int count=0;
       for(Reservation res:reservationList)
       {
           if(res.getStatus().equals(Reservation.ResStatus.CHECKED_IN))
           {
               count++;
           }
       }
       System.out.println("Count:"+count);
              return count;

      }
  protected int NumOfRooms()
   {
        List<Room> roomList = Room.loadRoomList("Rooms.bin");
       int Num = roomList.size();
       System.out.println(Num);
              return Num;

   }
    protected int NumOfAvailableRooms()
   {
       List<Room> roomList = Room.loadRoomList("Rooms.bin");
       int count=0;
       for(Room room : roomList)
       {
           if(room.getStatus().equals(Room.RoomStatus.NOT_BOOKED))
           {
               count++;
           }
       }
       System.out.println(count);
              return count;

   }
   protected int NumOfBookedRooms()
   {
       List<Room> roomList = Room.loadRoomList("Rooms.bin");
       int count=0;
       for(Room room : roomList)
       {
           if(room.getStatus().equals(Room.RoomStatus.BOOKED)||room.getStatus().equals(Room.RoomStatus.PENDING))
           {
               count++;
           }
       }
       System.out.println(count);
              return count;

   }
   public int countReceptionists() {
        List<? extends User> users = loadFromFile("Users.bin");
        int count =0;
        for(User u :users)
        {
            if (u instanceof Receptionist && u.getRole().equalsIgnoreCase("Receptionist") && u.getStatus() == Status.CONFIRMED) {
            count++;
        }
        }
        System.out.println("Count: " + count);
               return count;

    }
   public int countGuests() {
        ArrayList<Reservation> reservationList = (ArrayList<Reservation>) Reservation.loadReservationList("Reservations.bin");
        HashSet<Guest> uniqueGuests = new HashSet<>();

    for (Reservation reservation : reservationList) {
        if (reservation.getGuestInfo() != null) {
            uniqueGuests.add(reservation.getGuestInfo());
        }
    }

    int count = uniqueGuests.size();
    System.out.println("Count: " + count);
           return count;
    } 
   public int noOfRequests()
   {
      
      ArrayList<AdditionalServices> as = AdditionalServices.loadFromFile("AllServices.bin");
      int sum=0;
      for(AdditionalServices ADDS : as)
      {
          sum+=ADDS.getNumOfReq();
      }
       System.out.println("Number of Requests = "+sum);
          return sum;

   }
   public AdditionalServices mostRequestedService()
   {
       ArrayList<AdditionalServices> as = AdditionalServices.loadFromFile("AllServices.bin");
       AdditionalServices AD = null;
       int maxNumOfReq = 0;
       for(AdditionalServices ADDS : as)
      {
          int currentNumOfReq = ADDS.getNumOfReq();
          if (currentNumOfReq > maxNumOfReq)
          {
            maxNumOfReq = currentNumOfReq;
            AD = ADDS;
          }
      }
        if (AD != null) {
            System.out.println(AD);
        } else {
        System.out.println("No services found.");

    } 
                        return null;
   }
   public Room MostReservedRoom() {
         ArrayList<Room> roomList = Room.loadRoomList("Rooms.bin");
       Room room = null;
       int maxNumOfRes = 0;
       for(Room r : roomList)
      {
          int currentNumOfRes = r.getNumOfRes();
          if (currentNumOfRes > maxNumOfRes)
          {
            maxNumOfRes = currentNumOfRes;
            room = r;
          }
      }
        if (room != null) {
            System.out.println(room);
        } else {
        System.out.println("No Room found.");
    } 
   return null;
   }
      public void MostRevenueRoom() 
      {
       ArrayList<Room> roomList = Room.loadRoomList("Rooms.bin");
       Room room = null;
       double maxRevenue = 0.0;
       for(Room r : roomList)
      {
          double currentRevenue = r.getRevenue();
          if (currentRevenue > maxRevenue)
          {
            maxRevenue = currentRevenue;
            room = r;
          }
      }
        if (room != null) {
            System.out.println(room);
        } else {
        System.out.println("No Room found.");
    } 
      }
    public void totalcost()
   {
       System.out.println("Total cost of : 1.Rooms    2.Services    3.Both");
       Scanner s=new Scanner(System.in);
       int x=s.nextInt();
       double sum=0;
       switch(x)
       {
           case 1:
               ArrayList<Room> room = Room.loadRoomList("Rooms.bin");
               for(Room r1:room)
               {
                  sum+=r1.getRevenue();
               }
               System.out.println("The Total cost of Rooms = "+sum);
            break;
           case 2:
               ArrayList<AdditionalServices> service = AdditionalServices.loadFromFile("AllServices.bin");
               for(AdditionalServices r1:service)
               {
                  sum+=r1.getRevenue();
               }
               System.out.println("The Total cost of Services = "+sum);
            break;
           case 3:
               double sum1=0;
               double sum2=0;
               double bothsum=0;
               ArrayList<AdditionalServices> services = AdditionalServices.loadFromFile("AllServices.bin");
               for(AdditionalServices r1:services)
               {
                  sum1+=r1.getRevenue();
               }
               ArrayList<Room> room1 = Room.loadRoomList("Rooms.bin");
               for(Room r1:room1)
               {
                  sum2+=r1.getRevenue();
               }
               bothsum=sum1+sum2;
               System.out.println("The total cost for Both= "+bothsum);
               break;
       }
   }
   public void  AverageCost()
   {
      System.out.println("Average cost of : 1.Rooms    2.Services    3.Both");
       Scanner s=new Scanner(System.in);
       int x=s.nextInt();
       double sum=0;
       double count=0;
       switch(x)
       {
           case 1:
               ArrayList<Room> room = Room.loadRoomList("Rooms.bin");
               for(Room r1:room)
               {
                  sum+=r1.getRevenue();
                  count++;
               }
               System.out.println("The Total cost of Rooms = "+sum/count);
            break;
           case 2:
               ArrayList<AdditionalServices> service = AdditionalServices.loadFromFile("AllServices.bin");
               for(AdditionalServices r1:service)
               {
                  sum+=r1.getRevenue();
                  count++;
               }
               System.out.println("The Total cost of Services = "+sum/count);
            break;
           case 3:
               double sum1=0;
               int count1=0;
               
               double sum2=0;
               double bothsum=0;
               ArrayList<AdditionalServices> services = AdditionalServices.loadFromFile("AllServices.bin");
               for(AdditionalServices r1:services)
               {
                  sum1+=r1.getRevenue();
                  count1++;
               }
               ArrayList<Room> room1 = Room.loadRoomList("Rooms.bin");
               for(Room r1:room1)
               {
                  sum2+=r1.getRevenue();
                  count1++;
               }
               bothsum=sum1+sum2;
               System.out.println("The total cost for Both= "+bothsum/count1);
               break;
       } 
   }
   public void MostRevenueSer()
   {
       ArrayList<AdditionalServices> ASList = AdditionalServices.loadFromFile("AllServices.bin");
       AdditionalServices as = null;
       double maxRevenue = 0.0;
       for(AdditionalServices r : ASList)
      {
          double currentRevenue = r.getRevenue();
          if (currentRevenue > maxRevenue)
          {
            maxRevenue = currentRevenue;
            as = r;
          }
      }
        if (as != null) {
            System.out.println(as);
        } else {
        System.out.println("No Room found.");
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
