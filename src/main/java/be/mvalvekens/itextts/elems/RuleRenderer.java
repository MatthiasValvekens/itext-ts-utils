package be.mvalvekens.itextts.elems;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.CanvasArtifact;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.renderer.AbstractRenderer;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.TargetCounterHandler;

public class RuleRenderer extends AbstractRenderer {
    public RuleRenderer(Rule rule) {
        super(rule);
    }

    @Override
    public LayoutResult layout(LayoutContext layoutContext) {
        LayoutArea area = layoutContext.getArea();
        Float width = retrieveWidth(area.getBBox().getWidth());
        Float height = retrieveHeight();
        float raiseHeight = ((Rule) this.modelElement).getRaiseHeight();
        Rectangle bbox = new Rectangle(area.getBBox().getX(), raiseHeight + area.getBBox().getY() + area.getBBox().getHeight(), width, height);
        occupiedArea = new LayoutArea(area.getPageNumber(), bbox);

        TargetCounterHandler.addPageByID(this);

        return new LayoutResult(LayoutResult.FULL, occupiedArea, null, null);
    }

    @Override
    public void draw(DrawContext drawContext) {
        if (occupiedArea == null) {
            return;
        }

        boolean isTagged = drawContext.isTaggingEnabled();
        if (isTagged) {
            drawContext.getCanvas().openTag(new CanvasArtifact());
        }

        Rule rule = (Rule) this.modelElement;
        float raiseHeight = rule.getRaiseHeight();
        Color color = rule.getColor();
        beginElementOpacityApplying(drawContext);
        PdfCanvas canvas = drawContext.getCanvas();
        if (color != null) {
            canvas.saveState().setFillColor(color);
        }
        canvas.rectangle(occupiedArea.getBBox().moveUp(raiseHeight)).fill();
        if (color != null) {
            canvas.restoreState();
        }
        endElementOpacityApplying(drawContext);

        if (isTagged) {
            drawContext.getCanvas().closeTag();
        }
    }

    @Override
    public IRenderer getNextRenderer() {
        return new RuleRenderer((Rule) modelElement);
    }
}
