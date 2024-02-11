/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package user;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author Sarah Wael
 */
public abstract class User  {
    public enum Status {
    PENDING,
    CONFIRMED,
    DECLINED,
    REMOVED
}
      protected abstract String fname();
    protected abstract String lname();
    protected abstract String email();
    protected abstract int ID ();
    protected abstract String phoNumber();
    protected abstract String AUsername();
    protected abstract String Apass();
    protected abstract String Role();
    protected abstract Status status();
public abstract String getAUsername();
public abstract String getApass();
public abstract String getPhoNumber();
public abstract String getFname() ;
public abstract String getLname();
public abstract Status getStatus();
public abstract void setApass(String newPassword);
public abstract void setEmail(String email);
public abstract void setPhoNumber(String phoNumber);
public abstract void setAUsername(String AUsername);
public abstract void saveToFile(Object user,String filepath);
public abstract String getRole();
public abstract void setStatus(Status status);
public abstract void signup(String firstName, String lastName, String email, String contactNumber, String role, String username1, String password, int id) throws IOException ;
public static List<? extends User> loadFromFile(String filepath) {
        List<User> userList = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
            Object obj = ois.readObject();

            if (obj instanceof List<?>) {
                userList.clear();
                List<?> list = (List<?>) obj;
                for (Object item : list) {
                    if (item instanceof User) {
                        userList.add((User) item);
                    }
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading user list from file: " + e.getMessage());
        }

        return userList;
    }
public static void forgetPassword(String role, String username) {
Scanner scanner = new Scanner(System.in);
    List<User> userList = (List<User>) loadFromFile("Users.bin");
    User userToRecover = null;
    for (User user : userList) {
        if (user.getAUsername().equals(username)) {
            userToRecover = user;
            break;
        }
    }
    if (userToRecover != null) {
        // Display the masked phone number
        String maskedPhoneNumber = maskPhoneNumber(userToRecover.getPhoNumber());
        System.out.println("User found! Phone number: " + maskedPhoneNumber);

        // Ask the user to enter the last two digits of the phone number
        System.out.println("Enter the phone number that ends with: ");
        String enteredDigits = scanner.next();

        // Verify the entered digits
        if (verifyPhoneNumber(userToRecover.getPhoNumber(), enteredDigits)) {
            // Ask the user to enter a new password
            System.out.println("Enter your new password: ");
            String newPassword = scanner.next();

            // Update the user's password and save the changes
            userToRecover.setApass(newPassword);
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getAUsername().equals(username)) {
                    userList.set(i, userToRecover);
                    break;
                }
            }
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Users.bin", false))) {
        oos.writeObject(userList);
        System.out.println("User list saved to file.");
    } catch (IOException e) {
        System.out.println("Error saving user list to file: " + e.getMessage());
    }
    System.out.println("Password updated successfully!");
        } else {
            System.out.println("Incorrect phone number. Password recovery failed.");
        }
    } else {
        System.out.println("User not found. Password recovery failed.");
    }

    scanner.close();
}
private static String maskPhoneNumber(String phoneNumber) {
    int length = phoneNumber.length();
    return "*".repeat(Math.max(0, length - 2)) + phoneNumber.substring(length - 2);
}
private static boolean verifyPhoneNumber(String phoneNumber, String enteredDigits) {
    return phoneNumber.equals(enteredDigits);
}
public <T extends User> void view(String filepath, Class<T> userClass) {
    List<? extends User> userList = loadFromFile(filepath);

    System.out.println(filepath);
    
    for (User user : userList) {
        if (userClass.isInstance(user)) {
            System.out.println(user);
        }
    }
}
public static User login(String username, String password) {
    List<? extends User> userList = loadFromFile("Users.bin");

    for (User user : userList) {
        if (user != null && username.equals(user.getAUsername()) && password.equals(user.getApass()) &&
                user.getStatus() != null && user.getStatus().equals(Status.CONFIRMED)) {
            System.out.println("Successfully logged in as " + user.getRole() + ".");
            return user;
        }
    }

    System.out.println("Login failed. Please try again.");
    return null;
}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
       User user = null;
       Scanner scanner = new Scanner(System.in);
        System.out.println("Choose: ");
        System.out.println("1. LOGIN");
        System.out.println("2. SIGNUP");
        System.out.println("3. FORGET PASSWORD");
        System.out.println("4. SHOW FILES");
        String adminUsername;
        int option = scanner.nextInt();
        switch(option)
        {
             case 1:
            System.out.println("Enter your username: ");
             adminUsername = scanner.next();
            System.out.println("Enter your password: ");
            String adminPassword = scanner.next();
            User userr = User.login(adminUsername, adminPassword);
            if (userr instanceof Admin) {
            Admin admin= (Admin)userr;
            System.out.println("1)Add Room                   2) Edit Room                 3)Remove Room ");
            System.out.println("4)Search Room                5) View Additional Services             6)View Number of Reservation  ");
            System.out.println("7)Add Additional Services     8)Most Reserved Room         9)View Reservation Details ");
            System.out.println("10)Search Additional Services    11)Remove Additional Services    12)Edit Additional Services  ");
            System.out.println(" 15)Add User  ");
            System.out.println("16)Edit User    17)Remove User    18)Search User  ");
            System.out.println("23)View Room   26) number of rooms   "); 
            System.out.println("27)Number of Available Rooms   28) Number of Booked Rooms  29)number of receptionists");
            System.out.println("30)Number of Guests    31) Number of requests     32)Most Requested Service");
                System.out.println("33)Most Revenue Room  35)Total Cost");
                System.out.println("36)Average Cost     37)Most Revenue Service");
            int x=scanner.nextInt();
            while (x !=0)
            {
              switch(x)
                {
                 case 1:
                 System.out.println("Enter Room Number:");
                 int roomNumber = scanner.nextInt();
                 scanner.nextLine();  // Consume the newline character
                 System.out.println("Enter Category of this Room:");
                 String category = scanner.nextLine();
                 System.out.println("Enter Room price:");
                 double price = scanner.nextDouble();
                 admin.addRoom(roomNumber, category, price);
                 System.out.println("Room added successfully!");
                 System.out.println("Enter 0 to exit or any other number to continue:");
                 x = scanner.nextInt();
                 break;
                 case 2:
                 System.out.println("Enter Room Number you want to edit:");
                 int roomold=scanner.nextInt();
                 scanner.nextLine();
                 System.out.println("Enter Edited category:");
                 String Categorynew=scanner.nextLine();
                 System.out.println("Enter Room price:");
                 double pri = scanner.nextDouble();
                 admin.editRoom(roomold,Categorynew,pri);
                 System.out.println("Enter other choice or press 0 to exit");
                 x=scanner.nextInt();
                 break;
                 case 3:
                 System.out.println("Enter Room number that you want to remove: ");
                 int roomNum=scanner.nextInt();
                 admin.removeRoom(roomNum);
                 System.out.println("Enter other choice or press 0 to exit");
                 x=scanner.nextInt();
                 break;
                 case 4:
                 System.out.println("Enter Room number that you searched for");
                 int roonnumm=scanner.nextInt();
                 boolean T= admin.searchRoom(roonnumm);
                 if(T)
                 System.out.println("Valid Room");
                 else
                 System.out.println("invalid Room");
                 System.out.println("Enter other choice or press 0 to exit");
                 x=scanner.nextInt();
                 break;
                 case 5:
                  ArrayList<AdditionalServices> viewAS = admin.viewAS();
                 System.out.println("Enter other choice or press 0 to exit");
                 x=scanner.nextInt();
                 break;
                 case 6 :
                 admin.NumOfRes();
                 System.out.println("Enter other choice or press 0 to exit");
                 x=scanner.nextInt();
                 break;
                case 7:
                 System.out.println("Enter The additional service you want to add: ");
                 scanner.nextLine();
                 String AD=scanner.nextLine();
                 System.out.println("Enter price of the additional service:");
                 int pricee=scanner.nextInt();
                 admin.ASAdd(AD,pricee);
                 System.out.println("Enter other choice or press 0 to exit");
                 x=scanner.nextInt();
                 break; 
                case 8:
                    admin.MostReservedRoom();
                    System.out.println("Enter other choice or press 0 to exit");
                 x=scanner.nextInt();
                 case 23:
                 admin.viewroom();
                 System.out.println("Enter other choice or press 0 to exit");
                 x=scanner.nextInt();
                 break;
                 case 9:
                 admin.viewRes();
                 System.out.println("Enter other choice or press 0 to exit");
                 x=scanner.nextInt();
                  break;
                 case 10:
                 System.out.println("Enter The additional service you want to search for: ");
                 scanner.nextLine();
                 String AD1=scanner.nextLine();
                 boolean M= admin.ASSearch(AD1);
                 if(M)
                 System.out.println("Valid service");
                 else
                 System.out.println("invalid service");
                 System.out.println("Enter other choice or press 0 to exit");
                 x=scanner.nextInt();
                 break;
                 
                 case 11:
                 System.out.println("Enter Additional service that you want to remove: ");
                 scanner.nextLine();
                 String AD2=scanner.nextLine();
                 admin.ASRemove(AD2);
                 System.out.println("Enter other choice or press 0 to exit");
                 x=scanner.nextInt();
                 break;
                 
                 case 12:
                 System.out.println("Enter Additional service you want to edit:");
                 scanner.nextLine();
                 String AD3=scanner.nextLine();
                 System.out.println("Enter Edited price:");
                 int newprice =scanner.nextInt();
                 admin.ASEdit(AD3,newprice);
                 System.out.println("Enter other choice or press 0 to exit");
                 x=scanner.nextInt();
                 break;
                 case 15:
                    admin.addUser();
                    System.out.println("Enter other choice or press 0 to exit");
                    x=scanner.nextInt();
                    break;
                    case 16:
                    admin.editUser();
                    System.out.println("Enter other choice or press 0 to exit");
                    x = scanner.nextInt();
                    
                    break;
                    case 17:
                        admin.removeUser();
                        System.out.println("Enter other choice or press 0 to exit");
                    x = scanner.nextInt();
                    break;
                    case 18:
                        System.out.println("Enter the role of the user to search: ");
                        String searchRole = scanner.next();
                        System.out.println("Enter the phone number of the user to search: ");
                        String searchPhoneNumber = scanner.next();
                        User foundUser = admin.searchUser(searchRole, searchPhoneNumber);
                        if (foundUser != null) {
                        System.out.println("User found:\n" + foundUser);
                        } else {
                        System.out.println("User not found.");
                        }
                         System.out.println("Enter other choice or press 0 to exit");
                         x = scanner.nextInt();
                         break;   
                    case 26:
                        admin.NumOfRooms();
                        System.out.println("Enter other choice or press 0 to exit");
                         x = scanner.nextInt();
                         break;
                    case 27:
                        admin.NumOfAvailableRooms();
                        System.out.println("Enter other choice or press 0 to exit");
                         x = scanner.nextInt();
                         break;
                    case 28:
                        admin.NumOfBookedRooms();
                         System.out.println("Enter other choice or press 0 to exit");
                         x = scanner.nextInt();
                         break;
                    case 29:
                       admin.countReceptionists();
                       System.out.println("Enter other choice or press 0 to exit");
                         x = scanner.nextInt();
                         break;
                    case 30:
                        admin.countGuests();
                         System.out.println("Enter other choice or press 0 to exit");
                         x = scanner.nextInt();
                         break;
                         case 31:
                        admin.noOfRequests();
                         System.out.println("Enter other choice or press 0 to exit");
                         x = scanner.nextInt();
                         break;
                          case 32:
                        admin.mostRequestedService();
                         System.out.println("Enter other choice or press 0 to exit");
                         x = scanner.nextInt();
                         break; 
                         case 33:
                    admin.MostRevenueRoom();
                 System.out.println("Enter other choice or press 0 to exit");
                 x=scanner.nextInt();
                 break;
                         case 35:
                         admin.totalcost();
                         System.out.println("Enter other choice or press 0 to exit");
                         x=scanner.nextInt();
                         break;
                         case 36:
                             admin.AverageCost();
                             System.out.println("Enter other choice or press 0 to exit");
                         x=scanner.nextInt();
                         break;
                         case 37:
                             admin.MostRevenueSer();
                             System.out.println("Enter other choice or press 0 to exit");
                         x=scanner.nextInt();
                         break;
                 default:
                     System.out.println("Enter valid number");
                   break;
                }  
            }
                }
            else if (userr instanceof Guest) {
    Guest guest = (Guest) userr;
    int choice;
    do {
        System.out.println("1) Online Booking  2) Rating Reservation  3) View History   4) View Reservation  0) Exit");
        choice = scanner.nextInt();

        switch (choice) {
            case 1:
                guest.bookOnline();
                break;
            case 2:
                guest.rateRes();
                break;
            case 3:
                guest.viewHistory();
                break;
            case 4:
                guest.viewres();
                break;
            case 0:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }

    } while (choice != 0);
}
       else if (userr instanceof Receptionist) {
    Receptionist REC = (Receptionist) userr;
    int g;
    do {
        System.out.println("1) Check in   2) Cancel Reservation   3) View RecycleBin   4) Add   5) Checkout  0) Exit");
        g = scanner.nextInt();

        switch (g) {
            case 1:
                REC.Checkin();
                break;
            case 2:
                REC.cancelReservation();
                break;
            case 3:
                REC.displayRecycleBinContents();
                break;
            case 4:
                REC.addAdditionalService();
                break;
            case 5:
                REC.checkout();
                break;
            case 0:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }

    } while (g != 0);
}
            break;
             case 2:
            System.out.println("Enter your first name: ");
            String firstName = scanner.next();
            System.out.println("Enter your last name: ");
            String lastName = scanner.next();
            System.out.println("Enter your contact number: ");
            String contactNumber = scanner.next();
            System.out.println("Enter your email: ");
            String email = scanner.next();
            System.out.println("Enter your role (admin, guest, or receptionist): ");
            String type = scanner.next();
            System.out.println("Enter your username: ");
            String username = scanner.next();
            System.out.println("Enter your password: ");
            String password = scanner.next();
            System.out.println("Enter your ID: ");
            int id = scanner.nextInt();
            switch (type.toLowerCase()) {
                case "admin":
                   Admin adminh=new Admin();
                   adminh.signup(firstName, lastName, email, contactNumber, type, username, password, id);
                    break;
                case "guest":
                   Guest G1=new Guest( );
                   G1.signup(firstName, lastName, email, contactNumber, type, username, password, id);
                   
                    break;
                case "receptionist":
                   Receptionist REC=new Receptionist( );
                   REC.signup(firstName, lastName, email, contactNumber, type, username, password, id);
                   
                    break;
                default:
                    System.out.println("Invalid role");
                    break;
            }
         break;
             case 3:
             Scanner n=new Scanner (System.in);
            System.out.println("Enter your role (admin, guest, or receptionist): ");
            String rolle = n.next();
            System.out.println("Enter your username: ");
            String usernaame = n.next();
            User.forgetPassword(rolle, usernaame);  
            break;
             case 4:
            user = new Admin();
            user.view("Pending.bin",User.class);
            user.view("Users.bin", User.class);
            break; 
            
        default:
        System.out.println("Enter valid choice");
        break;
        }        
    }
}
