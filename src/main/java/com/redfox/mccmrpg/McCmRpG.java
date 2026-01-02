package com.redfox.mccmrpg;

import com.redfox.mccmrpg.types.CustomModel;

import java.io.IOException;
import java.nio.file.Path;

public class McCmRpG {
    public static void main(String[] args) {
        try {
//            new Generator(
//                    Path.of("C:\\Users\\xtdel\\IdeaProjects\\McCmRpG\\app_tests"),
//                    "TestPack", "This is a test pack",
//                    "test_pack", List.of(
//                            new CustomModel(
//                                    CustomModel.ModelType.BLOCK, "carved_pumpkin",
//                                    CustomModel.ModelType.BLOCK, "christmas_hat",
//                                    Path.of("C:\\Users\\xtdel\\IdeaProjects\\McCmRpG\\app_tests\\models\\christmas_hat.json"),
//                                    Path.of("C:\\Users\\xtdel\\IdeaProjects\\McCmRpG\\app_tests\\textures\\christmas_hat.png")
//                            )
//                    )
//            ).generate();
            new Modifier(
                    Path.of("C:\\Users\\xtdel\\IdeaProjects\\McCmRpG\\app_tests"),
                    "TestPack", "test_pack",
                    new CustomModel(
                            CustomModel.ModelType.ITEM, "emerald",
                            CustomModel.ModelType.ITEM, "emerald_biotic",
                            Path.of("C:\\Users\\xtdel\\IdeaProjects\\McCmRpG\\app_tests\\textures\\emerald_coin.png")
                    )
            ).add();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
