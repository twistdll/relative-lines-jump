package relativelinesjump.handlers;

import com.intellij.codeInsight.editorActions.enter.EnterHandlerDelegate;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.util.Ref;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import relativelinesjump.config.JumpState;
import relativelinesjump.utils.JumpHelper;

public class EnterInJumpModeHandler implements EnterHandlerDelegate {

    @Override
    public Result preprocessEnter(@NotNull PsiFile file,
                                  @NotNull Editor editor,
                                  @NotNull Ref<Integer> caretOffset,
                                  @NotNull Ref<Integer> caretAdvance,
                                  @NotNull DataContext dataContext,
                                  @Nullable EditorActionHandler originalHandler) {
        JumpState jumpState = ApplicationManager.getApplication().getService(JumpState.class);

        if (jumpState.getMode() == JumpState.JumpMode.None)
            return Result.Continue;

        JumpHelper.jumpToRelativeLine(editor, jumpState);
        return Result.Stop;
    }

    @Override
    public Result postProcessEnter(@NotNull PsiFile file, @NotNull Editor editor, @NotNull DataContext dataContext) {
        return Result.Continue;
    }
}