package sleepx10.gpmodels.common.entities;

import java.util.UUID;

import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GeckoPlayer implements IAnimatable {

	private UUID uuid;
	private AnimationFactory factory = new AnimationFactory(this);
	private AnimationController<GeckoPlayer> animationController;

	public GeckoPlayer(UUID uuid) {
		this.uuid = uuid;
	}

	@Override
	public void registerControllers(AnimationData data) {
		AnimationController<GeckoPlayer> animationController = new AnimationController<>(this, "controller", 0, this::predicate);
		this.animationController = animationController;
		
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> e) {
		e.getController().setAnimation(new AnimationBuilder().addAnimation("ARCOSIAN_NOTHING", false));
        if (e.getController().getAnimationState() == AnimationState.Transitioning) {
            return PlayState.CONTINUE;
        }
        
        final Animation current = e.getController().getCurrentAnimation();
        if (current != null && !current.loop && e.getController().getAnimationState() != AnimationState.Stopped) {
            return PlayState.CONTINUE;
        }
		return PlayState.CONTINUE;
	}

	public AnimationController<GeckoPlayer> getAnimationController() {
		return animationController;
	}
}