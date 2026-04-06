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

public class ClydeTest {
    private GhostMapParser mapParser;
    private PlayerFactory playerFactory;

    @BeforeEach
    void setup() {
        PacManSprites sprites = new PacManSprites();
        GhostFactory ghostFactory = new GhostFactory(sprites);
        BoardFactory boardFactory = new BoardFactory(sprites);
        PointCalculator pointCalculator = mock(PointCalculator.class);
        LevelFactory levelFactory = new LevelFactory(sprites, ghostFactory, pointCalculator);
        mapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
        playerFactory = new PlayerFactory(sprites);
    }

    /**
     * When the player is CLOSE (path <= 8 steps), Clyde should flee —
     * moving in the OPPOSITE direction away from the player.
     *
     * Clyde is 3 steps east of the player — within shyness range.
     * The path to player goes WEST, so Clyde flees EAST (opposite).
     */
    @Test
    void clydeFleeWhenPlayerIsClose() {
        // 3 spaces between P and C — well within SHYNESS threshold of 8
        Level level = mapParser.parseMap(List.of("P  C   "));

        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Clyde clyde = (Clyde) Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertThat(clyde).isNotNull();

        Optional<Direction> move = clyde.nextAiMove();
        assertThat(move).isPresent();
        // Player is to the WEST, so Clyde flees EAST
        assertThat(move.get()).isEqualTo(Direction.EAST);
    }

    /**
     * When the player is FAR (path > 8 steps), Clyde chases like Blinky.
     *
     * 10 spaces between player and Clyde — beyond the shyness threshold.
     * Clyde should move WEST toward the player.
     */
    @Test
    void clydeChaseWhenPlayerIsFar() {
        // 10 spaces between P and C — beyond SHYNESS threshold of 8
        Level level = mapParser.parseMap(List.of("P          C"));

        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Clyde clyde = (Clyde) Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertThat(clyde).isNotNull();

        Optional<Direction> move = clyde.nextAiMove();
        assertThat(move).isPresent();
        // Player is far to the WEST — Clyde chases, moves WEST
        assertThat(move.get()).isEqualTo(Direction.WEST);
    }

    /**
     * When there is no player on the board, Clyde should return empty.
     */
    @Test
    void clydeReturnsEmptyWithNoPlayer() {
        Level level = mapParser.parseMap(List.of("C   "));

        Clyde clyde = (Clyde) Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertThat(clyde).isNotNull();

        Optional<Direction> move = clyde.nextAiMove();
        assertThat(move).isEmpty();
    }

    /**
     * When Clyde is walled off from the player, he cannot move
     * and should return Optional.empty().
     */
    @Test
    void clydeReturnsEmptyWhenNoPath() {
        Level level = mapParser.parseMap(List.of("C#P"));

        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Clyde clyde = (Clyde) Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertThat(clyde).isNotNull();

        Optional<Direction> move = clyde.nextAiMove();
        assertThat(move).isEmpty();
    }

    /**
     * Clyde at exactly 8 steps from player should still flee (boundary case).
     * The code uses path.size() <= SHYNESS, so 8 steps means flee.
     */
    @Test
    void clydeFleeAtExactlyShynessThreshold() {
        // Exactly 8 spaces between P and C (path length = 8)
        Level level = mapParser.parseMap(List.of("P       C"));

        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Clyde clyde = (Clyde) Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertThat(clyde).isNotNull();

        Optional<Direction> move = clyde.nextAiMove();
        assertThat(move).isPresent();
        // path.size() == 8 which is <= SHYNESS(8), so Clyde flees EAST
        assertThat(move.get()).isEqualTo(Direction.EAST);
    }
}