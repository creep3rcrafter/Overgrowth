package creep3rcrafter.overgrowth.block;

import creep3rcrafter.overgrowth.register.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class NetherCropBlock extends CropsBlock {
	private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[] { Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D) };
	public Item item;

	public NetherCropBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected IItemProvider getBaseSeedId() {
		//return ModItems.NETHER_CARROT.get();
		return item;
	}

	@Override
	@SuppressWarnings("deprecation")
	public Item asItem() {
		if (this.item == null) {
			this.item = Item.byBlock(this);
		}
		return this.item.delegate.get(); // Forge: Vanilla caches the items, update with registry replacements.
	}

	@Override
	public boolean mayPlaceOn(BlockState blockState, IBlockReader blockReader, BlockPos blockPos) {
		return blockState.is(ModBlocks.NYLIUM_FARMLAND.get());
	}

	@Override
	public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_,
			ISelectionContext p_220053_4_) {
		return SHAPE_BY_AGE[p_220053_1_.getValue(this.getAgeProperty())];
	}
}
