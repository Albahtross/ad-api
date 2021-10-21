package com.skillstorm.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.skillstorm.beans.Ad;
import com.skillstorm.beans.AdId;

public class AdDAO {
	private static final String url = "jdbc:mysql://localhost:3306/adrev";
	private static final String username = "root";
	private static final String password = "root";
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Ad create(Ad ad){
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			String sql = "insert into ad(onClickURL, adDescription, adRelevanceScore, companyName, costPerClick, adDuration, clientFirstName, clientLastName) values (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);	// tells database to return generated keys
			stmt.setString(1, ad.getOnClickURL());
			stmt.setString(2, ad.getDescription());
			stmt.setFloat(3, ad.getRelevanceScore());
			stmt.setString(4, ad.getCompanyName());
			stmt.setString(5,ad.getCostPerClick());
			stmt.setInt(6, ad.getDuration());
			stmt.setString(7, ad.getFirstName());
			stmt.setString(8, ad.getLastName());
			
			
			// 4. execute the statement
			stmt.executeUpdate();	// Update because changing the table
			
			// getting back the auto-incremented id from database
			ResultSet keys = stmt.getGeneratedKeys();

			keys.next();
			int adID = keys.getInt(1);
			ad.setId(adID);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return ad;
	}
	
	public List<Ad> batchCreate(List<Ad> ads) throws SQLException {
		
		List<Ad> newMovies = new LinkedList<>();
		
		Connection conn = DriverManager.getConnection(url, username, password);
		conn.setAutoCommit(false); // begin our transaction
		boolean isCommitable = true;
		
		try {
			
			
			// multiple updates as ONE unit of work
			for (Ad a : ads) {
				
				String sql = "insert into ad(onClickURL, adDescription, adRelevanceScore, companyName, costPerClick, adDuration, clientFirstName, clientLastName) values (?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				
				stmt.setString(1, a.getOnClickURL());
				stmt.setString(2, a.getDescription());
				stmt.setFloat(3, a.getRelevanceScore());
				stmt.setString(4, a.getCompanyName());
				stmt.setString(5, a.getCostPerClick());
				stmt.setInt(6, a.getDuration());
				stmt.setString(7, a.getFirstName());
				stmt.setString(8, a.getLastName());
				
				stmt.executeUpdate();
				
				ResultSet keys = stmt.getGeneratedKeys();				
				keys.next();
				int adID = keys.getInt(1);
				a.setId(adID);
				
				newMovies.add(a);
			}
			
			
		} catch (SQLException e) {
			
			// if anything ever goes wrong, rollback the transaction
			isCommitable = false;
			conn.rollback();
			e.printStackTrace();
		} finally {
			
			// if no error occurred, commit the transaction
			if(isCommitable) {
				conn.commit();
			}
			conn.close();
		}
		
		return newMovies;
	}
	
	public List<Ad> findAll(){
		List<Ad> allAds = new LinkedList<>();
		
		
		// 2. create the connection -AND- 5. closing the connection
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			
			// 3. creating our statement
			String sql = "select adID, onClickURL, adDescription, adRelevanceScore, companyName, costPerClick, adDuration, clientFirstName, clientLastName from ad";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// 4. execute the statement
			ResultSet rs = stmt.executeQuery();	// Query because retrieving from table
			
			while(rs.next()) {
				
				// retrieving our returned attributes
				int adID = rs.getInt("adID");
				String onClickURL = rs.getString("onClickURL");
				String adDescription = rs.getString("adDescription");
				float adRelevanceScore = rs.getFloat("adRelevanceScore");
				String companyName = rs.getString("companyName");
				String costPerClick = rs.getString("costPerClick");
				int adDuration = rs.getInt("adDuration");
				String firstName = rs.getString("clientFirstName");
				String lastName = rs.getString("clientLastName");
				
				Ad newAd = new Ad(adID, onClickURL, adDescription, adRelevanceScore, companyName, costPerClick, adDuration, firstName, lastName);
				allAds.add(newAd);
			}
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		return allAds;
	}
	
	public Ad update(Ad ad) throws SQLException {
		Connection conn = DriverManager.getConnection(url, username, password);
		conn.setAutoCommit(false); // begin our transaction
		boolean isCommitable = true;
		// 2. create the connection -AND- 5. closing the connection
		try{
			
			// 3. creating our statement
			String sql = "update ad set onClickURL = ?, adDescription = ?, adRelevanceScore = ?, companyName = ?, costPerClick = ?, adDuration = ?, clientFirstName = ?, clientLastName = ? where adID = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);	// tells database to return generated keys
			stmt.setString(1, ad.getOnClickURL());
			stmt.setString(2, ad.getDescription());
			stmt.setFloat(3, ad.getRelevanceScore());
			stmt.setString(4, ad.getCompanyName());
			stmt.setString(5,ad.getCostPerClick());
			stmt.setInt(6, ad.getDuration());
			stmt.setString(7, ad.getFirstName());
			stmt.setString(8, ad.getLastName());
			stmt.setInt(9, ad.getId());
			
			// 4. execute the statement
			stmt.executeUpdate();	// Update because changing the table
			
		} catch (SQLException e) {
			e.printStackTrace();
			isCommitable = false;
			conn.rollback();
			e.printStackTrace();
		}
		finally {
			// if no error occurred, commit the transaction
			if(isCommitable) {
				conn.commit();
			}
			conn.close();
		}
		
		return ad;
	}
	
	public void delete(AdId adId) throws SQLException{
		
		Connection conn = DriverManager.getConnection(url, username, password);
		conn.setAutoCommit(false); // begin our transaction
		boolean isCommitable = true;
		
		try{
			
			// 3. creating our statement
			String sql = "delete from ad WHERE adID = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);

			System.out.println(sql);
			stmt.setInt(1, adId.getId());
			
			// 4. execute the statement
			stmt.executeUpdate();	// Update because changing the table
			
		} catch (SQLException e) {
			e.printStackTrace();
			isCommitable = false;
			conn.rollback();
			e.printStackTrace();
		}
		finally {
			
			// if no error occurred, commit the transaction
			if(isCommitable) {
				conn.commit();
			}
			conn.close();
		}
	}
}
