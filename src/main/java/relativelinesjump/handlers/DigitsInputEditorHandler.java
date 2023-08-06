package relativelinesjump.handlers;

import com.intellij.codeInsight.template.impl.editorActions.TypedActionHandlerBase;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import relativelinesjump.config.JumpState;
import relativelinesjump.config.JumpState.JumpMode;
import relativelinesjump.utils.JumpHelper;

public class DigitsInputEditorHandler extends TypedActionHandlerBase {
    private final TypedActionHandler originalHandler;

    public DigitsInputEditorHandler(@Nullable TypedActionHandler originalHandler) {
        super(originalHandler);

        this.originalHandler = originalHandler;
    }

    @Override
    public void execute(@NotNull Editor editor, char charTyped, @NotNull DataContext dataContext) {
        if (originalHandler != null) {
            originalHandler.execute(editor, charTyped, dataContext);
        }

        Project project = editor.getProject();

        if (project == null) {
            return;
        }

        JumpState jumpState = project.getService(JumpState.class);

        if (jumpState.getMode() == JumpMode.None) {
            return;
        }

        deleteWrittenChar(editor);
        jumpState.concatToLinesCount(charTyped, editor.getDocument().getLineCount() - 1);

        JumpHelper.changeHighlightedLine(editor, jumpState);
    }

    private static void deleteWrittenChar(Editor editor) {
        CaretModel caretModel = editor.getCaretModel();
        int offset = caretModel.getOffset();

        if (offset > 0) {
            editor.getDocument().deleteString(offset - 1, offset);
        }
    }
}