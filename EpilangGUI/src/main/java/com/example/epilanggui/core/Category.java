package com.example.epilanggui.core;

import javafx.scene.shape.SVGPath;

public class Category {
    private String name;
    private SVGPath icon;
    //TODO: Add link to Category logic
    private int place;
    private CategoryBar categoryBar;

    public Category(CategoryBar categoryBar, String name, SVGPath icon, int place) {
        this.categoryBar = categoryBar;
        this.name = name;
        this.icon = icon;
        this.place = place;
    }

    public Category(String categoryData) {

    }

    public CategoryBar getCategoryBar() {
        return categoryBar;
    }

    public String getName() {
        return name;
    }

    public SVGPath getIcon() {
        return icon;
    }

    public int getPlace() {
        return place;
    }
}
