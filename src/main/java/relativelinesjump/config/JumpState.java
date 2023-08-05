package relativelinesjump.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.apache.commons.lang.NullArgumentException;
import org.jetbrains.annotations.NotNull;

@State(name = "JumpModeState", storages = {@Storage("jump_mode_state.xml")})
public final class JumpState implements PersistentStateComponent<JumpState> {
    private JumpMode mode = JumpMode.None;
    private int linesCount = 0;

    public JumpMode getMode() {
        return mode;
    }

    public void setMode(JumpMode mode) {
        if (mode == null)
            throw new NullArgumentException("Jump mode must be not null");

        this.mode = mode;
    }

    public int getLinesCount() {
        return linesCount;
    }

    public void setLinesCount(int linesCount) {
        if (linesCount < 0)
            throw new RuntimeException("Lines count must be non negative");

        this.linesCount = linesCount;
    }

    public void concatToLinesCount(char character) {
        if (!Character.isDigit(character))
            return;

        try {
            setLinesCount(Integer.parseInt(String.valueOf(getLinesCount()) + character));
        } catch (NumberFormatException e) {
            // TODO: 06.08.2023 jump to last or first line
            setLinesCount(0);
        }
    }

    @Override
    public JumpState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull JumpState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public static enum JumpMode {
        None,
        Up,
        Down,
        Generic
    }
}