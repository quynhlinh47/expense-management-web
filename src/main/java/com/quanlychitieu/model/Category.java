package com.quanlychitieu.model;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Table(name = "categories")

@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String name;
    
    private String type;
    
    @Transient
    private Double totalSpent = 0.0;
    
 // GETTER SETTER

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
    public String getType() {
    	return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
    public Double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(Double totalSpent) {
        this.totalSpent = totalSpent;
    }
}