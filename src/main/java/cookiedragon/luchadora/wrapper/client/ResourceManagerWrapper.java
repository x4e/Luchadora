package cookiedragon.luchadora.wrapper.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author cookiedragon234 27/Dec/2019
 */
public class ResourceManagerWrapper implements IResourceManager
{
	private final Minecraft mc;
	
	public ResourceManagerWrapper(Minecraft mc)
	{
		this.mc = mc;
	}
	
	@Override
	public Set<String> getResourceDomains()
	{
		return mc.getResourceManager().getResourceDomains();
	}
	
	@Override
	public IResource getResource(ResourceLocation location) throws IOException
	{
		return mc.getResourceManager().getResource(location);
	}
	
	@Override
	public List<IResource> getAllResources(ResourceLocation location) throws IOException
	{
		return mc.getResourceManager().getAllResources(location);
	}
	
	public double viewerPosX()
	{
		return mc.getRenderManager().viewerPosX;
	}
	
	public double viewerPosY()
	{
		return mc.getRenderManager().viewerPosY;
	}
	
	public double viewerPosZ()
	{
		return mc.getRenderManager().viewerPosZ;
	}
}
