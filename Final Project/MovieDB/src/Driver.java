
abstract class Driver {
	protected static void loadDriver() {
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("Driver Loaded");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
