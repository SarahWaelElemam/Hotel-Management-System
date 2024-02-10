/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package user;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import user.AdditionalServices;
import user.Admin;
import user.Guest;
import user.Receptionist;
import user.Reservation;
import user.Room;
import user.User;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import static user.Reservation.ResStatus.PENDING;
/**
*
* @author SarahWael
*/
public class receptionistGUI extends Application {
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
private Stage receptionistWindow; 
private Button selectedButton = null;
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
           openAdminWindow(currentStage);
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
private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
private void openAdminWindow(Stage loginStage) {
    Stage adminWindow = new Stage();
    adminWindow.setTitle("Admin Window");
    adminWindow.show();
    loginStage.close();
}
private void openGuestWindow(Stage currentStage) {
    Stage GuestWindow = new Stage();
    GuestWindow.setTitle("Guest Window");
    GuestWindow.show();
    currentStage.close();
    }
private void openReceptionistWindow(Stage currentStage) {
    this.receptionistWindow = new Stage();
    receptionistWindow.setTitle("Receptionist Window");

    // Create a label for the welcome message
    Label welcomeLabel = new Label("HOTEL MANAGEMENT SYSTEM");
    welcomeLabel.setFont(new Font("New Times Roman", 40));
    welcomeLabel.setTextFill(Color.WHITE); // Set text color to white
    welcomeLabel.setPadding(new Insets(30, 0, 0, 30));
    // Increase the height of the label area
    double labelAreaHeight = 100;

    // Create a white box for the user display in the upper left corner
    StackPane userDisplayBox = new StackPane();
    userDisplayBox.setAlignment(Pos.TOP_LEFT);
    userDisplayBox.setStyle("-fx-background-color: white;");
    // Load the image
    Image userImage = new Image("file:C:\\Users\\Sarah Wael\\Downloads\\WhatsApp Image 2024-01-14 at 7.28.02 AM.jpeg");

    // Create an ImageView for the image
    ImageView imageView = new ImageView(userImage);
    imageView.setFitWidth(150); // Set the width as needed
    imageView.setFitHeight(100); // Set the height as needed
    
    // Create a Text node for the "online" text
    Text onlineText = new Text("online");
    onlineText.setFill(Color.GREEN); // Set the text color to green

    // Add the ImageView and Text to the userDisplayBox StackPane
    userDisplayBox.getChildren().addAll(imageView, onlineText);
    userDisplayBox.setAlignment(Pos.TOP_LEFT); // Set alignment to top-left

    // Create buttons
    Button checkInButton = createColoredButton("Check In", "white");
    Button checkOutButton = createColoredButton("Check Out", "white");
    Button cancelReservationButton = createColoredButton("Cancel Reservation", "white");
    Button addServiceButton = createColoredButton("Add Additional Service", "white");
    Button viewRecycleBinButton = createColoredButton("View Recycle Bin", "white");

    // Set initial styles for the buttons
    setButtonStyle(checkInButton);
    setButtonStyle(checkOutButton);
    setButtonStyle(cancelReservationButton);
    setButtonStyle(addServiceButton);
    setButtonStyle(viewRecycleBinButton);

    // Decrease the height of the buttons accordingly
    double buttonHeight = 100; // You can adjust the height as needed
    double buttonWidth = 150;
    checkInButton.setMinHeight(buttonHeight);
    checkInButton.setMinWidth(buttonWidth);
    checkOutButton.setMinHeight(buttonHeight);
    checkOutButton.setMinWidth(buttonWidth);
    cancelReservationButton.setMinHeight(buttonHeight);
    cancelReservationButton.setMinWidth(buttonWidth);
    addServiceButton.setMinHeight(buttonHeight);
    addServiceButton.setMinWidth(buttonWidth);
    viewRecycleBinButton.setMinHeight(buttonHeight);
    viewRecycleBinButton.setMinWidth(buttonWidth);

    // Create VBox for buttons
    VBox buttonsVBox = new VBox(0, checkInButton, checkOutButton, cancelReservationButton, addServiceButton, viewRecycleBinButton);
    buttonsVBox.setAlignment(Pos.CENTER_LEFT);
    buttonsVBox.setPadding(new Insets(0, 0, 0, 0)); // Set up padding to 100, down padding to 0

    // Create StackPane for dynamic content on the right side
    StackPane contentStackPane = new StackPane();
    contentStackPane.setStyle("-fx-background-color: #ffffff;"); // Set background color

    // Create HBox for the welcome label in the center
    HBox welcomeBox = new HBox(userDisplayBox, welcomeLabel);
    welcomeBox.setAlignment(Pos.TOP_LEFT);
    welcomeBox.setMinHeight(labelAreaHeight);
    welcomeBox.setStyle("-fx-background-color: #044974;"); // Set background color

    checkInButton.setOnAction(e -> {
        // Call the updateContent method with receptionistWindow and "Check In" as parameters
        updateContent(receptionistWindow, "Check In");
    });
    checkOutButton.setOnAction(e -> {
        // Call the updateContent method with receptionistWindow and "Check Out" as parameters
        updateContent(receptionistWindow, "Check Out");
    });
    cancelReservationButton.setOnAction(e -> {
        // Call the updateContent method with receptionistWindow and "Cancel Reservation" as parameters
        updateContent(receptionistWindow, "Cancel Reservation");
    });

    addServiceButton.setOnAction(e -> {
        // Call the updateContent method with receptionistWindow and "Add Additional Service" as parameters
        updateContent(receptionistWindow, "Add Additional Service");
    });

    viewRecycleBinButton.setOnAction(e -> {
        // Call the updateContent method with receptionistWindow and "View Recycle Bin" as parameters
        updateContent(receptionistWindow, "View Recycle Bin");
    });

    // Create BorderPane and set the HBox on the top and VBox on the left, StackPane on the right
    BorderPane mainLayout = new BorderPane();
    mainLayout.setTop(welcomeBox);
    mainLayout.setLeft(buttonsVBox);
    mainLayout.setCenter(contentStackPane);
    // Set the left insets to 0
    BorderPane.setMargin(buttonsVBox, new Insets(0, 0, 0, 0));

    // Set the scene
    Scene receptionistScene = new Scene(mainLayout, 800, 600); // Adjust the size accordingly
    receptionistWindow.setScene(receptionistScene);
    receptionistWindow.setResizable(false);

    // Show the window
    receptionistWindow.show();
    currentStage.close();
}
private void setButtonStyle(Button button) {
    button.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-size: 14;");
    button.setOnMouseClicked(event -> {
        // Change button color and text color on click
        button.setStyle("-fx-background-color: #044974; -fx-text-fill: white; -fx-font-size: 14;");
        
        // Reset styles for other buttons
        if (selectedButton != null && selectedButton != button) {
            selectedButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-size: 14;");
        }
        
        // Set the selected button
        selectedButton = button;

        // Update content based on the selected button
        updateContent(receptionistWindow, button.getText());
    });
}
private void updateContent(Stage receptionistWindow, String buttonName) {
    Parent newContent = null;

    if ("Check In".equals(buttonName)) {
        newContent = createCheckInContent();
    } else if ("Check Out".equals(buttonName)) {
        newContent = createCheckOutContent();
    } else if ("Cancel Reservation".equals(buttonName)) {
        newContent = createCancelReservationContent();
    } else if ("Add Additional Service".equals(buttonName)) {
        newContent = createAddAdditionalServicesContent();
    } else if ("View Recycle Bin".equals(buttonName)) {
        newContent = createViewRecycleBinContent();
    }

    if (newContent != null) {
        StackPane contentStackPane = (StackPane) ((BorderPane) receptionistWindow.getScene().getRoot()).getCenter();
        contentStackPane.getChildren().clear();
        contentStackPane.getChildren().add(newContent);
    }
}
private void forgetpasswordWindow(Stage primaryStage) {
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
        User.forgetPassword(selectedRole, username);
    });

    grid.getChildren().addAll(instructionLabel, usernameInput, roleLabel, roleComboBox, recoverButton);

    Scene forgetPasswordScene = new Scene(grid, 300, 200);
    forgetPasswordWindow.setScene(forgetPasswordScene);
    forgetPasswordWindow.show();
    primaryStage.close();
}
private void signupWindow(Stage primaryStage) {
        primaryStage.close();
        Stage signupWindow=new Stage();
        Label fname=new Label ("First Name");
        TextField fnametxt=new TextField();
        HBox fnameHBOX=new HBox(15,fname,fnametxt);
        Label lname=new Label ("Last Name");
        TextField lnametxt=new TextField();
        HBox lnameHBOX=new HBox(15,lname,lnametxt);
        Label email=new Label ("Email");
        TextField emailPrefix = new TextField(); 
        TextField emailSuffix = new TextField("@gmail.com");  
        emailSuffix.setDisable(true);  
        HBox emailHBOX = new HBox(15, email, emailPrefix, emailSuffix);
        Label phone=new Label ("Contact Number");
        TextField phoneSuffix = new TextField("+20"); 
        phoneSuffix.setDisable(true);
        TextField phonetxt=new TextField();
         HBox phoneHBOX=new HBox(15,phone,phoneSuffix,phonetxt);
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("Admin", "Guest", "Receptionist");
        roleComboBox.setValue("Role");
        Label role=new Label("Role");
        HBox roleHBOX=new HBox(15,role,roleComboBox);
        Label username=new Label ("Username");
        TextField usernametxt=new TextField();
        HBox usernameHBOX=new HBox(15,username,usernametxt);
        Label pass=new Label ("Password");
        PasswordField passtxt=new PasswordField();
        HBox passHBOX=new HBox(15,pass,passtxt);
        Button signUpButton = new Button("Sign Up");
        VBox vbox=new VBox(15,fnameHBOX,lnameHBOX,emailHBOX,phoneHBOX,roleHBOX,usernameHBOX,passHBOX,signUpButton);
        Scene scene = new Scene (vbox,400,500);
        signUpButton.setOnAction(e -> {
            if (isAnyFieldEmpty(fnametxt, lnametxt, emailPrefix, phonetxt, usernametxt, passtxt) || isComboBoxEmpty(roleComboBox)) {
             showAlert("Error", "All fields are required");
             return; // Stop further execution
            }
        
        String firstName = fnametxt.getText();
        String lastName = lnametxt.getText();
        String emailPrefixText = emailPrefix.getText();
        String emailSuffixText = emailSuffix.getText();
        String phoneNumber = phonetxt.getText();
        String selectedRole = roleComboBox.getValue();
        String usernameText = usernametxt.getText();
        String passwordText = passtxt.getText();
        if (phoneNumber.length()!=10)
        {
            showAlert("Error","Phone number must contains 10 numbers");
        }
        if (passwordText.length()<8)
        {
            showAlert("Error","Password must contains more than 8 Characters");
        }
        });
        signupWindow.setTitle("signup Window");
        signupWindow.setScene(scene);
        signupWindow.show();
        primaryStage.close();
    }
