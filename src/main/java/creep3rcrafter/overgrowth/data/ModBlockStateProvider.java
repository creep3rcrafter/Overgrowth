package creep3rcrafter.overgrowth.data;

import creep3rcrafter.overgrowth.Overgrowth;
import creep3rcrafter.overgrowth.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

	public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, Overgrowth.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		// simpleBlock(ModBlocks.TEST_BLOCK.get());
		/*
		getVariantBuilder(ModBlocks.NETHER_CARROTS.get()).forAllStates(state -> {
			int i = cropAgeToIndex(state.getValue(NetherCarrotBlock.AGE));
			return ConfiguredModel.builder()
					.modelFile(models().crop("nether_carrots" + i, modLoc("block/nether_carrots" + i))).build();
		});
		*/
		cropBuilder("nether_carrots", ModBlocks.NETHER_CARROTS.get());
		cropBuilder("nether_beetroots", ModBlocks.NETHER_BEETROOTS.get());
		farmlandBuilder("nylium_farmland", ModBlocks.NYLIUM_FARMLAND.get());

	}
	
	public VariantBlockStateBuilder farmlandBuilder(String name, Block block) {
		ModelFile farmlandTemplate = models().getExistingFile(mcLoc("block/template_farmland"));
		return getVariantBuilder(block).forAllStates(state -> {			
			return ConfiguredModel.builder().modelFile(models().getBuilder(name).parent(farmlandTemplate)
					.texture("dirt", modLoc("block/"+ name +"_other"))
					.texture("top", modLoc("block/"+ name +"_top")))
					.build();
		});
	}
	public VariantBlockStateBuilder cropBuilder(String name, Block block) {
		CropsBlock cropsBlock = (CropsBlock) block;
		return getVariantBuilder(block).forAllStates(state -> {
			int i = cropAgeToIndex(state.getValue(cropsBlock.AGE));
			return ConfiguredModel.builder()
					.modelFile(models().crop(name + i, modLoc("block/"+ name + i)))
					.build();
		});
	}

	private int cropAgeToIndex(int age) {
		if (age > 6) {
			return 3;
		} else if (age > 3) {
			return 2;
		} else if (age > 1) {
			return 1;
		} else {
			return 0;
		}
	}

}
