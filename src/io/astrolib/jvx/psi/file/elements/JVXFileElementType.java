package io.astrolib.jvx.psi.file.elements;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LighterASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.psi.impl.java.stubs.PsiJavaFileStub;
import com.intellij.psi.stubs.*;
import com.intellij.psi.tree.ILightStubFileElementType;
import com.intellij.util.diff.FlyweightCapableTreeStructure;
import com.intellij.util.io.StringRef;
import io.astrolib.jvx.psi.JVXLanguage;
import io.astrolib.jvx.psi.parser.JVXParser;
import io.astrolib.jvx.psi.parser.JVXParserUtil;
import io.astrolib.jvx.psi.stub.JVXLightStubBuilder;
import io.astrolib.jvx.psi.stub.impls.PsiJVXFileStubImpl;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class JVXFileElementType extends ILightStubFileElementType<PsiJavaFileStub> {
    public static final int STUB_VERSION = 27;

    public JVXFileElementType() {
        super("jvx.FILE", JVXLanguage.INSTANCE);
    }

    @Override
    public LightStubBuilder getBuilder() {
        return new JVXLightStubBuilder();
    }

    @Override
    public int getStubVersion() {
        return STUB_VERSION;
    }

    @Override
    public boolean shouldBuildStubFor(final VirtualFile file) {
        final VirtualFile dir = file.getParent();
        return dir == null || dir.getUserData(LanguageLevel.KEY) != null;
    }

    @Override
    public ASTNode createNode(final CharSequence text) {
        return new JVXFileElement(text);
    }

    @Override
    public FlyweightCapableTreeStructure<LighterASTNode> parseContentsLight(final ASTNode chameleon) {
        final PsiBuilder builder = JVXParserUtil.createBuilder(chameleon);
        doParse(builder);
        return builder.getLightTree();
    }

    @Override
    public ASTNode parseContents(final ASTNode chameleon) {
        final PsiBuilder builder = JVXParserUtil.createBuilder(chameleon);
        doParse(builder);
        return builder.getTreeBuilt().getFirstChildNode();
    }

    private void doParse(final PsiBuilder builder) {
        final PsiBuilder.Marker root = builder.mark();
        JVXParser.INSTANCE.getFileParser().parse(builder);
        root.done(this);
    }

    @NotNull
    @Override
    public String getExternalId() {
        return "jvx.FILE";
    }

    @Override
    public void serialize(@NotNull final PsiJavaFileStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeBoolean(stub.isCompiled());
        dataStream.writeName(stub.getPackageName());
    }

    @NotNull
    @Override
    public PsiJavaFileStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        boolean compiled = dataStream.readBoolean();
        StringRef packName = dataStream.readName();
        return new PsiJVXFileStubImpl(null, packName, compiled);
    }

    @Override
    public void indexStub(@NotNull final PsiJavaFileStub stub, @NotNull final IndexSink sink) {
        //Integer fileId = stub.getUserData(IndexingDataKeys.VIRTUAL_FILE_ID);
        //if (fileId == null) return;
        //IndexTree.Unit unit = JavaStubIndexer.translate(fileId, stub);
        //if (unit != null) {
        //  sink.occurrence(JavaStubIndexKeys.UNITS, unit);
        //}
    }
}