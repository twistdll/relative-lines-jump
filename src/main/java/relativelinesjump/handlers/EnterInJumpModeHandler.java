package relativelinesjump.handlers;

import com.intellij.codeInsight.editorActions.enter.EnterHandlerDelegate;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Ref;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import relativelinesjump.config.JumpState;
import relativelinesjump.config.JumpState.JumpMode;
import relativelinesjump.utils.JumpHelper;

import java.util.Date;

public class EnterInJumpModeHandler extends EditorActionHandler {

    @Override
    protected void doExecute(@NotNull Editor editor, @Nullable Caret caret, DataContext dataContext) {
        JumpState jumpState = getJumpState(editor);

        if (jumpState == null || jumpState.getMode() == JumpMode.None)
            return;

        JumpHelper.jumpToRelativeLine(editor, jumpState);
    }

    private static JumpState getJumpState(Editor editor) {
        Project project = editor.getProject();

        if (project == null)
            return null;

        return project.getService(JumpState.class);
    }
}