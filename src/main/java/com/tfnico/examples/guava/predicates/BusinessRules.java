package com.tfnico.examples.guava.predicates;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;

import java.util.regex.Pattern;

import static com.google.common.base.Predicates.*;

public class BusinessRules {

    public static boolean isCodeAllowed(String code) {
        return isNotNullOrEmpty(code) &&
               inCaseOfOnlyLettersCorrectLength(code) &&
               inCaseOfMixedCorrectLength(code) &&
               numbersOnlyNotAllowed(code) &&
               plusSignOnlyParticularity(code);
    }

    private static boolean plusSignOnlyParticularity(String code) {
        if(code.startsWith("999")){
            return code.length() <= 10;
        } else return !(code.contains("+"));
    }

    private static final Pattern NUMBERS_ONLY = Pattern.compile("^\\d+$");
    private static boolean numbersOnlyNotAllowed(String code) {
        return !NUMBERS_ONLY.matcher(code).matches();
    }

    private static boolean inCaseOfMixedCorrectLength(String code) {
        if(mixedLettersAndNumbers(code)){
           return code.length() == 10 || code.length() == 15;
        } else return true;
    }

    //Sorry, I suck at regexp.. This one is supposed to check that there is at least one letter and one number.
    private static final Pattern LETTERS_AND_NUMBERS = Pattern.compile("^[0-9A-Z]*[A-Z]+[0-9]+[0-9A-Z]*$");
    private static boolean mixedLettersAndNumbers(String code) {
        return LETTERS_AND_NUMBERS.matcher(code).matches();
    }

    private static boolean inCaseOfOnlyLettersCorrectLength(String code) {
        if(lettersOnly(code)){
            return 10 < code.length() && code.length() < 20;
        } else return true;
    }

    private static final Pattern LETTERS_ONLY = Pattern.compile("^[A-Z]*$");
    private static boolean lettersOnly(String code) {
        return LETTERS_ONLY.matcher(code).matches();
    }

    private static boolean isNotNullOrEmpty(String code) {
        return !Strings.isNullOrEmpty(code);
    }
}
