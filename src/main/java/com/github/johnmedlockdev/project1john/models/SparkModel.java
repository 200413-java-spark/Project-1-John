package com.github.johnmedlockdev.project1john.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SparkModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double calculation;

    public SparkModel(long id, double calculation) {
        this.id = id;
        this.calculation = calculation;
    }

    public SparkModel(double calculation) {
        this.calculation = calculation;
    }

    protected SparkModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getCalculation() {
        return calculation;
    }

    public void setCalculation(double calculation) {
        this.calculation = calculation;
    }
}
