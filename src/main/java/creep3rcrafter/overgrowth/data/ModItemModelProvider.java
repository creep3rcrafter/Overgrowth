package creep3rcrafter.overgrowth.data;

import creep3rcrafter.overgrowth.Overgrowth;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider{
	public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Overgrowth.MOD_ID, existingFileHelper);
	}
	@Override
	protected void registerModels() {
		itembuilder("nether_carrot");
		itembuilder("nether_beetroot_seeds");
		itembuilder("nether_beetroot");
		itembuilder("nether_potato");
		itembuilder("nether_poisonous_potato");
		itembuilder("baked_nether_potato");
		itembuilder("soul_paper");
		itembuilder("sulfur_cane");

		withExistingParent("nylium_farmland", modLoc("block/nylium_farmland"));
	}
	private ItemModelBuilder itembuilder(String name) {
		ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
		return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
	}
}
