package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class taskManager {
    static String[][] task;
    static final String filename = "tasks.csv";

    public static void main(String[] args) {
        task = tasksList(filename);
        options();
        Scanner scan = new Scanner(System.in);
        while(scan.hasNextLine()) {
            switch (scan.nextLine()) {
                case "dodaj":
                    dodaj();
                    System.out.println("\nZadanie zostało dodane. \n");
                    break;
                case "lista":
                    wyswietltabele(task);
                    break;
                case "usuń":
                   usuń(task,liczba());
                   break;
                case "exit":
                    wyjscie(filename,task);
                    System.out.println(ConsoleColors.RED + "Koniec :)");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wybierz jedną z opcji znajdujących sie w  [...]. \n");
            }
            options();
        }
    }

    private static void wyjscie(String filename,String[][] tab) {
        Path path =Paths.get(filename);
        String[] noweZadania = new String[task.length];
        for (int i = 0; i < tab.length; i++) {
            noweZadania[i] = String.join(",",tab[i]);

        }
        try{
            Files.write(path,Arrays.asList(noweZadania));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static int liczba(){
        Scanner scan1 = new Scanner(System.in);
        System.out.println("Podaj numer zadania który chcesz usunąć");
        String str= scan1.nextLine();

        while(!wiekszeOd0(str)) {

            System.out.println("Podany numer musi być równy lub większy od zera.");

            str=scan1.nextLine();
        }
        return Integer.parseInt(str);

    }

    private static void usuń(String[][] tab, int index) {
        try{
            if(index < tab.length){
                task = ArrayUtils.remove(tab,index);
                System.out.println("Zadanie usunięte poprawnie.");
            }else{
                System.out.println("Liczba więkasz niż rozmiar tabeli.");
            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("zły index");
        }
    }
    public static boolean wiekszeOd0(String str) {
        if (NumberUtils.isParsable(str)) {
            return Integer.parseInt(str) >= 0;
        }
        return false;
    }

    private static void dodaj() {
        Scanner scan = new Scanner(System.in);
            System.out.println("Wpisz treść zadania:");
            String zadanie = scan.nextLine();
            System.out.println("Podaj date wykonania zadania (rok-misiąc-dzień):");
            String data = scan.nextLine();
            System.out.println("Czy to zadanie jest ważne(true/false):");
            String important = scan.nextLine();
            task =Arrays.copyOf(task, task.length +1);
                task[task.length-1]= new String[3];
                task[task.length-1][0] = zadanie;
                task[task.length-1][1] = data;
                task[task.length-1][2] = important;

    }







    private static void options() {
        String[] options = {"Wybierz opcje:",
                  "Dodaj nowe zadanie - [dodaj]" ,
                  "Usuń zadanie - [usuń]",
                  "Wyświetl liste zadań - [lista]",
                  "Wyjście z programu - [exit]"
        };
        System.out.println(ConsoleColors.BLUE + options[0]);
        System.out.println(ConsoleColors.RESET + options[1]+ "\n"+options[2]+"\n"+options[3]+"\n"+options[4] );
    }

    private static String [][] tasksList(String filename) {
        Path path = Paths.get(filename);
        if (!Files.exists(path)) {
            System.out.println("Brak pliku");
        }
             String[][] tab = null;
            try {
                List<String> str = Files.readAllLines(path);
                tab = new String[str.size()][str.get(0).split(",").length];

                for (int i = 0; i < str.size(); i++) {
                    String[] arr = str.get(i).split(",");
                    for (int j = 0; j < arr.length; j++) {
                        tab[i][j] = arr[j];
                    }
                }



            } catch (IOException e) {
                e.printStackTrace();
            }return tab;



    }

    private static void wyswietltabele(String[][] tab) {
        System.out.print("Lista zadań:");
        for (int i = 0; i < tab.length; i++) {

            System.out.print("\n"+ i + " : ");

            for (int j = 0; j < tab[i].length; j++) {

                System.out.print(tab[i][j] + " ");

            }

        }
        System.out.println("\n");

    }
}
