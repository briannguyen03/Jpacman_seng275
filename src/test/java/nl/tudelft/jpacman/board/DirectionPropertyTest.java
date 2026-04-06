package nl.tudelft.jpacman.board;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Property-based tests for Direction using Jqwik.
 *
 * These tests verify properties that must hold for ALL directions,
 * not just specific hand-picked cases.
 */
public class DirectionPropertyTest {

    /**
     * For any Direction, deltaX must always be -1, 0, or 1.
     * A single step should never jump more than one cell.
     */
    @Property
    void deltaXIsAlwaysWithinBounds(@ForAll Direction direction) {
        assertThat(direction.getDeltaX()).isBetween(-1, 1);
    }

    /**
     * For any Direction, deltaY must always be -1, 0, or 1.
     * A single step should never jump more than one cell.
     */
    @Property
    void deltaYIsAlwaysWithinBounds(@ForAll Direction direction) {
        assertThat(direction.getDeltaY()).isBetween(-1, 1);
    }

    /**
     * No direction should have both deltaX and deltaY non-zero.
     * Movement is always purely horizontal OR purely vertical — never diagonal.
     */
    @Property
    void movementIsNeverDiagonal(@ForAll Direction direction) {
        boolean purleyHorizontal = direction.getDeltaX() != 0 && direction.getDeltaY() == 0;
        boolean purelyVertical = direction.getDeltaX() == 0 && direction.getDeltaY() != 0;
        assertThat(purleyHorizontal || purelyVertical).isTrue();
    }

    /**
     * No direction should have both deltaX and deltaY equal to zero.
     * Every direction must actually move somewhere.
     */
    @Property
    void noDirectionIsStationary(@ForAll Direction direction) {
        boolean isStationary = direction.getDeltaX() == 0 && direction.getDeltaY() == 0;
        assertThat(isStationary).isFalse();
    }

    /**
     * The sum of absolute deltas for any direction should always equal 1.
     * This confirms exactly one axis moves by exactly one step.
     */
    @Property
    void absoluteDeltaSumIsAlwaysOne(@ForAll Direction direction) {
        int sum = Math.abs(direction.getDeltaX()) + Math.abs(direction.getDeltaY());
        assertThat(sum).isEqualTo(1);
    }
}