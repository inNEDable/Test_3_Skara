import foods.Bread;
import foods.Meat;
import foods.Salad;
import skaradjinica.*;

import java.util.Random;
import java.util.concurrent.Callable;

public class Main {

    public static void main(String[] args) {


        BreadChef breadChef = new BreadChef();
        GrillChef grillChef = new GrillChef();
        SaladChef saladChef = new SaladChef();
        Seller seller = new Seller();
        Skaradjiinica skaradjiinica = new Skaradjiinica(breadChef, grillChef, saladChef, seller);

        for (int i = 0; i < 50; i++) {
            new Client("Client " + (i+1), 20).enterSkaradjiinica(skaradjiinica);
        }



    }
}
