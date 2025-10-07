package com.andela.colloquial.converter;

import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.andela.colloquial.converter.exception.ErrorHandler;
import com.andela.colloquial.converter.exception.InvalidInputException;
import com.andela.colloquial.converter.strategy.TimeToSpeechStrategy;

public class TimeToColloquialApp {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("H:mm");
    private static final Logger LOG = LoggerFactory.getLogger(TimeToColloquialApp.class);
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
        TimeToSpeechStrategy colloquialConverter = TimeToColloquialConverters.britishTimeToColloquialConverter();
        printUsage();
        runInteractiveMode(colloquialConverter);
    }

    private static void printUsage() {
        LOG.info("╔══════════════════════════════════════════════════════════════════╗");
        LOG.info("║        Colloquial Time Converter(British)                        ║");
        LOG.info("╚══════════════════════════════════════════════════════════════════╝");
        LOG.info(" Usage:");
        LOG.info("\n  Interactive mode:");
        LOG.info("    1. Enter time(H:mm) to get colloquially converted form");
        LOG.info("    2. To end type: 'exit', type 'help' for examples\n");
        LOG.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    private static void runInteractiveMode(TimeToSpeechStrategy colloquialConverter) {
        System.out.print("Enter time (H:mm): ");
        try(Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)){
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
            LOG.info("{}", result);
        } catch (com.andela.colloquial.converter.exception.ApplicationException e) {
            ErrorHandler.handleAndReport(e);
        }
    }

    private static String convert(TimeToSpeechStrategy colloquialConverter,String timeString) {
        if (timeString == null || timeString.trim().isEmpty()) {
            throw new InvalidInputException("Time cannot be null or empty");
        }

        try {
            var time = LocalTime.parse(timeString.trim(), TIME_FORMATTER);
            return colloquialConverter.convertToSpokenForm(time);
        } catch (DateTimeParseException e) {
            throw new InvalidInputException(
                    "Invalid time format. Expected format: H:mm or HH:mm (e.g., 9:30 or 09:30)", e
            );
        }
    }


    private static void printExitMessage() {
        LOG.info("\n Exiting......");
    }

    private static void printExamples() {
        LOG.info("\nExample conversions:");
        EXAMPLES.forEach((time, text) -> LOG.info(" {}  -> {}", time, text));
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
