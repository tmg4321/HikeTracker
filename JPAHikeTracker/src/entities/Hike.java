package entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Hike {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
//	@Column(name="name") unnecessary but illustrative mapping 
	private String name;
	
//	@Column(name="distance") unnecessary but illustrative mapping
	private double distance;
	
	private int elevation;
	
	private int time;
	
	@OneToMany(mappedBy = "hike", cascade=CascadeType.ALL)
	@OrderBy("id ASC")
	@JsonManagedReference
	private Set<Picture> pictures;
	

// getters & setters
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public Set<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(Set<Picture> pictures) {
		this.pictures = pictures;
	}
	

	public int getElevation() {
		return elevation;
	}

	public void setElevation(int elevation) {
		this.elevation = elevation;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getId() {
		return id;
	}

@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Hike [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", distance=");
		builder.append(distance);
		builder.append(", elevation=");
		builder.append(elevation);
		builder.append(", time=");
		builder.append(time);
		builder.append("]");
		return builder.toString();
	}
	
}
