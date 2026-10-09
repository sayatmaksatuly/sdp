package bonus.task03;

import java.util.List;
import java.util.Optional;

public record DatingProfile(String name, String bio, List<String> hobbies,
                            Optional<String> redFlag, boolean verified) {
    public DatingProfile {
        hobbies = List.copyOf(hobbies);
    }
}
