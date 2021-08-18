package sleepx10.gpmodels.common.network;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import sleepx10.gpmodels.client.handler.ClientEventHandler;
import sleepx10.gpmodels.common.entities.GeckoPlayer;
import software.bernie.geckolib3.core.builder.AnimationBuilder;

public class PacketPlaceBlock implements IMessage {

	private String entityId;
	
	public PacketPlaceBlock() {
	}

	public PacketPlaceBlock(String entityId) {
		this.entityId = entityId;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		entityId = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, entityId);
	}

	public static class Handler implements IMessageHandler<PacketPlaceBlock, IMessage> {

		@Override
		public IMessage onMessage(PacketPlaceBlock message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				@Override
				public void run() {
					handle(message, ctx);
				}
			});
			return null;
		}

		public void handle(PacketPlaceBlock message, MessageContext ctx) {
			GeckoPlayer player = ClientEventHandler.geckoPlayers.get(UUID.fromString(message.entityId));
			player.getAnimationController().setAnimation(new AnimationBuilder().addAnimation("ARCOSIAN_PUTTING_BLOCK_LARM", false));
			player.getAnimationController().markNeedsReload();
		}

	}
}