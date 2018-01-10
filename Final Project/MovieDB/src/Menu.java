import java.sql.SQLException;
import java.util.Scanner;

public class Menu {
	
	static Scanner input = new Scanner(System.in);
	public static int selection;
	public static boolean exitToMain = false;
	
	//The Admin Menu
	public static boolean getAdminMenu() throws SQLException {
		
		exitToMain = false;
		
		do {
			System.out.println("ADMIN MENU");
			System.out.println("________________________________________________________________________");
			System.out.println("1) VIEW MOVIES");
			System.out.println("2) ADD MOVIES");
			System.out.println("3) EDIT MOVIES");
			System.out.println("4) DELETE MOVIES");
			System.out.println("5) EXIT TO MAIN MENU");
			System.out.println("========================================================================");
			selection = input.nextInt();
		
			switch(selection) {
		
			//Displays all movies
			case 1: 
				try {
					Movie.viewMovies();
				} catch (SQLException ex) {
					System.out.println("SQLException: " + ex.getMessage());
					System.out.println("SQLState: " + ex.getSQLState());
					System.out.println("VendorError: " + ex.getErrorCode());
				}
				break;
			
			//Insert a new movie
			case 2:
				//Sets next available Movie_ID
				try {
					Movie.getMovieID();
				} catch (SQLException ex) {
					System.out.println("SQLException: " + ex.getMessage());
					System.out.println("SQLState: " + ex.getSQLState());
					System.out.println("VendorError: " + ex.getErrorCode());
				}
				
				//Inserts movie
				try {
					Movie.insertMovie();
				} catch (SQLException ex) {
					System.out.println("SQLException: " + ex.getMessage());
					System.out.println("SQLState: " + ex.getSQLState());
					System.out.println("VendorError: " + ex.getErrorCode());
				}
				break;
				
			//Update a movie
			case 3:
				try {
					Movie.updateMovie();
				} catch (SQLException ex) {
					System.out.println("SQLException: " + ex.getMessage());
					System.out.println("SQLState: " + ex.getSQLState());
					System.out.println("VendorError: " + ex.getErrorCode());
				}
				break;
			
			//Delete a movie
			case 4:
				try {
					Movie.deleteMovie();
				} catch (SQLException ex) {
					System.out.println("SQLException: " + ex.getMessage());
					System.out.println("SQLState: " + ex.getSQLState());
					System.out.println("VendorError: " + ex.getErrorCode());
				}
				break;
				
			//Exit Admin Menu
			case 5:
				System.out.println("Logged Out.");
				exitToMain = true;
				break;
			
			}
		} while (exitToMain == false);
		return exitToMain;
	}
	
	//The User Menu
	public static boolean getUserMenu() throws SQLException {
		
		exitToMain = false;
		
		do {
			System.out.println("USER MENU");
			System.out.println("________________________________________________________________________");
			System.out.println("1) VIEW MOVIES");
			System.out.println("2) SEARCH MOVIES");
			System.out.println("3) EXIT TO MAIN MENU");
			System.out.println("========================================================================");
			selection = input.nextInt();
			
			switch(selection) {
			
			//Displays all Movies
			case 1: 
				try {
					Movie.viewMovies();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				Rating.addRating();
				break;
			
			//Searches movies by either Movie_Name or Movie_ID (User selected)
			case 2:
				System.out.println("SEARCH MOVIES");
				System.out.println("________________________________________________________________________");
				System.out.println("1) SEARCH BY NAME: ");
				System.out.println("2) SEARCH BY ID: ");
				System.out.println("========================================================================");
				int menuOpt = input.nextInt();
				
				switch(menuOpt) {
				
				//Search by Name
				case 1:
					try {
						Movie.searchMovieName();
					} catch (SQLException ex) {
						System.out.println("SQLException: " + ex.getMessage());
						System.out.println("SQLState: " + ex.getSQLState());
						System.out.println("VendorError: " + ex.getErrorCode());
					}				
					break;
					
				//Search by ID	
				case 2:
					try {
						Movie.searchMovieID();
					} catch (SQLException ex) {
						System.out.println("SQLException: " + ex.getMessage());
						System.out.println("SQLState: " + ex.getSQLState());
						System.out.println("VendorError: " + ex.getErrorCode());
					}
					break;
				}
				break;
		
			//Exits User Menu
			case 3:
				System.out.println("Logged Out.");
				exitToMain = true;
				break;
			}
		} while (exitToMain == false);
		return exitToMain;
	}
}