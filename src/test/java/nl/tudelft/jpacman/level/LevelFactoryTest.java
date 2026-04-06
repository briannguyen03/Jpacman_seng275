package nl.tudelft.jpacman.level;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class LevelFactoryTest {

    private LevelFactory levelFactory;
    private final PacManSprites sprites = mock(PacManSprites.class);
    private final GhostFactory ghostFactory = mock(GhostFactory.class);
    private final PointCalculator pointCalculator = mock(PointCalculator.class);

    @BeforeEach
    void setUp() {
        levelFactory = new LevelFactory(sprites, ghostFactory, pointCalculator);
    }

    @Test
    void testCreateLevel() {
        Board board = mock(Board.class);
        List<Ghost> ghosts = new ArrayList<>();
        List<Square> startPositions = new ArrayList<>();
        startPositions.add(mock(Square.class));

        Level level = levelFactory.createLevel(board, ghosts, startPositions);

        assertThat(level).isNotNull();
        assertThat(level.getBoard()).isEqualTo(board);
    }

    // Testing the switch case for createGhost(). Calling it once (ghost index = 0) should return Blinky
    @Test
    void testCreateGhost() {
        levelFactory.createGhost();
        verify(ghostFactory).createBlinky();
    }

    @Test
    void testCreatePellet() {
        Pellet pellet = levelFactory.createPellet();

        assertThat(pellet).isNotNull();

        // The constant in LevelFactory is 10
        assertThat(pellet.getValue()).isEqualTo(10);
    }
}