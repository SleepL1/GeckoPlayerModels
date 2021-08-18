package sleepx10.gpmodels;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import sleepx10.gpmodels.common.capabilities.network.ModelsCapMeesage;
import sleepx10.gpmodels.common.network.PacketPlaceBlock;
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
	
	/** ITEM WAY **/
	//If you want the player to have a model by right clicking an item change this to true
	public static boolean itemWay = true;
	
	/** HEAD ROTATION **/
	//If you want the player to be able to rotate the head by moving the mouse change this to true
	public static boolean headRotation = true;
	
	/** RENDER ITEM HAND **/
	//If you want the player to render the item in hand change this to true
	public static boolean renderItemInHand = true;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit(e);
		
		NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
		NETWORK.registerMessage(ModelsCapMeesage.Handler.class, ModelsCapMeesage.class, 0, Side.CLIENT);
		NETWORK.registerMessage(PacketPlaceBlock.Handler.class, PacketPlaceBlock.class, 1, Side.CLIENT);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e) {}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {}

}
