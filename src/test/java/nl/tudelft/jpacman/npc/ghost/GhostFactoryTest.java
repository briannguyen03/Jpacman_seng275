package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GhostFactoryTest {
    private GhostFactory ghostFactory;

    @BeforeEach
    void setup() {
        PacManSprites sprites = new PacManSprites();
        ghostFactory = new GhostFactory(sprites);
    }

    @Test
    void createBlinkyReturnsNonNull() {
        assertThat(ghostFactory.createBlinky()).isNotNull();
    }

    @Test
    void createBlinkyReturnsCorrectType() {
        assertThat(ghostFactory.createBlinky()).isInstanceOf(Blinky.class);
    }

    @Test
    void createPinkyReturnsNonNull() {
        assertThat(ghostFactory.createPinky()).isNotNull();
    }

    @Test
    void createPinkyReturnsCorrectType() {
        assertThat(ghostFactory.createPinky()).isInstanceOf(Pinky.class);
    }

    @Test
    void createInkyReturnsNonNull() {
        assertThat(ghostFactory.createInky()).isNotNull();
    }

    @Test
    void createInkyReturnsCorrectType() {
        assertThat(ghostFactory.createInky()).isInstanceOf(Inky.class);
    }

    @Test
    void createClydeReturnsNonNull() {
        assertThat(ghostFactory.createClyde()).isNotNull();
    }

    @Test
    void createClydeReturnsCorrectType() {
        assertThat(ghostFactory.createClyde()).isInstanceOf(Clyde.class);
    }

    @Test
    void eachCallCreatesFreshBlinkyInstance() {
        assertThat(ghostFactory.createBlinky()).isNotSameAs(ghostFactory.createBlinky());
    }

    @Test
    void eachCallCreatesFreshClydeInstance() {
        assertThat(ghostFactory.createClyde()).isNotSameAs(ghostFactory.createClyde());
    }
}