package sleepx10.gpmodels.common.handler;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import sleepx10.gpmodels.MainGPModels;
import sleepx10.gpmodels.common.blocks.BlockInit;
import sleepx10.gpmodels.common.capabilities.IModelsCap;
import sleepx10.gpmodels.common.capabilities.core.ModCapabilities;
import sleepx10.gpmodels.common.items.ItemInit;

@EventBusSubscriber
public class CommonEventHandler {

	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> e) {
		if (MainGPModels.itemWay)
			e.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
	}

	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> e) {
		if (MainGPModels.blockWay)
			e.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
	}

	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<Entity> e) {
		if (e.getObject() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) e.getObject();

			if (!player.hasCapability(ModCapabilities.CAPABILITY_MODELS, null)) {
				IModelsCap modelsCap = ModCapabilities.CAPABILITY_MODELS.getDefaultInstance();
				e.addCapability(modelsCap.getKey(), modelsCap.getProvider());
			}
		}
	}

	@SubscribeEvent
	public static void onTickPlayer(PlayerTickEvent e) {
		if (e.phase == Phase.END) {
			if (e.side == Side.SERVER) {
				if (e.player instanceof EntityPlayerMP) {
					IModelsCap modelCap = e.player.getCapability(ModCapabilities.CAPABILITY_MODELS, null);
					if (modelCap != null) {
						modelCap.sendDataToAllTracking((EntityPlayerMP) e.player);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onItemRightClick(RightClickItem e) {
		if (e.getSide().equals(Side.SERVER)) {
			if (e.getEntityPlayer() != null && e.getEntityPlayer() instanceof EntityPlayerMP) {
				if (e.getEntityPlayer().getActiveItemStack() != null) {
					ItemStack itemstack = e.getEntityPlayer().getHeldItemMainhand();
					Item item = itemstack.getItem();
					if (item == ItemInit.TEST_ITEM && item != null) {
						if (MainGPModels.itemWay) {
							IModelsCap modelCap = e.getEntityPlayer().getCapability(ModCapabilities.CAPABILITY_MODELS,
									null);
							if (modelCap != null) {
								Random r = new Random();
								int i = r.nextInt(2);
								System.out.println(i);
								switch (i) {
								case 0:
									modelCap.setModelId("impModel");
									break;
								case 1:
									modelCap.setModelId("humanModel");
									break;
								}
								modelCap.serializeNBT();
								modelCap.sendDataToAllTracking((EntityPlayerMP) e.getEntityPlayer());
								e.getEntityPlayer().sendMessage(new TextComponentString(modelCap.getModelId()));
							}
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onClonePlayer(Clone e) {
		if (e.isWasDeath() && e.getEntityPlayer() instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) e.getEntityPlayer();

			IModelsCap oldStats = e.getOriginal().getCapability(ModCapabilities.CAPABILITY_MODELS, null);
			IModelsCap newStats = player.getCapability(ModCapabilities.CAPABILITY_MODELS, null);
			if (oldStats == null || newStats == null)
				return;
			newStats.deserializeNBT(oldStats.serializeNBT());
			newStats.sendDataTo(player);
		}
	}

}
