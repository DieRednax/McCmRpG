package com.redfox.mccmrpg;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.redfox.mccmrpg.types.CustomModel;
import com.redfox.mccmrpg.types.Override;
import com.redfox.mccmrpg.types.Overrider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Modifier {
    private Path packPath;
    private String nameSpace;
    private CustomModel customModel;

    public Modifier(Path packPath, String nameSpace, CustomModel customModel) {
        this.packPath = packPath;
        this.nameSpace = nameSpace;
        this.customModel = customModel;
    }
    public Modifier(Path packPath, String packName, String nameSpace, CustomModel customModel) {
        this.packPath = packPath.resolve(packName);
        this.nameSpace = nameSpace;
        this.customModel = customModel;
    }

    public void add() throws IOException {
        //textures
        final Path texturePath = packPath.resolve(String.format("assets/minecraft/textures/%s/custom/%s.png",
                        customModel.getType(),
                        customModel.getIdentifier()));
        Files.createDirectories(texturePath.getParent());
        Files.copy(
                customModel.getTexturePath(),
                texturePath);

        //model
        final Path modelPath = packPath.resolve(
                String.format("assets/%s/models/item/%s.json", nameSpace, customModel.getIdentifier())
        );
        Files.createDirectories(modelPath.getParent());
        Files.copy(
                customModel.getModelPath(),
                packPath.resolve(
                        String.format("assets/%s/models/item/%s.json", nameSpace, customModel.getIdentifier())
                ));

        //model reference
        final Path modelFilePath = packPath.resolve(String.format("assets/%s/items/%s.json", nameSpace, customModel.getIdentifier()));
        Files.createDirectories(modelFilePath.getParent());
        Files.writeString(
                modelFilePath,
                String.format("""
                        {
                            "model": {
                                "type": "minecraft:model",
                                "model": "%s:item/%s"
                            }
                        }
                        """, nameSpace, customModel.getIdentifier()
                ));

        //overrider
        final Path overriderPath = packPath.resolve(String.format("assets/%s/models/item/%s.json", nameSpace, customModel.getOverrider()));
        if (!Files.exists(overriderPath)) {
            Files.createDirectories(overriderPath.getParent());
            Files.writeString(
                    overriderPath,
                    String.format("""
                            {
                                "parent": "minecraft:%s/%s",
                               "overrides": [
                                    {"predicate":{"custom_model_data":1}, "model": "custom/%s"}
                               ]
                            }
                            """, customModel.getOverriderType().toString(), customModel.getOverrider(), customModel.getIdentifier())
            );
        } else {
            String prevContent = Files.readString(overriderPath);
            Gson gson = new Gson();
            Overrider prevContentOverrider = gson.fromJson(prevContent, Overrider.class);
            List<Override> newOverrides = new ArrayList<>();
            int i = 1;

            for (Override override : prevContentOverrider.getOverrides()) {
                newOverrides.add(override);

                i++;
            }
            newOverrides.add(new Override(Map.of("custom_model_data", i), "custom/" + customModel.getIdentifier()));
            Overrider newContentOverrider = new Overrider(prevContentOverrider.getParent(),newOverrides);
            String content = new GsonBuilder().setPrettyPrinting().create().toJson(newContentOverrider);
            Files.writeString(overriderPath, content);
        }
    }
    void remove() throws IOException {
        //texture
        Files.deleteIfExists(packPath.resolve(String.format("assets/minecraft/textures/%s/custom/%s.png",
                customModel.getType(),
                customModel.getIdentifier())));

        //model
        Files.deleteIfExists(packPath.resolve(
                String.format("assets/%s/models/item/%s.json", nameSpace, customModel.getIdentifier())
        ));

        //model reference
        Files.deleteIfExists(packPath.resolve(
                String.format("assets/%s/items/%s.json", nameSpace, customModel.getIdentifier())
        ));

        //overrider
        final Path overriderPath = packPath.resolve(String.format("assets/%s/models/item/%s.json", nameSpace, customModel.getOverrider()));
        if (!Files.exists(overriderPath)) {
            return;
        }
        String prevContent = Files.readString(overriderPath);
        Gson gson = new Gson();
        Overrider prevContentOverrider = gson.fromJson(prevContent, Overrider.class);

        if (prevContentOverrider.getOverrides().size() < 2) {
            Files.delete(overriderPath);
        } else {
            List<Override> newOverrides = new ArrayList<>();
            int i = 1;

            for (Override override : prevContentOverrider.getOverrides()) {
                if (!override.getModel().equals("custom/" + customModel.getIdentifier())) {
                    newOverrides.add(new Override(Map.of("custom_model_data", i), override.getModel()));

                    i++;
                }
            }

            Overrider newContentOverrider = new Overrider(prevContentOverrider.getParent(), newOverrides);
            String content = new GsonBuilder().setPrettyPrinting().create().toJson(newContentOverrider);
            Files.writeString(overriderPath, content);
        }
    }
}
