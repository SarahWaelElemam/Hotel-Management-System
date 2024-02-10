/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package user;

import com.sun.javafx.logging.PlatformLogger.Level;
import java.io.File;
import java.io.FileOutputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.System.Logger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import user.AdditionalServices;
import user.Admin;
import user.Guest;
import user.Receptionist;
import user.Reservation;
import user.Room;
import user.User;
import static user.User.loadFromFile;

/**
 *
 * @author SarahWael
 */
public class GUI extends Application {
    private Stage receptionistWindow; 
    private VBox vbox1;
    private VBox vbox2;
    private VBox vbox3;
    private VBox vbox4;
    private int selectedReservationNum = -1; // Track the selected reservation number
    private int selectedRating = 0;
    Button UserButton; 
    Button RoomButton; 
    Button roomManagementButton;
    Button additionalServicesButton;
    Button dashboardButton;
    private Button selectedButton;
    private TableView<Room> roomTable1;
    private TableView<User> UserTable1;
    private TableView<Reservation> RESTable1;
    private ObservableList<Room> roomList = FXCollections.observableArrayList();
    private TextField searchTextField;
    private TextField ASsearchTextField;
    private TextField UsersearchTextField;
    private ObservableList<AdditionalServices> AS = FXCollections.observableArrayList();
    private TableView<AdditionalServices> ASTable;
    private ObservableList<User> userList;
    private ObservableList<Reservation> RESList = FXCollections.observableArrayList();
    private Admin admin=new Admin();
    private Receptionist rec=new Receptionist();
    private Guest guest=new Guest();
    private User userR;
    @Override
    public void start(Stage primaryStage) {
       primaryStage.setTitle("Login Window");

        TextField usernameTextField = new TextField();
        HBox usernameHBox = createInputHBox("Username", usernameTextField);
        PasswordField pass= new PasswordField();
        HBox passwordHBox = createInputHBox("Password", pass);
        HBox buttonsHBox = new HBox(10);
        Button loginButton = new Button("Login");
        Button signupButton = new Button("Sign Up");
        buttonsHBox.getChildren().addAll(loginButton, signupButton);
        buttonsHBox.setAlignment(Pos.CENTER);
        Label forgetPasswordLabel = new Label("Forget Password?");
        forgetPasswordLabel.setOnMouseClicked(e -> {
            forgetpasswordWindow(primaryStage);
        });

        loginButton.setOnAction(e -> {
            String username = usernameTextField.getText();
            String password = pass.getText();
            User role = User.login(username, password);
            if (role != null) {
            handleLoginSuccess(primaryStage,role);
            } else {
            User rolle=null;
            handleLoginSuccess(primaryStage,rolle);
            }
            });
        signupButton.setOnAction(e->{
            signupWindow(primaryStage);
        });
        VBox mainVBox = new VBox(20);
        mainVBox.getChildren().addAll(usernameHBox, passwordHBox,forgetPasswordLabel, buttonsHBox);
        mainVBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(mainVBox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    private HBox createInputHBox(String label, TextField textField) {
        Label nameLabel = new Label(label);
        HBox inputHBox = new HBox(10);
        inputHBox.getChildren().addAll(nameLabel, textField);
        inputHBox.setAlignment(Pos.CENTER);
        return inputHBox;
    }
    private void handleLoginSuccess(Stage primaryStage, User role) {
        Stage currentStage = (Stage) primaryStage;
       if (role instanceof Admin) {
           openAdminWindow(role,currentStage);
       }
       else if(role instanceof Guest)
       {  
            openGuestWindow(currentStage);  
       }
            else if(role instanceof Receptionist)
            {
             openReceptionistWindow(currentStage);
            }
            else{
            showAlert("Warning", "Wrong username or Password.");
             }
    }
    private void openAdminWindow(User role,Stage loginStage) {
    loginStage.close();
    Stage adminWindow = new Stage();
    UserButton = new Button("User Management");
    RoomButton = new Button("Reservation Management");
    roomManagementButton = new Button("Room Management");
    additionalServicesButton = new Button("Services Management");
    dashboardButton = new Button("Dashboard");
    UserButton.setStyle("-fx-background-color: white; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 16;");
    RoomButton.setStyle("-fx-background-color: white; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 16;");
    roomManagementButton.setStyle("-fx-background-color: white; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 16;");
    additionalServicesButton.setStyle("-fx-background-color: white; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 16;");
    dashboardButton.setStyle("-fx-background-color: white; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 16;");

    ScrollPane ASTable=createASTable();
    ScrollPane roomTable = createRoomTable();
    ScrollPane UserTable=createUserTable();
    ScrollPane RESTable=createRESTable();
    roomManagementButton.setOnAction(e -> handleRoomManagement(adminWindow,roomTable));
    additionalServicesButton.setOnAction(e->handleadditionalServices(adminWindow,ASTable));
    UserButton.setOnAction(e->handleUser (adminWindow,UserTable));
    RoomButton.setOnAction(e->handleReservation(adminWindow,RESTable));
    dashboardButton.setOnAction(e->handleDashBoard(adminWindow));
    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.TOP_LEFT);   
    vbox1 = createColoredVBox(600, 100, "#044974");
    vbox2 = createColoredVBox(250, 150, "#eceeed");
    vbox3 = createColoredVBoxWithButtons(300, 400, "white", 
    dashboardButton, roomManagementButton, additionalServicesButton,
    UserButton,RoomButton);
    vbox4 = createColoredVBox(850, 450, "#e4e4e5");
    Label hotelLabel = new Label("Hotel");
    hotelLabel.setFont(Font.font("Times New Roman (Headings CS)", FontWeight.BOLD, 40));
    hotelLabel.setTextFill(Color.valueOf("#26a7f7"));
    Label managementLabel= new Label("Management System");
    managementLabel.setFont(Font.font("Times New Roman (Headings CS)", FontWeight.BOLD, 40));
    managementLabel.setTextFill(Color.valueOf("white"));
    HBox labelhbox=new HBox (5,hotelLabel,managementLabel);
    labelhbox.setAlignment(Pos.CENTER);
    labelhbox.setPadding(new Insets(30));
    vbox1.getChildren().addAll(labelhbox);
    ImageView userPhoto = new ImageView(new Image("file:///C:/Users/hanam/Desktop/user.png"));
    userPhoto.setFitWidth(100);
    userPhoto.setFitHeight(100);

    Label onlineLabel = new Label("Online");
    onlineLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    onlineLabel.setTextFill(Color.GREEN);
    Label usernameLabel = new Label(role.getAUsername()); // Replace with the actual username
    usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    usernameLabel.setTextFill(Color.BLACK);
    VBox textuser=new VBox(5 ,onlineLabel, usernameLabel);
    textuser.setAlignment(Pos.CENTER);
    HBox user=new HBox(5,userPhoto,textuser);
    user.setAlignment(Pos.CENTER);
    vbox2.getChildren().addAll(user);
    
    gridPane.add(vbox1, 1, 0);
    gridPane.add(vbox2, 0, 0);
     gridPane.add(vbox3, 0, 1, 2, 1);
    gridPane.add(vbox4, 1, 1);

    Scene adminScene = new Scene(gridPane, 1105, 605); 
    adminWindow.setTitle("Admin Dashboard");
    adminWindow.setScene(adminScene);
    adminWindow.show();
}
    private VBox createColoredVBox(double width, double height, String color) {
    VBox vbox = new VBox();
    vbox.setMinSize(width, height);
    vbox.setBackground(new Background(new BackgroundFill(Color.valueOf(color), CornerRadii.EMPTY, Insets.EMPTY)));
    return vbox;
    }
    private ScrollPane createRoomTable() {
    TableView<Room> roomTable = new TableView<>();

    TableColumn<Room, Integer> roomNumberColumn = new TableColumn<>("Room Number");
    roomNumberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getRoomNum()).asObject());

