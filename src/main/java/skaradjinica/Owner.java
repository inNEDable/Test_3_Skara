package skaradjinica;

import db_shit.DBManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Owner extends Thread {

    private Skaradjiinica skaradjiinica;

    public Owner(Skaradjiinica skaradjiinica){
        this.skaradjiinica = skaradjiinica;
    }



    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                createDBReport();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createDBReport() throws IOException {

        File file = new File("C:\\Users\\NED\\IdeaProjects\\Test_3_Skara\\src\\main\\java\\reports\\sales.txt");
        file.createNewFile();

        int breadTypeID;
        int meatTypeID;
        int saladTypeID;
        LocalDate date;

        Scanner sc = new Scanner(file.toPath());
        while (sc.hasNextLine()){
            String currentLine = sc.nextLine();
            String [] parametersInLine = currentLine.split(" ");
            breadTypeID = Integer.parseInt(parametersInLine[0]);
            meatTypeID = Integer.parseInt(parametersInLine[1]);
            saladTypeID = Integer.parseInt(parametersInLine[2]);
            date = LocalDate.parse(parametersInLine[3]);
            insertInDB(breadTypeID, meatTypeID, saladTypeID, date);
        }


    }

    private void insertInDB(int breadTypeID, int meatTypeID, int saladTypeID, LocalDate date) {

        Connection con = DBManager.getInstance().getConnection();

        try {
            PreparedStatement statement = con.prepareStatement("""
                INSERT INTO sales
                (shop_id, bread_type_id, meat_type_id, salad_type_id, date_created)
                VALUES (?, ?, ?, ?, ?)
    """);
            statement.setInt(1, skaradjiinica.getId());
            statement.setInt(2, breadTypeID);
            statement.setInt(3, meatTypeID);
            statement.setInt(4, saladTypeID);
            statement.setDate(5, Date.valueOf(date));

            statement.executeUpdate();
            Files.writeString(Path.of("C:\\Users\\NED\\IdeaProjects\\Test_3_Skara\\src\\main\\java\\reports\\sales.txt"), "");
        } catch (Exception e) {
            System.err.println("FAILED TO INPUT IN TO DATA BASE!!!!!!!!!!");
            e.printStackTrace();
        }

    }

}
