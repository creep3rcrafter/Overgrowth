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
		//withExistingParent("test_block", modLoc("block/test_block"));
		ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
		itembuilder(itemGenerated, "cherry");
		itembuilder(itemGenerated, "nether_carrot");
		itembuilder(itemGenerated, "nether_beetroot");
	}
	private ItemModelBuilder itembuilder(ModelFile itemGenerated, String name) {
		return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
	}
}
