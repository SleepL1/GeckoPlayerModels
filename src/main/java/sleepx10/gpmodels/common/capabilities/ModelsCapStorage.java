package sleepx10.gpmodels.common.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ModelsCapStorage<T extends IModelsCap> implements IStorage<T> {

	@Override
	public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side) {
		return instance.serializeNBT();
	}

	@Override
	public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt) {
		instance.deserializeNBT((NBTTagCompound) nbt);
	}

}
