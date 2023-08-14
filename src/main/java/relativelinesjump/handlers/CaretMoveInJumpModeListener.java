package relativelinesjump.handlers;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.CaretEvent;
import com.intellij.openapi.editor.event.CaretListener;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import relativelinesjump.config.JumpState;
import relativelinesjump.config.JumpState.JumpMode;
import relativelinesjump.utils.JumpHelper;


public class CaretMoveInJumpModeListener implements CaretListener {

    @Override
    public void caretPositionChanged(@NotNull CaretEvent event) {
        if (!isLineChanged(event))
            return;

        Editor editor = event.getEditor();
        Project project = event.getEditor().getProject();

        if (project == null)
            return;

        JumpState jumpState = project.getService(JumpState.class);

        if (jumpState.getMode() == JumpMode.None)
            return;

        if (jumpState.getLinesCount() == 0) {
            jumpState.disableJumpMode();
            JumpHelper.changeHighlightedLine(editor, jumpState);
            return;
        }

        JumpHelper.changeHighlightedLine(editor, jumpState);
    }

    private static boolean isLineChanged(CaretEvent event) {
        return event.getNewPosition().line != event.getOldPosition().line;
    }
}