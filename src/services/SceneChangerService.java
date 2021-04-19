package services;

import utils.ApplicationUtilities;
import views.Main;

public class SceneChangerService {
    public static void changeSceneTo(String newScene) {
        try {
            Main main = new Main();
            main.changeScene(newScene);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }
}
