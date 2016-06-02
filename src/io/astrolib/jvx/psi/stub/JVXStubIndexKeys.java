package io.astrolib.jvx.psi.stub;

import com.intellij.psi.PsiAnonymousClass;
import com.intellij.psi.PsiClass;
import com.intellij.psi.stubs.StubIndexKey;

/**
 * @author skeswa
 */
public class JVXStubIndexKeys {
    public static final StubIndexKey<String, PsiAnonymousClass> ANONYMOUS_BASEREF = StubIndexKey.createIndexKey("jvx.anonymous.baseref");
    public static final StubIndexKey<String, PsiClass> CLASS_SHORT_NAMES = StubIndexKey.createIndexKey("jvx.class.shortname");
    public static final StubIndexKey<Integer,PsiClass> CLASS_FQN = StubIndexKey.createIndexKey("jvx.class.fqn");

    private JVXStubIndexKeys() {
    }
}
