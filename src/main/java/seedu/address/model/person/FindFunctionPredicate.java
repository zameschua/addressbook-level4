package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;


/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class FindFunctionPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public FindFunctionPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        boolean name = keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
        boolean address = keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().toString(), keyword));
        boolean tag = tagSearch(person);

        if (name || address || tag) {
            return true;
        } else {
            return false;
        }
    }

// tests if person has tags matching keywords
    public boolean tagSearch(ReadOnlyPerson person) {
        Set<Tag> tags = person.getTags();
        for (Tag s : tags) {
            for (String key : keywords) {
                if (key.equals(s.tagName)) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindFunctionPredicate // instanceof handles nulls
                && this.keywords.equals(((FindFunctionPredicate) other).keywords)); // state check
    }

}

