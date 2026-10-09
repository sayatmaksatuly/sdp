package bonus.task03;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DatingProfileBuilderTest {
    private DatingProfileBuilder validBuilder() {
        return new DatingProfileBuilder().name("Alex").bio("Likes tea").addHobby("Baking");
    }

    @Test void atLeastOneHobbyIsRequired() {
        assertThrows(IllegalStateException.class,
                () -> new DatingProfileBuilder().name("Alex").bio("Likes tea").build());
    }

    @Test void exactlyOneHobbyIsValidAndRedFlagIsOptional() {
        DatingProfile profile = validBuilder().build();
        assertEquals(List.of("Baking"), profile.hobbies());
        assertTrue(profile.redFlag().isEmpty());
        assertFalse(profile.verified());
    }

    @Test void allFieldsAreIndependentOfConstructionOrder() {
        DatingProfile first = new DatingProfileBuilder().name("Alex").bio("Likes tea")
                .addHobby("Chess").addHobby("Baking").redFlag("Too many mugs").build();
        DatingProfile second = new DatingProfileBuilder().redFlag("Too many mugs")
                .addHobby("Baking").bio("Likes tea").addHobby("Chess").name("Alex").build();
        assertEquals(first, second);
    }

    @Test void duplicateHobbiesAreDeduplicated() {
        assertEquals(List.of("Baking"), validBuilder().addHobby(" Baking ").build().hobbies());
    }

    @Test void missingNameIsRejected() {
        assertThrows(IllegalStateException.class,
                () -> new DatingProfileBuilder().bio("Likes tea").addHobby("Baking").build());
    }

    @Test void missingBioIsRejected() {
        assertThrows(IllegalStateException.class,
                () -> new DatingProfileBuilder().name("Alex").addHobby("Baking").build());
    }

    @Test void blankAndNullFieldsAreRejected() {
        DatingProfileBuilder builder = validBuilder();
        assertThrows(IllegalArgumentException.class, () -> builder.name(" "));
        assertThrows(IllegalArgumentException.class, () -> builder.name(null));
        assertThrows(IllegalArgumentException.class, () -> builder.bio("\t"));
        assertThrows(IllegalArgumentException.class, () -> builder.bio(null));
        assertThrows(IllegalArgumentException.class, () -> builder.addHobby(""));
        assertThrows(IllegalArgumentException.class, () -> builder.addHobby(null));
        assertThrows(IllegalArgumentException.class, () -> builder.redFlag(" "));
        assertThrows(IllegalArgumentException.class, () -> builder.redFlag(null));
        assertEquals("Alex", builder.build().name());
    }

    @Test void inputIsTrimmed() {
        DatingProfile profile = new DatingProfileBuilder().name(" Alex ").bio(" Tea ")
                .addHobby(" Chess ").redFlag(" Mugs ").build();
        assertEquals("Alex", profile.name());
        assertEquals("Tea", profile.bio());
        assertEquals(List.of("Chess"), profile.hobbies());
        assertEquals("Mugs", profile.redFlag().orElseThrow());
    }

    @Test void optionalVerificationPreservesExistingClientFields() {
        DatingProfile original = validBuilder().build();
        DatingProfile verified = validBuilder().verify().build();
        assertFalse(original.verified());
        assertTrue(verified.verified());
        assertEquals(new DatingProfile(original.name(), original.bio(), original.hobbies(),
                original.redFlag(), true), verified);
    }

    @Test void verificationCanBeAddedInEitherOrder() {
        assertEquals(validBuilder().verify().build(), new DatingProfileBuilder().verify()
                .addHobby("Baking").bio("Likes tea").name("Alex").build());
    }

    @Test void verificationCannotBypassHobbyRule() {
        assertThrows(IllegalStateException.class, () -> new DatingProfileBuilder()
                .name("Alex").bio("Likes tea").verify().build());
    }

    @Test void builtHobbiesAreImmutableSnapshots() {
        DatingProfileBuilder builder = validBuilder();
        DatingProfile first = builder.build();
        builder.addHobby("Chess");
        assertEquals(List.of("Baking"), first.hobbies());
        assertThrows(UnsupportedOperationException.class, () -> first.hobbies().add("Tea"));
        assertEquals(2, builder.build().hobbies().size());
    }

    @Test void failedBuildCanBeCompletedWithHobby() {
        DatingProfileBuilder builder = new DatingProfileBuilder().name("Alex").bio("Likes tea");
        assertThrows(IllegalStateException.class, builder::build);
        assertEquals(1, builder.addHobby("Baking").build().hobbies().size());
    }

    @Test void fluentStepsReturnSameBuilder() {
        DatingProfileBuilder builder = new DatingProfileBuilder();
        assertSame(builder, builder.name("Alex"));
        assertSame(builder, builder.bio("Tea"));
        assertSame(builder, builder.addHobby("Baking"));
        assertSame(builder, builder.redFlag("Mugs"));
        assertSame(builder, builder.verify());
    }
}
