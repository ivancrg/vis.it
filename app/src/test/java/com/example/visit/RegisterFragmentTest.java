package com.example.visit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterFragmentTest {
    // Our custom password regex pattern used for validation
    // No whitespaces, minimum eight characters, at least one uppercase letter, one lowercase letter and one number
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");
    private static final Pattern EMAIL_ADDRESS = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    @BeforeAll
    public static void init() {
        System.out.println("RegisterFragmentTest starting.");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "register_validation_data.csv", numLinesToSkip = 1)
    void informationValidator(String firstName, String lastName, String email, String username, String password, String expectedResult) {

        /*
        CSV FILE DESCRIPTION
            Lines [2, 7] - manually added test files
            Lines [8, 507] - generated test files via https://codebeautify.org/generate-random-data-from-regexp with expected true result
                           - some cases were intentionally meddled with to generate some false results (for example deleting some values, deleting all numbers from passwords, changing expected results etc.)
            Lines [508, 707] - checks invalid password entries generated via https://codebeautify.org/generate-random-data-from-regexp with expected false results
                             - some entries have other fields intentionally invalidated too
            Lines [708, 807] - entries with faulty e-mails (no @ sign) with expected false results
                             - some entries have other fields intentionally invalidated too
            Lines [808, 907] - entries with faulty e-mails (no dot after @ sign) with expected false results
                             - some entries have other fields intentionally invalidated too
            Lines [908, 1007] - entries with faulty e-mails (no content before @ sign) with expected false results
                             - some entries have other fields intentionally invalidated too

        FIXED BY TESTING
            There should be tests for null values. Bug was fixed when null values were encountered in .csv file.
         */

        boolean valid = true;

        if (firstName == null || firstName.isEmpty()) {
            valid = false;
            System.out.println("FIRST NAME NOT VALID");
        }

        if (lastName == null || lastName.isEmpty()) {
            valid = false;
            System.out.println("LAST NAME NOT VALID");
        }

        if (email == null || email.isEmpty() || !EMAIL_ADDRESS.matcher(email).matches()) {
            valid = false;
            System.out.println("EMAIL NOT VALID");
        }

        if (username == null || username.isEmpty()) {
            valid = false;
            System.out.println("USERNAME NOT VALID");
        }

        if (password == null || password.isEmpty() || !PASSWORD_PATTERN.matcher(password).matches()) {
            valid = false;
            System.out.println("PASSWORD NOT VALID");
        }

        System.out.println(firstName + ", " + lastName + ", " + email + ", " + username + ", " + password + " ==> " + valid);

        assertEquals(Boolean.valueOf(expectedResult), valid);
    }
}