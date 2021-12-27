package plc.homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Contains JUnit tests for {@link Regex}. Test structure for steps 1 & 2 are
 * provided, you must create this yourself for step 3.
 *
 * To run tests, either click the run icon on the left margin, which can be used
 * to run all tests or only a specific test. You should make sure your tests are
 * run through IntelliJ (File > Settings > Build, Execution, Deployment > Build
 * Tools > Gradle > Run tests using <em>IntelliJ IDEA</em>). This ensures the
 * name and inputs for the tests are displayed correctly in the run window.
 */
public class RegexTests {

    /**
     * This is a parameterized test for the {@link Regex#EMAIL} regex. The
     * {@link ParameterizedTest} annotation defines this method as a
     * parameterized test, and {@link MethodSource} tells JUnit to look for the
     * static method {@link #testEmailRegex()}.
     *
     * For personal preference, I include a test name as the first parameter
     * which describes what that test should be testing - this is visible in
     * IntelliJ when running the tests (see above note if not working).
     */
    @ParameterizedTest
    @MethodSource
    public void testEmailRegex(String test, String input, boolean success) {
        test(input, Regex.EMAIL, success);
    }

    /**
     * This is the factory method providing test cases for the parameterized
     * test above - note that it is static, takes no arguments, and has the same
     * name as the test. The {@link Arguments} object contains the arguments for
     * each test to be passed to the function above.
     */
    public static Stream<Arguments> testEmailRegex() {
        return Stream.of(
                Arguments.of("Alphanumeric", "thelegend27@gmail.com", true),
                Arguments.of("UF domain", "otherdomain@ufl.edu", true),
                Arguments.of("Missing domain dot", "missingdot@gmailcom", false),
                Arguments.of("Symbols", "symbols#$%@gmail.com", false),

                //matching cases
                Arguments.of("Camelcase", "myEmail@gmail.com", true),
                Arguments.of("Underscore", "this_is_my_email@gmail.com", true),
                Arguments.of("Hyphenation", "super-cool-name@gmail.com", true),
                Arguments.of("Alphanumeric domain", "myEmail@gyah00.com", true),
                Arguments.of("Strictly numeric", "1234567890@gmail.com", true),

                //non-matching cases
                Arguments.of("Upper/Lowercase in top-level domain", "email@gmail.Com", false),
                Arguments.of("Multiple domain dots", "iheartdots@dots.dots.com", false),
                Arguments.of("Missing @", "email.net", false),
                Arguments.of("No domain", "insert_email_address_here", false),
                Arguments.of("Invalid top-level domain (too long)", "email@ufl.education", false)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testEvenStringsRegex(String test, String input, boolean success) {
        test(input, Regex.EVEN_STRINGS, success);
    }

    public static Stream<Arguments> testEvenStringsRegex() {
        return Stream.of(
                //what has ten letters and starts with gas?
                Arguments.of("10 characters", "automobile", true),
                Arguments.of("14 characters", "i<3pancakes10!", true),
                Arguments.of("6 characters", "6chars", false),
                Arguments.of("13 characters", "i<3pancakes9!", false),

                //matches
                Arguments.of("20 characters", "automobileautomobile", true),
                Arguments.of("Strictly alphanumeric (10 characters)", "a1b2c3d4e5", true),
                Arguments.of("Strictly non-alphanumeric (12 characters)", "!@#$%^&*()_+", true),
                Arguments.of("All spaces (16 characters)", "                ", true),
                Arguments.of("20 characters", "automobileautomobile", true),

                //non-matches
                Arguments.of("Much less than 10 characters", "hey", false),
                Arguments.of("Much more than 10 characters", "omg_this_is_so_long_isnt_that_so_crazy_and_absolutely_wild", false),
                Arguments.of("11 characters (odd at front of range)", "12345678901", false),
                Arguments.of("19 characters (odd at end of range", "1234567890123456789", false),
                Arguments.of("Empty string", "", false)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testIntegerListRegex(String test, String input, boolean success) {
        test(input, Regex.INTEGER_LIST, success);
    }

    public static Stream<Arguments> testIntegerListRegex() {
        return Stream.of(
                Arguments.of("Single Element", "[1]", true),
                Arguments.of("Multiple Elements", "[1,2,3]", true),
                Arguments.of("Missing Brackets", "1,2,3", false),
                Arguments.of("Missing Commas", "[1 2 3]", false),

                //matches
                Arguments.of("Empty list", "[]", true),
                Arguments.of("Mixed spaces", "[1,2, 3,  4]", true),
                Arguments.of("Double digits", "[11,12]", true),
                Arguments.of("Triple digits", "[1111,112]", true),
                Arguments.of("Long list with different digit counts", "[0,01,111,1111]", true),

                //non-matches
                Arguments.of("Space before comma", "[1 ,2]", false),
                Arguments.of("Reversed brackets", "]1,2[", false),
                Arguments.of("Double brackets", "[[1,2]]", false),
                Arguments.of("Alphabetical characters", "[a,b]", false),
                Arguments.of("Symbols", "[!,@]", false)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testNumberRegex(String test, String input, boolean success) {
        test(input, Regex.NUMBER, success);
    }

    public static Stream<Arguments> testNumberRegex() {
        return Stream.of(
                //matches
                Arguments.of("Number (no +\\-)", "1234567", true),
                Arguments.of("Number (+)", "+1234567", true),
                Arguments.of("Number (-)", "-1234567", true),
                Arguments.of("Decimal (no +\\-)", "0.123", true),
                Arguments.of("Decimal (-)", "-1.23", true),

                //non-matches
                Arguments.of("Leading decimal", ".123", false),
                Arguments.of("Trailing decimal", "1723.", false),
                Arguments.of("Multiple decimals", "1.2.3", false),
                Arguments.of("Letters", "abc", false),
                Arguments.of("Symbols", "+", false)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testStringRegex(String test, String input, boolean success) {
        test(input, Regex.STRING, success);
    }

    public static Stream<Arguments> testStringRegex() {
        return Stream.of(
                //matches
                Arguments.of("One word", "\"hi\"", true),
                Arguments.of("Escape sequence", "\"\\t\"", true),
                Arguments.of("Empty string", "\"\"", true),
                Arguments.of("Full sentence", "\"Hi, my name is Nicole!\"", true),
                Arguments.of("String of numbers", "\"123456789\"", true),

                //non-matches
                Arguments.of("No quotation marks", "[!,@]", false),
                Arguments.of("Empty input", "", false),
                Arguments.of("Invalid escape sequence", "\"\\h\"", false),
                Arguments.of("Single escape character (just \\)", "\"\\\"", false),
                Arguments.of("Missing quotation", "hello\"", false)
        );
    }

    /**
     * Asserts that the input matches the given pattern. This method doesn't do
     * much now, but you will see this concept in future assignments.
     */
    private static void test(String input, Pattern pattern, boolean success) {
        Assertions.assertEquals(success, pattern.matcher(input).matches());
    }

}