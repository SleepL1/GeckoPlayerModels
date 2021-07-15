package sleepx10.gpmodels.client.handler;

import java.util.HashMap;
import java.util.UUID;

import net.minecraft.block.Block;
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
	public static HashMap<UUID, GeckoPlayer> geckoPlayers = new HashMap<>();

	// List of the players models
	public static HashMap<UUID, ModelGeckoPlayer> geckoPlayerModels = new HashMap<>();

	// List of the players renderer
	public static HashMap<UUID, GeckoPlayerRenderer<GeckoPlayer>> geckoPlayerRenderers = new HashMap<>();

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
				if (!geckoPlayers.containsKey(e.getEntityPlayer().getPersistentID())) {
					geckoPlayers.put(e.getEntityPlayer().getPersistentID(), new GeckoPlayer(e.getEntityPlayer().getPersistentID()));
				}

				if (!geckoPlayerModels.containsKey(e.getEntityPlayer().getPersistentID())) {
					geckoPlayerModels.put(e.getEntityPlayer().getPersistentID(), new ModelGeckoPlayer());
				}

				if (!geckoPlayerRenderers.containsKey(e.getEntityPlayer().getPersistentID())) {
					geckoPlayerRenderers.put(e.getEntityPlayer().getPersistentID(),
							new GeckoPlayerRenderer<>(geckoPlayerModels.get(e.getEntityPlayer().getPersistentID())));
				}

				GeckoPlayerRenderer<GeckoPlayer> geckoPlayerRenderer = geckoPlayerRenderers.get(e.getEntityPlayer().getPersistentID());
				GeckoPlayer geckoPlayer = geckoPlayers.get(e.getEntityPlayer().getPersistentID());
				ModelGeckoPlayer geckoPlayerModel = geckoPlayerModels.get(e.getEntityPlayer().getPersistentID());

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
