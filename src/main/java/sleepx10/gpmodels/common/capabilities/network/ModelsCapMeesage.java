package sleepx10.gpmodels.common.capabilities.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import sleepx10.gpmodels.common.capabilities.IModelsCap;
import sleepx10.gpmodels.common.capabilities.core.ModCapabilities;

public class ModelsCapMeesage implements IMessage {

	private String modelId;
	private int entityId;


	public ModelsCapMeesage() {}

	public ModelsCapMeesage(String modelId, int entityId) {
		this.modelId = modelId;
		this.entityId = entityId;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		modelId = ByteBufUtils.readUTF8String(buf);
		entityId = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {	
		ByteBufUtils.writeUTF8String(buf, modelId);
		buf.writeInt(entityId);
	}

	public static class Handler implements IMessageHandler<ModelsCapMeesage, IMessage> {

		@Override
		public IMessage onMessage(ModelsCapMeesage message, MessageContext ctx) {
			IThreadListener mainThread = Minecraft.getMinecraft();
			mainThread.addScheduledTask(new Runnable() {
				@Override
				public void run() {
					Entity player = Minecraft.getMinecraft().world.getEntityByID(message.entityId);
					if (player == null) {
						System.out.println("Cannot found the entity.");
					} else {
						IModelsCap modelsCap = player.getCapability(ModCapabilities.CAPABILITY_MODELS, null);
						if (modelsCap != null) {
							modelsCap.setModelId(message.modelId);
						}
					}
				}
			});
			return null;
		}

	}
}