    TableColumn<Room, String> categoryColumn = new TableColumn<>("Category");
    categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

    TableColumn<Room, Double> priceColumn = new TableColumn<>("Price");
    priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

    TableColumn<Room, Room.RoomStatus> statuscolumn = new TableColumn<>("Status");
    statuscolumn.setCellValueFactory(cellData -> new SimpleObjectProperty (cellData.getValue().getStatus()));

    TableColumn<Room, Integer> childcolumn = new TableColumn<>("Dependents");
    childcolumn.setCellValueFactory(cellData -> new SimpleIntegerProperty (cellData.getValue().getNumOfChildren()).asObject());

    TableColumn<Room, Integer> adultscolumn = new TableColumn<>("Adults");
    adultscolumn.setCellValueFactory(cellData -> new SimpleIntegerProperty (cellData.getValue().getNumOfAdults()).asObject());

    TableColumn<Room, Double> revenuecolumn = new TableColumn<>("Revenue");
    revenuecolumn.setCellValueFactory(cellData -> new SimpleDoubleProperty (cellData.getValue().getRevenue()).asObject());

    TableColumn<Room, Integer> rescolumn = new TableColumn<>("Reservations");
    rescolumn.setCellValueFactory(cellData -> new SimpleIntegerProperty (cellData.getValue().getNumOfRes()).asObject());

    statuscolumn.setCellFactory(column -> new TableCell<Room, Room.RoomStatus>() {
    @Override
    protected void updateItem(Room.RoomStatus item, boolean empty) {
        super.updateItem(item, empty);

        if (item == null || empty) {
            setText(null);
            setStyle(""); // Reset style
        } else {
            setText(item.toString());

            // Apply styles based on RoomStatus
            if (item == Room.RoomStatus.BOOKED) {
                setStyle("-fx-background-color: #d5243c; -fx-text-fill: white;"); // Red for BOOKED
            } else {
                setStyle("-fx-background-color: #2ad600; -fx-text-fill: black;"); // Green for NOT_BOOKED
            }
        }
    }
});
    
    roomTable.getColumns().addAll(roomNumberColumn, categoryColumn, priceColumn,statuscolumn,childcolumn,adultscolumn,rescolumn,revenuecolumn);
    roomTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    roomTable.setPrefSize(830, 450);
    roomTable1 = new TableView<>();
    searchTextField = new TextField();
    Button addRoomButton = new Button("Add Room");
    Button removeButton = new Button("Remove Room");
    Button editRoomButton = new Button("Edit Room");
    
