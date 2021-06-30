package sleepx10.gpmodels;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import sleepx10.gpmodels.proxies.CommonProxy;
import sleepx10.gpmodels.utils.Reference;

@Mod(modid=Reference.MOD_ID, version = Reference.VERSION, name = Reference.MOD_NAME, dependencies = Reference.DEPENDENCIES, useMetadata = Reference.META)
public class MainGPModels {
	
	@Instance
	public static MainGPModels instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	//Network object
	public static SimpleNetworkWrapper NETWORK;
	
	// Different ways of render the player.
	public static boolean itemWay = false;
	public static boolean blockWay = false;
	public static boolean eventWay = false;
	public static boolean headRotation = false;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit(e);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e) {}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {}

}
