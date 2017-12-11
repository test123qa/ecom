package com.api.framework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class InMemoryDBUtil {

	private Connection dbConnection;

	public void getInMemoryDBConnection(boolean flag) throws ClassNotFoundException, SQLException {

		// ResourceReader.readBundle("DBConfig");
		ResourceReader.loadProperties("src/main/resources/DBConfig.properties");

		// sq lite driver

		String driverName = ResourceReader.getProperty("DriverName");
		Class.forName(driverName);

		// if flag is true connect to in Memory DB connection
		if (flag = true) {
			dbConnection = DriverManager.getConnection(ResourceReader.getProperty("InMemoryConnenctionURL"),
					ResourceReader.getProperty("InMemoryUserName"), ResourceReader.getProperty("InMemoryPassword"));
			Statement stmt = dbConnection.createStatement();
			stmt.execute("ATTACH '" + ResourceReader.getProperty("DBName") + "' AS fs");

		}
		// if flag set to false connect to Permanent DB
		else {
			dbConnection = DriverManager.getConnection(
					ResourceReader.getProperty("ConnectionURL") + ResourceReader.getProperty("DBName"),
					ResourceReader.getProperty("UserName"), ResourceReader.getProperty("Password"));
		}

	}

	public void executeQuery(String query) throws ClassNotFoundException, SQLException {

		// The query can be update query or can be select query

		Statement stmt = dbConnection.createStatement();
		boolean status = stmt.execute(query);
		if (status) {

			// Query is the select query
			ResultSet rs = stmt.getResultSet();

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1)
						System.out.print(",  ");
					String columnValue = rs.getString(i);
					System.out.print(columnValue + " " + rsmd.getColumnName(i));
				}
				System.out.println("");
			}
		}

		else {
			// query can be update or any query apart from select query
			int count = stmt.getUpdateCount();
			System.out.println("Total records updated: " + count);
		}

	}

	public void executeQueriesFromFile(String fileName) throws Exception {
		ArrayList<String> queryList = ReadFile.readtxtAsList(fileName);
		for (String query : queryList) {
			executeQuery(query);
		}
	}

	public void closeConnection() {
		try {
			dbConnection.close();
		} catch (Exception e) {
			System.out.println("Connection is not closed properly " + e.getMessage());
		}
	}
}
