package com.andela.colloquial.converter;

import java.util.Scanner;

public class TimeToColloquialApp {

    /**
     * Main method for command-line usage.
     * Supports interactive mode.
     *
     * @param args command line arguments (optional)
     */
    public static void main(String[] args) {
        // No arguments - show usage and enter interactive mode.
        printUsage();
        runInteractiveMode();
    }

    private static void printUsage() {
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║        Colloquial Time Converter(British)                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        System.out.println("\n Usage:");
        System.out.println("\n  Interactive mode:");
        System.out.println("    Enter time to get colloquially converted form");
        System.out.println("    To end type: 'exit', type 'help' for examples\n");
        System.out.println("\n" + "─".repeat(64));
    }

    private static void runInteractiveMode() {
        System.out.print("Enter time (H:mm): ");
        try(Scanner scanner = new Scanner(System.in)){
            
            while(scanner.hasNext()){
                String input = readInput(scanner);

                if(isExit(input)){
                    printExitMessage();
                    return;
                }
                
                if(isBlank(input)){
                    continue;
                }
                
                if(isHelp(input)){
                    printExamples();
                    continue;
                }
                

            }
        }
    }

    private static void printExitMessage() {
        System.out.println("\n Exiting......");
    }

    private static void printExamples() {
        System.out.println("\nExample conversions:");
        System.out.println(" 1:00  -> one o'clock");
        System.out.println(" 2:05  -> five past two");
        System.out.println(" 3:10  -> ten past three");
    }

    private static boolean isExit(String input) {
        return input.trim().equalsIgnoreCase("exit");
    }

    private static boolean isBlank(String input) {
        return input.trim().isEmpty();
    }

    private static boolean isHelp(String input) {
        return input.trim().equalsIgnoreCase("help");
    }

    /**
     * Reads and returns user input from the scanner.
     */
    private static String readInput(Scanner scanner) {
        //System.out.print("Enter time (H:mm): ");
        return scanner.nextLine().trim();
    }


}
