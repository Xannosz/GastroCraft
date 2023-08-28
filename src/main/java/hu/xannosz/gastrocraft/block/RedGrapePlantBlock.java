package hu.xannosz.gastrocraft.block;

import hu.xannosz.gastrocraft.item.ModItems;
import net.minecraft.world.item.Item;

public class RedGrapePlantBlock extends GrapePlantBlock{
	public RedGrapePlantBlock(Properties properties) {
		super(properties);
	}

	@Override
	public Item getGrape() {
		return ModItems.RED_GRAPE.get();
	}

	@Override
	public Item getSeed() {
		return ModItems.RED_GRAPE_SEED.get();
	}
}
