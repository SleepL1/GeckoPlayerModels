package sleepx10.gpmodels.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item {

	public ItemBase(String name) {
		setRegistryName(name);
		setCreativeTab(CreativeTabs.COMBAT);

		ItemInit.ITEMS.add(this);
	}
}
