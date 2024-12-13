module org.example.flappybird_game {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires static lombok;
    requires java.desktop;
    requires jdk.xml.dom;

    opens org.example.flappybird_game to javafx.fxml;
    exports org.example.flappybird_game;
    exports org.example.flappybird_game.FxControllers;
    opens org.example.flappybird_game.FxControllers to javafx.fxml;
    exports org.example.flappybird_game.Managers;
    opens org.example.flappybird_game.Managers to javafx.fxml;
}