package nl.tudelft.jpacman.level;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

class LevelTest {

    private Level level;
    private final Board board = mock(Board.class);
    private final Ghost ghost = mock(Ghost.class);
    private final CollisionMap collisions = mock(CollisionMap.class);
    private final Square startSquare = mock(Square.class);

    @BeforeEach
    void setUp() {
        List<Ghost> ghosts = new ArrayList<>();
        ghosts.add(ghost);

        List<Square> startPositions = new ArrayList<>();
        startPositions.add(startSquare);

        level = new Level(board, ghosts, startPositions, collisions);
    }

    @Test
    void testStartStop() {
        assertThat(level.isInProgress()).isFalse();
        level.start();
        assertThat(level.isInProgress()).isTrue();
        level.stop();
        assertThat(level.isInProgress()).isFalse();
    }

    @Test
    void testRegisterPlayer() {
        Player player = mock(Player.class);
        level.registerPlayer(player);

        // Verify the player was actually put on the start square
        verify(player).occupy(startSquare);
    }

    @Test
    void testNoMoveWhileStopped() {
        Unit unit = mock(Unit.class);
        level.move(unit, Direction.NORTH);

        // If game is stopped, unit.setDirection should never be called
        verify(unit, times(0)).setDirection(any());
    }

    @Test
    void testRemainingPellets() {
        // Setup a 1x1 board
        when(board.getWidth()).thenReturn(1);
        when(board.getHeight()).thenReturn(1);

        // Setup a square with one pellet
        Square square = mock(Square.class);
        Pellet pellet = mock(Pellet.class);
        List<Unit> occupants = new ArrayList<>();
        occupants.add(pellet);

        when(board.squareAt(0, 0)).thenReturn(square);
        when(square.getOccupants()).thenReturn(occupants);

        //  Assert
        assertEquals(1, level.remainingPellets());
    }
}