    double controlWidth = 0.2 * roomTable.getPrefWidth();
    searchTextField.setPrefWidth(controlWidth);
    addRoomButton.setPrefWidth(controlWidth);
    removeButton.setPrefWidth(controlWidth);
    editRoomButton.setPrefWidth(controlWidth);
    searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
    try {
        int searchValue = Integer.parseInt(newValue);
        updateTableWithSearchResult(searchValue);
    } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a valid integer.");
    }
});
    addRoomButton.setOnAction(event -> handleAddRoomButton());
    removeButton.setOnAction(event -> handleremoveButton());
    editRoomButton.setOnAction(event -> handleEditRoomButton());

    // Create an HBox to contain the controls (searchTextField, searchButton, addRoomButton, removeButton)
    HBox controlBox = new HBox(10, searchTextField,  addRoomButton, removeButton,editRoomButton);
    controlBox.setAlignment(Pos.CENTER);
    controlBox.setPadding(new Insets(10));

    admin = new Admin(); 
    ArrayList<Room> roomsFromAdmin = admin.viewroom();
    roomList.clear();
    roomList.addAll(roomsFromAdmin);
    roomTable.setItems(roomList);
    VBox vbox = new VBox(controlBox, new ScrollPane(roomTable));
    vbox.setPrefSize(830, 450);

    return new ScrollPane(vbox);
}
    private void handleAddRoomButton() {
        Stage addRoomStage = new Stage();

    Label roomNumberLabel = new Label("Room Number:");
    TextField roomNumberTextField = new TextField();

    Label categoryLabel = new Label("Category:");
    ChoiceBox<String> categoryChoiceBox = new ChoiceBox<>();
    categoryChoiceBox.getItems().addAll("Single", "Double", "Triple", "VIP", "Suite");

    Label priceLabel = new Label("Price:");
    TextField priceTextField = new TextField();

    Button saveRoomButton = new Button("Save Room");

    GridPane addRoomGrid = new GridPane();
    addRoomGrid.setAlignment(Pos.CENTER);
    addRoomGrid.setHgap(10);
    addRoomGrid.setVgap(10);

    addRoomGrid.add(roomNumberLabel, 0, 0);
    addRoomGrid.add(roomNumberTextField, 1, 0);
    addRoomGrid.add(categoryLabel, 0, 1);
    addRoomGrid.add(categoryChoiceBox, 1, 1);
    addRoomGrid.add(priceLabel, 0, 2);
    addRoomGrid.add(priceTextField, 1, 2);
    addRoomGrid.add(saveRoomButton, 0, 3, 2, 1);

    Scene addRoomScene = new Scene(addRoomGrid, 400, 200);
    addRoomStage.setScene(addRoomScene);
    addRoomStage.setTitle("Add Room");
    addRoomStage.show(); 

    saveRoomButton.setOnAction(e -> 
    {
    try {
        int roomNum = Integer.parseInt(roomNumberTextField.getText());
        String category = categoryChoiceBox.getValue(); // Get the selected category
        double price = Double.parseDouble(priceTextField.getText());

        // Assuming you have a reference to the Admin instance, replace 'admin' with your reference
        Admin admin = new Admin(); // Replace with your admin instance

        // Check if the room number already exists in the list of rooms
        boolean isRoomAlreadyAdded = admin.searchRoom(roomNum);

        if (isRoomAlreadyAdded) {
            showAlert("Warning", "This room is already added! It can't be added again.");
        } else {
            admin.addRoom(roomNum, category, price);
            roomList.clear();
            roomList.addAll(admin.viewroom());
        }
    } catch (NumberFormatException ex) {
            showAlert("Error", "Please enter valid room number and price.");
            ex.printStackTrace(); // Print the exception stack trace
        } catch (IOException ex) {
            showAlert("Error", "Error occurred while adding the room. Please try again.");
            ex.printStackTrace(); // Print the exception stack trace
        }
    }
    );
    }
    private void handleRoomManagement(Stage adminStage,ScrollPane roomTable) {
        UserButton.setStyle("-fx-background-color: white; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 16;");
        RoomButton.setStyle("-fx-background-color: white; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 16;");
        additionalServicesButton.setStyle("-fx-background-color: white; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 16;");
        dashboardButton.setStyle("-fx-background-color: white; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 16;");
        roomManagementButton.setStyle("-fx-background-color: #044974; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: white;; -fx-font-size: 16;");
        vbox4.getChildren().clear();
        vbox4.getChildren().addAll(roomTable);
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.add(vbox1, 1, 0);
        gridPane.add(vbox2, 0, 0);
        gridPane.add(vbox3, 0, 1, 2, 1);
        gridPane.add(vbox4, 1, 1);
        Scene adminScene = new Scene(gridPane, 1105, 605);
        adminStage.setTitle("Room Mangement");
        adminStage.setScene(adminScene);
        adminStage.show();
    }
    private VBox createColoredVBoxWithButtons(double width, double height, String color, Button... buttons) {
        VBox vbox = createColoredVBox(width, height, color);
        vbox.getChildren().addAll(buttons);
        for (Button button : buttons) {
        VBox.setVgrow(button, Priority.ALWAYS);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMinSize(width / buttons.length, height / buttons.length);
        button.setAlignment(Pos.CENTER_LEFT);
    }
    return vbox;
    }  
    private ScrollPane createASTable() {
     ASTable = new TableView<>();

    TableColumn<AdditionalServices, String> ASColumn = new TableColumn<>("Service");
    ASColumn.setCellValueFactory(new PropertyValueFactory<>("additionalSer"));

    TableColumn<AdditionalServices, Integer> priceColumn = new TableColumn<>("Price");
    priceColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPrice()).asObject());

    TableColumn<AdditionalServices, Integer> revenuecolumn = new TableColumn<>("Revenue");
    revenuecolumn.setCellValueFactory(cellData -> new SimpleIntegerProperty ((int) cellData.getValue().getRevenue()).asObject());

    TableColumn<AdditionalServices, Integer> reqcolumn = new TableColumn<>("Requests");
    reqcolumn.setCellValueFactory(cellData -> new SimpleIntegerProperty (cellData.getValue().getNumOfReq()).asObject());

    ASTable.getColumns().addAll(ASColumn, priceColumn,reqcolumn,revenuecolumn);
    ASTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    ASTable.setPrefSize(830,450); // Adjust the height of the table as needed

    
    // Create searchTextField, addRoomButton, searchButton, and removeButton
     ASsearchTextField = new TextField();
    Button editButton = new Button("Edit Service");
    Button addASButton = new Button("Add Service");
    Button removeButton = new Button("Remove Service");
    double controlWidth = 0.2 * ASTable.getPrefWidth();
    ASsearchTextField.setPrefWidth(controlWidth);
    editButton.setPrefWidth(controlWidth);
    addASButton.setPrefWidth(controlWidth);
    removeButton.setPrefWidth(controlWidth);

    addASButton.setOnAction(event -> handleAddASButton());
    removeButton.setOnAction(event -> handleremoveASButton());
    editButton.setOnAction(event -> handleEditASButton());
    ASsearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
    try {
        updateTableWithSearchResult(newValue);
    } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a valid integer.");
    }
});

    HBox controlBox = new HBox(10, ASsearchTextField, addASButton,editButton, removeButton);
    controlBox.setAlignment(Pos.CENTER);
    controlBox.setPadding(new Insets(10));

    admin = new Admin(); 
    ArrayList<AdditionalServices> ASFromAdmin = admin.viewAS();
    if (ASFromAdmin != null)
    ASTable.getItems().clear();
    ASTable.getItems().addAll(ASFromAdmin);
    
    VBox vbox = new VBox(controlBox, new ScrollPane(ASTable));
    vbox.setPrefSize(830,450);

    
    return new ScrollPane(vbox);  
    }
    private void handleAddASButton() {
       Stage addServiceStage = new Stage();

    Label serviceLabel = new Label("Service:");
    TextField serviceTextField = new TextField();

    Label priceLabel = new Label("Price:");
    TextField priceTextField = new TextField();

    Button addServiceButton = new Button("Add Service");

    GridPane addServiceGrid = new GridPane();
    addServiceGrid.setAlignment(Pos.CENTER);
    addServiceGrid.setHgap(10);
    addServiceGrid.setVgap(10);

    addServiceGrid.add(serviceLabel, 0, 0);
    addServiceGrid.add(serviceTextField, 1, 0);
    addServiceGrid.add(priceLabel, 0, 1);
    addServiceGrid.add(priceTextField, 1, 1);
    addServiceGrid.add(addServiceButton, 0, 2, 2, 1);

    Scene addServiceScene = new Scene(addServiceGrid, 400, 150);
    addServiceStage.setScene(addServiceScene);
    addServiceStage.setTitle("Add Service");
    addServiceStage.show();

    addServiceButton.setOnAction(e -> {
        try {
            String serviceName = serviceTextField.getText();
            int servicePrice = Integer.parseInt(priceTextField.getText());

            admin.ASAdd(serviceName, servicePrice);

            showAlert("Success", "Service added successfully!");
            updateTableAS();
            addServiceStage.close();
        } catch (NumberFormatException ex) {
            showAlert("Error", "Please enter a valid price.");
        } catch (IOException ex) {
         showAlert("Error", "Error occurred while editing the room. Please try again.");
       }
    });
    }
    private void handleadditionalServices(Stage adminStage, ScrollPane ASTable) {
        UserButton.setStyle("-fx-background-color: white; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 16;");
        RoomButton.setStyle("-fx-background-color: white; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 16;");
        additionalServicesButton.setStyle("-fx-background-color: #044974; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 16;");
        dashboardButton.setStyle("-fx-background-color: white; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 16;");
        roomManagementButton.setStyle("-fx-background-color: white; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: black;; -fx-font-size: 16;");
        vbox4.getChildren().clear();
        vbox4.getChildren().addAll(ASTable);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_LEFT);


        gridPane.add(vbox1, 1, 0);
        gridPane.add(vbox2, 0, 0);
        gridPane.add(vbox3, 0, 1, 2, 1);
        gridPane.add(vbox4, 1, 1);

        Scene adminScene = new Scene(gridPane, 1105, 605);

        adminStage.setTitle("Services Management");
        adminStage.setScene(adminScene);
        adminStage.show();
    }
    private void handleremoveButton() 
   {
     Stage removeRoomStage = new Stage();

    Label roomNumberLabel = new Label("Room Number:");
    TextField roomNumberTextField = new TextField();

    Button removeRoomButton = new Button("Remove Room");

    GridPane removeRoomGrid = new GridPane();
    removeRoomGrid.setAlignment(Pos.CENTER);
    removeRoomGrid.setHgap(10);
    removeRoomGrid.setVgap(10);

    removeRoomGrid.add(roomNumberLabel, 0, 0);
    removeRoomGrid.add(roomNumberTextField, 1, 0);
    removeRoomGrid.add(removeRoomButton, 0, 1, 2, 1);

    Scene removeRoomScene = new Scene(removeRoomGrid, 400, 150);
    removeRoomStage.setScene(removeRoomScene);
    removeRoomStage.setTitle("Remove Room");
    removeRoomStage.show();

    removeRoomButton.setOnAction(e -> {
        try {
            int roomNum = Integer.parseInt(roomNumberTextField.getText());

            // Assuming you have a reference to the Admin instance, replace 'admin' with your reference
             admin = new Admin(); // Replace with your admin instance

            // Check if the room number exists in the list of rooms
            boolean isRoomExists = admin.searchRoom(roomNum);

            if (isRoomExists) {
                // Room exists, remove it
                admin.removeRoom(roomNum);
                roomList.clear();
                roomList.addAll(admin.viewroom());
                showAlert("Success", "Room removed successfully!");
            } else {
                showAlert("Warning", "This room is not added, it can't be deleted.");
            }

            removeRoomStage.close();
        } catch (NumberFormatException ex) {
            showAlert("Error", "Please enter a valid room number.");
        } catch (IOException ex) {
           showAlert("Error", "Error occurred while removing the room. Please try again.");
        }
    });
    }
    private void handleEditRoomButton() {
        Stage editRoomDetailsStage = new Stage();
    Label roomnumberLabel=new Label ("Room Number:");
    TextField rommNumberTextField = new TextField();
    Label categoryLabel = new Label("New Category:");
    ChoiceBox<String> categoryChoiceBox = new ChoiceBox<>();
    categoryChoiceBox.getItems().addAll("Single", "Double", "Triple", "VIP", "Suite");

    Label priceLabel = new Label("New Price:");
    TextField priceTextField = new TextField();

    Button saveEditButton = new Button("Save Changes");

    GridPane editeditRoomGrid = new GridPane();
    editeditRoomGrid.setAlignment(Pos.CENTER);
    editeditRoomGrid.setHgap(10);
    editeditRoomGrid.setVgap(10);

    editeditRoomGrid.add(roomnumberLabel,0,0);
    editeditRoomGrid.add(rommNumberTextField,1,0);
    editeditRoomGrid.add(categoryLabel, 0, 1);
    editeditRoomGrid.add(categoryChoiceBox, 1, 1);
    editeditRoomGrid.add(priceLabel, 0, 2);
    editeditRoomGrid.add(priceTextField, 1, 2);
    editeditRoomGrid.add(saveEditButton, 1, 3, 3, 2);

    Scene editRoomDetailsScene = new Scene(editeditRoomGrid, 400, 200);
    editRoomDetailsStage.setScene(editRoomDetailsScene);
    editRoomDetailsStage.setTitle("Edit Room Details");
    editRoomDetailsStage.show();

    saveEditButton.setOnAction(event -> {
        try {
            int roomNum = Integer.parseInt(rommNumberTextField.getText());
            String newCategory = categoryChoiceBox.getValue();
            double newPrice = Double.parseDouble(priceTextField.getText());

             admin = new Admin();
              boolean isRoomExists = admin.searchRoom(roomNum);
             if (isRoomExists) {
            admin.editRoom(roomNum, newCategory, newPrice);
            roomList.clear();
                roomList.addAll(admin.viewroom());
                showAlert("Success", "Room edited successfully!");
                 editRoomDetailsStage.close();
             }
         else {
                showAlert("Warning", "This room is not added, it can't be edited.");
            }
           
        } catch (NumberFormatException ex) {
            showAlert("Error", "Please enter a valid price.");
        } catch (IOException | ClassNotFoundException ex) {
           showAlert("Error", "Error occurred while editing the room. Please try again.");
        }
    });
    }
    private void updateTableWithSearchResult(int searchValue) {
    admin = new Admin();
    boolean roomExists = admin.searchRoom(searchValue);

    if (roomExists) {
        // If the room exists, you might want to get additional details or display it
        Room matchedRoom = admin.getRoom(searchValue);

        // Clear existing items in the table and add the matched room
        roomList.clear();
        roomList.add(matchedRoom);
        roomTable1.setItems(roomList);
    }else{
        ArrayList<Room> allRooms = admin.viewroom();
        roomList.clear();
        roomList.addAll(allRooms);
        roomTable1.setItems(roomList);
    } 
}
    private void updateTableAS() {
        ArrayList<AdditionalServices> ASFromAdmin = admin.viewAS();
    if (ASFromAdmin != null) {
        ASTable.getItems().clear();
        ASTable.getItems().addAll(ASFromAdmin);
    }
    }
    private void handleremoveASButton() {
     Stage removeServiceStage = new Stage();

    Label serviceLabel = new Label("Service:");
    TextField serviceTextField = new TextField();

    Button removeServiceButton = new Button("Remove Service");

    GridPane removeServiceGrid = new GridPane();
    removeServiceGrid.setAlignment(Pos.CENTER);
    removeServiceGrid.setHgap(10);
    removeServiceGrid.setVgap(10);

    removeServiceGrid.add(serviceLabel, 0, 0);
    removeServiceGrid.add(serviceTextField, 1, 0);
    removeServiceGrid.add(removeServiceButton, 0, 1, 2, 1);

    Scene removeServiceScene = new Scene(removeServiceGrid, 400, 150);
    removeServiceStage.setScene(removeServiceScene);
    removeServiceStage.setTitle("Remove Service");
    removeServiceStage.show();

    removeServiceButton.setOnAction(e -> {
        try {
            String serviceName = serviceTextField.getText();

            // Use the existing admin instance
            boolean isServiceAdded = admin.ASSearch(serviceName);

            if (isServiceAdded) {
                // Service exists, remove it
                admin.ASRemove(serviceName);
                
                showAlert("Success", "Service removed successfully!");
                updateTableAS();
                removeServiceStage.close();
            } else {
                showAlert("Warning", "This additional service is not added! It can't be removed.");
            }

            removeServiceStage.close();
        } catch (IOException ex) {
            showAlert("Error", "Error occurred while editing the room. Please try again.");
       }
    });   
    }
    private void handleEditASButton() {
        Stage editASDetailsStage = new Stage();
    Label ASLabel=new Label ("Service Name:");
    TextField AStxt=new TextField();
    Label priceLabel = new Label("New Price:");
    TextField priceTextField = new TextField();

    Button saveEditButton = new Button("Save Changes");

    GridPane editASDetailsgrid = new GridPane();
    editASDetailsgrid.setAlignment(Pos.CENTER);
    editASDetailsgrid.setHgap(10);
    editASDetailsgrid.setVgap(10);

    editASDetailsgrid.add(ASLabel,0,0);
    editASDetailsgrid.add(AStxt,1,0);
    editASDetailsgrid.add(priceLabel, 0, 1);
    editASDetailsgrid.add(priceTextField, 1, 1);
    editASDetailsgrid.add(saveEditButton, 0, 2, 2, 1);

    Scene editRoomDetailsScene = new Scene(editASDetailsgrid, 400, 200);
    editASDetailsStage.setScene(editRoomDetailsScene);
    editASDetailsStage.setTitle("Edit Service Details");
    editASDetailsStage.show();

    saveEditButton.setOnAction(event -> {
        try {
            String service = AStxt.getText();
            int newPrice = Integer.parseInt(priceTextField.getText());

             admin = new Admin();
              boolean isRoomExists = admin.ASSearch(service);
             if (isRoomExists) {
            admin.ASEdit(service, newPrice);
      
            showAlert("Success", "Room edited successfully!");
            updateTableAS();
            editASDetailsStage.close();
             }
         else {
                showAlert("Warning", "This room is not added, it can't be edited.");
            }
           
        } catch (NumberFormatException ex) {
            showAlert("Error", "Please enter a valid price.");
        } catch (IOException ex) {
           showAlert("Error", "Error occurred while editing the room. Please try again.");
        }
    });
    }
    private void updateTableWithSearchResult(String searchString) {
    if (searchString == null || searchString.isEmpty()) {
        ASTable.getItems().clear();
        ASTable.getItems().addAll(admin.viewAS());
    } else {
        // Search for the specified string
        AdditionalServices foundService = admin.getAS(searchString);
        if (foundService != null) {
            // If search is successful, update the table with the result
          ASTable.getItems().clear();
          ASTable.getItems().add(foundService);
        } else {
          ASTable.getItems().clear();
          ASTable.getItems().addAll(admin.viewAS());}
    }
}
    private ScrollPane createRESTable() {
       RESTable1 = new TableView<>();
    TableColumn<Reservation, Integer> ResNumColumn = new TableColumn<>("Reservation Number");
    ResNumColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getResNum()).asObject());

     TableColumn<Reservation, LocalDate> CHINColumn = new TableColumn<>("Check IN ");
     CHINColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));

     TableColumn<Reservation, LocalDate> CHOUTColumn = new TableColumn<>("Check Out");
   CHOUTColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
   
   TableColumn<Reservation, Integer> RoomNumColumn = new TableColumn<>("Room Number");
   RoomNumColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getRoom1().getRoomNum()).asObject());

   TableColumn<Reservation, Long> DaysColumn = new TableColumn<>("Total Days");
   DaysColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getTotalDays()).asObject());

    TableColumn<Reservation, String> recnamecolumn = new TableColumn<>("Receptionist");
    recnamecolumn.setCellValueFactory(cellData -> new SimpleStringProperty (cellData.getValue().getRecName()));

    TableColumn<Reservation, Reservation.ResStatus> statuscolumn = new TableColumn<>("Status");
    statuscolumn.setCellValueFactory(cellData -> new SimpleObjectProperty (cellData.getValue().getStatus()));
    statuscolumn.setCellFactory(column -> new TableCell<Reservation, Reservation.ResStatus> () {
    @Override
    protected void updateItem(Reservation.ResStatus item, boolean empty) {
        super.updateItem(item, empty);

        if (item == null || empty) {
            setText(null);
            setStyle(""); // Reset style
        } else {
            setText(item.toString());

            // Apply styles based on RoomStatus
            if (item == Reservation.ResStatus.CANCELLED) {
                setStyle("-fx-background-color: #d5243c; -fx-text-fill: white;"); // Red for BOOKED
            } else if(item == Reservation.ResStatus.CHECKED_IN){
                setStyle("-fx-background-color: #2ad600; -fx-text-fill: black;"); // Green for NOT_BOOKED
            }
            else if(item == Reservation.ResStatus.CHECKED_OUT)
            {
                setStyle("-fx-background-color: #bdbebe; -fx-text-fill: black;"); 
            }
        }
    }
});
    RESTable1.getColumns().addAll(ResNumColumn, CHINColumn,CHOUTColumn,RoomNumColumn,DaysColumn,recnamecolumn,statuscolumn);
    RESTable1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    RESTable1.setPrefSize(830,450); // Adjust the height of the table as needed
    Button recycleButton = new Button("Recycle Bin");
    ComboBox<String> filterComboBox = new ComboBox<>();
    filterComboBox.getItems().addAll("All", "Check In", "Check Out", "Pending");
    filterComboBox.setValue("All");
    double controlWidth = 0.2 * RESTable1.getPrefWidth();
    recycleButton.setPrefWidth(controlWidth);
    recycleButton.setOnAction(event -> handlerecycleButton());
    filterComboBox.setOnAction(event -> {
        String selectedStatus = filterComboBox.getValue();
        updatedresTable(selectedStatus);
    });
    HBox controlBox = new HBox(10, filterComboBox, recycleButton);
    controlBox.setAlignment(Pos.CENTER);
    controlBox.setPadding(new Insets(10)); 
    List<Reservation> ASFromAdmin = Reservation.loadReservationList("Reservations.bin");
        RESTable1.getItems().clear();
        RESTable1.getItems().addAll(ASFromAdmin);
    
    VBox vbox = new VBox(controlBox, new ScrollPane(RESTable1));
    vbox.setPrefSize(830,450);

    
    return new ScrollPane(vbox);  
    }
    private void handleReservation(Stage adminWindow, ScrollPane RESTable) {
     UserButton.setStyle("-fx-background-color: white; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 16;");
        RoomButton.setStyle("-fx-background-color: #044974; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 16;");
        additionalServicesButton.setStyle("-fx-background-color: white; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 16;");
        dashboardButton.setStyle("-fx-background-color: white; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 16;");
        roomManagementButton.setStyle("-fx-background-color: white; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: black;; -fx-font-size: 16;");
        vbox4.getChildren().clear();
        vbox4.getChildren().addAll(RESTable);
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.add(vbox1, 1, 0);
        gridPane.add(vbox2, 0, 0);
        gridPane.add(vbox3, 0, 1, 2, 1);
        gridPane.add(vbox4, 1, 1);
        Scene adminScene = new Scene(gridPane, 1105, 605);
        adminWindow.setTitle("Reservation Managment");
        adminWindow.setScene(adminScene);
        adminWindow.show();
    } 
