package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameTest {

    private Game game;
    private final Level level = mock(Level.class);
    private final Player player = mock(Player.class);
    private final PointCalculator pointCalculator = mock(PointCalculator.class);

    @BeforeEach
    void setUp() {
        game = new Game(pointCalculator) {
            @Override
            public List<Player> getPlayers() {
                return List.of(player);
            }

            @Override
            public Level getLevel() {
                return level;
            }
        };
    }

    @Test
    void start() {
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(1);

        game.start();

        assertTrue(game.isInProgress());
        verify(level).addObserver(game);
        verify(level).start();
    }

    @Test
    void stop() {
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(1);
        game.start();

        game.stop();

        assertFalse(game.isInProgress());
        verify(level).stop();
    }

    @Test
    void isInProgress() {
        assertFalse(game.isInProgress());
    }

    @Test
    void getPlayers() {
        assertEquals(1, game.getPlayers().size());
        assertEquals(player, game.getPlayers().get(0));
    }

    @Test
    void getLevel() {
        assertEquals(level, game.getLevel());
    }

    @Test
    void move() {
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(1);
        game.start();

        game.move(player, Direction.NORTH);

        verify(level).move(player, Direction.NORTH);
        verify(pointCalculator).pacmanMoved(player, Direction.NORTH);
    }

    @Test
    void levelWon() {
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(1);
        game.start();

        game.levelWon();

        assertFalse(game.isInProgress());
    }

    @Test
    void levelLost() {
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(1);
        game.start();

        game.levelLost();

        assertFalse(game.isInProgress());
    }
}