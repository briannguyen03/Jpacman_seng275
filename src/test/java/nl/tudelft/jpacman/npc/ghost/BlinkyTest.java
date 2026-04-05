package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class BlinkyTest {
    private GhostMapParser mapParser;
    private PlayerFactory playerFactory;

    @BeforeEach
    void setup() {
        PacManSprites sprites = new PacManSprites();
        GhostFactory ghostFactory = new GhostFactory(sprites);
        BoardFactory boardFactory = new BoardFactory(sprites);
        // Mock PointCalculator — scoring is irrelevant for ghost AI tests
        PointCalculator pointCalculator = mock(PointCalculator.class);
        LevelFactory levelFactory = new LevelFactory(sprites, ghostFactory, pointCalculator);
        mapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
        playerFactory = new PlayerFactory(sprites);
    }

    /**
     * When Blinky and the player are in a corridor with a clear path,
     * Blinky should return a direction toward the player.
     * Blinky is to the left of the player — he should move EAST.
     */
    @Test
    void blinkyMovesTowardPlayer() {
        Level level = mapParser.parseMap(List.of("B P"));

        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Blinky blinky = (Blinky) Navigation.findUnitInBoard(Blinky.class, level.getBoard());
        assertThat(blinky).isNotNull();

        Optional<Direction> move = blinky.nextAiMove();
        assertThat(move).isPresent();
        assertThat(move.get()).isEqualTo(Direction.EAST);
    }

    /**
     * When there is no player on the board, Blinky cannot find a target
     * and should return Optional.empty().
     */
    @Test
    void blinkyReturnsEmptyWithNoPlayer() {
        Level level = mapParser.parseMap(List.of("B  "));

        Blinky blinky = (Blinky) Navigation.findUnitInBoard(Blinky.class, level.getBoard());
        assertThat(blinky).isNotNull();

        Optional<Direction> move = blinky.nextAiMove();
        assertThat(move).isEmpty();
    }

    /**
     * When a wall separates Blinky from the player,
     * no path exists and nextAiMove() should return Optional.empty().
     */
    @Test
    void blinkyReturnsEmptyWhenNoPathToPlayer() {
        Level level = mapParser.parseMap(List.of("B#P"));

        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Blinky blinky = (Blinky) Navigation.findUnitInBoard(Blinky.class, level.getBoard());
        assertThat(blinky).isNotNull();

        Optional<Direction> move = blinky.nextAiMove();
        assertThat(move).isEmpty();
    }

    /**
     * Blinky should move NORTH when the player is directly above him.
     */
    @Test
    void blinkyMovesNorthWhenPlayerIsAbove() {
        Level level = mapParser.parseMap(List.of(
                "P",
                " ",
                "B"
        ));

        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Blinky blinky = (Blinky) Navigation.findUnitInBoard(Blinky.class, level.getBoard());
        assertThat(blinky).isNotNull();

        Optional<Direction> move = blinky.nextAiMove();
        assertThat(move).isPresent();
        assertThat(move.get()).isEqualTo(Direction.NORTH);
    }
}