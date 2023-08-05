package relativelinesjump.actions.mode;

import relativelinesjump.config.JumpState.JumpMode;

public class JumpDownModeAction extends JumpModeAction {

    @Override
    protected JumpMode getMode() {
        return JumpMode.DOWN;
    }
}