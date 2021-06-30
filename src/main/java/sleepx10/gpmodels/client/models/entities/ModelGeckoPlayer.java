package sleepx10.gpmodels.client.models.entities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import sleepx10.gpmodels.client.handler.ClientEventHandler;
import sleepx10.gpmodels.common.entities.GeckoPlayer;
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
	
	public void setAnimationFileLocation(ResourceLocation animationFileLocation) {
		this.animationFileLocation = animationFileLocation;
	}
	
	public void setModelLocation(ResourceLocation modelLocation) {
		this.modelLocation = modelLocation;
	}
	
	public void setTextureLocation(ResourceLocation textureLocation) {
		this.textureLocation = textureLocation;
	}
	
	/** Check if the modelId has some ResourceLocation **/
	public boolean resourceForModelId(String modelId, EntityPlayer player) {
		switch(modelId) {
		case "test":
			setAnimationFileLocation(new ResourceLocation(""));
			setAnimationFileLocation(new ResourceLocation(""));
			setAnimationFileLocation(new ResourceLocation(""));
			return true;
		case "test2":
			return true;
		default:
			System.out.println("Could not find any ResourceLocation for the modelId: "+modelId);
			ClientEventHandler.geckoPlayers.remove(player);
			ClientEventHandler.geckoPlayerModels.remove(player);
			ClientEventHandler.geckoPlayerRenderers.remove(player);
			return false;
		}
	}
}