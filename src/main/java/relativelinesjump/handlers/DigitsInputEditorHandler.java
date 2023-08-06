package relativelinesjump.handlers;

import com.intellij.codeInsight.CodeInsightSettings;
import com.intellij.codeInsight.template.impl.editorActions.TypedActionHandlerBase;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import relativelinesjump.config.JumpState;

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

        JumpState jumpState = ApplicationManager.getApplication().getService(JumpState.class);

        if (jumpState.getMode() == JumpState.JumpMode.None) {
            return;
        }

        deleteWrittenChar(editor);
        jumpState.concatToLinesCount(charTyped);
    }

    private static void deleteWrittenChar(Editor editor) {
        CaretModel caretModel = editor.getCaretModel();
        int offset = caretModel.getOffset();

        if (offset > 0) {
            editor.getDocument().deleteString(offset - 1, offset);
        }
    }
}