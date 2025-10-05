package com.andela.colloquial.converter;

import com.andela.colloquial.converter.strategy.TimeToSpeechStrategy;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Scanner;

public class TimeToColloquialApp {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("H:mm");
    private static final Map<String, String> EXAMPLES = Map.of(
            "1:00", "one o'clock",
            "2:05", "five past two",
            "3:10", "ten past three"
    );
    /**
     * Main method for command-line usage.
     * Supports interactive mode.
     *
     * @param args command line arguments (optional)
     */
    public static void main(String[] args) {
        // No arguments - show usage and enter interactive mode.
        TimeToSpeechStrategy colloquialConverter = TimeToColloquialConverterFactory.britishTimeToColloquialConverter();
        printUsage();
        runInteractiveMode(colloquialConverter);
    }

    private static void printUsage() {
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║        Colloquial Time Converter(British)                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        System.out.println(" Usage:");
        System.out.println("\n  Interactive mode:");
        System.out.println("    1. Enter time(H:mm) to get colloquially converted form");
        System.out.println("    2. To end type: 'exit', type 'help' for examples\n");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    private static void runInteractiveMode(TimeToSpeechStrategy colloquialConverter) {
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
                
                process(colloquialConverter, input);
                System.out.print("Enter time (H:mm): ");
            }
        }
    }

    private static void process(TimeToSpeechStrategy colloquialConverter, String input) {
        try {
            String result = convert(colloquialConverter, input);
            System.out.println(result + "\n");
        } catch (IllegalArgumentException e) {
            System.out.printf("   X Error: %s%n%n", e.getMessage());
        }
    }

    private static String convert(TimeToSpeechStrategy colloquialConverter,String timeString) {
        if (timeString == null || timeString.trim().isEmpty()) {
            throw new IllegalArgumentException("Time cannot be null or empty");
        }

        try {
            LocalTime time = LocalTime.parse(timeString.trim(), TIME_FORMATTER);
            return colloquialConverter.convertToSpokenForm(time);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Invalid time format. Expected format: H:mm or HH:mm (e.g., 9:30 or 09:30)", e
            );
        }
    }


    private static void printExitMessage() {
        System.out.println("\n Exiting......");
    }

    private static void printExamples() {
        System.out.println("\nExample conversions:");
        EXAMPLES.forEach((time, text) ->
                System.out.printf(" %s  -> %s%n", time, text)
        );
        System.out.print("Enter time (H:mm): ");
    }

    private static boolean isExit(String input) {
        return input.equalsIgnoreCase("exit");
    }

    private static boolean isBlank(String input) {
        return input.isEmpty();
    }

    private static boolean isHelp(String input) {
        return input.equalsIgnoreCase("help");
    }

    /**
     * Reads and returns user input from the scanner.
     */
    private static String readInput(Scanner scanner) {
        return scanner.nextLine().trim();
    }
}
