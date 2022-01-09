package com.example.epilanggui.core;

import javafx.scene.control.Button;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class Category {
    private String name;
    private SVG icon;
    //TODO: Add link to Category logic
    private int place;
    private CategoryBar categoryBar;
    private Button button;

    public Category(CategoryBar categoryBar, String name, SVG icon, int place) {
        this.categoryBar = categoryBar;
        this.name = name;
        this.icon = icon;
        this.place = place;
    }

    public Category(CategoryBar categoryBar, String categoryData) {
        Category category = parseCategorySource(categoryBar, categoryData);
        this.name = category.name;
        this.categoryBar = category.categoryBar;
        this.icon = category.icon;
        this.place = category.place;
    }

    public CategoryBar getCategoryBar() {
        return categoryBar;
    }

    public String getName() {
        return name;
    }

    public SVG getIcon() {
        return icon;
    }

    public int getPlace() {
        return place;
    }

    private static Category parseCategorySource(CategoryBar categoryBar, String data) {
        String[] segments = data.split(":");
        int numberOfSegments = segments.length;
        if (numberOfSegments != 2) {
            throw new IllegalArgumentException("Error: the number of declared elements for a category must be two in this fashion:\n" +
                    "<name>:<svgPath>");
        }
        else {
            String name = segments[0];
            String svgPathStr = segments[1];
            SVG svg = SVG.load(svgPathStr, "category-%s-icon.svg");
            return new Category(categoryBar, name, svg, categoryBar.getNumberOfCategories());
        }
    }

    public static List<String> readTextFileLines(String location) {
        List<String> lines = new Vector<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    location));
            String line = reader.readLine();
            lines.add(line);
            while (line != null) {
                // read next line
                line = reader.readLine();
                lines.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    private void initializeButton() {
        button = new Button();
        button.setGraphic(icon.getSvg());
    }
}
