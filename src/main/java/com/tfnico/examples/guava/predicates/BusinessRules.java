package com.tfnico.examples.guava.predicates;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;

import java.util.regex.Pattern;

import static com.google.common.base.Predicates.*;
import static com.google.common.base.Predicates.and;
import static com.google.common.base.Predicates.or;
import static com.tfnico.examples.guava.predicates.BusinessRules.ExactStringLengthPredicate.exactLength;
import static com.tfnico.examples.guava.predicates.BusinessRules.LongerStringPredicate.longerThan;
import static com.tfnico.examples.guava.predicates.BusinessRules.PatternMatchingPredicate.matching;
import static com.tfnico.examples.guava.predicates.BusinessRules.ShorterStringPredicate.shorterThan;

public class BusinessRules {

    public static boolean isCodeAllowed(String code) {
        Predicate<String> predicate = and(
                not(NULL_OR_EMPTY),
                ONLY_LETTERS_CORRECT_LENGTH,
                LETTERS_AND_NUMBERS_CORRECT_LENGTH,
                not(matching(NUMBERS_ONLY)),
                PLUS_SIGN_PARTICULARITY);
        return predicate.apply(code);
    }

    private static final Pattern NUMBERS_ONLY = Pattern.compile("^\\d+$");

    private static final Predicate<String> STARTS_WITH_999 = new Predicate<String>() {
        public boolean apply(String code) {
            return code.startsWith("999");
        }
    };

    private static final Predicate<String> CONTAINS_PLUS = new Predicate<String>() {
        public boolean apply(String code) {
            return code.contains("+");
        }
    };

    private static final Predicate<String> PLUS_SIGN_PARTICULARITY = 
            or(not(CONTAINS_PLUS),
                and(STARTS_WITH_999,
                shorterThan(11))
    );

    //Sorry, I suck at regexp.. This one is supposed to check that there is at least one letter and one number.
    private static final Pattern LETTERS_AND_NUMBERS = Pattern.compile("^[0-9A-Z]*[A-Z]+[0-9]+[0-9A-Z]*$");
    private static final Predicate<String> ARE_LETTERS_AND_NUMBERS = matching(LETTERS_AND_NUMBERS);
    private static final Predicate<String> LETTERS_AND_NUMBERS_CORRECT_LENGTH = or(
            not(ARE_LETTERS_AND_NUMBERS),
            (or (exactLength(10),
                    exactLength(15))));

    private static final Pattern LETTERS_ONLY = Pattern.compile("^[A-Z]*$");
    private static final Predicate<String> ARE_ONLY_LETTERS = matching(LETTERS_ONLY);


    private static final Predicate<String> ONLY_LETTERS_CORRECT_LENGTH = or(
            not(ARE_ONLY_LETTERS),
            (and (longerThan(10),
                  shorterThan(20))));


    private static final Predicate<String> NULL_OR_EMPTY = new Predicate<String>() {
        public boolean apply(String input) {
            return Strings.isNullOrEmpty(input);
        }
    };



    static class LongerStringPredicate implements Predicate<String> {
        private int length;

        private LongerStringPredicate(int length) {
            this.length = length;
        }

        public boolean apply(String input) {
            return input.length() > length;
        }

        public static Predicate<String> longerThan(int length) {
            return new LongerStringPredicate(length);
        }
    }

    static class ShorterStringPredicate implements Predicate<String> {
        private int length;

        private ShorterStringPredicate(int length) {
            this.length = length;
        }

        public boolean apply(String input) {
            return input.length() < length;
        }

        public static Predicate<String> shorterThan(int length) {
            return new ShorterStringPredicate(length);
        }
    }

    static class ExactStringLengthPredicate implements Predicate<String> {
        private int length;

        private ExactStringLengthPredicate(int length) {
            this.length = length;
        }

        public boolean apply(String input) {
            return input.length() == length;
        }

        public static Predicate<String> exactLength(int length) {
            return new ExactStringLengthPredicate(length);
        }
    }

    static class PatternMatchingPredicate implements Predicate<String> {
        private Pattern pattern;

        private PatternMatchingPredicate(Pattern pattern) {
            this.pattern = pattern;
        }

        public static Predicate<String> matching(Pattern pattern) {
            return new PatternMatchingPredicate(pattern);
        }

        public boolean apply(String input) {
            return pattern.matcher(input).matches();
        }
    }
}
