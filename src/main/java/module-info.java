module perfectbody {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires com.jfoenix;

    opens pl.edu.dsw.perfectbody.controllers to javafx.fxml;

    exports pl.edu.dsw.perfectbody to javafx.graphics;
    exports pl.edu.dsw.perfectbody.controllers to javafx.fxml;
}