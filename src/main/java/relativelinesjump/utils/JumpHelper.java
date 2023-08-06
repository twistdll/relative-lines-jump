package relativelinesjump.utils;

import com.intellij.codeInsight.CodeInsightSettings;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.ui.JBColor;
import relativelinesjump.config.JumpState;
import relativelinesjump.config.JumpState.JumpMode;

public class JumpHelper {
    public static void jumpToRelativeLine(Editor editor, JumpState jumpState) {
        final int currentLine = getCurrentLineInEditor(editor);

        editor.getCaretModel()
                .getPrimaryCaret()
                .moveToLogicalPosition(new LogicalPosition(jumpState.getTargetLine(currentLine), 0));
        jumpState.setLinesCount(0);
        jumpState.setMode(JumpMode.None);

        CodeInsightSettings.getInstance().AUTO_POPUP_COMPLETION_LOOKUP = true;
        removeLineHighlight(editor, jumpState);
    }

    public static int getCurrentLineInEditor(Editor editor) {
        return editor.getDocument().getLineNumber(editor.getCaretModel().getPrimaryCaret().getOffset());
    }

    public static void changeHighlightedLine(Editor editor, JumpState jumpState) {
        removeLineHighlight(editor, jumpState);

        TextAttributes attributes = new TextAttributes();
        // TODO: 06.08.2023 get color from settings
        attributes.setBackgroundColor(JBColor.YELLOW);

        RangeHighlighter highlighter = editor.getMarkupModel().addLineHighlighter(
                jumpState.getTargetLine(JumpHelper.getCurrentLineInEditor(editor)),
                HighlighterLayer.ERROR + 1,
                attributes
        );
        jumpState.setHighlighter(highlighter);
    }

    public static void removeLineHighlight(Editor editor, JumpState jumpState) {
        if (jumpState.getHighlighter() == null)
            return;

        editor.getMarkupModel().removeHighlighter(jumpState.getHighlighter());
        jumpState.setHighlighter(null);
    }
}