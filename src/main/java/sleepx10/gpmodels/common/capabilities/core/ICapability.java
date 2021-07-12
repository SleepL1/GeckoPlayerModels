package sleepx10.gpmodels.common.capabilities.core;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public interface ICapability extends INBTSerializable<NBTTagCompound> {

	ResourceLocation getKey();

	ICapabilityProvider getProvider();

	void sendDataTo(EntityPlayerMP player);
	
	void sendDataToAllTracking(EntityPlayerMP player);
	

}