private boolean isAnyFieldEmpty(TextField... fields) {
    return Arrays.stream(fields)
            .anyMatch(field -> field.getText().trim().isEmpty());
}
private boolean isComboBoxEmpty(ComboBox<?> comboBox) {
    return comboBox.getValue() == null || comboBox.getValue().toString().trim().isEmpty();
}
private Parent createCheckInContent() {
// Create buttons for New Reservation and Confirm Pending Reservation
    Button newReservationButton = new Button("New Reservation");
    Button confirmPendingReservationButton = new Button("Confirm Pending Reservation");
    // Set action event for New Reservation Button
    newReservationButton.setOnAction(event -> {
       // Create labels and text fields for the New Reservation window
Label createGuestAccountLabel = new Label("Guest Details:");
createGuestAccountLabel.setFont(new Font("Arial", 18));
createGuestAccountLabel.setTextFill(Color.BLACK);

// Create labels and text fields for the New Reservation window
Label reservationInfoLabel = new Label("Reservation Information:");
reservationInfoLabel.setFont(new Font("Arial", 18));
reservationInfoLabel.setTextFill(Color.BLACK);
reservationInfoLabel.setPadding(new Insets(10, 100, 10, 100));

Label firstNameLabel = new Label("First Name:");
TextField firstNameTextField = new TextField();

Label lastNameLabel = new Label("Last Name:");
TextField lastNameTextField = new TextField();

Label phoneNumberLabel = new Label("Phone Number:");
TextField phoneNumberTextField = new TextField();

Label emailLabel = new Label("Email:");
TextField emailTextField = new TextField();

Label idLabel = new Label("ID:");
TextField idTextField = new TextField();

Label usernameLabel = new Label("Username:");
TextField usernameTextField = new TextField();

Label passwordLabel = new Label("Password:");
PasswordField passwordField = new PasswordField();

// Create HBox for the first name and last name
HBox nameHBox = new HBox(25, firstNameLabel, firstNameTextField, lastNameLabel, lastNameTextField);
nameHBox.setAlignment(Pos.CENTER_LEFT);
nameHBox.setPadding(new Insets(0, 0, 0, 50));
// Create HBox for phone number and email
HBox contactHBox = new HBox(25, phoneNumberLabel, phoneNumberTextField, emailLabel, emailTextField);
contactHBox.setAlignment(Pos.CENTER_LEFT);
contactHBox.setPadding(new Insets(0, 0, 0, 50));
// Create HBox for username and password
HBox credentialsHBox = new HBox(25, usernameLabel, usernameTextField, passwordLabel, passwordField);
credentialsHBox.setAlignment(Pos.CENTER_LEFT);
credentialsHBox.setPadding(new Insets(0, 0, 0, 50));
// Create HBox for ID and text field
HBox idHBox = new HBox(10, idLabel, idTextField);
idHBox.setAlignment(Pos.CENTER_LEFT);
Label roomNumberLabel = new Label("Room Number:");

        ComboBox<Integer> roomNumberComboBox = new ComboBox<>();
        Label roomTypeLabel = new Label("Room Type:");
        ComboBox<String> roomTypeComboBox = new ComboBox<>();
        List<Room> roomList = Room.loadRoomList("Rooms.bin");

        roomTypeComboBox.getItems().addAll(
                roomList.stream()
                        .map(Room::getCategory)
                        .distinct()
                        .collect(Collectors.toList())
        );

        Label adultsLabel = new Label("Number Of Adults:");
        ComboBox<Integer> adultsComboBox = new ComboBox<>();
        adultsComboBox.setValue(1);

        Label childrenLabel = new Label("Number Of Children:");
        ComboBox<Integer> childrenComboBox = new ComboBox<>();
        childrenComboBox.setValue(0);

        HBox guestsComboBoxesHBox = new HBox(15, adultsLabel, adultsComboBox, childrenLabel, childrenComboBox);
        guestsComboBoxesHBox.setAlignment(Pos.CENTER_LEFT);
        guestsComboBoxesHBox.setPadding(new Insets(0, 0, 0, 50));

       roomTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
    roomNumberComboBox.getItems().clear();
    adultsComboBox.getItems().clear();
    childrenComboBox.getItems().clear();

    Room room = roomList.stream()
            .filter(r -> r.getCategory().equalsIgnoreCase(newValue))
            .findFirst()
            .orElse(null);

    if (room != null) {
        int maxAdults = room.getNumOfAdults();
        int maxChildren = room.getNumOfChildren();

        for (int i = 1; i <= maxAdults; i++) {
            adultsComboBox.getItems().add(i);
        }

        for (int i = 0; i < maxChildren; i++) {  // Corrected loop
            childrenComboBox.getItems().add(i);
        }

        roomNumberComboBox.getItems().addAll(
                roomList.stream()
                        .filter(r -> r.getCategory().equalsIgnoreCase(newValue))
                        .map(Room::getRoomNum)
                        .collect(Collectors.toList())
        );
    }
});

        if (!roomNumberComboBox.getItems().isEmpty()) {
            roomNumberComboBox.setValue(roomNumberComboBox.getItems().get(0));
        }
        if (!adultsComboBox.getItems().isEmpty()) {
            adultsComboBox.setValue(adultsComboBox.getItems().get(0));
        }
        if (!childrenComboBox.getItems().isEmpty()) {
            childrenComboBox.setValue(childrenComboBox.getItems().get(0));
        }

        HBox roomComboBoxesHBox = new HBox(15, roomTypeLabel, roomTypeComboBox, roomNumberLabel, roomNumberComboBox);
        roomComboBoxesHBox.setAlignment(Pos.CENTER_LEFT);
        roomComboBoxesHBox.setPadding(new Insets(0, 0, 0, 50));

// Create DatePickers for check-in and check-out dates
Label checkInDateLabel = new Label("Check-In Date:");
DatePicker checkInDatePicker = new DatePicker();

Label checkOutDateLabel = new Label("Check-Out Date:");
DatePicker checkOutDatePicker = new DatePicker();

// Create HBox for check-in and check-out DatePickers
HBox datePickersHBox = new HBox(15, checkInDateLabel, checkInDatePicker, checkOutDateLabel, checkOutDatePicker);
datePickersHBox.setAlignment(Pos.CENTER_LEFT);
datePickersHBox.setPadding(new Insets(0, 0, 0, 50));

// Create Text objects for Total Days, Price, and Total Cost
Text totalDaysText = new Text("Total Days:");
Text priceText = new Text("Price:");
Text totalCostText = new Text("Total Cost:");

// Set the font for Total Days, Price, and Total Cost Text
Font textFont = Font.font("Times New Roman", FontWeight.BOLD, 15);
totalDaysText.setFont(textFont);
priceText.setFont(textFont);
totalCostText.setFont(textFont);
// Create HBox for Total Days, Price, and Total Cost Text
VBox costDetailsVBox = new VBox(5, totalDaysText, priceText, totalCostText);
costDetailsVBox.setAlignment(Pos.CENTER_LEFT);
costDetailsVBox.setPadding(new Insets(0, 0, 0, 50));

// Create Check-In Button
 Button checkInButton = new Button("Check-In");

        HBox idSubmitHBox = new HBox(10, idHBox);
        idSubmitHBox.setAlignment(Pos.CENTER_LEFT);
        idSubmitHBox.setPadding(new Insets(0, 0, 0, 150));

        VBox newReservationContentVBox = new VBox(15,
                createGuestAccountLabel,
                nameHBox,
                contactHBox,
                credentialsHBox,
                idSubmitHBox,
                reservationInfoLabel,
                roomComboBoxesHBox,
                guestsComboBoxesHBox,
                datePickersHBox,
                costDetailsVBox,
                checkInButton
        );
        newReservationContentVBox.setAlignment(Pos.TOP_CENTER);

        StackPane contentStackPane = (StackPane) ((BorderPane) receptionistWindow.getScene().getRoot()).getCenter();
        contentStackPane.getChildren().clear();
        contentStackPane.getChildren().add(newReservationContentVBox);
    });
