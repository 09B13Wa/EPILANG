package com.example.epilanggui.core;

import com.example.logic.LocationFile;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

public class CategoryBar {
    private List<Category> categoryList;
    private List<String> categoryNamesList;
    private Dictionary<Category, Integer> categoryIndexes;
    private Dictionary<String, Integer> categoryNameIndexes;
    private CategoryBarStyle categoryBarStyle;
    private int numberOfCategories;
    private static final String categoryListLocationFileItem = "category-bar-list";
    private static final String categoryStyleLocationFileItem = "category-bar-style";
    private int selectedItem;

    public CategoryBar() {
        categoryList = new Vector<>();
        numberOfCategories = 0;
        categoryBarStyle = CategoryBarStyle.generateDefaultStyle();
        selectedItem = -1;
    }

    public CategoryBar(LocationFile location) {
        initializeData(location);
        selectedItem = -1;
    }

    public CategoryBar(String locationFileLocation) {
        initializeData(locationFileLocation);
        selectedItem = -1;
    }

    public CategoryBar(List<Category> categoryList) {
        this.categoryList = new Vector<>();
        this.categoryList.addAll(categoryList);
        categoryBarStyle = CategoryBarStyle.generateDefaultStyle();
        selectedItem = -1;
    }

    public CategoryBar(CategoryBarStyle categoryBarStyle) {
        categoryList = new Vector<>();
        this.categoryBarStyle = categoryBarStyle;
        selectedItem = -1;
    }

    public CategoryBar(List<Category> categoryList, CategoryBarStyle categoryBarStyle) {
        this.categoryList = new Vector<>();
        this.categoryList.addAll(categoryList);
        this.categoryBarStyle = categoryBarStyle;
        selectedItem = -1;
    }

    public int getNumberOfCategories() {
        return numberOfCategories;
    }

    public List<Category> getCategoryListClone() {
        List<Category> copy = new Vector<>(categoryList);
        copy.addAll(categoryList);
        return copy;
    }

    public CategoryBarStyle getCategoryBarStyle() {
        return categoryBarStyle;
    }

    public int getIndexOf(Category category) {
        return categoryIndexes.get(category);
    }

    public int getIndexOf(String categoryName) {
        return categoryNameIndexes.get(categoryName);
    }

    public Category getCategoryAt(int index) {
        return categoryList.get(index);
    }

    public String getCategoryNameAt(int index) {
        return categoryNamesList.get(index);
    }

    public boolean moveLeft(Category category) {
        return moveLeft(category, true);
    }

    public boolean moveLeft(Category category, boolean up) {
        return up ? moveUp(category) : moveDown(category);
    }

    public boolean moveLeft(int index) {
        return moveLeft(index, true);
    }

    public boolean moveLeft(int index, boolean up) {
        return up ? moveUp(index) : moveDown(index);
    }

    public boolean moveLeft(String categoryName) {
        return moveLeft(categoryName, true);
    }

    public boolean moveLeft(String categoryName, boolean up) {
        return up ? moveUp(categoryName) : moveDown(categoryName);
    }

    public boolean moveUp(Category category) {
        int index = getIndexOf(category);
        return swap(index, index - 1);
    }

    public boolean moveUp(int index) {
        return swap(index, index - 1);
    }

    public boolean moveUp(String categoryName) {
        int index = getIndexOf(categoryName);
        return swap(index, index - 1);
    }

    public boolean swap(Category firstCategory, Category secondCategory) {
        int firstCategoryIndex = getIndexOf(firstCategory);
        int secondCategoryIndex = getIndexOf(secondCategory);
        return swap(firstCategoryIndex, secondCategoryIndex);
    }

    public boolean swap(Category category, int index) {
        int categoryIndex = getIndexOf(category);
        return swap(categoryIndex, index);
    }

    public boolean swap(String firstCategoryName, Category secondCategory) {
        int firstCategoryNameIndex = getIndexOf(firstCategoryName);
        int secondCategoryIndex = getIndexOf(secondCategory);
        return swap(firstCategoryNameIndex, secondCategoryIndex);
    }

    public boolean swap(String categoryName, int index) {
        int categoryIndexName = getIndexOf(categoryName);
        return swap(categoryIndexName, index);
    }

