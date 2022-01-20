package skaradjinica;

import foods.Bread;
import foods.Meat;
import foods.Salad;

import java.util.Random;

public class Client{

    private double money;
    private String name;
    private Skaradjiinica skaradjiinica;
    private Seller seller;
    private Bread.BreadType wantedBread;
    private Meat.MeatType wantedMeat;
    private Salad.SaladType wantedSalad;
    private Order recievedOrder;


    public Client(String name, double money) {
        this.name = name;
        this.money = money;

    }

    public void enterSkaradjiinica (Skaradjiinica skaradjiinica){

        this.skaradjiinica = skaradjiinica;

        this.wantedBread = Bread.BreadType.values()[new Random().nextInt(Bread.BreadType.values().length)];
        this.wantedMeat = Meat.MeatType.values()[new Random().nextInt(Meat.MeatType.values().length)];
        this.wantedSalad = Salad.SaladType.values()[new Random().nextInt(Salad.SaladType.values().length)];

        skaradjiinica.welcomeClient(this);
    }

    public Bread.BreadType getWantedBread() {
        return wantedBread;
    }

    public Meat.MeatType getWantedMeat() {
        return wantedMeat;
    }

    public Salad.SaladType getWantedSalad() {
        return wantedSalad;
    }

    public double payBill(double bill) throws Exception {
        if (bill > this.money){
            throw new Exception("Not enough money", new Throwable(this.name + " I DON'T HAVE ENOUGH MONEY"));
        }
        this.money -= bill;
        return bill;
    }


    public void recieveOrder(Order clientOrder) {
        this.recievedOrder = clientOrder;
        System.out.println(this.name + ":  <<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>  ПОЛУЧИХ СИ КЛьоПАНЕТО: " + clientOrder);
    }
}