confirmPendingReservationButton.setOnAction(event -> {
  // Create label for Check-Out text
        Label checkOutLabel = new Label("Pending Reservations: ");
        checkOutLabel.setFont(new Font("Arial", 18));
        checkOutLabel.setTextFill(Color.BLACK);
    checkOutLabel.setAlignment(Pos.TOP_CENTER);
// Create text field for searching
    TextField searchTextField = new TextField();
    searchTextField.setAlignment(Pos.TOP_CENTER);

    // Create search button
    Button searchButton = new Button("Search");

    // Create a GridPane to arrange the components
    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.TOP_CENTER);
    gridPane.setVgap(10);

    // Add Check-out label at the top center
GridPane.setConstraints(checkOutLabel, 0, 0, 2, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);

/// Add Text field below Check-out label
GridPane.setConstraints(searchTextField, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
searchTextField.setMaxWidth(150);  // Set the maximum width as needed
GridPane.setMargin(searchTextField, new Insets(0, 0, 0, 0));  // Set margin to 0

// Add Search button beside Text field with 0 units of spacing
GridPane.setConstraints(searchButton, 1, 1, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
GridPane.setMargin(searchButton, new Insets(0, 0, 0, 0));  // Set margin to 0

// Add components to GridPane
gridPane.getChildren().addAll(checkOutLabel, searchTextField, searchButton);
    // Create TableView for displaying reservation information
    TableView<Reservation> reservationTable = new TableView<>();
    reservationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    // Create columns for the table
TableColumn<Reservation, Integer> roomNumberCol = new TableColumn<>("Room Number");
roomNumberCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getRoom1().getRoomNum()).asObject());

TableColumn<Reservation, String> customerNameCol = new TableColumn<>("Customer Name");
customerNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGuestInfo().getFname() + " " + cellData.getValue().getGuestInfo().getLname()));

