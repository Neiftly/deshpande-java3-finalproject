/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deshpande;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author Vinayak
 */
public class Job implements Comparable<Job>, Serializable {

    private final long serialVersionUID = 1L;
    private int id;
    private boolean active;
    private LocalDate dateCreated;
    private String title;
    private String city;
    private String state;
    private boolean fullTime;
    private String department;
    private String experience;
    private String wageCatagory;
    private double salary;

    public Job(int id, boolean active, LocalDate dateCreated, String title,
            String city, String state, boolean fullTime, String department,
            String experience, String wageCatagory, double salary) {
        this.id = id;
        this.active = active;
        this.dateCreated = dateCreated;
        this.title = title;
        this.city = city;
        this.state = state;
        this.fullTime = fullTime;
        this.department = department;
        this.experience = experience;
        this.wageCatagory = wageCatagory;
        this.salary = salary;
    }

    public Job() {
        this.id = 1;
        this.active = true;
        this.dateCreated = LocalDate.now();
        this.title = "Default Job";
        this.city = "Default City";
        this.state = "Default State";
        this.fullTime = true;
        this.department = "Default Department";
        this.experience = "Default Experience";
        this.wageCatagory = "Default Wage Catagory";
        this.salary = 15.00f;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isFullTime() {
        return fullTime;
    }

    public void setFullTime(boolean fullTime) {
        this.fullTime = fullTime;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getWageCatagory() {
        return wageCatagory;
    }

    public void setWageCatagory(String wageCatagory) {
        this.wageCatagory = wageCatagory;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date newDateCreated(){
        return Date.from(dateCreated.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    
    @Override
    public String toString() {
        return "Title: " + title + "\nCity: " + city + "\nState: " + state 
                + "\nDepartment: " + department + "\nActive: " + active;
    }

    @Override
    public int compareTo(Job other) {
        int result = this.dateCreated.compareTo(other.dateCreated);
        if (result == 0) {
            result = this.title.compareToIgnoreCase(other.title);
        }
        return result;
    }
}
