package bonus.task03;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public final class DatingProfileBuilder {
    private String name;
    private String bio;
    private final Set<String> hobbies = new TreeSet<>();
    private String redFlag;
    private boolean verified;

    public DatingProfileBuilder name(String value) {
        name = requireText(value, "Name");
        return this;
    }

    public DatingProfileBuilder bio(String value) {
        bio = requireText(value, "Bio");
        return this;
    }

    public DatingProfileBuilder addHobby(String value) {
        hobbies.add(requireText(value, "Hobby"));
        return this;
    }

    public DatingProfileBuilder redFlag(String value) {
        redFlag = requireText(value, "Red flag");
        return this;
    }

    /** Optional local verification marker; existing profiles remain unverified by default. */
    public DatingProfileBuilder verify() {
        verified = true;
        return this;
    }

    public DatingProfile build() {
        if (name == null || bio == null) {
            throw new IllegalStateException("A profile requires a name and bio");
        }
        if (hobbies.isEmpty()) {
            throw new IllegalStateException("A profile requires at least one hobby");
        }
        return new DatingProfile(name, bio, new ArrayList<>(hobbies),
                Optional.ofNullable(redFlag), verified);
    }

    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return value.strip();
    }
}
