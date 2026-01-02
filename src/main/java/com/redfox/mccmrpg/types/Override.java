package com.redfox.mccmrpg.types;

import java.util.Map;

public class Override {
    private Map<String, Integer> predicate;
    private String model;

    public Override(Map<String, Integer> predicate, String model) {
        this.predicate = predicate;
        this.model = model;
    }

    public Map<String, Integer> getPredicate() { return this.predicate; }
    public String getModel() { return this.model; }
}
