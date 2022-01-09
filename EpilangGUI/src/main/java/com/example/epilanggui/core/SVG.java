package com.example.epilanggui.core;

import afester.javafx.svg.SvgLoader;
import javafx.scene.Group;

import java.io.File;
import java.io.IOException;

public class SVG {
    private Group svg;
    private double scaleX;
    private double scaleY;
    private String name;

    public SVG(Group svgData) {
        svg = svgData;
        scaleX = 1;
        scaleY = 1;
        name = "random svg name";
    }

    public SVG(Group svgData, String name) {
        svg = svgData;
        scaleX = 1;
        scaleY = 1;
        this.name = name;
    }

    public SVG(Group svgData, double scale) {
        svg = svgData;
        scaleX = scale;
        scaleY = scale;
        name = "random svg name";
    }

    public SVG(Group svgData, double scaleX, double scaleY) {
        svg = svgData;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        name = "random svg name";
    }

    public SVG(Group svgData, String name, double scale) {
        svg = svgData;
        scaleX = scale;
        scaleY = scale;
        this.name = name;
    }

    public SVG(Group svgData, String name, double scaleX, double scaleY) {
        svg = svgData;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.name = name;
    }

    public Group getSvg() {
        return svg;
    }

    public String getName() {
        return name;
    }

    public double getScaleX() {
        return scaleX;
    }

    public void setScaleX(double scaleX) {
        this.scaleX = scaleX;
    }

    public double getScaleY() {
        return scaleY;
    }

    public void setScaleY(double scaleY) {
        this.scaleY = scaleY;
    }

    public static SVG load(String location, String name) {
        SvgLoader svgLoader = new SvgLoader();
        Group svgData = svgLoader.loadSvg(location);
        return new SVG(svgData, "");
    }

    public static SVG parse(String data, String name) {
        SvgLoader svgLoader = new SvgLoader();
        File file = new File("%s.svg".formatted(name));
        boolean isCreated = false;
        try {
            isCreated = file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return load(name, name);
    }

    public Group exportToGroup() {
        return svg;
    }
}
