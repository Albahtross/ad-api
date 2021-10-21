package com.skillstorm.servlets;


import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.beans.Ad;
import com.skillstorm.beans.AdId;
import com.skillstorm.data.AdDAO;

@WebServlet(urlPatterns = "/ad/api")
public class AdServlet extends HttpServlet{
	AdDAO dao = new AdDAO();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("get method");
		// use the DAO to retrieve all movies
		List<Ad> allAds = dao.findAll();
		
		// convert our list of movies to a json string
		String json = new ObjectMapper().writeValueAsString(allAds);
		
		// write the json string to our http response
		resp.getWriter().print(json);
		
		// setting content type to JSON
		resp.setContentType("application/json");
		
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("post method");
		
		// parse the body of the http request
		InputStream requestBody = req.getInputStream();
		System.out.println(requestBody);
		
		// convert the request body into a java object
		Ad ad = new ObjectMapper().readValue(requestBody, Ad.class);
		
		// updating the movie object to contain the generated id
		Ad updatedAd = dao.create(ad);
		
		// returning back the updated movie as a json string
		resp.getWriter().print(new ObjectMapper().writeValueAsString(updatedAd));
		
		// set the status code to 201: CREATED
		resp.setStatus(201);
		
		// setting content type to JSON
		resp.setContentType("application/json");
		
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("put method");
		
		// parse the body of the http request
		InputStream requestBody = req.getInputStream();
		System.out.println(requestBody);
		
		// convert the request body into a java object
		Ad ad = new ObjectMapper().readValue(requestBody, Ad.class);
		System.out.println(ad.toString());
		
		// updating the movie object to contain the generated id
		Ad updatedAd;
		try {
			updatedAd = dao.update(ad);
			resp.getWriter().print(new ObjectMapper().writeValueAsString(updatedAd));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		resp.setStatus(200); //successfully updated with response
		
		// setting content type to JSON
		resp.setContentType("application/json");
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		System.out.println("delete method");
		InputStream requestBody = req.getInputStream();
		AdId adId = new ObjectMapper().readValue(requestBody, AdId.class);
		try {
			dao.delete(adId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resp.setStatus(204); // success but no response entity
	}
}
