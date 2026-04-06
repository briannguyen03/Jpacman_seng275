package nl.tudelft.jpacman.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameFactoryTest {

    private GameFactory gameFactory;
    private final PlayerFactory playerFactory = mock(PlayerFactory.class);
    private final Player player = mock(Player.class);
    private final Level level = mock(Level.class);
    private final PointCalculator pointCalculator = mock(PointCalculator.class);

    @BeforeEach
    void setUp() {
        gameFactory = new GameFactory(playerFactory);

        // Ensure that whenever the factory is asked for a player, it returns our mock player
        when(playerFactory.createPacMan()).thenReturn(player);
    }

    @Test
    void testCreateSinglePlayerGame() {
        Game game = gameFactory.createSinglePlayerGame(level, pointCalculator);

        assertThat(game).isInstanceOf(SinglePlayerGame.class);
        assertThat(game.getPlayers()).containsExactly(player);
        assertThat(game.getLevel()).isEqualTo(level);
        verify(playerFactory, times(1)).createPacMan();
    }

    @Test
    void testGetPlayerFactory() {
        // Simple getter test to ensure the factory passed in the constructor is returned
        assertThat(gameFactory.getPlayerFactory()).isEqualTo(playerFactory);
    }
}