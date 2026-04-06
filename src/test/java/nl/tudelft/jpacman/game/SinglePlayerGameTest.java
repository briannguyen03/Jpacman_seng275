package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SinglePlayerGameTest {

    private SinglePlayerGame spg;
    private Player player = mock(Player.class);
    private Level level = mock(Level.class);
    private final PointCalculator pointCalculator = mock(PointCalculator.class);


    @BeforeEach
    void setUp() {
        spg = new SinglePlayerGame(player, level, pointCalculator);
    }

    @Test
    void getPlayers() {
        assertThat(spg.getPlayers()).containsExactly(player);
    }

    @Test
    void getLevel() {
        assertEquals(level, spg.getLevel());
    }
}