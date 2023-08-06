package relativelinesjump.actions.mode;

import com.intellij.codeInsight.CodeInsightSettings;
import com.intellij.codeInsight.editorActions.enter.EnterHandlerDelegate;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import relativelinesjump.actions.JRLAction;
import relativelinesjump.config.JumpState;
import relativelinesjump.config.JumpState.JumpMode;

public abstract class JumpModeAction extends JRLAction {

    @Override
    public final void actionPerformed(@NotNull AnActionEvent event) {
        CodeInsightSettings.getInstance().AUTO_POPUP_COMPLETION_LOOKUP = false;
        Project project = event.getProject();

        if (project == null)
            return;

        JumpState state = project.getService(JumpState.class);
        state.setLinesCount(0);
        state.setMode(getMode());
    }

    protected abstract JumpMode getMode();
}