TableColumn<Reservation, LocalDate> checkInDateCol = new TableColumn<>("Check-In Date");
checkInDateCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCheckInDate()));

TableColumn<Reservation, LocalDate> checkOutDateCol = new TableColumn<>("Check-Out Date");
checkOutDateCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCheckOutDate()));

TableColumn<Reservation, Long> totalDaysCol = new TableColumn<>("Total Days");
totalDaysCol.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getTotalDays()).asObject());

TableColumn<Reservation, Double> totalPriceCol = new TableColumn<>("Total Price");
totalPriceCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getRoomcost()).asObject());

TableColumn<Reservation, String> statusCol = new TableColumn<>("Status");
statusCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));

    // Add columns to the table
    reservationTable.getColumns().addAll(roomNumberCol, customerNameCol, checkInDateCol, checkOutDateCol, totalDaysCol, totalPriceCol, statusCol);

    // Create VBox for the table
    VBox tableVBox = new VBox(10, reservationTable);
    tableVBox.setAlignment(Pos.CENTER);

    // Create HBox for "Check-out" and "Print" buttons
    Button confirmButton = new Button("Confirm");
    Button printButton = new Button("Print");
      
    HBox bottomButtonsHBox = new HBox(100, confirmButton, printButton);
    bottomButtonsHBox.setAlignment(Pos.BOTTOM_CENTER);

    // Add the HBox for buttons and VBox for the table to the GridPane
    gridPane.setAlignment(Pos.TOP_CENTER);
    gridPane.setVgap(10);
    GridPane.setConstraints(bottomButtonsHBox, 0, 2, 2, 1);
    GridPane.setConstraints(tableVBox, 0, 3, 2, 1);
    gridPane.getChildren().addAll(bottomButtonsHBox, tableVBox);

    // Set the preferred width for the entire TableView
