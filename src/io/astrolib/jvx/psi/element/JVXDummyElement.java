package io.astrolib.jvx.psi.element;

import com.intellij.pom.java.LanguageLevel;
import com.intellij.psi.impl.source.tree.FileElement;
import com.intellij.psi.impl.source.tree.TreeElement;
import io.astrolib.jvx.psi.parser.JVXParserUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by skeswa on 6/1/16.
 */
public class JVXDummyElement extends FileElement {
    private final JVXParserUtil.ParserWrapper myParser;
    private final LanguageLevel myLanguageLevel;
    private final boolean myConsumeAll;
    private Throwable myParserError = null;

    public JVXDummyElement(@Nullable final CharSequence text,
                            @NotNull final JVXParserUtil.ParserWrapper parser,
                            @NotNull final LanguageLevel level) {
        this(text, parser, level, false);
    }

    public JVXDummyElement(@Nullable final CharSequence text,
                            @NotNull final JVXParserUtil.ParserWrapper parser,
                            @NotNull final LanguageLevel level,
                            final boolean consumeAll) {
        super(JVXElementType.DUMMY_ELEMENT, text);
        myParser = parser;
        myLanguageLevel = level;
        myConsumeAll = consumeAll;
    }

    @NotNull
    public JVXParserUtil.ParserWrapper getParser() {
        return myParser;
    }

    public boolean consumeAll() {
        return myConsumeAll;
    }

    @NotNull
    public LanguageLevel getLanguageLevel() {
        return myLanguageLevel;
    }

    @Override
    public TreeElement getFirstChildNode() {
        try {
            return super.getFirstChildNode();
        }
        catch (AssertionError e) {
            myParserError = e;
            return null;  // masquerade parser errors
        }
    }

    @Override
    public TreeElement getLastChildNode() {
        try {
            return super.getLastChildNode();
        }
        catch (AssertionError e) {
            myParserError = e;
            return null;  // masquerade parser errors
        }
    }

    @Nullable
    public Throwable getParserError() {
        return myParserError;
    }
}