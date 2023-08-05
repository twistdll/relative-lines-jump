package relativelinesjump.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.apache.commons.lang.NullArgumentException;
import org.jetbrains.annotations.NotNull;

@State(name = "JumpModeState", storages = {@Storage("jump_mode_state.xml")})
public final class JumpState implements PersistentStateComponent<JumpState> {
    private JumpMode mode = JumpMode.NONE;
    private int linesCount = 5;

    public JumpMode getMode() {
        return mode;
    }

    public void setMode(JumpMode mode) {
        if(mode == null)
            throw new NullArgumentException("Jump mode must be not null");

        this.mode = mode;
    }

    public int getLinesCount() {
        return linesCount;
    }

    public void setLinesCount(int linesCount) {
        if(linesCount < 0)
            throw new RuntimeException("Lines count must be positive");

        this.linesCount = linesCount;
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
        NONE,
        UP,
        DOWN,
        GENERIC
    }
}