reservationTable.setPrefWidth(700); // Adjust the width as needed
    loadPendingReservations(reservationTable);

    // Set up the search functionality
    searchButton.setOnAction(searchEvent -> {
        String roomNumberText = searchTextField.getText().trim();
        if (!roomNumberText.isEmpty()) {
            int roomNumber = Integer.parseInt(roomNumberText);

            // Filter the reservations based on the entered room number
            List<Reservation> filteredReservations = reservationTable.getItems().stream()
                    .filter(reservation -> reservation.getRoom1().getRoomNum() == roomNumber)
                    .collect(Collectors.toList());

            // Update the table with the filtered reservations
            reservationTable.getItems().clear();
            reservationTable.getItems().addAll(filteredReservations);
        } else {
            // If the search field is empty, reload all pending reservations
            loadPendingReservations(reservationTable);
        }
    });
List<Reservation> reservationList = new ArrayList<>(); // Maintain the list outside the event handler

confirmButton.setOnAction(confirmEvent -> {
    Reservation selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
    if (selectedReservation != null) {
        // Confirm the selected reservation
        selectedReservation.confirmReservation();  // You need to implement confirmReservation() in your Reservation class

        // Remove the confirmed reservation from the list
        reservationList.remove(selectedReservation);

        // Save the updated list to the file
        saveReservationsToFile(reservationList);

        // Reload pending reservations after confirmation
        loadPendingReservations(reservationTable);
    } else {
        // If no reservation is selected, show a message or handle it as needed
        System.out.println("Please select a reservation to confirm.");
    }
});
    // Clear existing content and set the new content
    StackPane contentStackPane = (StackPane) ((BorderPane) receptionistWindow.getScene().getRoot()).getCenter();
    contentStackPane.getChildren().clear();
    contentStackPane.getChildren().add(gridPane);
});
    VBox buttonsVBox = new VBox(10, newReservationButton, confirmPendingReservationButton);
    buttonsVBox.setAlignment(Pos.CENTER);

    StackPane contentStackPane = (StackPane) ((BorderPane) receptionistWindow.getScene().getRoot()).getCenter();
    contentStackPane.getChildren().clear();
    contentStackPane.getChildren().add(buttonsVBox);

    return buttonsVBox;
}
private Parent createCheckOutContent() {
           // Create label for Check-Out text
        Label checkOutLabel = new Label("Check-out: ");
        checkOutLabel.setFont(new Font("Arial", 18));
        checkOutLabel.setTextFill(Color.BLACK);
    checkOutLabel.setAlignment(Pos.TOP_CENTER);
// Create text field for searching
    TextField searchTextField = new TextField();
    searchTextField.setAlignment(Pos.TOP_CENTER);

    // Create search button
    Button searchButton = new Button("Search");

    // Create a GridPane to arrange the components
    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.TOP_CENTER);
    gridPane.setVgap(10);

    // Add Check-out label at the top center
GridPane.setConstraints(checkOutLabel, 0, 0, 2, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);

/// Add Text field below Check-out label
GridPane.setConstraints(searchTextField, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
searchTextField.setMaxWidth(150);  // Set the maximum width as needed
GridPane.setMargin(searchTextField, new Insets(0, 0, 0, 0));  // Set margin to 0

// Add Search button beside Text field with 0 units of spacing
GridPane.setConstraints(searchButton, 1, 1, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
GridPane.setMargin(searchButton, new Insets(0, 0, 0, 0));  // Set margin to 0

// Add components to GridPane
gridPane.getChildren().addAll(checkOutLabel, searchTextField, searchButton);
    // Create TableView for displaying reservation information
    TableView<Reservation> reservationTable = new TableView<>();
    reservationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    // Create columns for the table
TableColumn<Reservation, Integer> roomNumberCol = new TableColumn<>("Room Number");
roomNumberCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getRoom1().getRoomNum()).asObject());

TableColumn<Reservation, String> customerNameCol = new TableColumn<>("Customer Name");
customerNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGuestInfo().getFname() + " " + cellData.getValue().getGuestInfo().getLname()));

TableColumn<Reservation, LocalDate> checkInDateCol = new TableColumn<>("Check-In Date");
checkInDateCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCheckInDate()));

