package com.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="car_maker")
public class CarMaker {    
    
	@Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)    
    private Long id;
    
	@NotNull
    private String name;
    
	@OneToMany(mappedBy="maker", orphanRemoval = true, cascade=CascadeType.ALL)
    private List<CarModel> models;

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

	public List<CarModel> getModels() {
		return models;
	}

	public void setModels(List<CarModel> models) {
		this.models = models;
	}
	
}
