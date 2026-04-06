package nl.tudelft.jpacman.level;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import nl.tudelft.jpacman.sprite.Sprite;
import org.junit.jupiter.api.Test;

class PelletTest {

    @Test
    void testConstructorAndGetters() {
        //Setup
        int points = 10;
        Sprite sprite = mock(Sprite.class);

        Pellet pellet = new Pellet(points, sprite);

        assertThat(pellet.getValue()).isEqualTo(points);
        assertThat(pellet.getSprite()).isEqualTo(sprite);
    }
}