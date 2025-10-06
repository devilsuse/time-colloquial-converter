package com.andela.colloquial.converter.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Centralized application-level error handling helper.
 */
public final class ErrorHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ErrorHandler.class);

    private ErrorHandler() { }

    public static void handleAndReport(Throwable throwable) {
        if (throwable instanceof InvalidInputException) {
            LOG.warn("X Error: {}", throwable.getMessage());
            return;
        }

        if (throwable instanceof LocalizationException) {
            LOG.error("Localization error: {}", throwable.getMessage());
            return;
        }

        if (throwable instanceof ApplicationException) {
            LOG.error("Application error: {}", throwable.getMessage());
            return;
        }

        LOG.error("Unexpected error", throwable);
    }
}


