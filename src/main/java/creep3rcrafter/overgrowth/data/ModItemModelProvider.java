package creep3rcrafter.overgrowth.data;

import creep3rcrafter.overgrowth.Overgrowth;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Overgrowth.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        itembuilder("nether_carrot");
        itembuilder("nether_beetroot_seeds");
        itembuilder("nether_beetroot");
        itembuilder("nether_potato");
        itembuilder("baked_nether_potato");
        itembuilder("nether_poisonous_potato");
        itembuilder("sulfur_cane");
        itembuilder("soul_paper");
        itembuilder("dark_paper");
        itembuilder("soul_book");
        itembuilder("sulfur");
    }

    public static String name(Block block) {
        return block.getRegistryName().getPath();
    }

    public static ResourceLocation itemTexture(Block block) {
        ResourceLocation name = block.getRegistryName();
        return new ResourceLocation(name.getNamespace(), ModelProvider.ITEM_FOLDER + "/" + name.getPath());
    }

    private void itembuilder(String name) {
        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
        getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }

    private void itembuilder(Block block) {
        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
        getBuilder(block.getRegistryName().toString()).parent(itemGenerated).texture("layer0", "item/" + block.getRegistryName().toString());
    }


}
