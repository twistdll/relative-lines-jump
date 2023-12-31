package relativelinesjump.handlers;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import relativelinesjump.config.JumpState;
import relativelinesjump.utils.JumpHelper;

public class EnterInJumpModeHandler extends EditorActionInJumpModeHandler {
    public EnterInJumpModeHandler(EditorActionHandler originalHandler) {
        super(originalHandler);
    }

    @Override
    protected void execute(Editor editor, JumpState jumpState) {
        JumpHelper.jumpToRelativeLine(editor, jumpState);
    }
}