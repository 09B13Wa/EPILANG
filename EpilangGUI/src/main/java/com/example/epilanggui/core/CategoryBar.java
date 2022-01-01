package com.example.epilanggui.core;

import com.example.logic.LocationFile;
import javafx.scene.shape.SVGPath;

import java.io.*;
import java.util.List;
import java.util.Vector;

public class CategoryBar {
    private List<Category> categoryList;
    private CategoryBarStyle categoryBarStyle;
    private int numberOfCategories;
    private static String categoryListLocationFileItem = "category-bar-list";
    private static String categoryStyleLocationFileItem = "category-bar-style";

    public CategoryBar() {
        categoryList = new Vector<>();
        numberOfCategories = 0;
        categoryBarStyle = CategoryBarStyle.generateDefaultStyle();
    }

    public CategoryBar(LocationFile location) {
        initializeData(location);
    }

    public CategoryBar(String locationFileLocation) {
        initializeData(locationFileLocation);
    }

    public CategoryBar(List<Category> categoryList) {
        this.categoryList = new Vector<>();
        this.categoryList.addAll(categoryList);
        categoryBarStyle = CategoryBarStyle.generateDefaultStyle();
    }

    public CategoryBar(CategoryBarStyle categoryBarStyle) {
        categoryList = new Vector<>();
        this.categoryBarStyle = categoryBarStyle;
    }

    public CategoryBar(List<Category> categoryList, CategoryBarStyle categoryBarStyle) {
        this.categoryList = new Vector<>();
        this.categoryList.addAll(categoryList);
        this.categoryBarStyle = categoryBarStyle;
    }

    private void initializeData(String locationFileLocation) {
        LocationFile locationFile = new LocationFile(locationFileLocation);
        initializeData(locationFile);
    }

    private void initializeData(LocationFile locationFile) {
        String[] locations = parseLocations(locationFile);
        String listLocation = locations[0];
        String styleLocation = locations[1];
        categoryBarStyle = new CategoryBarStyle(styleLocation);
        categoryList = parseCategoryListSource(listLocation);
    }

    private String[] parseLocations(LocationFile locationFile) throws IllegalArgumentException {
        String barListLocation = CategoryBar.categoryListLocationFileItem;
        String barStyle = CategoryBar.categoryStyleLocationFileItem;
        boolean containsCategoryBarList = locationFile.isLocation(barListLocation);
        boolean containsCategoryBarStyle = locationFile.isLocation(barStyle);
        if (containsCategoryBarList && containsCategoryBarStyle) {
            String categoryBarListLocation = locationFile.getLocation(barListLocation);
            String categoryBarStyleLocation = locationFile.getLocation(barStyle);
            String[] locations = {categoryBarListLocation, categoryBarStyleLocation};
            return locations;
        }
        else if(containsCategoryBarList) {
            throw new IllegalArgumentException("Error: the category bar list location wasn't found in the configuration file");
        }
        else {
            throw new IllegalArgumentException("Error: the category bar style location wasn't found in the configuration file");
        }
    }

    private List<Category> parseCategoryListSource(String location) {
        numberOfCategories = 0;
        List<String> lines = readTextFileLines(location);
        List<Category> categoryList = new Vector<>();
        for (String line : lines) {
            String[] segments = line.split(":");
            int numberOfSegments = segments.length;
            if (numberOfSegments != 2) {
                throw new IllegalArgumentException("Error: the number of declared elements for a category must be two in this fashion:\n" +
                        "<name>:<svgPath>");
            }
            else {
                String name = segments[0];
                String svgPathStr = segments[1];
                SVGPath svgPath = new SVGPath();
                svgPath
                Category category = new Category(this, name, svgPath, numberOfCategories);
            }
        }


    }

    private List<String> readTextFileLines(String location) {
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
}
