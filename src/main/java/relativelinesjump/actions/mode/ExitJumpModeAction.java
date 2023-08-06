package relativelinesjump.actions.mode;

import static relativelinesjump.config.JumpState.JumpMode;

public class ExitJumpModeAction extends JumpModeAction {

    @Override
    protected JumpMode getMode() {
        return JumpMode.None;
    }
}