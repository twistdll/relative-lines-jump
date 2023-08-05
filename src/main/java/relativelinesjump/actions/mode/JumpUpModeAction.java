package relativelinesjump.actions.mode;

import relativelinesjump.config.JumpState.JumpMode;

public class JumpUpModeAction extends JumpModeAction {

    @Override
    protected JumpMode getMode() {
        return JumpMode.Up;
    }
}