private void forgetpasswordWindow(Stage primaryStage) 
{
    Stage forgetPasswordWindow = new Stage();
    forgetPasswordWindow.setTitle("Forget Password");

    GridPane grid = new GridPane();
    grid.setPadding(new Insets(20, 20, 20, 20));
    grid.setVgap(8);
    grid.setHgap(10);

    Label instructionLabel = new Label("Write your username here:");
    GridPane.setConstraints(instructionLabel, 0, 0);

    TextField usernameInput = new TextField();
    GridPane.setConstraints(usernameInput, 0, 1);

    Label roleLabel = new Label("Role:");
    GridPane.setConstraints(roleLabel, 0, 3);

    ComboBox<String> roleComboBox = new ComboBox<>();
    roleComboBox.getItems().addAll("Admin", "Guest", "Receptionist");
    roleComboBox.setValue("Role");
    GridPane.setConstraints(roleComboBox, 0, 4);

    Button recoverButton = new Button("Recover Password");
    GridPane.setConstraints(recoverButton, 0, 5);

    recoverButton.setOnAction(e -> {
        String username = usernameInput.getText();
        String selectedRole = roleComboBox.getValue();
        List<User> userList = (List<User>) loadFromFile("Users.bin");
        User[] userTooRecover = {null};

        for (User user : userList) {
            if (user.getAUsername().equals(username) && user.getRole().equals(selectedRole)) {
                userTooRecover[0] = user;
                break;
            }
        }

        if (userTooRecover[0] != null) {
            String maskedPhoneNumber = userR.maskPhoneNumber(userTooRecover[0].getPhoNumber());
            forgetPasswordWindow.close();

            Stage recover = new Stage();
            Label starphone = new Label("Write the phone number that ends with " + maskedPhoneNumber);
            TextField startxt = new TextField();
            Button submit = new Button("Submit");
            VBox star = new VBox(15, starphone, startxt, submit);
            Scene scene1 = new Scene(star, 300, 200);
            recover.setScene(scene1);
            recover.show();
            recover.setTitle("Recover");

            submit.setOnAction(submitEvent -> {
                String enteredDigits = startxt.getText();

                if (userR.verifyPhoneNumber(userTooRecover[0].getPhoNumber(), enteredDigits)) {
                    recover.close();

                    Stage newpass = new Stage();
                    Label newppass = new Label("New Password");
                    TextField newppasstxt = new TextField();
                    VBox pass1 = new VBox(15, newppass, newppasstxt);
                    Button submitNewPassword = new Button("Submit");
                    VBox pass2 = new VBox(15, pass1, submitNewPassword);
                    VBox collect = new VBox(15, pass2);
                    Scene scene2 = new Scene(collect, 300, 200);
                    newpass.setScene(scene2);
                    newpass.show();
                    newpass.setTitle("New Password");

                    submitNewPassword.setOnAction(submitNewPasswordEvent -> {
                        String newPassword = newppasstxt.getText();

                        // Update the password in the ArrayList
                        userTooRecover[0].setApass(newPassword);

                        // Update the password in the ArrayList
                        for (int i = 0; i < userList.size(); i++) {
                            if (userList.get(i).getAUsername().equals(username) && userList.get(i).getRole().equals(selectedRole)) {
                                userList.set(i, userTooRecover[0]);
                                break;
                            }
                        }

                        // Save the updated user list to file
                        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Users.bin", false))) {
                            oos.writeObject(userList);
                            System.out.println("User list saved to file.");
                        } catch (IOException ex) {
                            System.out.println("Error saving user list to file: " + ex.getMessage());
                        }

                        System.out.println("Password updated successfully!");
                        newpass.close();
                    });
                } else {
                    System.out.println("Incorrect phone number. Password recovery failed.");
                }
            });
        } else {
            System.out.println("User not found. Password recovery failed.");
        }
    });

    grid.getChildren().addAll(instructionLabel, usernameInput, roleLabel, roleComboBox, recoverButton);

    Scene forgetPasswordScene = new Scene(grid, 300, 200);
    forgetPasswordWindow.setScene(forgetPasswordScene);
    forgetPasswordWindow.show();
    primaryStage.close();

}


    private void signupWindow(Stage primaryStage) {
       primaryStage.close();
    Stage signupWindow = new Stage();

    // Set background color
    String backgroundColor = "-fx-background-color: #044974;";

    Label fname = new Label("First Name");
    fname.setStyle("-fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-size: 12; -fx-font-weight: bold;");
    TextField fnametxt = new TextField();
    HBox fnameHBOX = new HBox(15, fname, fnametxt);

    Label lname = new Label("Last Name");
    lname.setStyle("-fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-size: 12; -fx-font-weight: bold;");
    TextField lnametxt = new TextField();
    HBox lnameHBOX = new HBox(15, lname, lnametxt);

    Label email = new Label("Email");
    email.setStyle("-fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-size: 12; -fx-font-weight: bold;");
    TextField emailPrefix = new TextField();
    TextField emailSuffix = new TextField("@gmail.com");
    emailSuffix.setDisable(true);
    HBox emailHBOX = new HBox(15, email, emailPrefix, emailSuffix);

    Label phone = new Label("Contact Number");
    phone.setStyle("-fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-size: 12; -fx-font-weight: bold;");
    TextField phoneSuffix = new TextField("+20");
    phoneSuffix.setPrefWidth(50);
    phoneSuffix.setMaxWidth(50);
    phoneSuffix.setDisable(true);
    TextField phonetxt = new TextField();
    HBox phoneHBOX = new HBox(15, phone, phoneSuffix, phonetxt);

    Label id = new Label("ID");
    id.setStyle("-fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-size: 12; -fx-font-weight: bold;");
    TextField idtxt = new TextField();
    HBox idHBOX = new HBox(15, id, idtxt);

    ComboBox<String> roleComboBox = new ComboBox<>();
    roleComboBox.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 12; -fx-font-weight: bold;");
    roleComboBox.getItems().addAll("Admin", "Guest", "Receptionist");
    roleComboBox.setValue("Role");

    Label role = new Label("Role");
    role.setStyle("-fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-size: 12; -fx-font-weight: bold;");
    HBox roleHBOX = new HBox(15, role, roleComboBox);

    Label username = new Label("Username");
    username.setStyle("-fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-size: 12; -fx-font-weight: bold;");
    TextField usernametxt = new TextField();
    HBox usernameHBOX = new HBox(15, username, usernametxt);
    Button alreadyHaveAnAccount_Button = new Button("already Have An Account?");
    Label pass = new Label("Password");
    pass.setStyle("-fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-size: 12; -fx-font-weight: bold;");
    PasswordField passtxt = new PasswordField();
    HBox passHBOX = new HBox(15, pass, passtxt);
    alreadyHaveAnAccount_Button.setUnderline(true);
        alreadyHaveAnAccount_Button.setStyle(
                "-fx-border-color: transparent; "
                + "-fx-background-color: transparent; "
                + "-fx-text-fill: black; "
                + "-fx-cursor: hand;"
        );

        alreadyHaveAnAccount_Button.setOnMouseEntered(e -> {
            alreadyHaveAnAccount_Button.setStyle("-fx-border-color: transparent; "
                    + "-fx-background-color: transparent; "
                    + "-fx-text-fill: blue; "
                    + "-fx-cursor: hand;");
        });

        alreadyHaveAnAccount_Button.setOnMouseExited(e -> {
            alreadyHaveAnAccount_Button.setStyle("-fx-border-color: transparent; "
                    + "-fx-background-color: transparent; "
                    + "-fx-text-fill: black; "
                    + "-fx-cursor: hand;");
        });
        alreadyHaveAnAccount_Button.setOnAction((t) -> {
            start(primaryStage);
            signupWindow.close();
            
        });

    Button signUpButton = new Button("Sign Up");
    signUpButton.setStyle("-fx-background-color: #26a7f7; -fx-text-fill: white; -fx-font-weight: bold;");
    HBox but=new HBox(15,signUpButton,alreadyHaveAnAccount_Button);
    VBox vbox = new VBox(15, fnameHBOX, lnameHBOX, emailHBOX, phoneHBOX, idHBOX, roleHBOX, usernameHBOX, passHBOX, but);
    vbox.setAlignment(Pos.CENTER);
    vbox.setPadding(new Insets(10));
    vbox.setStyle(backgroundColor);

    
        Scene scene = new Scene (vbox,400,400);
         signupWindow.setScene(scene);
        signUpButton.setOnAction(e -> {
            if (isAnyFieldEmpty(fnametxt, lnametxt, emailPrefix, phonetxt, usernametxt, passtxt,idtxt) || isComboBoxEmpty(roleComboBox)) {
             showAlert("Error", "All fields are required");
             return; // Stop further execution
            }
        
        String firstName = fnametxt.getText();
        String lastName = lnametxt.getText();
        String emailPrefixText = emailPrefix.getText();
        String phoneNumber = phonetxt.getText();
        String selectedRole = roleComboBox.getValue();
        String usernameText = usernametxt.getText();
        String passwordText = passtxt.getText();
        String idText = idtxt.getText();
        int idd=0;

         try {
        idd = Integer.parseInt(idText);
    // Now 'id' contains the integer value from the text field
             } catch (NumberFormatException el) {
    // Handle the case where the input is not a valid integer
        System.err.println("Invalid input for id: " + idText);
        }
        if (phoneNumber.length()!=10)
        {
            showAlert("Error","Phone number must contains 10 numbers");
        }
        else if (passwordText.length()<8)
        {
            showAlert("Error","Password must contains more than 8 Characters");
        }
        else if(isPhoneExist(phoneNumber))
        {
            showAlert("Error","There is Already an account with this phone number ! login or press forget password");
        }
        else{
            if(selectedRole.equalsIgnoreCase("admin"))
            {
                try {
                    admin.signup(firstName, lastName, emailPrefixText, phoneNumber, selectedRole, usernameText, passwordText,idd );
                     displayMessage("Your account Successfully created ! Now wait for the admin to confirm your registeration");
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
            else if(selectedRole.equalsIgnoreCase("receptionist"))
            {
                try {
                    rec.signup(firstName, lastName, emailPrefixText, phoneNumber, selectedRole, usernameText, passwordText, idd);
                    displayMessage("Your account Successfully created ! Now wait for the admin to confirm your registeration");
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
            else if(selectedRole.equalsIgnoreCase("guest"))
            {
                try {
                    guest.signup(firstName, lastName, emailPrefixText, phoneNumber, selectedRole, usernameText, passwordText, idd);
                    displayMessage("Your account Successfully created !");
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
        }
        });
        signupWindow.setTitle("signup Window");
        signupWindow.show();
        primaryStage.close();
    }

    private ScrollPane createUserTable() {
    TableView<User> UserTable = new TableView<>();
   userList = FXCollections.observableArrayList();
   TableColumn<User, String> fnameColumn = new TableColumn<>("First Name");
   fnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFname())));

    TableColumn<User, String> lnameColumn = new TableColumn<>("Last Name");
   lnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getLname())));

   TableColumn<User, String> phonecolumn = new TableColumn<>("Phone Number");
   phonecolumn.setCellValueFactory(cellData -> {
    String phoneNumber = cellData.getValue().getPhoNumber();
    String formattedPhoneNumber = "+20" + phoneNumber;
    return new SimpleStringProperty(formattedPhoneNumber);
   });
   TableColumn<User, String> emailcolumn = new TableColumn<>("Email");
   emailcolumn.setCellValueFactory(cellData -> {
    String email = cellData.getValue().getEmail();
    String formattedemail = email+"@gmail.com";
    return new SimpleStringProperty(formattedemail);
   });
    TableColumn<User, Integer> idcolumn = new TableColumn<>("ID");
    idcolumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getID()).asObject());

   TableColumn<User, String> roleColumn = new TableColumn<>("Role");
   roleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRole()));

    TableColumn<User, User.Status> statuscolumn = new TableColumn<>("Status");
    statuscolumn.setCellValueFactory(cellData -> new SimpleObjectProperty (cellData.getValue().getStatus()));

    statuscolumn.setCellFactory(column -> new TableCell<User, User.Status>() {
    @Override
    protected void updateItem(User.Status item, boolean empty) {
        super.updateItem(item, empty);

        if (item == null || empty) {
            setText(null);
            setStyle(""); // Reset style
        } else {
            setText(item.toString());

            // Apply styles based on RoomStatus
            if (item == User.Status.DECLINED) {
                setStyle("-fx-background-color: #d5243c; -fx-text-fill: white;"); 
            } else if (item==User.Status.CONFIRMED){
                setStyle("-fx-background-color: #2ad600; -fx-text-fill: black;"); 
            }else if(item==User.Status.REMOVED){
                setStyle("-fx-background-color: #bdbebe; -fx-text-fill: black;"); 
            }
        }
    }
});
    
    UserTable.getColumns().addAll(fnameColumn, lnameColumn, phonecolumn,emailcolumn,idcolumn,roleColumn,statuscolumn);
    UserTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    UserTable.setPrefSize(830, 450);
    UserTable1 = new TableView<>();
    UsersearchTextField = new TextField();
    Button adduserButton = new Button("Add User");
    Button removeButton = new Button("Remove User");
    Button editUserButton = new Button("Edit User");
    
    double controlWidth = 0.2 * UserTable.getPrefWidth();
    UsersearchTextField.setPrefWidth(controlWidth);
    adduserButton.setPrefWidth(controlWidth);
    removeButton.setPrefWidth(controlWidth);
    editUserButton.setPrefWidth(controlWidth);
    UsersearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
    try {
        UserSearchResult(newValue);
    } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a valid text.");
    }
});
    adduserButton.setOnAction(event -> handleAddUserButton());
    removeButton.setOnAction(event -> handleremoveUserButton());
    editUserButton.setOnAction(event -> handleEditUserButton(UserTable));

    // Create an HBox to contain the controls (searchTextField, searchButton, addRoomButton, removeButton)
    HBox controlBox = new HBox(10, UsersearchTextField,  adduserButton, removeButton,editUserButton);
    controlBox.setAlignment(Pos.CENTER);
    controlBox.setPadding(new Insets(10));
    List<? extends User> UserFromAdmin = User.loadFromFile("Users.bin");
    userList.clear();
    userList.addAll(UserFromAdmin);
    UserTable.setItems(userList);
    VBox vbox = new VBox(controlBox, new ScrollPane(UserTable));
    vbox.setPrefSize(830, 450);

    return new ScrollPane(vbox);
    }

