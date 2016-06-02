package io.astrolib.jvx.psi;

import com.intellij.lang.Language;

/**
 * Created by skeswa on 5/30/16.
 */
public class JVXLanguage extends Language {
    public static final JVXLanguage INSTANCE = new JVXLanguage();

    private JVXLanguage() {
        super("JVX");
    }
}
