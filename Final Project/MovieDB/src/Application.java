import java.sql.SQLException;
import java.util.Scanner;

public class Application {
	
	//Main Variables
	static Scanner input = new Scanner(System.in);
	static boolean exit = false;
	static boolean menu = false;
	static int selection = 0;
	static boolean auth = false;
	public static String userInput = null;
	public static String passInput = null;
	
	public static void main(String[] args) throws SQLException {
		do {
			//Display Login Menu
			System.out.println("MOVIE DATABASE LOGIN");
			System.out.println("________________________________________________________________________");
			System.out.println("1) ADMIN");
			System.out.println("2) USER");
			System.out.println("3) EXIT");
			System.out.println("========================================================================");
			
			//User Selection (Needs Exception code)**********
			do {
				selection = input.nextInt();
				if (selection > 4 || selection < 1) {
					System.out.println("Invalid selection. Choose 1 - 4 from the login menu.");
				}
			} while (selection > 4 || selection < 1);
			
			//Login Menu
			switch(selection) {
			
			//login as Admin
			case 1:
				//Login
				do {
					Login.setUsername();
					Login.setPassword();
					auth = Login.adminLogin();
				} while (auth == false);
				
				//Validate
				if (auth == true) {
					do {
						//Retrieves Admin Menu
						menu = Menu.getAdminMenu();
					} while (menu == false);
				}
				break;
			
			//login as User
			case 2:
				do {
					//Retrieves User Menu
					menu = Menu.getUserMenu();
				} while (menu == false);
				break;
			
			//exit program
			case 3:
				Login.closeConnection();
				System.out.println("Closed.");
				exit = true;
				break;
				
			}
		} while (exit != true);
	}
}