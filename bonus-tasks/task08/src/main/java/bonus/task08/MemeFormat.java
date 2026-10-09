package bonus.task08;

import java.util.List;

public interface MemeFormat {
    String name();
    MemeLayout layout(List<String> captions);
}