    public boolean swap(int firstIndex, int secondIndex) {
        if (firstIndex < numberOfCategories && secondIndex < numberOfCategories) {
            Category firstIndexCategory = getCategoryAt(firstIndex);
            Category secondIndexCategory = getCategoryAt(secondIndex);
            String firstIndexStr = getCategoryNameAt(firstIndex);
            String secondIndexStr = getCategoryNameAt(secondIndex);

            categoryList.set(secondIndex, firstIndexCategory);
            categoryList.set(firstIndex, secondIndexCategory);
            categoryNamesList.set(secondIndex, firstIndexStr);
            categoryNamesList.set(firstIndex, secondIndexStr);
            categoryIndexes.put(firstIndexCategory, secondIndex);
            categoryIndexes.put(secondIndexCategory, firstIndex);
            categoryNameIndexes.put(firstIndexStr, secondIndex);
            categoryNameIndexes.put(secondIndexStr, firstIndex);
            return true;
        }
        return false;
    }

    public boolean swap(int index, Category category) {
        int categoryIndex = getIndexOf(category);
        return swap(index, categoryIndex);
    }

    public boolean swap(Category firstCategory, String secondCategoryName) {
        int firstCategoryIndex = getIndexOf(firstCategory);
        int secondCategoryNameIndex = getIndexOf(secondCategoryName);
        return swap(firstCategoryIndex, secondCategoryNameIndex);
    }

    public boolean swap(int index, String categoryName) {
        int categoryIndexName = getIndexOf(categoryName);
        return swap(index, categoryIndexName);
    }

    public boolean swap(String firstCategoryName, String secondCategoryName) {
        int firstCategoryNameIndex = getIndexOf(firstCategoryName);
        int secondCategoryNameIndex = getIndexOf(secondCategoryName);
        return swap(firstCategoryNameIndex, secondCategoryNameIndex);
    }

    public boolean moveDown(Category category) {
        int index = getIndexOf(category);
        return swap(index, index + 1);
    }

    public boolean moveDown(int index) {
        return swap(index, index + 1);
    }

    public boolean moveDown(String categoryName) {
        int index = getIndexOf(categoryName);
        return swap(index, index + 1);
    }

    public boolean moveRight(Category category) {
        return moveRight(category, true);
    }

    public boolean moveRight(Category category, boolean down) {
        return down ? moveDown(category) : moveUp(category);
    }

    public boolean moveRight(int index) {
        return moveRight(index, true);
    }

    public boolean moveRight(int index, boolean down) {
        return down ? moveDown(index) : moveUp(index);
    }

    public boolean moveRight(String categoryName) {
        return moveRight(categoryName, true);
    }

    public boolean moveRight(String categoryName, boolean down) {
        return down ? moveDown(categoryName) : moveUp(categoryName);
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
        categoryNamesList = computeCategoryListNames(categoryList);
        categoryNameIndexes = computeCategoryNameMap(categoryList);
        categoryIndexes = computeCategoryMap(categoryList);
    }

    private String[] parseLocations(LocationFile locationFile) throws IllegalArgumentException {
        String barListLocation = CategoryBar.categoryListLocationFileItem;
        String barStyle = CategoryBar.categoryStyleLocationFileItem;
        boolean containsCategoryBarList = locationFile.isLocation(barListLocation);
        boolean containsCategoryBarStyle = locationFile.isLocation(barStyle);
        if (containsCategoryBarList && containsCategoryBarStyle) {
            String categoryBarListLocation = locationFile.getLocation(barListLocation);
            String categoryBarStyleLocation = locationFile.getLocation(barStyle);
            return new String[]{categoryBarListLocation, categoryBarStyleLocation};
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
        List<String> lines = Category.readTextFileLines(location);
        List<Category> categoryList = new Vector<>();
        for (String line : lines) {
           Category category = new Category(this, line);
           categoryList.add(category);
           numberOfCategories += 1;
        }

        return categoryList;
    }

    private List<String> computeCategoryListNames(List<Category> categoryList) {
        List<String> names = new Vector<>();
        for (Category category : categoryList)
            names.add(category.getName());

        return names;
    }

    private Dictionary<String, Integer> computeCategoryNameMap(List<Category> categoryList) {
        Dictionary<String, Integer> names = new Hashtable<>();
        int index = 0;
        for (Category category : categoryList) {
            names.put(category.getName(), index);
            index += 1;
        }

        return names;
    }

    private Dictionary<Category, Integer> computeCategoryMap(List<Category> categoryList) {
        Dictionary<Category, Integer> names = new Hashtable<>();
        int index = 0;
        for (Category category : categoryList) {
            names.put(category, index);
            index += 1;
        }

        return names;
    }
}
