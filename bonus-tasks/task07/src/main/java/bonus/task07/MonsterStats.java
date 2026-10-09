package bonus.task07;

public record MonsterStats(int health, int fire, int defense) {
    public MonsterStats {
        if (health < 1 || fire < 0 || defense < 0) {
            throw new IllegalArgumentException("Invalid monster stats");
        }
    }
}
