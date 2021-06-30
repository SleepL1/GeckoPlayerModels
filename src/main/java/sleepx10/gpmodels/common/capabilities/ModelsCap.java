package sleepx10.gpmodels.common.capabilities;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import sleepx10.gpmodels.MainGPModels;
import sleepx10.gpmodels.common.capabilities.network.ModelsCapMeesage;
import sleepx10.gpmodels.utils.Reference;

public class ModelsCap implements IModelsCap {
	
	private final ResourceLocation KEY = new ResourceLocation(Reference.MOD_ID, "ModelsCap");
	
	private String modelId = "";


	@Override
	public ResourceLocation getKey() {
		return KEY;
	}

	@Override
	public ICapabilityProvider getProvider() {
		return new ModelsCapProvider();
	}

	@Override
	public void sendDataTo(EntityPlayerMP player) {
		MainGPModels.NETWORK.sendTo(new ModelsCapMeesage(modelId, player.getEntityId()),
				player);
	}

	@Override
	public void sendDataToAllTracking(EntityPlayerMP player) {
		MainGPModels.NETWORK.sendToAllTracking(new ModelsCapMeesage(modelId, player.getEntityId()), player);
		sendDataTo(player);
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound props = new NBTTagCompound();
		props.setString("MODEL_ID", modelId);
		return props;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		this.modelId = nbt.getString("MODEL_ID");
	}

	@Override
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	@Override
	public String getModelId() {
		return modelId;
	}

}
