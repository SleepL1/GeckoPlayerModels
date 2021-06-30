package sleepx10.gpmodels.common.entities;

import net.minecraft.entity.player.EntityPlayer;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GeckoPlayer implements IAnimatable {

	private EntityPlayer player;
	private AnimationFactory factory = new AnimationFactory(this);

	public GeckoPlayer(EntityPlayer player) {
		this.player = player;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	/**
	 * Be careful here! When the model swap the animation doesn't stop. If you know
	 * a solution for this feel free to open a PR!
	 **/
	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> e) {
		e.getController().setAnimation(new AnimationBuilder().addAnimation("test"));
		return PlayState.CONTINUE;
	}

	public EntityPlayer getPlayer() {
		return player;
	}
}