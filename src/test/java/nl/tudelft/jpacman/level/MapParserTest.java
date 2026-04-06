package nl.tudelft.jpacman.level;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;

class MapParserTest {

    private MapParser parser;
    private final LevelFactory levelFactory = mock(LevelFactory.class);
    private final BoardFactory boardFactory = mock(BoardFactory.class);

    @BeforeEach
    void setUp() {
        parser = new MapParser(levelFactory, boardFactory);
    }

    /**
     * Specification Testing
     * Tests that each valid character in the spec triggers the correct factory call.
     */
    @ParameterizedTest
    @CsvSource({
            " ' ', createGround ",
            " '#', createWall ",
            " 'P', createGround "
    })
    void testCharacterSpec(char input, String expectedMethod) {
        char[][] map = {{input}};

        // Stubbing to avoid NullPointer
        when(boardFactory.createGround()).thenReturn(mock(Square.class));
        when(boardFactory.createWall()).thenReturn(mock(Square.class));

        parser.parseMap(map);

        if (expectedMethod.equals("createGround")) {
            verify(boardFactory).createGround();
        } else {
            verify(boardFactory).createWall();
        }
    }

    /**
     * Boundary/Coverage Testing
     * Checks checkMapFormat to ensure it returns a PacmanConfigurationException.
     */
    @Test
    void testUnequalRowsThrowsException() {
        List<String> invalidMap = Arrays.asList(
                "###",
                "##"  // invalid width
        );

        assertThatThrownBy(() -> parser.parseMap(invalidMap))
                .isInstanceOf(PacmanConfigurationException.class)
                .hasMessageContaining("lines are not of equal width");
    }
}