/**package com.example.epilanggui.core;

import com.example.epilanggui.svg.SVG;
import javafx.scene.shape.SVGPath;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class Element {
    private String name;
    private SVGPath icon;
    //TODO: Add link to Category logic
    private int place;
    private ElementBar<Element> elementBar;

    public Element(ElementBar<Element> elementBar, String name, SVGPath icon, int place) {
        this.elementBar = elementBar;
        this.name = name;
        this.icon = icon;
        this.place = place;
    }

    public Element(ElementBar<Element> elementBar, String elementData) {
        Element element = parseCategorySource(elementBar, elementData);
        this.name = element.name;
        this.elementBar = element.elementBar;
        this.icon = element.icon;
        this.place = element.place;
    }

    public ElementBar<Element> getElementBar() {
        return elementBar;
    }

    public static <T extends Element> T create(ElementBar<Element> elementBar, String elementData) {
        Element element = new Element(elementBar, elementData);
        return (T)element;
    }

    public static <T extends Element> T create(ElementBar<Element> elementBar, String name, SVGPath icon, int place) {
        Element element = new Element(elementBar, name, icon, place);
        return (T)element;
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

    private Element parseCategorySource(ElementBar elementBar, String data) {
        String[] segments = data.split(":");
        int numberOfSegments = segments.length;
        if (numberOfSegments != 2) {
            throw new IllegalArgumentException("Error: the number of declared elements for a category must be two in this fashion:\n" +
                    "<name>:<svgPath>");
        }
        else {
            String name = segments[0];
            String svgPathStr = segments[1];
            SVG svg = SVG.load(svgPathStr);
            SVGPath svgPath = svg.exportSvgPath();
            return new Element(elementBar, name, icon, elementBar.numberOfElements);
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
}*/
