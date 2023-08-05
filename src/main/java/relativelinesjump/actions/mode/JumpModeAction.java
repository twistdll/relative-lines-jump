package relativelinesjump.actions.mode;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.annotations.NotNull;
import relativelinesjump.actions.JRLAction;
import relativelinesjump.config.JumpState;
import relativelinesjump.config.JumpState.JumpMode;

public abstract class JumpModeAction extends JRLAction {

    @Override
    public final void actionPerformed(@NotNull AnActionEvent event) {
        JumpState state = ApplicationManager.getApplication().getService(JumpState.class);
        state.setLinesCount(0);
        state.setMode(getMode());
    }

    protected abstract JumpMode getMode();
}