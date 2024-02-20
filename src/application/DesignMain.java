package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DesignMain extends Application {

	private static FunctionClass functionClass;
	Connection connection;
	Statement statement;
	ColorPicker colorbackgroundPicker;
	ColorPicker colorbuttonPicker;
	Color myColor;
	String DESMasterKey  = "B6Q9TOVhyws=";
	String AESMasterKey  = "wP0aJee2F/zWPPz9jTS3qA==";
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		 colorDataConnection(); 
		 firstScene(primaryStage);

	}
	
	
	public void firstScene(Stage primaryStage) {
		functionClass = new FunctionClass(); // instantiate the FunctionClass

		BorderPane mainBP = new BorderPane();
		mainBP.setPadding(new Insets(170));

		GridPane innerGP = new GridPane();
		innerGP.setAlignment(Pos.CENTER);
		innerGP.setHgap(20);
		innerGP.setVgap(10);
		innerGP.setPadding(new Insets(30, 30, 30, 30));

		Button registerButton = new Button("Register");
		Button loginButton = new Button("Login");

		Text choose = new Text();
		choose.setText("Choose an option (●'◡'●)");

		// set position for grid
		GridPane.setColumnSpan(choose, 2);
		innerGP.add(choose, 0, 0, 2, 1);
		innerGP.add(registerButton, 0, 2);
		innerGP.add(loginButton, 1, 2);

		registerButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				registerScene(primaryStage);
			}
		});

		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				loginScene(primaryStage);
			}
		});

		innerGP.setMinHeight(200);
		innerGP.setMinWidth(100);
		innerGP.setMaxHeight(500);
		innerGP.setMaxWidth(400);
		innerGP.setStyle("-fx-background-color:rgba(255,255,255,0.7)");
		mainBP.setCenter(innerGP);
		mainBP.setStyle("-fx-background-image: url('mainPage.png');" + "-fx-background-size: cover;"
				+ "-fx-background-repeat: no-repeat;");

		Scene scene = new Scene(mainBP, 1000, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Choose Action");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public void registerScene(Stage primaryStage) {

		functionClass = new FunctionClass(); // instantiate the FunctionClass

		BorderPane mainBP = new BorderPane();
		mainBP.setPadding(new Insets(170));

		GridPane innerGP = new GridPane();
		innerGP.setAlignment(Pos.CENTER);
		innerGP.setHgap(20);
		innerGP.setVgap(10);
		innerGP.setPadding(new Insets(30, 30, 30, 30));

		Text reminder = new Text("Please remember your password !");

		TextField usernameInput = new TextField();
		usernameInput.setPromptText("Enter create username");
//	    PasswordField
		TextField passwordInput = new TextField();
		passwordInput.setPromptText("Enter create password");

		Label username = new Label("Username: ");
		Label password = new Label("Password: ");
		Label title = new Label("User Register: ");
		Button registerButton = new Button("Register");

		// set position for grid
		GridPane.setColumnSpan(title, 2);
		innerGP.add(title, 0, 0, 2, 1);
		innerGP.add(username, 0, 2);
		innerGP.add(usernameInput, 1, 2);
		innerGP.add(password, 0, 3);
		innerGP.add(passwordInput, 1, 3);
		innerGP.add(reminder, 1, 4);
		innerGP.add(registerButton, 1, 7, 2, 1);

		registerButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String username = usernameInput.getText();
				String password = passwordInput.getText();

				boolean registrationSuccessful = functionClass.registerUser(username, password);
				loginScene(primaryStage);
			}
		});

		innerGP.setMinHeight(200);
		innerGP.setMinWidth(100);
		innerGP.setMaxHeight(500);
		innerGP.setMaxWidth(400);
		innerGP.setStyle("-fx-background-color:rgba(255,255,255,0.7)");
		mainBP.setCenter(innerGP);
		mainBP.setStyle("-fx-background-image: url('register.png');" + "-fx-background-size: cover;"
				+ "-fx-background-repeat: no-repeat;");

		Scene scene = new Scene(mainBP, 1000, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("User Register");
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	public void loginScene(Stage primaryStage) {
		functionClass = new FunctionClass(); // instantiate the FunctionClass

		BorderPane mainBP = new BorderPane();
		mainBP.setPadding(new Insets(170));

		GridPane innerGP = new GridPane();
		innerGP.setAlignment(Pos.CENTER);
		innerGP.setHgap(20);
		innerGP.setVgap(10);
		innerGP.setPadding(new Insets(30, 30, 30, 30));

		TextField usernameInput = new TextField();
		usernameInput.setPromptText("Enter username");
//	    PasswordField
		TextField passwordInput = new TextField();
		passwordInput.setPromptText("Enter password");

		Label username = new Label("Username: ");
		Label password = new Label("Password: ");
		Label title = new Label("User Login: ");
		Label unLoginreminder = new Label();
		Button loginButton = new Button("Login");

		// set position for grid
		GridPane.setColumnSpan(title, 2);
		innerGP.add(title, 0, 0, 2, 1);
		innerGP.add(username, 0, 2);
		innerGP.add(usernameInput, 1, 2);
		innerGP.add(password, 0, 3);
		innerGP.add(passwordInput, 1, 3);
		innerGP.add(loginButton, 1, 7, 2, 1);
		innerGP.add(unLoginreminder, 1, 8, 2, 1);

		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String Username = usernameInput.getText();
				String Password = passwordInput.getText();

				boolean loginDataConnection = functionClass.loginDataConnection(Username, Password);
				if (loginDataConnection) {
					decryptAndEncrypt(primaryStage);
				} else {
					unLoginreminder.setText("Invalid username or password!");
				}
			}
		});

		innerGP.setMinHeight(200);
		innerGP.setMinWidth(100);
		innerGP.setMaxHeight(500);
		innerGP.setMaxWidth(400);
		innerGP.setStyle("-fx-background-color:rgba(255,255,255,0.7)");
		mainBP.setCenter(innerGP);
		mainBP.setStyle("-fx-background-image: url('login2.png');" + "-fx-background-size: cover;"
				+ "-fx-background-repeat: no-repeat;");

		Scene scene = new Scene(mainBP, 1000, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("User Login ");
		primaryStage.setResizable(false);
		primaryStage.show();
	}



	public void decryptAndEncrypt(Stage primaryStage) {
		functionClass = new FunctionClass(); // instantiate the FunctionClass
		colorbackgroundPicker = new ColorPicker(); 
		colorbuttonPicker = new ColorPicker();
		
		
		BorderPane mainBP = new BorderPane();
		mainBP.setPadding(new Insets(170));
		
		GridPane mainPagePain = new GridPane();
		mainPagePain.setAlignment(Pos.CENTER);
		mainPagePain.setHgap(20);
		mainPagePain.setVgap(10);
//		mainPagePain.setPadding(new Insets(30, 30, 30, 30));

		Text text = new Text();
		text.setText("Choose an algorithm (●'◡'●)");

		Button buttonEncrypt = new Button();
		buttonEncrypt.setText("Encrypt Text 加密");

		Button buttonDecrypt = new Button();
		buttonDecrypt.setText("Decrypt Text 解密");

		Button buttonGenerateKEY = new Button();
		buttonGenerateKEY.setText("Generate Key");
		
		Button saveDESkeyBt = new Button();
		saveDESkeyBt.setText("Save DES key");
		
		Button saveAESkeyBt = new Button();
		saveAESkeyBt.setText("Save AES key");
		
		MenuButton colorSetting = new MenuButton("⚙ Change Color");
				
		Menu menubackgroundColor = new Menu("⚙ Background Color");
		menubackgroundColor.getItems().add(new CustomMenuItem(colorbackgroundPicker, false));
	
		colorSetting.getItems().addAll(menubackgroundColor);

		MenuItem caesarMenuItem = new MenuItem("Caesar cipher");
		MenuItem desMenuItem = new MenuItem("DES algorithm");
		MenuItem aesMenuItem = new MenuItem("AES algorithm");
		MenuButton algorithmButton = new MenuButton("Choose algorithm", null, caesarMenuItem, desMenuItem, aesMenuItem);

		TextField inputField = new TextField();
		inputField.setPromptText("Please input text here");
		TextField keyField = new TextField();
		keyField.setPrefWidth(240);
		keyField.setPromptText("Please only input key for Caesar only");
		TextField resultField = new TextField();
		resultField.setPromptText("Result of encrypt or decrypt");
		resultField.setEditable(false);

		Label titleLabel = new Label("Let's start the Application !");
		Label textLabel = new Label("Enter Text:");
		Label resultLabel = new Label("Result:");
	
		GridPane.setHalignment(titleLabel, HPos.CENTER);
		GridPane.setHalignment(buttonEncrypt, HPos.CENTER);
		GridPane.setHalignment(buttonDecrypt, HPos.CENTER);
		mainPagePain.add(titleLabel, 0, 0, 3, 1); 
		mainPagePain.add(colorSetting, 1, 1, 3, 1);
		mainPagePain.add(algorithmButton, 0, 1);
		mainPagePain.add(buttonGenerateKEY, 0, 3);
		mainPagePain.add(buttonEncrypt, 0, 4, 3, 1);
		mainPagePain.add(buttonDecrypt, 0, 5, 3, 1);
		mainPagePain.add(resultLabel, 0, 7); 
		mainPagePain.add(textLabel, 0, 2); 
		mainPagePain.add(inputField, 1, 2, 3, 1); 
		mainPagePain.add(saveDESkeyBt, 2, 3, 1, 1); 
		mainPagePain.add(saveAESkeyBt, 3, 3, 1, 1); 
		mainPagePain.add(keyField, 1, 3, 1, 1);
		mainPagePain.add(resultField, 1, 7, 3, 1); 
	
		
		saveDESkeyBt.setOnAction(event -> {
		    try {
		     String savedKey = keyField.getText();
		     String encryptSavedKey = functionClass.DESMasterKEYFileEncrypt(DESMasterKey, savedKey);
		     functionClass.saveDESkey("storeDESKey.txt", encryptSavedKey);  //每次都会覆盖上一次的key
		    } catch (Exception e) {
		     e.printStackTrace();
		    }
		   });
		
		saveAESkeyBt.setOnAction(event -> {
		    try {
		     String savedKey = keyField.getText();
		     String encryptSavedKey = functionClass.AESMasterKEYFileEncrypt(AESMasterKey, savedKey);
		     functionClass.saveAESkey("storeAESKey.txt", encryptSavedKey);  //每次都会覆盖上一次的key
		    } catch (Exception e) {
		     e.printStackTrace();
		    }
		   });


		// change color for background
		colorbackgroundPicker.setOnAction(e -> {
		    Color selectedColor = colorbackgroundPicker.getValue();

		    // Update myColor and apply the background
		    myColor = selectedColor;
		    mainPagePain.setBackground(new Background(new BackgroundFill(myColor, null, null)));
		    
		    try {
		        String sql = "UPDATE colorData SET colorValue = ? WHERE colorName = \"backgroundColor\"";
		        PreparedStatement statement = connection.prepareStatement(sql);
		        String strColor = selectedColor.getRed() + "," + selectedColor.getGreen() + "," + selectedColor.getBlue();
		        System.out.println("String: " + strColor);
		        
		        statement.setString(1, strColor);
		        statement.executeUpdate();

		        System.out.println("Color updated successfully in the database!");
		    } catch (SQLException ex) {
		        System.err.println("Error updating color in the database:");
		        ex.printStackTrace();
		    }
		});

		try {  //load previous color to application
			statement = connection.createStatement();
			String sql = "SELECT colorValue FROM colorData WHERE colorName =\"backgroundColor\"";
			ResultSet resultSet = statement.executeQuery(sql);

			if (resultSet.next()) {
				String strColor = resultSet.getString("colorValue");
				String colors[] = strColor.split(",");
				Color c = new Color(Double.parseDouble(colors[0]), Double.parseDouble(colors[1]),
						Double.parseDouble(colors[2]), 1);
				mainPagePain.setBackground(new Background(new BackgroundFill(c, null, null)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		caesarMenuItem.setOnAction(e -> {
			algorithmButton.setText("Caesar cipher");
			buttonEncrypt.setOnAction(ev -> {
				String inputTextString = inputField.getText();
				String keyString = keyField.getText();

				if (!inputTextString.isEmpty() && !keyString.isEmpty()) {
					functionClass.setKey(Integer.parseInt(keyString)); // Set the key in FunctionClass
					String encryptString = functionClass.encryptCaesar(inputTextString);
					resultField.setText(encryptString);

					functionClass.caesarDataConnection(keyString, encryptString);
				} else {
					resultField.setText("Please input key value to Encrypt");
				}
			});

			buttonDecrypt.setOnAction(ev -> {
				String inputTextString = inputField.getText();
				String keyString = keyField.getText();

				if (!inputTextString.isEmpty() && !keyString.isEmpty()) {
					functionClass.setKey(Integer.parseInt(keyString)); // Set the key in FunctionClass
					String decryptString = functionClass.decryptCaesar(inputTextString);
					resultField.setText(decryptString);

				} else {
					resultField.setText("Please input key value to Decrypt");
				}
			});
		});

		// DES
		desMenuItem.setOnAction(e -> {
			algorithmButton.setText("DES algorithm");

			// generate key for DES and AES
			buttonGenerateKEY.setOnAction(event -> {
				functionClass.generateKey("DES");
				SecretKey generatedKey = functionClass.getDESGeneratedKey();
				String encodedKey = Base64.getEncoder().encodeToString(generatedKey.getEncoded());
				keyField.setText(encodedKey);
			});

			buttonEncrypt.setOnAction(ev -> {
				String inputTextString = inputField.getText();
				String encryptString = functionClass.encryptDES(inputTextString);
				resultField.setText(encryptString);

				functionClass.desDataConnection(encryptString);
			});

			buttonDecrypt.setOnAction(ev -> {
				String inputTextString = inputField.getText();
				String inputKey = keyField.getText();
				String decryptString = functionClass.decryptDES(inputTextString, inputKey);
				resultField.setText(decryptString);
			});
		});

		// AES
		aesMenuItem.setOnAction(e -> {
			algorithmButton.setText("AES algorithm");

			buttonGenerateKEY.setOnAction(event -> {
				functionClass.generateKey("AES");
				SecretKey generatedKey = functionClass.getAESGeneratedKey();
				String encodedKey = Base64.getEncoder().encodeToString(generatedKey.getEncoded());
				keyField.setText(encodedKey);
			});

			buttonEncrypt.setOnAction(ev -> {
				String inputTextString = inputField.getText();
				String encryptString = functionClass.encryptAES(inputTextString);
				resultField.setText(encryptString);

				functionClass.aesDataConnection(encryptString);
			});

			buttonDecrypt.setOnAction(ev -> {
				String inputTextString = inputField.getText();
				String inputKey = keyField.getText();
				String decryptString = functionClass.decryptAES(inputTextString, inputKey);
				resultField.setText(decryptString);
			});
		});
		
		mainPagePain.setMinHeight(300);
		mainPagePain.setMinWidth(300);
		mainPagePain.setMaxHeight(500);
		mainPagePain.setMaxWidth(800);
		mainPagePain.setStyle("-fx-background-color:rgba(255,255,255,0.7)");
		mainBP.setCenter(mainPagePain);
		mainBP.setStyle("-fx-background-image: url('login.png');" + "-fx-background-size: cover;"
				+ "-fx-background-repeat: no-repeat;");

		Scene scene = new Scene(mainBP, 1000, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Encrepty and Decrepty Game 🎮");
		primaryStage.setResizable(false);
		primaryStage.show();


	}
  
	public void colorDataConnection() {

		String JDBC_URL = "jdbc:mysql://localhost:3306/CloudandSecurityAssessment";
		String USERNAME = "root";
		String PASSWORD = "";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Open a connection

			this.connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
			System.out.println("Connected to database...");
		} catch (ClassNotFoundException | SQLException e) {
        // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}