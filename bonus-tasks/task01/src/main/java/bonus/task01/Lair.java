package bonus.task01;

/** Immutable snapshot of the rooms selected by a builder. */
public record Lair(int trapRooms, boolean sharkTank, boolean observationDeck,
                   int henchmenQuarters, boolean monologueBalcony, boolean escapeTunnel) {
}
