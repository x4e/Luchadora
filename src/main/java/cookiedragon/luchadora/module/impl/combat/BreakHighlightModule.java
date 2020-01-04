package cookiedragon.luchadora.module.impl.combat;

import cookiedragon.luchadora.event.api.Subscriber;
import cookiedragon.luchadora.event.client.Render3dEvent;
import cookiedragon.luchadora.mixin.mixins.world.IMixinRenderGlobal;
import cookiedragon.luchadora.module.AbstractModule;
import cookiedragon.luchadora.module.Category;
import cookiedragon.luchadora.util.RenderUtils;
import cookiedragon.luchadora.value.Value;
import cookiedragon.luchadora.value.values.ColourValue;
import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;

import java.awt.*;
import java.util.Map;

/**
 * @author cookiedragon234 26/Dec/2019
 */
@AbstractModule.Declaration(name = "Break Highlight", description = "Highlight blocks that other players are breaking", category = Category.COMBAT)
public class BreakHighlightModule extends AbstractModule
{
	private final Value<Color> colourValue = new ColourValue("Colour", new Color(0, 64, 255, 100));
	
	@Subscriber
	private void onRenderWorld(Render3dEvent event)
	{
		Map<Integer, DestroyBlockProgress> damagedBlocks = ((IMixinRenderGlobal) mc.getRenderGlobalActual()).getDamagedBlocks();
		
		if (damagedBlocks.isEmpty())
			return;
		
		GlStateManager.glLineWidth(5);
		
		Color colour = colourValue.getValue();
		for (DestroyBlockProgress value : damagedBlocks.values())
		{
			AxisAlignedBB bb = new AxisAlignedBB(value.getPosition());
			
			RenderUtils.drawSelectionBox(bb, colour);
		}
		
		GlStateManager.glLineWidth(1);
	}
}
