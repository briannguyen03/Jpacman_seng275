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
        //Verifies that starting the game sets the state to in-progress and initializes the level and observers.
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(1);

        game.start();

        //Assert and verify that the state has changed to start
        assertTrue(game.isInProgress());
        verify(level).addObserver(game);
        verify(level).start();
    }

    @Test
    void stop() {
        //Ensures that stopping a running game updates its status to not-in-progress and halts level execution.
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(1);
        game.start();

        game.stop();

        //Checks that the game stopped
        assertFalse(game.isInProgress());
        verify(level).stop();
    }

    @Test
    void isInProgress() {
        //Checks that the game correctly reports a default state of not in progress before it is started.
        assertFalse(game.isInProgress());
    }

    @Test
    void getPlayers() {
        //Validates that the game correctly returns the list containing the expected player instance.
        assertEquals(1, game.getPlayers().size());
        assertEquals(player, game.getPlayers().get(0));
    }

    @Test
    void getLevel() {
        //Confirms that the game returns the correct level object associated with the current session.
        assertEquals(level, game.getLevel());
    }

    @Test
    void move() {
        //Verifies that triggering a player move commands the level to update the position and notifies the point calculator.
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(1);
        game.start();

        game.move(player, Direction.NORTH);

        verify(level).move(player, Direction.NORTH);
        verify(pointCalculator).pacmanMoved(player, Direction.NORTH);
    }

    @Test
    void levelWon() {
        //Checks that the game automatically stops being in-progress once the level is successfully completed.
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(1);
        game.start();

        game.levelWon();

        assertFalse(game.isInProgress());
    }

    @Test
    void levelLost() {
        //Ensures that the game stops being in-progress immediately after a player loses the level.
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(1);
        game.start();

        game.levelLost();

        assertFalse(game.isInProgress());
    }
}