TableColumn<Reservation, LocalDate> checkOutDateCol = new TableColumn<>("Check-Out Date");
checkOutDateCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCheckOutDate()));

TableColumn<Reservation, Long> totalDaysCol = new TableColumn<>("Total Days");
totalDaysCol.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getTotalDays()).asObject());

TableColumn<Reservation, Double> totalPriceCol = new TableColumn<>("Total Price");
totalPriceCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getRoomcost()).asObject());

TableColumn<Reservation, String> statusCol = new TableColumn<>("Status");
statusCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));

    // Add columns to the table
    reservationTable.getColumns().addAll(roomNumberCol, customerNameCol, checkInDateCol, checkOutDateCol, totalDaysCol, totalPriceCol, statusCol);

    // Create VBox for the table
    VBox tableVBox = new VBox(10, reservationTable);
    tableVBox.setAlignment(Pos.CENTER);

    // Create HBox for "Check-out" and "Print" buttons
    Button checkOutButton = new Button("Check-out");
    Button printButton = new Button("Print");
      
    HBox bottomButtonsHBox = new HBox(100, checkOutButton, printButton);
    bottomButtonsHBox.setAlignment(Pos.BOTTOM_CENTER);
 // Set up the search functionality
    searchButton.setOnAction(searchEvent -> {
        String roomNumberText = searchTextField.getText().trim();
        if (!roomNumberText.isEmpty()) {
            int roomNumber = Integer.parseInt(roomNumberText);

            // Filter the reservations based on the entered room number
            List<Reservation> filteredReservations = reservationTable.getItems().stream()
                    .filter(reservation -> reservation.getRoom1().getRoomNum() == roomNumber)
                    .collect(Collectors.toList());

            // Update the table with the filtered reservations
            reservationTable.getItems().clear();
            reservationTable.getItems().addAll(filteredReservations);
        } else {
            // If the search field is empty, reload all pending reservations
            loadPendingReservations(reservationTable);
        }
    // Add the HBox for buttons and VBox for the table to the GridPane
    gridPane.setAlignment(Pos.TOP_CENTER);
    gridPane.setVgap(10);
    GridPane.setConstraints(bottomButtonsHBox, 0, 2, 2, 1);
    GridPane.setConstraints(tableVBox, 0, 3, 2, 1);
    gridPane.getChildren().addAll(bottomButtonsHBox, tableVBox);

    // Set the preferred width for the entire TableView
reservationTable.setPrefWidth(700); // Adjust the width as needed
// Load checked-in reservations and populate the table
    loadCheckedInReservations(reservationTable);
    // Get the StackPane for dynamic content
    StackPane contentStackPane = (StackPane) ((BorderPane) receptionistWindow.getScene().getRoot()).getCenter();

    // Clear existing content and set the new content
    contentStackPane.getChildren().clear();
    contentStackPane.getChildren().add(gridPane);

    return gridPane;
}
            } 
