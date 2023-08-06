package relativelinesjump.handlers;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import relativelinesjump.config.JumpState;
import relativelinesjump.config.JumpState.JumpMode;

public abstract class EditorActionInJumpModeHandler extends EditorActionHandler {
    private final EditorActionHandler originalHandler;

    public EditorActionInJumpModeHandler(EditorActionHandler originalHandler) {
        this.originalHandler = originalHandler;
    }

    @Override
    protected void doExecute(@NotNull Editor editor, @Nullable Caret caret, DataContext dataContext) {
        JumpState jumpState = getJumpState(editor);

        if (jumpState == null || jumpState.getMode() == JumpMode.None) {
            originalHandler.execute(editor, caret, dataContext);
            return;
        }

        execute(editor, jumpState);
    }

    protected abstract void execute(Editor editor, JumpState jumpState);

    private static JumpState getJumpState(Editor editor) {
        Project project = editor.getProject();

        if (project == null)
            return null;

        return project.getService(JumpState.class);
    }
}