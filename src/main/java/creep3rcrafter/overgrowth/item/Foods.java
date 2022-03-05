package creep3rcrafter.overgrowth.item;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class Foods {
	public static final Food CHERRY = new Food.Builder().nutrition(1).saturationMod(0.3F).fast().build();
	public static final Food NETHER_CARROT = new Food.Builder().nutrition(2).saturationMod(0.6F).build();
	public static final Food NETHER_BEETROOT = new Food.Builder().nutrition(2).saturationMod(0.6F).build();
	public static final Food NETHER_BEETROOT_SOUP = stew(7);
	public static final Food NETHER_POTATO = new Food.Builder().nutrition(2).saturationMod(0.3F).build();
	public static final Food BAKED_NETHER_POTATO = new Food.Builder().nutrition(6).saturationMod(0.6F).build();
	public static final Food NETHER_POISONOUS_POTATO = new Food.Builder().nutrition(3).saturationMod(0.3F)
			.effect(() -> new EffectInstance(Effects.REGENERATION, 100, 0), 0.6F).build();

	private static Food stew(int nutrition) {
		return (new Food.Builder()).nutrition(nutrition).saturationMod(0.6F).build();
	}
}
