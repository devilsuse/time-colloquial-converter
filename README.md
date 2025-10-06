## Time Colloquial Converter (British)

Convert times (H:mm) into colloquial locale (currently only British English), e.g. 8:00 → "eight o'clock", 4:15 → "quarter past four", 9:10 → "ten past nine".

### Key Features
- **British colloquial text conversion** (quarter past/to, half past, past/to minutes)
- **Strategy pattern** for pluggable locale implementations
- **ResourceBundle**- in-built localization support
- **Static factory** with cached singleton instance for the British strategy
- **Java 17** compatible
- **Quality gates**: SpotBugs (static analysis), JaCoCo (coverage), optional SonarCloud/SonarQube integration

### Requirements
- Java 17+
- Maven 3.9+

### Build
```bash
mvn -q clean verify
```
This command compile, tests, SpotBugs, and generates JaCoCo coverage reports.

### Run (CLI)
Interactive command-line app


Then follow the on-screen prompt:
- Enter time in H:mm or HH:mm (e.g., 6:13, 06:13)
- Type `help` for examples
- Type `exit` to quit

### Examples
Input → Output:
```text
1:00  -> one o'clock
2:05  -> five past two
3:10  -> ten past three
```

### Project Structure (high level)
- `com.andela.colloquial.converter.strategy.TimeToSpeechStrategy`: Strategy interface
- `com.andela.colloquial.converter.strategy.AbstractTimeToSpeechStrategy`: Template method for the core algorithm
- `com.andela.colloquial.converter.strategy.LocalizedTimeToSpeechStrategy`: ResourceBundle-backed localization base
- `com.andela.colloquial.converter.strategy.BritishTimeToSpeechStrategy`: British implementation
- `com.andela.colloquial.converter.TimeToColloquialConverterFactory`: Static factory with a cached British instance
- `com/andela/colloquial/converter/TimeToColloquialApp`: CLI entry point
- `src/main/resources/messages_en_GB.properties`: Localized phrases

### Design Notes
- The conversion algorithm is expressed once in the abstract base (template method), delegating language-specific bits to concrete strategies.
- A **static factory** (`TimeToColloquialConverterFactory`) returns a cached, thread-safe British strategy instance to avoid unnecessary allocations.
- Input validation is explicit; exceptions surface invalid formats early.

### Exception Handling
- Custom unchecked exceptions centralize error semantics:
  - `com.andela.colloquial.converter.exception.InvalidInputException`: invalid or empty user input; bad time format
  - `com.andela.colloquial.converter.exception.LocalizationException`: missing resource bundle or localization key
  - `com.andela.colloquial.converter.exception.ApplicationException`: base type for app errors
- A global handler routes all errors consistently:
  - `com.andela.colloquial.converter.exception.ErrorHandler#handleAndReport(Throwable)`
  - Behavior:
    - InvalidInputException → warn with concise message (user-fixable)
    - LocalizationException → error with message (configuration/runtime issue)
    - Other ApplicationException → error with message
    - Unexpected exceptions → error with stack trace

Usage in CLI:
```text
TimeToColloquialApp.process(...) wraps conversion and delegates any Throwable to ErrorHandler.handleAndReport.
Empty or badly formatted inputs throw InvalidInputException; missing bundles/keys throw LocalizationException.
```

### Logging
The CLI prints prompts and results to the console for a clean interactive experience. If you prefer structured logging (e.g., SLF4J or `java.util.logging`), you can route messages accordingly without changing the conversion logic.

Notes with the error handler:
- User input issues are logged at WARN; operational/configuration issues at ERROR.
- Adjust log levels or messages by editing `ErrorHandler`.

### Quality and Reports
- SpotBugs (runs at `verify`):
  - XML: `target/spotbugsXml.xml`
  - HTML (optional):
    ```bash
    mvn com.github.spotbugs:spotbugs-maven-plugin:4.8.6.2:spotbugs
    # Report: target/site/spotbugs.html
    ```
- JaCoCo coverage (generated at `verify`):
  - XML: `target/site/jacoco/jacoco.xml`
  - HTML: `target/site/jacoco/index.html`


### Extending to other locales
- Add a new `XYZTimeToSpeechStrategy` subclass of `LocalizedTimeToSpeechStrategy`.
- Provide a `messages_xx_YY.properties` bundle for that locale.
- Expose a factory method (or a locale-based registry) to obtain the strategy instance.

### Development Tips
- Java 17 is required. Consider `var` sparingly where the type is obvious and readability improves.
- Use clean code principles, loose-coupling strategies  and keep changes side-effect free for easy reuse and testing.
- Run `mvn -q clean verify` before committing to keep SpotBugs and coverage healthy.
- Avoid Unnecessary Object Creation: Reuse objects where appropriate and be mindful of object creation in performance-critical sections.
- Ensure that resources like file streams and database connections are properly closed, ideally using try-with-resources.

### License

This project is not licensed for public use, modification, or distribution. All rights are reserved by the author.