private void saveReservationsToFile(List<Reservation> reservations) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Reservations.bin"))) {
        oos.writeObject(reservations);
    } catch (IOException e) {
        e.printStackTrace();
        // Handle the exception as needed
    }
}
private void loadCheckedInReservations(TableView<Reservation> reservationTable) {
        // Load reservations from file
        List<Reservation> reservationList = Reservation.loadReservationList("Reservations.bin");
        // Clear existing data in the table
        reservationTable.getItems().clear();
        // Filter reservations with status CHECKED_IN
        for (Reservation reservation : reservationList) {
            if (reservation.getStatus() == Reservation.ResStatus.CHECKED_IN) {
                reservationTable.getItems().add(reservation);
            }
        }
}
private Parent createCancelReservationContent() {
    // Create label for Check-Out text
        Label cancelResLabel = new Label("Cancel Reservation: ");
        cancelResLabel.setFont(new Font("Arial", 18));
        cancelResLabel.setTextFill(Color.BLACK);
    cancelResLabel.setAlignment(Pos.CENTER);
// Create text field for searching
    TextField searchTextField1 = new TextField();
    searchTextField1.setAlignment(Pos.CENTER);
    // Create search button
    Button searchButton = new Button("Search");

    // Create a GridPane to arrange the components
    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.TOP_CENTER);
    gridPane.setVgap(10);

    // Add Check-out label at the top center
    GridPane.setConstraints(cancelResLabel, 0, 0, 2, 1);
    // Add Text field below Check-out label
    GridPane.setConstraints(searchTextField1, 0, 1, 1, 1);
    // Add Search button beside Text field
    GridPane.setConstraints(searchButton, 1, 1, 1, 1);

    // Add components to GridPane
    gridPane.getChildren().addAll(cancelResLabel, searchTextField1, searchButton);
  
  // Create VBox for radio buttons and text
    VBox radioButtonsVBox = new VBox(10);
    radioButtonsVBox.setAlignment(Pos.TOP_LEFT);
    radioButtonsVBox.setPadding(new Insets(10, 0, 0, 10));

    // Add label for radio buttons
    Text reasonLabel = new Text("Tell us Your reason For Cancelling:");
    radioButtonsVBox.getChildren().add(reasonLabel);

    // Create and add radio buttons
    String[] reasons = {"Personal Reasons/Trip called off", "Change of dates or destination",
            "Change in the number of travelers", "Found a different accommodation option", "None of the above"};

    ToggleGroup toggleGroup = new ToggleGroup();

    for (String reason : reasons) {
        RadioButton radioButton = new RadioButton(reason);
        radioButton.setToggleGroup(toggleGroup);
        radioButtonsVBox.getChildren().add(radioButton);
    }
    // Create HBox for the "Cancel Reservation" button
    Button cancelReservationButton = new Button("Cancel Reservation");
    cancelReservationButton.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #FF0000;"); // Red color

    // Add action to the "Search" button
    searchButton.setOnAction(event -> {
        // Perform the search operation based on the room number
        String roomNumberText = searchTextField1.getText().trim();
        if (!roomNumberText.isEmpty()) {
            int roomNumber = Integer.parseInt(roomNumberText);
            // Assuming reservationList is an ArrayList containing Reservation objects
            List<Reservation> reservationList = Reservation.loadReservationList("Reservations.bin");

            Optional<Reservation> foundReservation = reservationList.stream()
                    .filter(reservation -> reservation.getRoom1().getRoomNum() == roomNumber)
                    .findFirst();

            if (foundReservation.isPresent()) {
                // Show success message
                showInfoAlert("Room found!", "Room is found. Cancel now.");
            } else {
                // Show error message
                showErrorAlert("Room not found!", "No reservation found for the provided room number.");
            }
        } else {
            // Show error message for empty room number
            showErrorAlert("Invalid input", "Please enter a room number.");
        }
    });

    // Add action to the "Cancel Reservation" button
    cancelReservationButton.setOnAction(event -> {
        // Get selected reason from the radio buttons
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        String cancelReason = (selectedRadioButton != null) ? selectedRadioButton.getText() : "No reason selected";

        // Assuming room1 is the room associated with the cancellation
        try {
            cancellation("First Name", "Last Name"); // Pass actual first and last names
            // Additional logic related to cancellation, if needed
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }

        // Display confirmation or perform additional actions after cancellation
        showInfoAlert("Reservation Canceled", "Reservation canceled successfully.\nReason: " + cancelReason);
    });
    
    // Create a VBox for the entire content
    VBox contentVBox = new VBox(10, gridPane, radioButtonsVBox, cancelReservationButton);
    contentVBox.setAlignment(Pos.CENTER);

    // Get the StackPane for dynamic content
    StackPane contentStackPane = (StackPane) ((BorderPane) receptionistWindow.getScene().getRoot()).getCenter();

    // Clear existing content and set the new content
    contentStackPane.getChildren().clear();
    contentStackPane.getChildren().add(contentVBox);

    return contentVBox;
}
private Parent createAddAdditionalServicesContent() {
// Create labels and set their properties
    Label titleLabel = new Label("Add Additional Services");
    titleLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));
    Label chooseServiceLabel = new Label("Choose an additional service:");
    Label howManyLabel = new Label("How Many?");
    Label additionalServicePriceLabel = new Label("Additional Service Price:");
    Label roomCostLabel = new Label("Room Cost:");
    Label totalCostLabel = new Label("Total Reservation Cost:");
    // Create text field and button
    TextField searchTextField = new TextField();
    Button searchButton = new Button("Search");
    // Create combo boxes
    ComboBox<String> serviceComboBox = new ComboBox<>();
    ComboBox<Integer> quantityComboBox = new ComboBox<>();
     List<String> services = new ArrayList<>();  // Declare the list outside the try block
    try {
        services = readServicesFromFile("AllServices.bin");
        // Rest of the code to read from the file
    } catch (FileNotFoundException e) {
        System.err.println("Error: AllServices file not found.");
        e.printStackTrace();
        // Handle the error as needed (e.g., show an alert to the user)
    }
    serviceComboBox.getItems().addAll(services);
    // Create a GridPane to arrange the components
    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.TOP_CENTER);
    gridPane.setVgap(10);
    gridPane.setHgap(10);
    gridPane.setPadding(new Insets(20));

    // Add components to GridPane
    gridPane.add(titleLabel, 0, 0, 2, 1);
    gridPane.add(searchTextField, 0, 1);
    gridPane.add(searchButton, 1, 1);

    gridPane.add(chooseServiceLabel, 0, 2);
    gridPane.add(serviceComboBox, 1, 2);
    gridPane.add(howManyLabel, 0, 3);
    gridPane.add(quantityComboBox, 1, 3);

    gridPane.add(additionalServicePriceLabel, 0, 4);
    gridPane.add(roomCostLabel, 0, 5);
    gridPane.add(totalCostLabel, 0, 6);

    // Set additional properties
    GridPane.setHalignment(titleLabel, HPos.CENTER);
    GridPane.setColumnSpan(titleLabel, 2);
    GridPane.setHalignment(searchButton, HPos.LEFT);
    GridPane.setHalignment(chooseServiceLabel, HPos.LEFT);
    GridPane.setHalignment(howManyLabel, HPos.LEFT);
