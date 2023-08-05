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
        ApplicationManager.getApplication().getService(JumpState.class).setMode(getMode());
    }

    protected abstract JumpMode getMode();
}