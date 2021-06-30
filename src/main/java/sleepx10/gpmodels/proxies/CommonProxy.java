package sleepx10.gpmodels.proxies;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import sleepx10.gpmodels.common.capabilities.core.ModCapabilities;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent e) {
		ModCapabilities.registerCapabilities();
	}

	public void registerItemRenderer(Item item, int meta, String id) {
	}

}
