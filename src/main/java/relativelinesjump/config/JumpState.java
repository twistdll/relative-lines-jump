package relativelinesjump.config;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import org.apache.commons.lang.NullArgumentException;

@Service(Service.Level.PROJECT)
public final class JumpState {
    private JumpMode mode = JumpMode.None;
    private int linesCount = 0;
    private RangeHighlighter highlighter = null;

    public JumpMode getMode() {
        return mode;
    }

    public void setMode(JumpMode mode) {
        if (mode == null)
            throw new NullArgumentException("Jump mode must be not null");

        this.mode = mode;
    }

    public int getLinesCount() {
        return linesCount;
    }

    public void setLinesCount(int linesCount) {
        if (linesCount < 0)
            throw new RuntimeException("Lines count must be non negative");

        this.linesCount = linesCount;
    }

    public void concatToLinesCount(char character, int maxLineNumber) {
        if (!Character.isDigit(character))
            return;

        try {
            setLinesCount(Integer.parseInt(String.valueOf(getLinesCount()) + character));
        } catch (NumberFormatException e) {
            setLinesCount(getDirection() == -1 ? 0 : maxLineNumber);
        }
    }

    public void eraseCharInLinesCount() {
        String linesCountStr = String.valueOf(getLinesCount());

        if (linesCountStr.length() == 1) {
            setLinesCount(0);
            return;
        }

        linesCountStr = linesCountStr.substring(0, linesCountStr.length() - 1);
        setLinesCount(Integer.parseInt(linesCountStr));
    }

    public int getTargetLine(int currentLine, int maxLineNumber) {
        int newLine = currentLine + (getDirection() * getLinesCount());

        if (newLine > maxLineNumber)
            return maxLineNumber;
        else if (newLine < 0)
            return 0;
        else
            return newLine;
    }

    public int getDirection() {
        return switch (getMode()) {
            case Up -> -1;
            case Down -> 1;
            default -> throw new RuntimeException("Unexpected jump mode");
        };
    }

    public RangeHighlighter getHighlighter() {
        return highlighter;
    }

    public void setHighlighter(RangeHighlighter highlighter) {
        this.highlighter = highlighter;
    }

    public static enum JumpMode {
        None,
        Up,
        Down,
        Generic
    }
}