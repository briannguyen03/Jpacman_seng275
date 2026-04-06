package nl.tudelft.jpacman.ui;

import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScorePanelTest {

    private ScorePanel scorePanel;
    private Player player = mock(Player.class);

    @BeforeEach
    void setUp() {
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        scorePanel = new ScorePanel(playerList);
    }


    @Test
    void initScore() {
        JLabel scoreLabel = (JLabel) scorePanel.getComponent(1);
        assertThat(scoreLabel.getText()).isEqualTo("0");
    }

    @Test
    void refresh() {
        when(player.isAlive()).thenReturn(true);
        when(player.getScore()).thenReturn(100);

        scorePanel.refresh();

        JLabel scoreLabel = (JLabel) scorePanel.getComponent(1);
        assertThat(scoreLabel.getText()).contains("100");
        assertThat(scoreLabel.getText()).doesNotContain("You died");
    }

    @Test
    void testRefreshDead() {
        when(player.isAlive()).thenReturn(false);
        when(player.getScore()).thenReturn(50);

        scorePanel.refresh();

        JLabel scoreLabel = (JLabel) scorePanel.getComponent(1);
        assertThat(scoreLabel.getText()).contains("You died");
        assertThat(scoreLabel.getText()).contains("50");
    }
}