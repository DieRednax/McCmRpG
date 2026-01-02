package com.redfox.mccmrpg;

import com.redfox.mccmrpg.types.CustomModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Generator {
    private Path generatePath;
    private String description;
    private String nameSpace;
    List<CustomModel> models;

    public Generator(Path generatePath, String description, String namespace, List<CustomModel> models) {
        this.generatePath = generatePath;
        this.description = description;
        this.nameSpace = namespace;
        this.models = models;
    }
    public Generator(Path generatePath, String name, String description, String namespace, List<CustomModel> models) {
        this.generatePath = generatePath.resolve(name);
        this.description = description;
        this.nameSpace = namespace;
        this.models = models;
    }

    public void generate() throws IOException {
        final Path mcmeta = this.generatePath.resolve("pack.mcmeta");
        Files.createDirectories(mcmeta.getParent());
        Files.writeString(mcmeta, String.format("""
                {
                    "pack": {
                        "pack_format": 46,
                        "description": %s
                    }
                }
                """, description));

        for (CustomModel model : models) {
            new Modifier(generatePath, nameSpace, model).add();
        }
    }
}
