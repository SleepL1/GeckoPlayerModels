package sleepx10.gpmodels.client.handler;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import sleepx10.gpmodels.MainGPModels;
import sleepx10.gpmodels.client.models.entities.ModelGeckoPlayer;
import sleepx10.gpmodels.client.models.entities.renderer.GeckoPlayerRenderer;
import sleepx10.gpmodels.common.blocks.BlockInit;
import sleepx10.gpmodels.common.capabilities.IModelsCap;
import sleepx10.gpmodels.common.capabilities.core.ModCapabilities;
import sleepx10.gpmodels.common.entities.GeckoPlayer;
import sleepx10.gpmodels.common.items.ItemInit;

@EventBusSubscriber(Side.CLIENT)
public class ClientEventHandler {

	// List of players that are under GeckoPlayer
	public static HashMap<EntityPlayer, GeckoPlayer> geckoPlayers = new HashMap<>();

	// List of the players models
	public static HashMap<EntityPlayer, ModelGeckoPlayer> geckoPlayerModels = new HashMap<>();

	// List of the players renderer
	public static HashMap<EntityPlayer, GeckoPlayerRenderer<GeckoPlayer>> geckoPlayerRenderers = new HashMap<>();

	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent e) {
		if (MainGPModels.itemWay) {
			for (Item item : ItemInit.ITEMS) {
				MainGPModels.proxy.registerItemRenderer(item, 0, "inventory");
			}
		}

		for (Block block : BlockInit.BLOCKS) {
			MainGPModels.proxy.registerItemRenderer(Item.getItemFromBlock(block), 0, "inventory");
		}
	}

	@SubscribeEvent
	public static void onPlayerRender(RenderPlayerEvent.Pre e) {
		IModelsCap modelsCap = e.getEntityPlayer().getCapability(ModCapabilities.CAPABILITY_MODELS, null);

		if (modelsCap != null) {
			if (!modelsCap.getModelId().equals("")) {
				/**
				 * This look a little bit messy, if you know another way of how to do this feel
				 * free to open a PR!
				 **/
				if (!geckoPlayers.containsKey(e.getEntityPlayer())) {
					geckoPlayers.put(e.getEntityPlayer(), new GeckoPlayer(e.getEntityPlayer()));
				}

				if (!geckoPlayerModels.containsKey(e.getEntityPlayer())) {
					geckoPlayerModels.put(e.getEntityPlayer(), new ModelGeckoPlayer());
				}

				if (!geckoPlayerRenderers.containsKey(e.getEntityPlayer())) {
					geckoPlayerRenderers.put(e.getEntityPlayer(),
							new GeckoPlayerRenderer<>(geckoPlayerModels.get(e.getEntityPlayer())));
				}

				GeckoPlayerRenderer<GeckoPlayer> geckoPlayerRenderer = geckoPlayerRenderers.get(e.getEntityPlayer());
				GeckoPlayer geckoPlayer = geckoPlayers.get(e.getEntityPlayer());
				ModelGeckoPlayer geckoPlayerModel = geckoPlayerModels.get(e.getEntityPlayer());

				if (!geckoPlayerRenderer.getModelsToLoad().containsKey(geckoPlayer.getClass())) {
					geckoPlayerRenderer.getModelsToLoad().put(geckoPlayer.getClass(), geckoPlayerRenderer);
				}

				e.setCanceled(geckoPlayerModel.resourceForModelId(modelsCap.getModelId(), e.getEntityPlayer()));

				if (e.isCanceled())
					// After all the comprobation we can already render the player. Yay!
					geckoPlayerRenderer.renderPlayer(
							geckoPlayerRenderer.getGeoModelProvider()
									.getModel(geckoPlayerRenderer.getGeoModelProvider().getModelLocation(geckoPlayer)),
							geckoPlayer, e.getPartialRenderTick(), 0, 0, 0, 0, e, modelsCap);
			}
		}
	}
}
