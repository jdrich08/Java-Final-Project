import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Rating {
	
	static Scanner input = new Scanner(System.in);
	static double ratingNum;
	static String ratingComment = null;
	static int ratingID;
	
	//Set SQL Rating insert statement
	private static final String INSERT_INTO_RATING = "insert into rating values (? , ? , ? , ?) ";
	
	//Get next Rating ID
	private static final String SELECT_FROM_RATING_ID = "select Rating_ID from rating order by Rating_ID desc limit 1";

	public static void insertRating() throws SQLException {
		
		Connection connection = Login.connection();	
		
		try {
			PreparedStatement insertRating = connection.prepareStatement(INSERT_INTO_RATING);

			int i=1;
			insertRating.setInt(i++, ratingID);
			insertRating.setString(i++, ratingComment);
			insertRating.setDouble(i++, ratingNum);
			insertRating.setInt(i++, Movie.movieID);
			int result = insertRating.executeUpdate();
			
			if (result == 1) {
				System.out.println("Successfully inserted.");
			}
			else {
				System.out.println("Failed to insert.");
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	
	public static void getRatingID() throws SQLException {

		Connection connection = Login.connection();
		
		PreparedStatement ratingIDStmt = connection.prepareStatement(SELECT_FROM_RATING_ID);
		ResultSet resultSet = ratingIDStmt.executeQuery();
		
		//Sets movieID to next available Movie_ID for insertToMovieTable()
		if (!resultSet.next()) {
			ratingID = 1;
		}
		else {
			ratingID = resultSet.getInt(1);
			ratingID++;
		}
	}
	
	public static void setMovieID() {
		System.out.println("ENTER MOVIE ID: ");
		Movie.movieID = input.nextInt();
	}
	
	public static void setRatingNum() {
		do {
			System.out.println("ENTER MOVIE RATING 1-10: ");
			ratingNum = input.nextDouble();
			if (ratingNum > 10 || ratingNum < 1) {
				System.out.println("Invalid rating. Try again.");
			}
		} while (ratingNum > 10 || ratingNum < 1);
	}
	
	public static void setComment() {	
			System.out.println("ENTER A COMMENT(OPTIONAL): ");
			input.nextLine();
			ratingComment = input.nextLine();
	}
	
	public static void addRating() throws SQLException {
		int rateOpt = 0;
		
		do {
			System.out.println("RATE A MOVIE? '1' FOR 'Yes', '2' FOR 'No': ");
			rateOpt = input.nextInt();
			if (rateOpt < 1 || rateOpt > 2) {
				System.out.println("Invalid input. Try again.");
			}
		} while (rateOpt < 1 || rateOpt > 2);
		
		if (rateOpt == 1) {
			Rating.setMovieID();
			Rating.setRatingNum();
			Rating.setComment();
			
			try {
				Rating.getRatingID();
			} catch (SQLException ex) {
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
			
			try {
				Rating.insertRating();
			} catch (SQLException ex) {
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
		}
	}

}
