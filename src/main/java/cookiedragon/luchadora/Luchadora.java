package cookiedragon.luchadora;

import cookiedragon.luchadora.integration.discord.DiscordIntegration;
import cookiedragon.luchadora.managers.BindManager;
import cookiedragon.luchadora.managers.ForgeEventListener;
import cookiedragon.luchadora.managers.PerspectiveManager;
import cookiedragon.luchadora.module.ModuleManager;
import cookiedragon.luchadora.module.impl.ui.HudManager;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.item.Item;
import net.minecraft.network.NetworkManager;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
		BindManager.init();
		HudManager.init();
		ForgeEventListener.init();
		PerspectiveManager.init();
		ModuleManager.init();
		new DiscordIntegration().start();
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event)
	{
		if (event.getGui() instanceof GuiDisconnected)
		{
			new RuntimeException("Disconnect Exception").printStackTrace();
		}
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
