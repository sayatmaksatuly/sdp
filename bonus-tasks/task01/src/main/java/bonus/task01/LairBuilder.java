package bonus.task01;

public final class LairBuilder {
    private int trapRooms;
    private boolean sharkTank;
    private boolean observationDeck;
    private int henchmenQuarters;
    private boolean monologueBalcony;
    private boolean escapeTunnel;

    public LairBuilder addTrapRoom() {
        trapRooms = Math.incrementExact(trapRooms);
        return this;
    }

    public LairBuilder addSharkTank() {
        sharkTank = true;
        return this;
    }

    public LairBuilder addObservationDeck() {
        observationDeck = true;
        return this;
    }

    public LairBuilder addHenchmenQuarters() {
        henchmenQuarters = Math.incrementExact(henchmenQuarters);
        return this;
    }

    public LairBuilder addMonologueBalcony() {
        monologueBalcony = true;
        return this;
    }

    public LairBuilder addEscapeTunnel() {
        escapeTunnel = true;
        return this;
    }

    public Lair build() {
        if (sharkTank && !observationDeck) {
            throw new IllegalStateException("A shark tank requires an observation deck");
        }
        return new Lair(trapRooms, sharkTank, observationDeck, henchmenQuarters,
                monologueBalcony, escapeTunnel);
    }
}
