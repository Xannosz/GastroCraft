package hu.xannosz.gastrocraft.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import hu.xannosz.gastrocraft.item.ModItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class GrapeModifier extends LootModifier {

	public static final Codec<GrapeModifier> CODEC =
			RecordCodecBuilder.create(inst -> LootModifier.codecStart(inst).
					apply(inst, GrapeModifier::new));

	protected GrapeModifier(LootItemCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		float chance = context.getRandom().nextFloat();
		if (chance >= 0.85f) {
			generatedLoot.add(new ItemStack(ModItems.WHITE_GRAPE_SEED.get(), 1));
		}
		if (0.85f > chance && chance >= 0.7f) {
			generatedLoot.add(new ItemStack(ModItems.RED_GRAPE_SEED.get(), 1));
		}

		return generatedLoot;
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC;
	}
}
