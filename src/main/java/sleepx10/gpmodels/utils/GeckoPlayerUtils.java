package sleepx10.gpmodels.utils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class GeckoPlayerUtils {

	public static void isElytraFlying(boolean fly, RenderPlayerEvent e, float renderYawOffset) {
		if (e.getEntityPlayer().isElytraFlying()) {
			GlStateManager.rotate(180.0F - e.getEntityPlayer().rotationYaw, 0.0F, 1.0F, 0.0F);
			float f = (float) e.getEntityPlayer().getTicksElytraFlying() + e.getPartialRenderTick();
			float f1 = MathHelper.clamp(f * f / 100.0F, 0.0F, 1.0F);
			GlStateManager.rotate(f1 * (-90.0F - e.getEntityPlayer().rotationPitch), 1.0F, 0.0F, 0.0F);
			Vec3d vec3d = e.getEntityPlayer().getLook(e.getPartialRenderTick());
			double d0 = e.getEntityPlayer().motionX * e.getEntityPlayer().motionX
					+ e.getEntityPlayer().motionZ * e.getEntityPlayer().motionZ;
			double d1 = vec3d.x * vec3d.x + vec3d.z * vec3d.z;

			if (d0 > 0.0D && d1 > 0.0D) {
				double d2 = (e.getEntityPlayer().motionX * vec3d.x + e.getEntityPlayer().motionZ * vec3d.z)
						/ (Math.sqrt(d0) * Math.sqrt(d1));
				double d3 = e.getEntityPlayer().motionX * vec3d.z - e.getEntityPlayer().motionZ * vec3d.x;
				GlStateManager.rotate((float) (Math.signum(d3) * Math.acos(d2)) * 180.0F / (float) Math.PI, 0.0F, 1.0F,
						0.0F);
			}
		} else {
			GlStateManager.rotate(-renderYawOffset + 180, 0, 1, 0);
		}
	}

	public static float getSwingProgress(EntityLivingBase livingBase, float partialTickTime) {
		return livingBase.getSwingProgress(partialTickTime);
	}

	public static float interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks) {
		float f;

		for (f = yawOffset - prevYawOffset; f < -180.0F; f += 360.0F) {
			;
		}

		while (f >= 180.0F) {
			f -= 360.0F;
		}

		return prevYawOffset + partialTicks * f;
	}

	public static float handleRotationFloat(EntityLivingBase livingBase, float partialTicks) {
		return (float) livingBase.ticksExisted + partialTicks;
	}

	public static float prepareScale(EntityLivingBase entitylivingbaseIn, float partialTicks) {
		return 0.0625F;
	}

	public static void applyRotations(EntityLivingBase entityLiving, float ageInTicks, float rotationYaw,
			float partialTicks) {

		if (entityLiving.deathTime > 0) {
			float f = ((float) entityLiving.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
			f = MathHelper.sqrt(f);

			if (f > 1.0F) {
				f = 1.0F;
			}

			GlStateManager.rotate(f * 90.0F, 0.0F, 0.0F, 1.0F);
		}
	}

	public static void checkHeadRotations(RenderPlayerEvent e) {
		try {
			e.getRenderer().getMainModel().swingProgress = getSwingProgress(e.getEntityPlayer(),
					e.getPartialRenderTick());

			float x = interpolateRotation(e.getEntityPlayer().prevRenderYawOffset, e.getEntityPlayer().renderYawOffset,
					e.getPartialRenderTick());
			float x1 = interpolateRotation(e.getEntityPlayer().prevRotationYawHead, e.getEntityPlayer().rotationYawHead,
					e.getPartialRenderTick());
			float x2 = x1 - x;

			float x7 = e.getEntityPlayer().prevRotationPitch
					+ (e.getEntityPlayer().rotationPitch - e.getEntityPlayer().prevRotationPitch)
							* e.getPartialRenderTick();
			GlStateManager.translate(e.getX(), e.getY(), e.getZ());
			float x8 = handleRotationFloat(e.getEntityPlayer(), e.getPartialRenderTick());

			applyRotations(e.getEntityPlayer(), x8, x, e.getPartialRenderTick());
			float x4 = prepareScale(e.getEntityPlayer(), e.getPartialRenderTick());
			float x5 = 0.0F;
			float x6 = 0.0F;

			x5 = e.getEntityPlayer().prevLimbSwingAmount
					+ (e.getEntityPlayer().limbSwingAmount - e.getEntityPlayer().prevLimbSwingAmount)
							* e.getPartialRenderTick();
			x6 = e.getEntityPlayer().limbSwing
					- e.getEntityPlayer().limbSwingAmount * (1.0F - e.getPartialRenderTick());

			if (x5 > 1.0F) {
				x5 = 1.0F;
			}
			x2 = x1 - x;

			e.getRenderer().getMainModel().setLivingAnimations(e.getEntityPlayer(), x6, x5, e.getPartialRenderTick());
			e.getRenderer().getMainModel().setRotationAngles(x6, x5, x8, x2, x7, x4, e.getEntityPlayer());
		} catch (Exception ex) {
			System.out.println("Cannot render the entity.");
		}
	}

}