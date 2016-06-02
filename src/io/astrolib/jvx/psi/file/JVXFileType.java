package io.astrolib.jvx.psi.file;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.fileTypes.LanguageFileType;
import io.astrolib.jvx.psi.JVXLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by skeswa on 5/30/16.
 */
public class JVXFileType extends LanguageFileType {
    public static final JVXFileType INSTANCE = new JVXFileType();

    private JVXFileType() {
        super(JVXLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "JVX";
    }

    @NotNull
    @Override
    public String getDescription() {
        // TODO(skeswa): real description should go here.
        return "JVX language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "jvx";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return AllIcons.FileTypes.Java;
    }
}
