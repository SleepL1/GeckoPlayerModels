package sleepx10.gpmodels.common.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockInit {

	public static final List<Block> BLOCKS = new ArrayList<Block>();

	public static final Block TEST_BLOCK = new BlockBase("test_block", Material.IRON, 10.0f, 10.0f, 2, "pickaxe");

}
