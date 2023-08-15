package relativelinesjump.actions.mode;

import com.intellij.codeInsight.CodeInsightSettings;
import com.intellij.codeInsight.editorActions.enter.EnterHandlerDelegate;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import relativelinesjump.actions.JRLAction;
import relativelinesjump.config.JumpState;
import relativelinesjump.config.JumpState.JumpMode;
import relativelinesjump.handlers.CaretMoveInJumpModeListener;
import relativelinesjump.utils.JumpHelper;

public abstract class JumpModeAction extends JRLAction {

    @Override
    public final void actionPerformed(@NotNull AnActionEvent event) {
        CodeInsightSettings.getInstance().AUTO_POPUP_COMPLETION_LOOKUP = false;
        Project project = event.getProject();

        if (project == null)
            return;

        JumpState state = project.getService(JumpState.class);
        state.setLinesCount(0);
        JumpMode mode = getMode();

        if(mode == JumpMode.None)
            state.disableJumpMode();
        else
            state.setMode(mode);

        Editor editor = event.getData(CommonDataKeys.EDITOR);

        if (editor == null)
            return;

        JumpHelper.removeLineHighlight(editor, state);
        editor.getCaretModel().addCaretListener(new CaretMoveInJumpModeListener());
    }

    protected abstract JumpMode getMode();
}