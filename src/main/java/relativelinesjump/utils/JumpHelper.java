package relativelinesjump.utils;

import com.intellij.codeInsight.CodeInsightSettings;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import relativelinesjump.config.JumpState;

public class JumpHelper {
    public static void jumpToRelativeLine(Editor editor, JumpState jumpState) {
        Caret caret = editor.getCaretModel().getPrimaryCaret();
        final int currentLine = editor.getDocument().getLineNumber(caret.getOffset());
        int direction;

        switch (jumpState.getMode()) {
            case Up:
                direction = -1;
                break;
            case Down:
                direction = 1;
                break;
            default:
                throw new RuntimeException("Unexpected jump mode");
        }

        int newLine = currentLine + (direction * jumpState.getLinesCount());

        if (newLine < 0) {
            newLine = 0;
        }

        editor.getCaretModel()
                .getPrimaryCaret()
                .moveToLogicalPosition(new LogicalPosition(newLine, 0));
        jumpState.setLinesCount(0);
        jumpState.setMode(JumpState.JumpMode.None);
        CodeInsightSettings.getInstance().AUTO_POPUP_COMPLETION_LOOKUP = true;
    }
}