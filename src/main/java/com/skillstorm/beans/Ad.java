package com.skillstorm.beans;

import java.util.Objects;

public class Ad {
	
	private int id;
	private String onClickURL;
	private String description;
	private float relevanceScore;
	private String companyName;
	private String costPerClick;
	private int duration; //hours
	private String firstName;
	private String lastName;

	public Ad() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Ad(int id, String onClickURL, String description, float relevanceScore, String companyName,
			String costPerClick, int duration, String firstName, String lastName) {
		super();
		this.id = id;
		this.onClickURL = onClickURL;
		this.description = description;
		this.relevanceScore = relevanceScore;
		this.companyName = companyName;
		this.costPerClick = costPerClick;
		this.duration = duration;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Ad(String onClickURL, String description, float relevanceScore, String companyName,
			String costPerClick, int duration, String firstName, String lastName) {
		super();
		this.onClickURL = onClickURL;
		this.description = description;
		this.relevanceScore = relevanceScore;
		this.companyName = companyName;
		this.costPerClick = costPerClick;
		this.duration = duration;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOnClickURL() {
		return onClickURL;
	}
	public void setOnClickURL(String onClickURL) {
		this.onClickURL = onClickURL;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getRelevanceScore() {
		return relevanceScore;
	}
	public void setRelevanceScore(float relevanceScore) {
		this.relevanceScore = relevanceScore;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCostPerClick() {
		return costPerClick;
	}
	public void setCostPerClick(String costPerClick) {
		this.costPerClick = costPerClick;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(companyName, costPerClick, description, duration, firstName, id, lastName, onClickURL,
				relevanceScore);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ad other = (Ad) obj;
		return Objects.equals(companyName, other.companyName) && Objects.equals(costPerClick, other.costPerClick)
				&& Objects.equals(description, other.description) && duration == other.duration
				&& Objects.equals(firstName, other.firstName) && id == other.id
				&& Objects.equals(lastName, other.lastName) && Objects.equals(onClickURL, other.onClickURL)
				&& Float.floatToIntBits(relevanceScore) == Float.floatToIntBits(other.relevanceScore);
	}

	@Override
	public String toString() {
		return "Ad [id=" + id + ", onClickURL=" + onClickURL + ", description=" + description + ", relevanceScore="
				+ relevanceScore + ", companyName=" + companyName + ", costPerClick=" + costPerClick + ", duration="
				+ duration + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
}
