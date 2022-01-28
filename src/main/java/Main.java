import foods.Bread;
import foods.Meat;
import foods.Salad;
import skaradjinica.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.concurrent.Callable;

public class Main {

    public static void main(String[] args) {


        BreadChef breadChef = new BreadChef();
        GrillChef grillChef = new GrillChef();
        SaladChef saladChef = new SaladChef();
        Seller seller = new Seller();
        Skaradjiinica skaradjiinica = new Skaradjiinica(breadChef, grillChef, saladChef, seller);
        try {
            Files.deleteIfExists(Path.of("C:\\Users\\NED\\IdeaProjects\\Test_3_Skara\\src\\main\\java\\reports\\sales.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        for (int i = 0; i < 50; i++) {
            new Client("Client " + (i+1), 20).enterSkaradjiinica(skaradjiinica);
        }



    }
}
