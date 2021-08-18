package sleepx10.gpmodels.client.models.entities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import sleepx10.gpmodels.client.handler.ClientEventHandler;
import sleepx10.gpmodels.common.entities.GeckoPlayer;
import sleepx10.gpmodels.utils.Reference;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelGeckoPlayer extends AnimatedGeoModel<GeckoPlayer> {
	
	private ResourceLocation animationFileLocation;
	private ResourceLocation modelLocation;
	private ResourceLocation textureLocation;
	
	@Override
	public ResourceLocation getAnimationFileLocation(GeckoPlayer animatable) {
		return animationFileLocation;
	}

	@Override
	public ResourceLocation getModelLocation(GeckoPlayer animatable) {
		return modelLocation;
	}

	@Override
	public ResourceLocation getTextureLocation(GeckoPlayer animatable) {
		return textureLocation;
	}
	
	/** Check if the modelId has some ResourceLocation **/
	public boolean resourceForModelId(String modelId, EntityPlayer player) {
		switch(modelId) {
		case "impModel":
			this.animationFileLocation = new ResourceLocation(Reference.MOD_ID, "animations/player.imp.animation.json");
			this.modelLocation = new ResourceLocation(Reference.MOD_ID, "geo/models/player.impmodel.geo.json");
			this.textureLocation = new ResourceLocation(Reference.MOD_ID, "textures/entities/player/player.imp.texture.png");
			return true;
		case "humanModel":
			this.animationFileLocation = new ResourceLocation(Reference.MOD_ID, "animations/player.human.animation.json");
			this.modelLocation = new ResourceLocation(Reference.MOD_ID, "geo/models/player.humanmodel.geo.json");
			this.textureLocation = new ResourceLocation(Reference.MOD_ID, "textures/entities/player/player.human.texture.png");
			return true;
		case "arcosianModel":
			this.animationFileLocation = new ResourceLocation(Reference.MOD_ID, "animations/player.arcosian.animation.json");
			this.modelLocation = new ResourceLocation(Reference.MOD_ID, "geo/models/player.arcosian.geo.json");
			this.textureLocation = new ResourceLocation(Reference.MOD_ID, "textures/entities/player/player.arcosian.texture.png");
			return true;
		default:
			System.out.println("Could not find any ResourceLocation for the modelId: "+modelId);
			ClientEventHandler.geckoPlayers.remove(player.getPersistentID());
			ClientEventHandler.geckoPlayerModels.remove(player.getPersistentID());
			ClientEventHandler.geckoPlayerRenderers.remove(player.getPersistentID());
			return false;
		}
	}
}