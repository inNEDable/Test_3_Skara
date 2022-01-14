package skaradjinica;

import foods.Bread;
import foods.Meat;
import foods.Salad;

public class Order {

    private Bread breadType;
    private Meat meatType;
    private Salad saladType;

    public Order(Bread breadType, Meat meatType, Salad saladType) {
        this.breadType = breadType;
        this.meatType = meatType;
        this.saladType = saladType;
    }

    @Override
    public String toString() {
        return "Order{ " +
                 breadType.getFoodSubtype() +
                meatType.getFoodSubtype() +
                 saladType.getFoodSubtype() +
                " ";
    }
}
