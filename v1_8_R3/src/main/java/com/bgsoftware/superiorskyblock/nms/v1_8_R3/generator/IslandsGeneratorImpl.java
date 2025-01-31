package com.bgsoftware.superiorskyblock.nms.v1_8_R3.generator;

import com.bgsoftware.common.reflection.ReflectField;
import com.bgsoftware.superiorskyblock.SuperiorSkyblockPlugin;
import com.bgsoftware.superiorskyblock.world.generator.IslandsGenerator;
import net.minecraft.server.v1_8_R3.BiomeBase;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public class IslandsGeneratorImpl extends IslandsGenerator {

    private static final ReflectField<BiomeBase[]> BIOME_BASE_ARRAY = new ReflectField<>(
            "org.bukkit.craftbukkit.VERSION.generator.CustomChunkGenerator$CustomBiomeGrid", BiomeBase[].class, "biome");

    private final SuperiorSkyblockPlugin plugin;

    public IslandsGeneratorImpl(SuperiorSkyblockPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomeGrid) {
        ChunkData chunkData = createChunkData(world);

        Biome targetBiome;

        switch (world.getEnvironment()) {
            case NETHER: {
                try {
                    targetBiome = Biome.valueOf(plugin.getSettings().getWorlds().getNether().getBiome());
                } catch (IllegalArgumentException error) {
                    targetBiome = Biome.HELL;
                }
                break;
            }
            case THE_END: {
                try {
                    targetBiome = Biome.valueOf(plugin.getSettings().getWorlds().getEnd().getBiome());
                } catch (IllegalArgumentException error) {
                    targetBiome = Biome.SKY;
                }
                break;
            }
            default: {
                try {
                    targetBiome = Biome.valueOf(plugin.getSettings().getWorlds().getNormal().getBiome());
                } catch (IllegalArgumentException error) {
                    targetBiome = Biome.PLAINS;
                }
                break;
            }
        }

        setBiome(biomeGrid, targetBiome);

        if (chunkX == 0 && chunkZ == 0 && world.getEnvironment() == plugin.getSettings().getWorlds().getDefaultWorld()) {
            chunkData.setBlock(0, 99, 0, Material.BEDROCK);
        }

        return chunkData;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return Collections.emptyList();
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 0, 100, 0);
    }

    private static void setBiome(ChunkGenerator.BiomeGrid biomeGrid, Biome biome) {
        BiomeBase biomeBase = CraftBlock.biomeToBiomeBase(biome);

        BiomeBase[] biomeBases = BIOME_BASE_ARRAY.get(biomeGrid);

        if (biomeBases == null)
            return;

        Arrays.fill(biomeBases, biomeBase);
    }

}
