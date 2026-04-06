package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.sprite.AnimatedSprite;
import nl.tudelft.jpacman.sprite.Sprite;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;


class PlayerTest {

    private Player ptest;
    private Map<Direction, Sprite> spriteMap;
    private AnimatedSprite deathsprite;

    @BeforeEach
    void setUp() {
        //init the params of player
        spriteMap = new HashMap<>();
        deathsprite = mock(AnimatedSprite.class);

        //init the sprite map
        spriteMap.put(Direction.NORTH, mock(Sprite.class));

        //create player
        ptest = new Player(spriteMap, deathsprite);

    }

    @Test
    void isAlive() {
        assertTrue(ptest.isAlive());
    }


    @Test
    void getScore() {
        assertEquals(0, ptest.getScore());
    }

    @Test
    void addPoints() {
        ptest.addPoints(3);
        assertEquals(3, ptest.getScore());
    }

    @Test
    void playerDeath() {
        ptest.setAlive(false);
        assertFalse(ptest.isAlive());
    }
}