private void handleUser(Stage adminWindow, ScrollPane userTableScrollPane) {
        UserButton.setStyle("-fx-background-color: #044974; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 16;");
        RoomButton.setStyle("-fx-background-color: white; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 16;");
        additionalServicesButton.setStyle("-fx-background-color: white; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 16;");
        dashboardButton.setStyle("-fx-background-color: white; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 16;");
        roomManagementButton.setStyle("-fx-background-color: white; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: black;; -fx-font-size: 16;");
        vbox4.getChildren().clear();
        vbox4.getChildren().addAll(userTableScrollPane);
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.add(vbox1, 1, 0);
        gridPane.add(vbox2, 0, 0);
        gridPane.add(vbox3, 0, 1, 2, 1);
        gridPane.add(vbox4, 1, 1);
        
        Scene adminScene = new Scene(gridPane, 1105, 605);
        adminWindow.setTitle("User Mangement");
        adminWindow.setScene(adminScene);
        adminWindow.show();
    }
private void handleAddUserButton() {
    Stage addUserStage = new Stage();
    VBox addUserLayout = new VBox(10);
    addUserLayout.setAlignment(Pos.CENTER);

    Button addPendingButton = new Button("Add Pending User");
    Button addNewButton = new Button("Add New User");

    addPendingButton.setOnAction(e -> openAddUserWindow(true));
    addNewButton.setOnAction(e -> openAddUserWindow(false));
    addUserLayout.getChildren().addAll(addPendingButton, addNewButton);

    
    Scene addUserScene = new Scene(addUserLayout, 300, 200);
    addUserStage.setScene(addUserScene);
    addUserStage.setTitle("Add User");
    addUserStage.show();
    }
