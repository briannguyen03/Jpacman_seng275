package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import org.junit.jupiter.api.Test;

/**
 * A very simple (and not particularly useful)
 * test class to have a starting point where to put tests.
 *
 * @author Arie van Deursen
 */
public class DirectionTest {
    /**
     * Do we get the correct delta when moving north?
     */
    @Test
    void testNorth() {
        Direction north = Direction.valueOf("NORTH");
        assertThat(north.getDeltaY()).isEqualTo(-1);
    }

    /**
     * Do we get the correct delta when moving south?
     */
    @Test
    void testSouth() {
        Direction south = Direction.valueOf("SOUTH");
        assertThat(south.getDeltaY()).isEqualTo(1);
    }

    /**
     * Do we get the correct delta when moving west?
     */
    @Test
    void testWest() {
        Direction west = Direction.valueOf("WEST");
        assertThat(west.getDeltaX()).isEqualTo(-1);
    }

    /**
     * Do we get the correct delta when moving east?
     */
    @Test
    void testEast() {
        Direction east = Direction.valueOf("EAST");
        assertThat(east.getDeltaX()).isEqualTo(1);
    }

    /**
     * Parameterized test verifying deltaX for all four directions.
     */
    @ParameterizedTest
    @CsvSource({
            "NORTH, 0",
            "SOUTH, 0",
            "WEST, -1",
            "EAST, 1"
    })
    void testDeltaX(String directionName, int expectedDeltaX) {
        Direction direction = Direction.valueOf(directionName);
        assertThat(direction.getDeltaX()).isEqualTo(expectedDeltaX);
    }

    /**
     * Parameterized test verifying deltaY for all four directions.
     */
    @ParameterizedTest
    @CsvSource({
            "NORTH, -1",
            "SOUTH, 1",
            "WEST, 0",
            "EAST, 0"
    })
    void testDeltaY(String directionName, int expectedDeltaY) {
        Direction direction = Direction.valueOf(directionName);
        assertThat(direction.getDeltaY()).isEqualTo(expectedDeltaY);
    }

    /**
     * Horizontal directions (EAST/WEST) should have deltaY of zero.
     */
    @ParameterizedTest
    @CsvSource({"EAST", "WEST"})
    void horizontalDirectionsHaveZeroDeltaY(String directionName) {
        Direction direction = Direction.valueOf(directionName);
        assertThat(direction.getDeltaY()).isZero();
    }

    /**
     * Vertical directions (NORTH/SOUTH) should have deltaX of zero.
     */
    @ParameterizedTest
    @CsvSource({"NORTH", "SOUTH"})
    void verticalDirectionsHaveZeroDeltaX(String directionName) {
        Direction direction = Direction.valueOf(directionName);
        assertThat(direction.getDeltaX()).isZero();
    }
}
