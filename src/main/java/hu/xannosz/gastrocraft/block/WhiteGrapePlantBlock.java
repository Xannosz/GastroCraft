package hu.xannosz.gastrocraft.block;

import hu.xannosz.gastrocraft.item.ModItems;
import net.minecraft.world.item.Item;

public class WhiteGrapePlantBlock extends GrapePlantBlock{
	public WhiteGrapePlantBlock(Properties properties) {
		super(properties);
	}

	@Override
	public Item getGrape() {
		return ModItems.WHITE_GRAPE.get();
	}

	@Override
	public Item getSeed() {
		return ModItems.WHITE_GRAPE_SEED.get();
	}
}
