package bonus.task01;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LairBuilderTest {
    @Test void originalClientChainRemainsValid() {
        Lair lair = new LairBuilder().addTrapRoom().addHenchmenQuarters()
                .addMonologueBalcony().build();
        assertEquals(1, lair.trapRooms());
        assertEquals(1, lair.henchmenQuarters());
        assertTrue(lair.monologueBalcony());
        assertFalse(lair.escapeTunnel());
    }

    @Test void sharkTankWithoutDeckIsRejected() {
        assertThrows(IllegalStateException.class, () -> new LairBuilder().addSharkTank().build());
    }

    @Test void unrelatedRoomsCannotSatisfyDeckDependency() {
        assertThrows(IllegalStateException.class, () -> new LairBuilder().addSharkTank()
                .addEscapeTunnel().addMonologueBalcony().addTrapRoom().build());
    }

    @Test void deckMayBeAddedBeforeOrAfterTank() {
        assertEquals(new LairBuilder().addSharkTank().addObservationDeck().build(),
                new LairBuilder().addObservationDeck().addSharkTank().build());
    }

    @Test void emptyLairIsValid() {
        assertEquals(new Lair(0, false, false, 0, false, false), new LairBuilder().build());
    }

    @Test void escapeTunnelIsOptionalAndIndependent() {
        assertTrue(new LairBuilder().addEscapeTunnel().build().escapeTunnel());
        assertTrue(new LairBuilder().addSharkTank().addObservationDeck()
                .addEscapeTunnel().build().escapeTunnel());
    }

    @Test void repeatedRoomStepsCountRoomsAndOptionalStepsAreIdempotent() {
        Lair lair = new LairBuilder().addTrapRoom().addTrapRoom().addHenchmenQuarters()
                .addHenchmenQuarters().addEscapeTunnel().addEscapeTunnel().build();
        assertEquals(2, lair.trapRooms());
        assertEquals(2, lair.henchmenQuarters());
        assertTrue(lair.escapeTunnel());
    }

    @Test void builtSnapshotDoesNotChangeWhenBuilderIsReused() {
        LairBuilder builder = new LairBuilder().addTrapRoom();
        Lair first = builder.build();
        Lair second = builder.addEscapeTunnel().addTrapRoom().build();
        assertEquals(1, first.trapRooms());
        assertFalse(first.escapeTunnel());
        assertEquals(2, second.trapRooms());
        assertTrue(second.escapeTunnel());
    }

    @Test void failedBuildCanBeCompletedByAddingDeck() {
        LairBuilder builder = new LairBuilder().addSharkTank();
        assertThrows(IllegalStateException.class, builder::build);
        assertTrue(builder.addObservationDeck().build().sharkTank());
    }

    @Test void everyStepReturnsSameBuilder() {
        LairBuilder builder = new LairBuilder();
        assertSame(builder, builder.addTrapRoom());
        assertSame(builder, builder.addSharkTank());
        assertSame(builder, builder.addObservationDeck());
        assertSame(builder, builder.addHenchmenQuarters());
        assertSame(builder, builder.addMonologueBalcony());
        assertSame(builder, builder.addEscapeTunnel());
    }
}
