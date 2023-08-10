package relativelinesjump.handlers;

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import relativelinesjump.config.JumpState;
import relativelinesjump.config.JumpState.JumpMode;
import relativelinesjump.utils.JumpHelper;

public class DigitsInputEditorHandler extends TypedHandlerDelegate {

    @Override
    public @NotNull Result beforeCharTyped(char c, @NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file, @NotNull FileType fileType) {
        JumpState jumpState = project.getService(JumpState.class);

        if (jumpState.getMode() == JumpMode.None)
            return Result.DEFAULT;

        jumpState.concatToLinesCount(c, editor.getDocument().getLineCount() - 1);

        JumpHelper.changeHighlightedLine(editor, jumpState);
        return Result.CONTINUE;
    }

    @Override
    public @NotNull Result charTyped(char c, @NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
        JumpState jumpState = project.getService(JumpState.class);

        if (jumpState.getMode() == JumpMode.None)
            return Result.DEFAULT;

        deleteWrittenChar(editor);
        return Result.STOP;
    }

    private static void deleteWrittenChar(Editor editor) {
        CaretModel caretModel = editor.getCaretModel();
        int offset = caretModel.getOffset();

        if (offset > 0) {
            editor.getDocument().deleteString(offset - 1, offset);
        }
    }
}