package cookiedragon.luchadora.module.impl.ui.elements.clickgui.values;

import cookiedragon.luchadora.Luchadora;
import cookiedragon.luchadora.kotlin.ExtensionsKt;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.ModuleElement;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.ValueElement;
import cookiedragon.luchadora.util.RenderUtils;
import cookiedragon.luchadora.util.Vec2f;
import cookiedragon.luchadora.value.values.ColourValue;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * @author cookiedragon234 26/Dec/2019
 */
@SuppressWarnings("Duplicates")
public class ColourValueElement extends ValueElement<ColourValue>
{
	private static final ResourceLocation COLOUR_PICKER_RESOURCE = new ResourceLocation(Luchadora.MOD_ID, "colourpicker.png");
	private static BufferedImage pickerImage;
	
	private static float downScale;
	private static float upScale;
	
	private boolean open = false;
	private Vec2f textPos = new Vec2f(0,0);
	private Vec2f textSize = new Vec2f(0,0);
	private Vec2f imgPos = new Vec2f(0,0);
	private Vec2f imgSize = new Vec2f(0,0);
	
	private Vec2f draggingPos = null;
	
	public ColourValueElement(ColourValue value, ModuleElement moduleElement)
	{
		super(value, moduleElement);
		try
		{
			InputStream inputStream = mc.getResourceManager().getResource(COLOUR_PICKER_RESOURCE).getInputStream();
			pickerImage = ImageIO.read(inputStream);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Error getting colour picker bytes", e);
		}
	}
	
	@SuppressWarnings("Duplicates")
	@Override
	public void render(Vec2f mousePos)
	{
		if (moduleElement.collapsed)
		{
			size = new Vec2f(0,0);
			return;
		}
		
		size = new Vec2f(
			100,
			mc.fontRenderer.FONT_HEIGHT + 2
		);
		
		position.x += 2;
		size.x -= 2;
		
		textPos = position.copy();
		textSize = size.copy();
		
		RenderUtils.renderRectangle(
			textPos.x,
			textPos.y,
			textPos.x + textSize.x,
			textPos.y + textSize.y,
			moduleElement.categoryElement.guiModule.negativeColour.getValue().getRGB()
		);
		
		RenderUtils.renderOutline(
			textPos.x,
			textPos.y,
			textPos.x + textSize.x,
			textPos.y + textSize.y,
			new Color(0,0,0, 50).getRGB()
		);
		
		ExtensionsKt.drawStringClamped(
			mc.fontRenderer,
			this.value.getName(),
			textPos.x + 1,
			textPos.y + 1,
			size.x / 2,
			moduleElement.categoryElement.guiModule.textColour.getValue().getRGB()
		);
		
		/*mc.fontRenderer.drawStringRightClamped(
			colourToString(value.getValue()),
			textPos.x + textSize.x,
			textPos.y + 1,
			textSize.x / 2,
			Color.LIGHT_GRAY.getRGB()
		);*/
		
		RenderUtils.renderRectangle(
			textPos.x + size.x - (size.y + 2),
			textPos.y + 2,
			textPos.x + size.x - 2,
			textPos.y + size.y - 2,
			this.value.getValue().getRGB()
		);
		
		if (open)
		{
			imgPos = position.copy();
			imgSize = size.copy();
			
			imgPos.x += 2;
			imgPos.y += textSize.y;
			imgSize.x -= 2;
			imgSize.y = imgSize.x;
			
			size.y += imgSize.y;
			
			downScale = imgSize.x / pickerImage.getWidth();
			upScale = pickerImage.getWidth() / imgSize.x;
			
			GlStateManager.pushAttrib();
			GlStateManager.scale(downScale, downScale, 1);
			mc.renderEngine.bindTexture(COLOUR_PICKER_RESOURCE);
			int x = Math.round(imgPos.x * upScale);
			int y = Math.round(imgPos.y * upScale);
			int width = Math.round(imgSize.x * upScale);
			int height = Math.round(imgSize.y * upScale);
			
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GlStateManager.color(1F, 1F, 1F, 1F);
			mc.ingameGUI.drawTexturedModalRect(x, y, 0, 0, width, height);
			GlStateManager.disableBlend();
			GlStateManager.scale(upScale, upScale, 1);
			GlStateManager.popAttrib();
			
			RenderUtils.renderOutline(
				imgPos.x,
				imgPos.y,
				imgPos.x + imgSize.x,
				imgPos.y + imgSize.y,
				Color.BLACK.getRGB()
			);
			
			if (draggingPos != null)
			{
				Tessellator tessellator = Tessellator.getInstance();
				BufferBuilder bufferbuilder = tessellator.getBuffer();
				GlStateManager.enableBlend();
				GlStateManager.disableTexture2D();
				GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
				bufferbuilder.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION);
				bufferbuilder.pos((double)draggingPos.x - 2, (double)draggingPos.y, 0.0D).endVertex();
				bufferbuilder.pos((double)draggingPos.x, (double)draggingPos.y + 2, 0.0D).endVertex();
				bufferbuilder.pos((double)draggingPos.x + 2, (double)draggingPos.y, 0.0D).endVertex();
				bufferbuilder.pos((double)draggingPos.x, (double)draggingPos.y - 2, 0.0D).endVertex();
				tessellator.draw();
				GlStateManager.enableTexture2D();
				GlStateManager.disableBlend();
			}
		}
	}
	
	private static Color getColorAtPixel(int x, int y)
	{
		return new Color(pickerImage.getRGB(x,y));
	}
	
	@Override
	public boolean mouseClick(Vec2f mousePos, int mouseID)
	{
		if (textPos.contains(textSize, mousePos) && mouseID == 1)
		{
			this.open = !this.open;
			return true;
		}
		else if (open && imgPos.contains(imgSize, mousePos) && mouseID == 0)
		{
			draggingPos = mousePos;
			int x = Math.round((mousePos.x - imgPos.x) * upScale);
			int y = Math.round((mousePos.y - imgPos.y) * upScale);
			return setValue(x, y);
		}
		draggingPos = null;
		
		return false;
	}
	
	@Override
	public boolean mouseClickMove(Vec2f mousePos, int mouseID)
	{
		if (open && imgPos.contains(imgSize, mousePos) && mouseID == 0)
		{
			draggingPos = mousePos;
			int x = Math.round((mousePos.x - imgPos.x) * upScale);
			int y = Math.round((mousePos.y - imgPos.y) * upScale);
			return setValue(x, y);
		}
		draggingPos = null;
		return false;
	}
	
	@Override
	public boolean mouseRelease(Vec2f mousePos, int mouseID)
	{
		draggingPos = null;
		return false;
	}
	
	private String colourToString(Color color)
	{
		return "RGBA(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + "," + color.getAlpha() + ")";
	}
	
	private boolean setValue(int x, int y)
	{
		try
		{
			this.value.setValue(getColorAtPixel(x,y));
			return true;
		}
		catch (Exception ignored){}
		return false;
	}
}
