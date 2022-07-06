package be.mvalvekens.itextts.md;

import be.mvalvekens.itextts.context.IDocumentContext;
import com.itextpdf.layout.element.Div;
import org.commonmark.node.Node;
import org.commonmark.node.Visitor;
import org.commonmark.parser.Parser;

import java.util.Collections;

public class SimpleRichText {
    private final String markdownInput;
    private final boolean isSnippet;
    private String role = null;
    private String namespace = null;
    private boolean emphasisIsSemantic;

    public SimpleRichText(String markdownInput) {
        this(markdownInput, false);
    }

    public SimpleRichText(String markdownInput, boolean isSnippet) {
        this.markdownInput = markdownInput;
        this.isSnippet = isSnippet;
        this.emphasisIsSemantic = !isSnippet;
    }

    public String getRole() {
        return role;
    }

    public String getNamespace() {
        return namespace;
    }

    public SimpleRichText setRole(String role) {
        return setRole(role, null);
    }

    public SimpleRichText setRole(String role, String namespace) {
        this.role = role;
        this.namespace = namespace;
        return this;
    }

    public SimpleRichText setSemanticEmphasis(boolean emphasisIsSemantic) {
        this.emphasisIsSemantic = emphasisIsSemantic;
        return this;
    }

    public Div performLayout(IDocumentContext context) {
        // no special blocks enabled
        Parser p = Parser.builder()
                .enabledBlockTypes(Collections.emptySet())
                .build();
        Node node = p.parse(this.markdownInput);
        Div result = new Div();
        Visitor v = new ITextLayoutVisitor(context, result::add, isSnippet, emphasisIsSemantic);
        node.accept(v);
        return result;
    }
}
