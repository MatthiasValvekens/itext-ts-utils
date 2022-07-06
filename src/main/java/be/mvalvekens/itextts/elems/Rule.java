package be.mvalvekens.itextts.elems;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.layout.element.AbstractElement;
import com.itextpdf.layout.element.ILeafElement;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.renderer.IRenderer;

public class Rule extends AbstractElement<Rule> implements ILeafElement {

    private final float raiseHeight;
    private final Color color;

    public Rule(UnitValue width, UnitValue height, float raiseHeight, Color color) {
        this.raiseHeight = raiseHeight;
        setProperty(Property.WIDTH, width);
        setProperty(Property.HEIGHT, height);
        this.color = color;
    }

    public Rule(UnitValue width, UnitValue height, float raiseHeight) {
        this(width, height, raiseHeight, null);
    }

    @Override
    protected IRenderer makeNewRenderer() {
        return new RuleRenderer(this);
    }

    public float getRaiseHeight() {
        return raiseHeight;
    }

    public Color getColor() {
        return color;
    }

}
