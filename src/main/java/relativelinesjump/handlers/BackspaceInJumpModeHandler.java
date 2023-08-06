package relativelinesjump.handlers;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import relativelinesjump.config.JumpState;
import relativelinesjump.utils.JumpHelper;

public class BackspaceInJumpModeHandler extends EditorActionInJumpModeHandler {
    public BackspaceInJumpModeHandler(EditorActionHandler originalHandler) {
        super(originalHandler);
    }

    @Override
    protected void execute(Editor editor, JumpState jumpState) {
        jumpState.eraseCharInLinesCount();
        JumpHelper.changeHighlightedLine(editor, jumpState);
    }
}