private void handleremoveUserButton() {
    Stage removeUserDetails = new Stage();
    List<User> userListt = (List<User>) User.loadFromFile("Users.bin");
    ComboBox<User> userComboBox = new ComboBox<>();
    userComboBox.getItems().addAll(userListt);
    Button removeButton = new Button("Remove");
    removeButton.setOnAction(event -> {
        User selectedUser=userComboBox.getValue();
       boolean done= admin.removeUserGUI(selectedUser,userListt);
        if(done)
        {
           updatedUserTable();
           showAlert("Success", "User Removed successfully!");
           removeUserDetails.close(); 
        
        } else {
            showAlert("Error", "Failed To removed.");
        }
    });
    VBox removeLayout = new VBox(10,userComboBox,removeButton);
    removeLayout.setAlignment(Pos.CENTER);
    Scene removeScene = new Scene(removeLayout, 300, 200);

    removeUserDetails.setScene(removeScene);
    removeUserDetails.setTitle("Remove User");
    removeUserDetails.show();
   
    }
private void handleEditUserButton(TableView<User> UserTable) {
    Stage editUserDetails = new Stage();
    List<User> userListt = (List<User>) User.loadFromFile("Users.bin");
    ComboBox<User> userComboBox = new ComboBox<>();
    userComboBox.getItems().addAll(userListt);
    TextField phoneNumberTextField = new TextField();
    TextField emailTextField = new TextField();
    ComboBox<User.Status> statusComboBox = new ComboBox<>();
    statusComboBox.getItems().addAll(User.Status.values());
    Button editButton = new Button("Edit");

    userComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            User selectedUser = newValue;

            // Fill other fields based on the selected user
            phoneNumberTextField.setText(selectedUser.getPhoNumber());
            emailTextField.setText(selectedUser.getEmail());
            statusComboBox.setValue(selectedUser.getStatus());

            editButton.setOnAction(event -> {
                String phone = phoneNumberTextField.getText();
                String mail = emailTextField.getText();
                User.Status st = statusComboBox.getValue();
                User userToEdit = null;

                for (User userr : userListt) {
                    if (userr.getFname().equalsIgnoreCase(selectedUser.getFname()) &&
                            userr.getLname().equalsIgnoreCase(selectedUser.getLname()) &&
                            userr.getRole().equalsIgnoreCase(selectedUser.getRole())) {
                        userToEdit = userr;
                        break;
                    }
                }

                if (userToEdit != null) {
                     if (!isPhoneExistInOtherUser(phone,selectedUser)) {
                    userToEdit.setEmail(mail);
                    userToEdit.setPhoNumber(phone);
                    userToEdit.setStatus(st);

                    // Assuming you have a class containing the saveeditUser method (e.g., Admin)
                    // Replace 'admin' with the appropriate object or instance.
                    admin.saveeditUser(userListt, userToEdit);

                    // Call the method responsible for updating the UserTable
                    // Replace 'updatedUserTable()' with the actual method name.
                    updatedUserTable();

                    showAlert("Success", "User edited successfully!");
                    editUserDetails.close();
                     } else {
            // Phone number already exists, show an alert
            showAlert("Error", "Phone number already exists. Please choose a different phone number.");
        }
                }
            });
        }
    });

    VBox editLayout = new VBox(10,
            new Label("Select User:"),
            userComboBox,
            new Label("Phone Number:"),
            phoneNumberTextField,
            new Label("Email:"),
            emailTextField, statusComboBox,
            editButton);

    editLayout.setAlignment(Pos.CENTER);
    Scene editScene = new Scene(editLayout, 300, 200);

    editUserDetails.setScene(editScene);
    editUserDetails.setTitle("Edit User");
    editUserDetails.show();
}          
private void UserSearchResult(String newValue) {
        List<? extends User> usersFromAdmin = User.loadFromFile("Users.bin");

    if (usersFromAdmin != null) {
        List<User> filteredUsers = usersFromAdmin.stream()
            .filter(user -> 
                user.getFname().toLowerCase().contains(newValue.toLowerCase()) ||
                user.getLname().toLowerCase().contains(newValue.toLowerCase()) ||
                String.valueOf(user.getPhoNumber()).contains(newValue) ||
                user.getRole().toString().toLowerCase().contains(newValue.toLowerCase())
            )
            .collect(Collectors.toList());

        userList.clear();
        userList.addAll(filteredUsers);
    }
    }
