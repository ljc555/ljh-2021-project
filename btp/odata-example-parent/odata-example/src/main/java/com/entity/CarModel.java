package com.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="car_model")
public class CarModel {
	
    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
     
    @NotNull
    private String name;
     
    @NotNull
    private Integer year;
     
    @ManyToOne(optional=false,fetch=FetchType.LAZY) 
    @JoinColumn(name="maker_fk")
    private CarMaker maker;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public CarMaker getMaker() {
		return maker;
	}

	public void setMaker(CarMaker maker) {
		this.maker = maker;
	}
     
    
}
