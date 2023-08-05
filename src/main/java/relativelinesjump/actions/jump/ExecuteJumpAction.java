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
import relativelinesjump.utils.JumpHelper;

import static relativelinesjump.config.JumpState.*;

public class ExecuteJumpAction extends JRLAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        JumpState jumpState = ApplicationManager.getApplication().getService(JumpState.class);

        if (jumpState.getMode() == JumpMode.None) {
            return;
        }

        if (jumpState.getLinesCount() == 0) {
            jumpState.setMode(JumpMode.None);
            return;
        }

        Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);

        JumpHelper.jumpToRelativeLine(editor, jumpState);
    }
}