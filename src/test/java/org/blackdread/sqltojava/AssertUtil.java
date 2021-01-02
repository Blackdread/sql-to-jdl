package org.blackdread.sqltojava;

import org.junit.jupiter.api.Assertions;

import java.util.Collection;
import java.util.Iterator;

/**
 * <p>Created on 2020/12/30.</p>
 *
 * @author Yoann CAPLAIN
 */
public final class AssertUtil {


    public static void assertFileSame(final Collection<String> expected, final Collection<String> actual) {
        if (expected.size() != actual.size()) {
            Assertions.fail(String.format("Expected (%s) and actual (%s) size are different", expected.size(), actual.size()));
        }
        if (expected.isEmpty()) {
            return;
        }

        final Iterator<String> expectedIterator = expected.iterator();
        final Iterator<String> actualIterator = actual.iterator();
        for (int i = 0; i < expected.size(); i++) {
            final String expectedNext = expectedIterator.next();
            final String actualNext = actualIterator.next();
            if (!expected.equals(actual)) {
                Assertions.fail(String.format("Line %d (%s) is different from expected (%s)", i, actualNext, expectedNext));
            }
        }
    }

}
