package com.andela.colloquial.converter;

import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.andela.colloquial.converter.exception.ApplicationException;
import com.andela.colloquial.converter.exception.ErrorHandler;
import com.andela.colloquial.converter.exception.InvalidInputException;
import com.andela.colloquial.converter.strategy.TimeToSpeechStrategy;

/**
 * Command-line application entry point for converting times to colloquial
 * British English.
 * <p>
 * Validates user input against a whitelisted set of time patterns provided by
 * {@link TimeParsers} and prints colloquial-form results.
 * </p>
 */
public class TimeToColloquialApp {
    private static final Logger LOG = LoggerFactory.getLogger(TimeToColloquialApp.class);
    private static final Map<String, String> EXAMPLES = Map.of(
            // Baseline examples
            "1:00", "one o'clock",
            "2:05", "five past two",
            "3:10", "ten past three",
            // Allowed pattern variants for 2:05 across supported formats
            "02:05", "five past two",
            "2.05", "five past two",
            "2-05", "five past two",
            "0205", "five past two"
    );
    /**
     * Main method for command-line usage. Launches interactive mode.
     *
     * @param args command line arguments (currently unused)
     */
    public static void main(String[] args) {
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
        LOG.info(" Supported formats: {}", String.join(", ", TimeParsers.getAllowedPatterns()));
        LOG.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    /**
     * Runs interactive input loop, validating the input using {@link TimeParsers}
     * and printing converted results.
     */
    private static void runInteractiveMode(TimeToSpeechStrategy colloquialConverter) {
        System.out.print("Enter time: ");
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
                System.out.print("Enter time: ");
            }
        }
    }

    private static void process(TimeToSpeechStrategy colloquialConverter, String input) {
        try {
            String result = convert(colloquialConverter, input);
            System.out.println("===>" + result);
        } catch (ApplicationException e) {
            ErrorHandler.handleAndReport(e);
        }
    }

    /**
     * Converts a user-provided time string to its colloquial form.
     * Uses {@link TimeParsers#getFormatters()} to validate and parse the input.
     *
     * @param colloquialConverter converter strategy implementation
     * @param timeString user input time string
     * @return colloquial-form representation
     * @throws InvalidInputException if input is blank or does not match any approved pattern
     */
    private static String convert(TimeToSpeechStrategy colloquialConverter,String timeString) {
        if (timeString == null || timeString.trim().isEmpty()) {
            throw new InvalidInputException("Time cannot be null or empty");
        }

        LocalTime time = parseWithAllowedPatterns(timeString.trim());
        return colloquialConverter.convertToSpokenForm(time);
    }

    private static LocalTime parseWithAllowedPatterns(String timeString) {
        List<java.time.format.DateTimeFormatter> formatters = TimeParsers.getFormatters();
        for (var formatter : formatters) {
            try {
                return LocalTime.parse(timeString, formatter);
            } catch (DateTimeParseException ignored) { }
        }
        var allowedPatterns = String.join(", ", TimeParsers.getAllowedPatterns());
        throw new InvalidInputException("Invalid time format. Supported formats: " + allowedPatterns);
    }

    private static void printExitMessage() {
        LOG.info("\n Exiting......");
    }

    private static void printExamples() {
        LOG.info("\nExample conversions:");
        EXAMPLES.forEach((time, text) -> LOG.info(" {}  -> {}", time, text));
        LOG.info("\nAccepted input formats: {}", String.join(", ", TimeParsers.getAllowedPatterns()));
        System.out.print("Enter time: ");
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
