package skaradjinica;

import foods.Bread;
import foods.Meat;
import foods.Salad;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Order {

    private Bread breadType;
    private Meat meatType;
    private Salad saladType;
    private LocalDate date;

    public Order(Bread breadType, Meat meatType, Salad saladType) {
        this.breadType = breadType;
        this.meatType = meatType;
        this.saladType = saladType;
    }

    @Override
    public String toString() {
        return breadType.getFoodSubtype().getId() + " " + meatType.getFoodSubtype().getId() + " " + saladType.getFoodSubtype().getId() +" "+ date;
    }

    public double getTotalSum() {
        return breadType.getFoodSubtype().getPrice()
                + meatType.getFoodSubtype().getPrice()
                + saladType.getFoodSubtype().getPrice();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Bread getBreadType() {
        return breadType;
    }

    public Meat getMeatType() {
        return meatType;
    }

    public Salad getSaladType() {
        return saladType;
    }
}
