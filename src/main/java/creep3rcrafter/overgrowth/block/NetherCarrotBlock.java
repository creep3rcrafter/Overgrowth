package creep3rcrafter.overgrowth.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.CarrotBlock;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class NetherCarrotBlock extends CarrotBlock {

	public NetherCarrotBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected IItemProvider getBaseSeedId() {
		return Items.CARROT;
	}

	@Override
	protected boolean mayPlaceOn(BlockState blockState, IBlockReader blockReader, BlockPos blockPos) {
		return blockState.is(ModBlocks.NYLIUM_FARMLAND.get());
	}

}
