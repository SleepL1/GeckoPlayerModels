package sleepx10.gpmodels.common.capabilities.core;

import java.util.concurrent.Callable;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import sleepx10.gpmodels.common.capabilities.IModelsCap;
import sleepx10.gpmodels.common.capabilities.ModelsCap;

public class ModCapabilities {

	@CapabilityInject(IModelsCap.class)
	public static final Capability<IModelsCap> CAPABILITY_MODELS = null;


	public static void registerCapabilities() {
		CapabilityManager.INSTANCE.register(IModelsCap.class, new Storage<IModelsCap>(), new Callable<IModelsCap>() {
			@Override
			public IModelsCap call() throws Exception {
				return new ModelsCap();
			}
		});
	}
}
