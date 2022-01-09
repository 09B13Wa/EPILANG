package com.example.epilanggui;

import com.example.epilanggui.core.Activity;
import com.example.epilanggui.core.ActivityBar;
import com.example.epilanggui.core.CategoryBar;
import com.example.epilanggui.core.MacroBar;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Epilang extends Application {
    private CategoryBar categoryBar;
    private ActivityBar activityBar;
    private MacroBar macroBar;
    private Activity mainActivity;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Epilang.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public void loadData() {
        new CategoryBar("locationFile");
    }

    public static void main(String[] args) {
        launch();
    }
}