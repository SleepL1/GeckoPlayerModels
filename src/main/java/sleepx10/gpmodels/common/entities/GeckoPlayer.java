package sleepx10.gpmodels.common.entities;

import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import sleepx10.gpmodels.common.capabilities.IModelsCap;
import sleepx10.gpmodels.common.capabilities.core.ModCapabilities;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GeckoPlayer implements IAnimatable {

	private UUID uuid;
	private AnimationFactory factory = new AnimationFactory(this);

	public GeckoPlayer(UUID uuid) {
		this.uuid = uuid;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> e) {
		EntityPlayer player = Minecraft.getMinecraft().world.getPlayerEntityByUUID(uuid);
		if (player != null) {
			IModelsCap modelCap = player.getCapability(ModCapabilities.CAPABILITY_MODELS, null);
			if (modelCap != null) {
				switch (modelCap.getModelId()) {
				case "impModel":
					e.getController().setAnimation(new AnimationBuilder().addAnimation("attacking"));
					break;
				case "humanModel":
					e.getController().setAnimation(new AnimationBuilder().addAnimation("HUMAN_WALKING2"));
					break;
				default:
					System.out.println("Could not find any animation for the modelId:" + modelCap.getModelId());
				}
			}
		}
		return PlayState.CONTINUE;
	}
}