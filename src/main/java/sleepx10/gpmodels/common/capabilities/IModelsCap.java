package sleepx10.gpmodels.common.capabilities;

import sleepx10.gpmodels.common.capabilities.core.ICapability;

public interface IModelsCap extends ICapability {
	
	void setModelId(String modelId);
	String getModelId();
}
