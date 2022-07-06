package be.mvalvekens.itextts.context;

import be.mvalvekens.itextts.utils.ITextUtils;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.tagutils.TagStructureContext;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Paragraph;

public interface IDocumentContext {

    Style getStyle(StyleType styleType);
    Paragraph createDefaultParagraph();
    Paragraph createHeading(HeadingType hType, String headingText);
    PdfFont getMainFont();
    String getLang();
    TaggingMode getTaggingMode();
    TagStructureContext getTagStructureContext();

    default Paragraph createDefaultParagraph(String content) {
        Paragraph p = createDefaultParagraph();
        if(content != null) {
            p.add(ITextUtils.neutralText(content));
        }
        return p;
    }

    default Paragraph createParagraphWithRole(String content, String role) {
        Paragraph p = createDefaultParagraph(content);
        p.getAccessibilityProperties().setRole(role);
        return p;
    }

    Paragraph createMainTextParagraph();
}
