package cookiedragon.luchadora;

import cookiedragon.luchadora.integration.discord.DiscordIntegration;
import cookiedragon.luchadora.managers.BindManager;
import cookiedragon.luchadora.managers.ForgeEventListener;
import cookiedragon.luchadora.managers.GenericEventListener;
import cookiedragon.luchadora.managers.PerspectiveManager;
import cookiedragon.luchadora.module.ModuleManager;
import cookiedragon.luchadora.module.impl.ui.HudManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(
	modid = Luchadora.MOD_ID,
	name = Luchadora.MOD_NAME,
	version = Luchadora.VERSION
)
public class Luchadora
{
	public static final String MOD_ID = "luchadora";
	public static final String MOD_NAME = "Luchadora";
	public static final String VERSION = "0.1";
	
	@Mod.Instance(MOD_ID)
	public static Luchadora INSTANCE;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		BindManager.INSTANCE.init();
		HudManager.init();
		ForgeEventListener.init();
		PerspectiveManager.init();
		ModuleManager.init();
		GenericEventListener.INSTANCE.init();
		new DiscordIntegration().start();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
	}
	
	public static String getBrand()
	{
		return String.format("%s %s", MOD_NAME, VERSION);
	}
}
