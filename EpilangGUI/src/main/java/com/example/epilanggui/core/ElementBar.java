/**package com.example.epilanggui.core;

import com.example.logic.LocationFile;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

public class ElementBar<ElementDerived extends Element> {
    protected List<ElementDerived> elementList;
    protected List<String> elementNamesList;
    protected Dictionary<ElementDerived, Integer> elementIndexes;
    protected Dictionary<String, Integer> elementNameIndexes;
    protected ElementBarStyle<ElementDerived> elementBarStyle;
    protected int numberOfElements;
    private ElementBar<Element> baseBar;
    private ElementBar<ElementDerived> parent;
    private boolean isBase;

    protected String elementListLocationFileItem = "";
    protected String elementStyleLocationFileItem = "";

    public ElementBar() {
        elementList = new Vector<>();
        baseBar = new ElementBar<>();
        numberOfElements = 0;
        elementBarStyle = ElementBarStyle.generateDefaultStyle();
        elementListLocationFileItem = "";
        elementStyleLocationFileItem = "";
    }

    public ElementBar(LocationFile location) {
        initializeData(location);
        baseBar = new ElementBar<>();
        elementListLocationFileItem = "";
        elementStyleLocationFileItem = "";
    }

    public ElementBar(String locationFileLocation) {
        initializeData(locationFileLocation);
        baseBar = new ElementBar<>();
        elementListLocationFileItem = "";
        elementStyleLocationFileItem = "";
    }

    public ElementBar(List<ElementDerived> elementList) {
        this.elementList = new Vector<>();
        this.elementList.addAll(elementList);
        baseBar = new ElementBar<>();
        elementBarStyle = ElementBarStyle.generateDefaultStyle();
        elementListLocationFileItem = "";
        elementStyleLocationFileItem = "";
    }

    public ElementBar(ElementBarStyle<ElementDerived> elementBarStyle) {
        elementList = new Vector<>();
        this.elementBarStyle = elementBarStyle;
        baseBar = new ElementBar<>();
        elementListLocationFileItem = "";
        elementStyleLocationFileItem = "";
    }

    public ElementBar(List<ElementDerived> elementList, ElementBarStyle<ElementDerived> elementBarStyle) {
        this.elementList = new Vector<>();
        this.elementList.addAll(elementList);
        this.elementBarStyle = elementBarStyle;
        elementListLocationFileItem = "";
        elementStyleLocationFileItem = "";
        baseBar = new ElementBar<>(this);
        isBase = false;
    }

    private <U extends Element> ElementBar(ElementBar<U> parent) {

        elementList = new Vector<>();
        for (U element : parent.elementList)
            elementList.add((ElementDerived) element);
        elementIndexes = computeElementMap(elementList);
        elementNameIndexes = computeElementNameMap(elementList);
        elementNamesList = computeElementListNames(elementList);
        elementListLocationFileItem = parent.elementListLocationFileItem;
        elementStyleLocationFileItem = parent.elementStyleLocationFileItem;
        elementBarStyle = ElementBarStyle.generateDefaultStyle();
        baseBar = null;
        isBase = true;
    }

    private void initializeData(String locationFileLocation) {
        LocationFile locationFile = new LocationFile(locationFileLocation);
        initializeData(locationFile);
    }

    private void initializeData(LocationFile locationFile) {
        String[] locations = parseLocations(locationFile);
        String listLocation = locations[0];
        String styleLocation = locations[1];
        elementBarStyle = ElementBarStyle.create(styleLocation);
        elementList = parseElementListSource(listLocation);
        elementNamesList = computeElementListNames(elementList);
        elementNameIndexes = computeElementNameMap(elementList);
        elementIndexes = computeElementMap(elementList);
    }

    public int getIndexOf(Category category) {
        return elementIndexes.get(category);
    }

    public int getIndexOf(String categoryName) {
        return elementNameIndexes.get(categoryName);
    }

    public ElementDerived getCategoryAt(int index) {
        return elementList.get(index);
    }

    public String getCategoryNameAt(int index) {
        return elementNamesList.get(index);
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
        if (firstIndex < numberOfElements && secondIndex < numberOfElements) {
            ElementDerived firstIndexCategory = getCategoryAt(firstIndex);
            ElementDerived secondIndexCategory = getCategoryAt(secondIndex);
            String firstIndexStr = getCategoryNameAt(firstIndex);
            String secondIndexStr = getCategoryNameAt(secondIndex);

            elementList.set(secondIndex, firstIndexCategory);
            elementList.set(firstIndex, secondIndexCategory);
            elementNamesList.set(secondIndex, firstIndexStr);
            elementNamesList.set(firstIndex, secondIndexStr);
            elementIndexes.put(firstIndexCategory, secondIndex);
            elementIndexes.put(secondIndexCategory, firstIndex);
            elementNameIndexes.put(firstIndexStr, secondIndex);
            elementNameIndexes.put(secondIndexStr, firstIndex);
            baseBar.swap(firstIndex, secondIndex);
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

    private String[] parseLocations(LocationFile locationFile) throws IllegalArgumentException {
        String barListLocation = elementListLocationFileItem;
        String barStyle = elementStyleLocationFileItem;
        boolean containsElementBarList = locationFile.isLocation(barListLocation);
        boolean containsElementBarStyle = locationFile.isLocation(barStyle);
        if (containsElementBarList && containsElementBarStyle) {
            String elementBarListLocation = locationFile.getLocation(barListLocation);
            String elementBarStyleLocation = locationFile.getLocation(barStyle);
            return new String[]{elementBarListLocation, elementBarStyleLocation};
        }
        else if(containsElementBarList) {
            throw new IllegalArgumentException("Error: the element bar list location wasn't found in the configuration file");
        }
        else {
            throw new IllegalArgumentException("Error: the element bar style location wasn't found in the configuration file");
        }
    }

    private List<ElementDerived> parseElementListSource(String location) {
        numberOfElements = 0;
        List<String> lines = com.example.epilanggui.core.Element.readTextFileLines(location);
        List<ElementDerived> elementList = new Vector<>();
        List<Element> elementListBase = new Vector<>();
        for (String line : lines) {
            ElementDerived element = ElementDerived.create(baseBar, line);
            elementList.add(element);
            numberOfElements += 1;
        }

        return elementList;
    }

    private List<String> computeElementListNames(List<ElementDerived> elementList) {
        List<String> names = new Vector<>();
        for (ElementDerived element : elementList)
            names.add(element.getName());

        return names;
    }

    private Dictionary<String, Integer> computeElementNameMap(List<ElementDerived> elementList) {
        Dictionary<String, Integer> names = new Hashtable<>();
        int index = 0;
        for (ElementDerived element : elementList) {
            names.put(element.getName(), index);
            index += 1;
        }

        return names;
    }

    private Dictionary<ElementDerived, Integer> computeElementMap(List<ElementDerived> elementList) {
        Dictionary<ElementDerived, Integer> names = new Hashtable<>();
        int index = 0;
        for (ElementDerived element : elementList) {
            names.put(element, index);
            index += 1;
        }

        return names;
    }
}*/
