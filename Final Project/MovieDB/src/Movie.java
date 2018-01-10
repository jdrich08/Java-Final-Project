import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Movie {
	
	static Scanner input = new Scanner(System.in);
	static int movieID = 0;
	static String movieName;
	
	//Set SQL Select all statement
	private static final String SELECT_ALL_FROM_MOVIE = "SELECT movie.*, (select AVG(rating.Rating_Num) FROM rating WHERE movie.Movie_ID = rating.Movie_ID) AS Average_Rating FROM movie";
	
	//Set SQL Insert statement
	private static final String INSERT_INTO_MOVIE = "insert into movie values (? , ? , ?) ";
	
	//Get next Movie ID
	private static final String SELECT_FROM_MOVIE_ID = "select Movie_ID from movie order by Movie_ID desc limit 1";
	
	//Set SQL Update statement
	private static final String UPDATE_MOVIE = "update movie set movie_name = ? , movie_description = ? where movie_id = ?";
	
	//Set SQL Delete statement
	private static final String DELETE_FROM_MOVIE = "delete from movie where movie_id = ?";
	
	//Set SQL Search by Movie_Name statement
	private static final String SELECT_FROM_MOVIE_SEARCH_NAME = "SELECT movie.*, (select AVG(rating.Rating_Num) FROM rating WHERE movie.Movie_ID = rating.Movie_ID) AS Average_Rating FROM movie WHERE Movie_Name = ?";
	
	//Set SQL Search by Movie_ID statement
	private static final String SELECT_FROM_MOVIE_SEARCH_ID = "SELECT movie.*, (select AVG(rating.Rating_Num) FROM rating WHERE movie.Movie_ID = rating.Movie_ID) AS Average_Rating FROM movie WHERE Movie_ID = ?";
	
	//Displays all ratings and comments for a movie
	private static final String SELECT_ALL_FROM_RATING_BY_MOVIE_ID = "select Rating_Num, Rating_Comment from rating where Movie_ID = ?";
	
	//Displays all movies in database
	public static void viewMovies() throws SQLException {

		Connection connection = Login.connection();
		
		PreparedStatement allMovies = connection.prepareStatement(SELECT_ALL_FROM_MOVIE);
		ResultSet resultSet = allMovies.executeQuery();

		System.out.println("MOVIES");
		
		while (resultSet.next()) {
			// 
			String data = "ID: "+ resultSet.getString("MOVIE_ID") + ", NAME: " + resultSet.getString("MOVIE_NAME") + ", RATING: " + resultSet.getString("Average_Rating") + "/10, DESCRIPTION: " + resultSet.getString("MOVIE_DESCRIPTION");
			System.out.println("________________________________________________________________________");
			System.out.println(data);
		}
		System.out.println("========================================================================");

	}

	//Inserts New Movie into database
	public static void insertMovie() throws SQLException {
		
		Connection connection = Login.connection();
		
		System.out.println("INSERT NEW MOVIE");
		System.out.println("________________________________________________________________________");
		System.out.println("ENTER MOVIE NAME: ");
		String movieName = input.nextLine();
		System.out.println("ENTER MOVIE DESCRIPTION: ");
		String movieDescription = input.nextLine();
		
		try {
			PreparedStatement insert = connection.prepareStatement(INSERT_INTO_MOVIE);

			int i=1;
			insert.setInt(i++, movieID);
			insert.setString(i++, movieName);
			insert.setString(i++, movieDescription);
			int result = insert.executeUpdate();
			
			if (result == 1) {
				System.out.println("Successfully inserted.");
				System.out.println("========================================================================");
			}
			else {
				System.out.println("Failed to insert.");
				System.out.println("========================================================================");
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
	
	//Fetches next Movie_ID
	public static void getMovieID() throws SQLException {
		
		Connection connection = Login.connection();
		
		PreparedStatement prepareStatement = connection.prepareStatement(SELECT_FROM_MOVIE_ID);
		ResultSet resultSet = prepareStatement.executeQuery();
		
		//Sets movieID to next available Movie_ID for insertToMovieTable()
		if (!resultSet.next()) {
			movieID = 1;
		}
		else {
			movieID = resultSet.getInt(1);
			movieID++;
		}
	}

	//Searches for Movies by Movie_Name
	public static void searchMovieName() throws SQLException {
		
		Connection connection = Login.connection();

		System.out.println("SEARCH BY NAME");
		System.out.println("________________________________________________________________________");
		System.out.println("ENTER MOVIE NAME: ");
		//code to prevent "skipping" of movieName input
		if (movieID != 0) {
			input.nextLine();
		}
		movieName = input.nextLine();
		
		PreparedStatement searchMovies = connection.prepareStatement(SELECT_FROM_MOVIE_SEARCH_NAME);
		searchMovies.setString(1, movieName);
		ResultSet resultSet = searchMovies.executeQuery();
		
		try {
			
			if (resultSet.next()) {
				String movieSearch = resultSet.getString("MOVIE_ID") + ", " + resultSet.getString("MOVIE_NAME") + ", " + resultSet.getString("Average_Rating") + ", " + resultSet.getString("MOVIE_DESCRIPTION");
				System.out.println(movieSearch);
				movieID = resultSet.getInt("Movie_ID");
				
				displayComment();
				Rating.addRating();
			}
			else {
				System.out.println("Movie not found");
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
		System.out.println("========================================================================");
	}
	
	//Searches for Movies by Movie_ID
	public static void searchMovieID() throws SQLException {
		Connection connection = Login.connection();

		System.out.println("SELECT A MOVIE");
		System.out.println("________________________________________________________________________");
		System.out.println("ENTER MOVIE ID: ");
		movieID = input.nextInt();
		
		PreparedStatement searchMovies = connection.prepareStatement(SELECT_FROM_MOVIE_SEARCH_ID);
		searchMovies.setInt(1, movieID);
		ResultSet resultSet = searchMovies.executeQuery();
		
		try {
			
			if (resultSet.next()) {
				String movieSearch = resultSet.getString("MOVIE_ID") + ", " + resultSet.getString("MOVIE_NAME") + ", " + resultSet.getString("Average_Rating") + ", " + resultSet.getString("MOVIE_DESCRIPTION");
				System.out.println(movieSearch);
				displayComment();
				
				try {
					Rating.addRating();
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
			else {
				System.out.println("Movie not found");
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
		System.out.println("========================================================================");
	}
	
	public static void displayComment() throws SQLException {
		Connection connection = Login.connection();
		
		PreparedStatement allComments = connection.prepareStatement(SELECT_ALL_FROM_RATING_BY_MOVIE_ID);
		allComments.setInt(1, movieID);
		ResultSet resultSet = allComments.executeQuery();

		System.out.println("========================================================================");
		System.out.println("RATINGS AND COMMENTS");
		
		while (resultSet.next()) {
			// 
			String data = "RATING: "+ resultSet.getString("Rating_Num") + ", COMMENT: " + resultSet.getString("Rating_Comment");
			System.out.println("________________________________________________________________________");
			System.out.println(data);
		}
		System.out.println("========================================================================");
	}
		
	//Edits and updates a movie by ID
	public static void updateMovie() throws SQLException {

		Connection connection = Login.connection();
		
		System.out.println("EDIT MOVIE");
		System.out.println("________________________________________________________________________");
		System.out.println("ENTER MOVIE ID: ");
		movieID = input.nextInt();
		System.out.println("ENTER MOVIE NAME: ");
		String movieName = input.nextLine();
		System.out.println("ENTER MOVIE DESCRIPTION: ");
		String movieDescription = input.nextLine();
		
		try {
			PreparedStatement insert = connection.prepareStatement(UPDATE_MOVIE);

			int i=1;
			insert.setString(i++, movieName);
			insert.setString(i++, movieDescription);
			insert.setInt(i++, movieID);
			int result = insert.executeUpdate();
			
			if (result == 1) {
				System.out.println("Successfully updated.");
			}
			else {
				System.out.println("Failed to update.");
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
		System.out.println("========================================================================");
	}

	//Deletes a movie by ID
	public static void deleteMovie() throws SQLException {
		
		Connection connection = Login.connection();
		
		System.out.println("DELETE MOVIE");
		System.out.println("________________________________________________________________________");
		System.out.println("ENTER MOVIE ID: ");
		movieID = input.nextInt();
		
		try {
			PreparedStatement delete = connection.prepareStatement(DELETE_FROM_MOVIE);
			delete.setInt(1, movieID);
			int result = delete.executeUpdate();
			
			if (result == 1) {
				System.out.println("Successfully deleted.");
			}
			else {
				System.out.println("Failed to delete.");
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
		System.out.println("========================================================================");
	}
	

}
