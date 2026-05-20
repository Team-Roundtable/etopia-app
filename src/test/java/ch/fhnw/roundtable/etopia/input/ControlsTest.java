package ch.fhnw.roundtable.etopia.input;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControlsTest {

    private Map<ButtonType, Button> mockButtons;
    private Controls controls;

    @BeforeEach
    void setUp() {
        mockButtons = new EnumMap<>(ButtonType.class);
        for (ButtonType type : ButtonType.values()) {
            mockButtons.put(type, mock(Button.class));
        }
        controls = new Controls(mockButtons);
    }

    @Test
    void testDelegationMethodsForwardCorrectly() {
        Button upMock = mockButtons.get(ButtonType.UP);
        when(upMock.isPressed()).thenReturn(true);
        when(upMock.isJustPressed()).thenReturn(true);
        when(upMock.isJustReleased()).thenReturn(false);

        assertTrue(controls.isButtonPressed(ButtonType.UP));
        assertTrue(controls.isButtonJustPressed(ButtonType.UP));
        assertFalse(controls.isButtonReleased(ButtonType.UP));
    }

    @Test
    void testUpdateCallsEverySingleButton() {
        controls.update();

        for (Button mockButton : mockButtons.values()) {
            verify(mockButton, times(1)).update();
        }
    }

    @Test
    void testGetButtonHorizontalVectorMath() {
        // Neutral center setup (0, 0)
        assertEquals(0f, controls.getButtonHorizontal());

        // Press RIGHT (Vector should be +1)
        when(mockButtons.get(ButtonType.RIGHT).isPressed()).thenReturn(true);
        assertEquals(1f, controls.getButtonHorizontal());

        // Press LEFT simultaneously (Cancels out to 0)
        when(mockButtons.get(ButtonType.LEFT).isPressed()).thenReturn(true);
        assertEquals(0f, controls.getButtonHorizontal());

        // Release RIGHT (Vector should be -1)
        when(mockButtons.get(ButtonType.RIGHT).isPressed()).thenReturn(false);
        assertEquals(-1f, controls.getButtonHorizontal());
    }

    @Test
    void testGetButtonVerticalVectorMath() {
        // Press UP (Vector should be +1)
        when(mockButtons.get(ButtonType.UP).isPressed()).thenReturn(true);
        assertEquals(1f, controls.getButtonVertical());

        // Press DOWN simultaneously (Cancels out to 0)
        when(mockButtons.get(ButtonType.DOWN).isPressed()).thenReturn(true);
        assertEquals(0f, controls.getButtonVertical());

        // Released UP (Vector should be -1)
        when(mockButtons.get(ButtonType.UP).isPressed()).thenReturn(false);
        assertEquals(-1f, controls.getButtonVertical());
    }

    @Test
    void testSetButtonLightPlayableLayout() {
        controls.setButtonLightPlayable();

        verify(mockButtons.get(ButtonType.UP)).setLight(true);
        verify(mockButtons.get(ButtonType.DOWN)).setLight(true);
        verify(mockButtons.get(ButtonType.LEFT)).setLight(true);
        verify(mockButtons.get(ButtonType.RIGHT)).setLight(true);
        verify(mockButtons.get(ButtonType.SELECT)).setLight(true);
        verify(mockButtons.get(ButtonType.BACK)).setLight(false);
    }

    @Test
    void testSetButtonLightHorizontalLayout() {
        controls.setButtonLightHorizontal();

        verify(mockButtons.get(ButtonType.LEFT)).setLight(true);
        verify(mockButtons.get(ButtonType.RIGHT)).setLight(true);
        verify(mockButtons.get(ButtonType.UP)).setLight(false);
        verify(mockButtons.get(ButtonType.DOWN)).setLight(false);
    }

    @Test
    void testSetButtonLightNoneTurnsOffAllButtons() {
        // Act
        controls.setButtonLightNone();

        // Assert: Every single button type must be explicitly turned off
        for (ButtonType type : ButtonType.values()) {
            verify(mockButtons.get(type)).setLight(false);
        }
    }

    @Test
    void testSetButtonLightVerticalLayout() {
        // Act
        controls.setButtonLightVertical();

        // Assert: Vertically aligned execution tracking pins must be ON
        verify(mockButtons.get(ButtonType.UP)).setLight(true);
        verify(mockButtons.get(ButtonType.DOWN)).setLight(true);

        // Assert: Everything else must be explicitly turned OFF
        verify(mockButtons.get(ButtonType.LEFT)).setLight(false);
        verify(mockButtons.get(ButtonType.RIGHT)).setLight(false);
        verify(mockButtons.get(ButtonType.SELECT)).setLight(false);
        verify(mockButtons.get(ButtonType.BACK)).setLight(false);
    }
}