// Create "Add Additional Service" button
    Button addServiceButton = new Button("Add Additional Service");
    addServiceButton.setStyle("-fx-background-color: darkgreen; -fx-text-fill: white;");

    // Add the button to the GridPane
    gridPane.add(addServiceButton, 0, 7, 2, 1);
    GridPane.setHalignment(addServiceButton, HPos.CENTER);

    searchButton.setOnAction(event -> {
        // Perform the search operation based on the room number
        String roomNumberText = searchTextField.getText().trim();
        if (!roomNumberText.isEmpty()) {
            int roomNumber = Integer.parseInt(roomNumberText);
            // Assuming reservationList is an ArrayList containing Reservation objects
            List<Reservation> reservationList = Reservation.loadReservationList("Reservations.bin");

           Optional<Reservation> foundReservation = reservationList.stream()
        .filter(reservation -> reservation.getRoom1().getRoomNum() == roomNumber)
        .findFirst();

            if (foundReservation.isPresent()) {
                // Show success message
                showInfoAlert("Reservation found!", "Choose the additional service you want to add.");
            } else {
                // Show error message
                showErrorAlert("Reservation not found!", "No reservation found for the provided room number.");
            }
        } else {
            // Show error message for empty room number
            showErrorAlert("Invalid input", "Please enter a room number.");
        }
    });
    
    // Return the GridPane within a StackPane
    return new StackPane(gridPane);
    }
private List<String> readServicesFromFile(String filename) {
    List<String> services = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = reader.readLine()) != null) {
            services.add(line);
        }
    } catch (IOException e) {
        e.printStackTrace(); // Handle the exception according to your needs
    }
    return services;
} 
private void showInfoAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
}
private void showErrorAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
}
private Parent createViewRecycleBinContent() {
        // Create label for Recycle Bin text
        Label recycleBinLabel = new Label("Recycle Bin");
        recycleBinLabel.setFont(new Font("Arial", 18));
        recycleBinLabel.setTextFill(Color.BLACK);
        recycleBinLabel.setAlignment(Pos.TOP_CENTER);

        // Create a GridPane to arrange the components
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setVgap(10);

        // Add Recycle Bin label at the top center
        GridPane.setConstraints(recycleBinLabel, 0, 0, 2, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);

        // Add TableView below Recycle Bin label
        TableView<Reservation> recycleBinTable = createRecycleBinTable();
        GridPane.setConstraints(recycleBinTable, 0, 1, 2, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);

        // Add components to GridPane
        gridPane.getChildren().addAll(recycleBinLabel, recycleBinTable);

        // Get the StackPane for dynamic content
        StackPane contentStackPane = (StackPane) ((BorderPane) receptionistWindow.getScene().getRoot()).getCenter();

        // Clear existing content and set the new content
        contentStackPane.getChildren().clear();
        contentStackPane.getChildren().add(gridPane);

        return gridPane;
    }
private TableView<Reservation> createRecycleBinTable() {
        TableView<Reservation> recycleBinTable = new TableView<>();
        recycleBinTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Create columns for the table
        TableColumn<Reservation, Integer> roomNumberCol = new TableColumn<>("Room Number");
        roomNumberCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getRoom1().getRoomNum()).asObject());

        TableColumn<Reservation, String> customerNameCol = new TableColumn<>("Customer Name");
        customerNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGuestInfo().getFname() + " " + cellData.getValue().getGuestInfo().getLname()));

        TableColumn<Reservation, LocalDate> checkInDateCol = new TableColumn<>("Check-In Date");
        checkInDateCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCheckInDate()));

        TableColumn<Reservation, LocalDate> checkOutDateCol = new TableColumn<>("Check-Out Date");
        checkOutDateCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCheckOutDate()));

        TableColumn<Reservation, Long> totalDaysCol = new TableColumn<>("Total Days");
        totalDaysCol.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getTotalDays()).asObject());

        TableColumn<Reservation, Double> totalPriceCol = new TableColumn<>("Total Price");
        totalPriceCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getRoomcost()).asObject());

        TableColumn<Reservation, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));

        // Add columns to the table
        recycleBinTable.getColumns().addAll(roomNumberCol, customerNameCol, checkInDateCol, checkOutDateCol, totalDaysCol, totalPriceCol, statusCol);

        // Load data from RecycleBin.bin file
        ObservableList<Reservation> recycleBinData = FXCollections.observableArrayList(loadRecycleBinList("RecycleBin.bin"));
        recycleBinTable.setItems(recycleBinData);

        return recycleBinTable;
    }
private List<Reservation> loadRecycleBinList(String filepath) {
        List<Reservation> recycleBinList = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                recycleBinList = (List<Reservation>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            // Handle exceptions or print a more detailed error message
            e.printStackTrace();
        }

        if (recycleBinList == null) {
            recycleBinList = List.of(); // Use an empty list if loading fails
        }

        return recycleBinList;
    } 
private Button createColoredButton(String buttonText, String initialColor) {
   Button button = new Button(buttonText);
    button.setStyle("-fx-text-fill: #ffffff; -fx-background-color: " + initialColor + ";");

    // Set the action to change color on click
    button.setOnAction(e -> {
        button.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #044974;");
        
        // Reset styles for other buttons
        if (selectedButton != null && selectedButton != button) {
            selectedButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-size: 14;");
        }
        
        // Set the selected button
        selectedButton = button;

        // Update content based on the selected button
        updateContent(receptionistWindow, buttonText);
    });

    return button;
}
private void loadPendingReservations(TableView<Reservation> reservationTable) {
    // Assuming reservationList is an ArrayList containing Reservation objects
    List<Reservation> reservationList = Reservation.loadReservationList("Reservations.bin");

    // Filter the reservations based on the status "Pending"
    List<Reservation> pendingReservations = reservationList.stream()
            .filter(reservation -> reservation.getStatus() == PENDING)
            .collect(Collectors.toList());

    // Clear the table
    reservationTable.getItems().clear();

    // Add the filtered reservations to the table
    reservationTable.getItems().addAll(pendingReservations);
}
}
}