private void openAddUserWindow(boolean isPending) {
    Stage addPendingUserStage = new Stage();
    VBox addUserLayout = new VBox(10);
    addUserLayout.setAlignment(Pos.CENTER);
    if (isPending) {
    ComboBox<User> pendingUsersComboBox = new ComboBox<>();  
    List<User> pendingUsersList = admin.loadPendingUsersFromFile();
     pendingUsersComboBox.getItems().addAll(pendingUsersList);
        Button addPendingButton = new Button("Add Pending User");
        addPendingButton.setOnAction(e -> {
            // Check if a user is selected
            User selectedUser = pendingUsersComboBox.getValue();
            if (selectedUser != null) {
                boolean done=admin.addPending(pendingUsersList, selectedUser);
                if(done)
                {
                    showAlert("Success","This User added Successfully");
                    addPendingUserStage.close();
                    updatedUserTable();
                }
                else{
                   showAlert("Warning", "There is an Error happened while adding the user.");
                }  
            } else {
                showAlert("Warning", "Please select a user.");
            }
        });
       addUserLayout.getChildren().addAll(pendingUsersComboBox, addPendingButton);
    } else {
        Label fnameLabel = new Label ("First Name");
        TextField fname=new TextField();
        HBox fnameHBOX=new HBox (15,fnameLabel,fname);
        Label
