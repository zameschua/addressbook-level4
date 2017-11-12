package seedu.address.model;

//@@author ReneeSeet

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.model.tag.MassEmailPredicate;
import seedu.address.testutil.PersonBuilder;

public class MassEmailPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        MassEmailPredicate firstPredicate = new MassEmailPredicate(firstPredicateKeywordList);
        MassEmailPredicate secondPredicate = new MassEmailPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MassEmailPredicate firstPredicateCopy = new MassEmailPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        MassEmailPredicate predicate = new MassEmailPredicate(Collections.singletonList("friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Multiple keywords
        predicate = new  MassEmailPredicate(Arrays.asList("sec2", "sec1"));
        assertTrue(predicate.test(new PersonBuilder().withTags("sec2", "sec1").build()));

        // Only one matching keyword
        predicate = new  MassEmailPredicate(Arrays.asList("sec1"));
        assertTrue(predicate.test(new PersonBuilder().withTags("sec2", "sec1").build()));

        //if keyword is 'all'
        predicate = new  MassEmailPredicate(Arrays.asList("all"));
        assertTrue(predicate.test(new PersonBuilder().withTags("sec2", "sec1").build()));
        assertTrue(predicate.test(new PersonBuilder().withTags("sec3").build()));

    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        MassEmailPredicate predicate = new  MassEmailPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("sec1").build()));

        // Non-matching keyword
        predicate = new  MassEmailPredicate(Arrays.asList("sec1"));
        assertFalse(predicate.test(new PersonBuilder().withTags("sec3").build()));

        // Mixed-case keywords
        predicate = new  MassEmailPredicate(Arrays.asList("SeC1", "Sec2"));
        assertFalse(predicate.test(new PersonBuilder().withTags("sec2", "sec1").build()));

        predicate = new  MassEmailPredicate(Arrays.asList("ALL"));
        assertFalse(predicate.test(new PersonBuilder().withTags("sec2", "sec1").build()));

        // Keywords match phone, email and address
        predicate = new  MassEmailPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
        //Keywords match phone
        predicate = new  MassEmailPredicate(Arrays.asList("12345"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345").build()));

        //Keywords match address
        predicate = new  MassEmailPredicate(Arrays.asList("Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("Main Street").build()));

        //Keywords match email
        predicate = new  MassEmailPredicate(Arrays.asList("alice", "email.com"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@email.com").build()));
    }
}
