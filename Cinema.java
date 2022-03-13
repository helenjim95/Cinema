package cinema;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Cinema {
    static int numberOfRows;
    static int numberOfSeats;
    static int totalNumberOfSeats;
    static int ticketPrice;
    static int currentIncome;
    static int totalIncome;
    static int rowBooked;
    static int seatBooked;
    static String[][] seatPlan;
    static int userInput;
    static boolean stopProgram = false;
    static boolean taken = true;
    static int numberOfPurchasedTicket;
    static double percentage;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows: ");
        numberOfRows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row: ");
        numberOfSeats = scanner.nextInt();
        seatPlan = new String[numberOfRows + 1][numberOfSeats + 1];
        totalNumberOfSeats = numberOfRows * numberOfSeats;
        initiateSeatPlan();
        while (!stopProgram) {
            printMenu();
            proceed();
        }
    }

    public static void initiateSeatPlan() {
        seatPlan[0][0] = " ";
//      assign row number
        for (int i = 0; i < seatPlan.length - 1; i++) {
            int rowIndex = i + 1;
            String rowIndexStr = Integer.toString(rowIndex);
            seatPlan[i + 1][0] = rowIndexStr;
        }
//      assign seat number
        for (int j = 0; j < seatPlan[0].length - 1; j++) {
            int seatIndex = j + 1;
            String seatIndexStr = Integer.toString(seatIndex);
            seatPlan[0][seatIndex] = seatIndexStr;
        }
//        configure seats
        String empty = "S";
        for (int i = 0; i < seatPlan.length - 1; i++) {
            for (int j = 0; j < seatPlan[i].length - 1; j++) {
                seatPlan[i + 1][j + 1] = empty;
            }
        }
    }

    public static void printSeatPlan() {
        System.out.println("Cinema:");
        for (int i = 0; i < seatPlan.length;i ++) {
            for (int j = 0; j < seatPlan[0].length; j++) {
                System.out.print(seatPlan[i][j]);
                System.out.print(" ");
            }
            System.out.println(" ");
        }
    }

    public static void updateSeatPlan() {
        String booked = "B";
        for (int i = 0; i < seatPlan.length - 1; i++) {
            for (int j = 0; j < seatPlan[i].length - 1; j++) {
                int rowIndex = i + 1;
                int seatIndex = j + 1;
                if (rowIndex == rowBooked && seatIndex == seatBooked) {
                    seatPlan[rowIndex][seatIndex] = booked;
                }
            }
        }
    }

    public static void printMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
        userInput = scanner.nextInt();
    }

    public static void proceed() {
        switch (userInput) {
            case 1:
                printSeatPlan();
                break;
            case 2:
                buyTicket();
                break;
            case 3:
                showStatistics();
                break;
            case 0:
                stopProgram = true;
                break;
        }
    }

    public static void buyTicket() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a row number:");
        rowBooked = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        seatBooked = scanner.nextInt();
        checkSeat();
        while (taken) {
            System.out.println("Enter a row number:");
            rowBooked = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            seatBooked = scanner.nextInt();
            checkSeat();
        }
            updateSeatPlan();
            calculateTicketPrice();
            System.out.println("Ticket price: $" + ticketPrice);
            numberOfPurchasedTicket++;
            currentIncome += ticketPrice;
            calculatePercentage();
    }

    public static void checkSeat() {
        if ((rowBooked > seatPlan.length - 1) || (seatBooked > seatPlan[0].length - 1) || rowBooked < 1 || seatBooked < 1) {
            System.out.println("Wrong input!");
            taken = true;
        } else if ("B".equals(seatPlan[rowBooked][seatBooked])) {
            System.out.println("That ticket has already been purchased!");
            taken = true;
        } else {
            taken = false;
        }
    }

    public static void calculateIncome() {
        int totalSeats = numberOfSeats * numberOfRows;
        if (totalSeats <= 60) {
            ticketPrice = 10;
            totalIncome = ticketPrice * totalSeats;
        } else {
            int frontRows = numberOfRows / 2;
            int BackRows = numberOfRows - frontRows;
            int ticketPriceFrontRows = 10;
            int ticketPriceBackRows = 8;
            totalIncome = (frontRows * ticketPriceFrontRows + BackRows * ticketPriceBackRows) * numberOfSeats;
        }
    }

    public static void calculateTicketPrice() {
        int totalSeats = numberOfSeats * numberOfRows;
            if (totalSeats <= 60) {
            ticketPrice = 10;
        } else {
                int frontRows = numberOfRows / 2;
                int BackRows = numberOfRows - frontRows;
                int ticketPriceFrontRows = 10;
                int ticketPriceBackRows = 8;
                if (rowBooked <= frontRows) {
                    ticketPrice = ticketPriceFrontRows;
                } else {
                    ticketPrice = ticketPriceBackRows;
                }
        }
    }

    public static void calculatePercentage() {
        percentage = (double) numberOfPurchasedTicket / totalNumberOfSeats * 100;
    }

    public static void showStatistics() {
        calculateIncome();
        System.out.printf("Number of purchased tickets: %d", numberOfPurchasedTicket);
        System.out.println();
        System.out.printf("Percentage: %.2f%%", percentage);
        System.out.println();
        System.out.printf("Current income: $%d", currentIncome);
        System.out.println();
        System.out.printf("Total income: $%d", totalIncome);
        System.out.println();
    }
}