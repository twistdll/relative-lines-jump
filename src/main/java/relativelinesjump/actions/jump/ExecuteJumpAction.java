package relativelinesjump.actions.jump;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import org.jetbrains.annotations.NotNull;
import relativelinesjump.actions.JRLAction;
import relativelinesjump.config.JumpState;

import static relativelinesjump.config.JumpState.*;

public class ExecuteJumpAction extends JRLAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        JumpState jumpState = ApplicationManager.getApplication().getService(JumpState.class);

        if (jumpState.getMode() == JumpMode.NONE) {
            return;
        }

        if (jumpState.getLinesCount() == 0) {
            jumpState.setMode(JumpMode.NONE);
            return;
        }

        Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
        Caret caret = editor.getCaretModel().getPrimaryCaret();
        final int currentLine = editor.getDocument().getLineNumber(caret.getOffset());
        int direction;

        switch (jumpState.getMode()) {
            case UP:
                direction = -1;
                break;
            case DOWN:
                direction = 1;
                break;
            default:
                throw new RuntimeException("Unexpected jump mode");
        }

        editor.getCaretModel()
                .getPrimaryCaret()
                .moveToLogicalPosition(new LogicalPosition(currentLine + (direction * jumpState.getLinesCount()), 0));
    }
}