package be.mvalvekens.itextts.elems;

import be.mvalvekens.itextts.context.IDocumentContext;
import be.mvalvekens.itextts.context.StyleType;
import be.mvalvekens.itextts.utils.ITextUtils;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.HorizontalAlignment;

public class IconLink {
    private final Label label;
    private final String displayText;
    private final String uri;

    // TODO need builder with sane defaults
    private IconLink(Label label, String displayText, String uri) {
        this.label = label;
        this.displayText = displayText;
        this.uri = uri;
    }

    public IBlockElement format(IDocumentContext context) {
        Paragraph para = new Paragraph().setHorizontalAlignment(HorizontalAlignment.RIGHT);
        para.getAccessibilityProperties().setRole(StandardRoles.LI);
        para.add(label.asText());

        Paragraph body = new Paragraph().setMargin(0);
        Style style = context.getStyle(StyleType.ObliqueText);
        body.addStyle(style);
        body.getAccessibilityProperties().setRole(StandardRoles.LBODY);
        body.add(ITextUtils.borderlessLink(displayText, uri, label.replacementText + " link"));
        para.add(body);

        para.setMultipliedLeading(1f);
        return para;
    }

    public static class Label {
        private final String replacementText;
        private final String symbolText;
        private final PdfFont symbolFont;

        public Label(String replacementText, String symbolText, PdfFont symbolFont) {
            this.replacementText = replacementText;
            this.symbolText = symbolText;
            this.symbolFont = symbolFont;
        }

        private Text asText() {
            Text iconText = new Text(symbolText + " ").setFont(this.symbolFont);
            iconText.getAccessibilityProperties()
                    .setActualText(replacementText)
                    .setRole(StandardRoles.LBL);
            return iconText;
        }

        public String getReplacementText() {
            return replacementText;
        }

        public String getSymbolText() {
            return symbolText;
        }

        public PdfFont getSymbolFont() {
            return symbolFont;
        }
    }

    public static class Factory {
        private final PdfFont font;

        public Factory(PdfFont font) {
            this.font = font;
        }

        public IconLink build(String displayText, String uri, String labelSymbolText, String labelReplacementText) {
            Label l = new IconLink.Label(labelReplacementText, labelSymbolText, this.font);
            return new IconLink(l, displayText, uri);
        }

        public IconLink buildEmail(String email, String labelSymbolText, String labelReplacementText) {
            return build(email, "mailto:" + email, labelSymbolText, labelReplacementText);
        }

        public IconLink buildPhone(String telNo, String labelSymbolText, String labelReplacementText) {
            String uri = "tel:" + telNo.replace(" ", "");
            return build(telNo, uri, labelSymbolText, labelReplacementText);
        }
    }

    public Label getLabel() {
        return label;
    }

    public String getDisplayText() {
        return displayText;
    }

    public String getUri() {
        return uri;
    }
}
