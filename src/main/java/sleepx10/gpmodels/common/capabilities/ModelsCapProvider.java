package sleepx10.gpmodels.common.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import sleepx10.gpmodels.common.capabilities.core.ModCapabilities;

public class ModelsCapProvider implements ICapabilitySerializable<NBTTagCompound> {

	private IModelsCap modelsCap;

	public ModelsCapProvider() {
		modelsCap = new ModelsCap();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == ModCapabilities.CAPABILITY_MODELS;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return hasCapability(capability, facing) ? (T) modelsCap : null;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		return modelsCap.serializeNBT();
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		modelsCap.deserializeNBT(nbt);
	}

}
