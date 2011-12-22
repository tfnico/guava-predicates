package com.tfnico.examples.guava.predicates;

import org.junit.Test;

import static com.tfnico.examples.guava.predicates.BusinessRules.isCodeAllowed;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: tfnico
 * Date: 12/22/11
 * Time: 10:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class BusinessRulesTest {

    @Test
    public void cannot_be_blank_or_null(){
        assertFalse(isCodeAllowed(null));
        assertFalse(isCodeAllowed(""));
    }

    @Test
    public void only_letters_length_must_be_between_10_and_20() {
        assertFalse(isCodeAllowed("ABCDEFGHIJ")); // 10 chars
        assertTrue(isCodeAllowed("ABCDEFGHIJK"));// 11
        assertTrue(isCodeAllowed("ABCDEFGHIJ" + "ABCDEFGHI")); // 19 chars
        assertFalse(isCodeAllowed("ABCDEFGHIJ" + "ABCDEFGHIJ")); // 20 chars
    }

    @Test
    public void mixed_length_must_be_either_10_or_15() {
        assertTrue(isCodeAllowed("ABCDEF1234")); // 10
        assertTrue(isCodeAllowed("ABCDEF12345ABCD")); // 15
        assertFalse(isCodeAllowed("ABCDEF12345A")); // 12
    }

    @Test
    public void numbers_only_are_not_allowed() {
        assertFalse(isCodeAllowed("123"));
    }

    @Test
    public void plus_sign_allowed_if_starts_with_999_and_max_length_is_10() throws Exception {
        assertTrue(isCodeAllowed("999+"));
        assertTrue(isCodeAllowed("99912C45+6")); // 10 chars
        assertFalse(isCodeAllowed("99912C45+67")); // 11 chars
        assertFalse(isCodeAllowed("88812C45+6")); // 10 chars, wrong prefix
    }
}
