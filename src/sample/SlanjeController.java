package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class SlanjeController implements Initializable {
    public Button saljiBtn;
    public TextField postanskiBroj;

    public SlanjeController() {}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saljiBtn.setDisable(true);
        postanskiBroj.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (oldValue && !newValue) {
                    Ispravnost validacija = new Ispravnost(postanskiBroj.getText());
                    Thread myThread = new Thread(validacija);
                    postanskiBroj.setDisable(true);
                    myThread.start();
                }
            }
        });
    }

    public void salji(ActionEvent actionEvent) {
        Stage prozor = (Stage) postanskiBroj.getScene().getWindow();
        prozor.close();
    }

    public class Ispravnost implements Runnable {
        String string;

        public Ispravnost(String x) {
            string = x;
        }

        @Override
        public void run() {
            try {
                ispravno(string);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void ispravno (String postanski) throws IOException {
            URL url = new URL("http://c9.etf.unsa.ba/proba/postanskiBroj.php?postanskiBroj=" + postanski);
            InputStream tok = url.openStream();
            int velicina = tok.available();
            StringBuilder rezultat = new StringBuilder();
            for (int i = 0; i < velicina; i++) {
                rezultat.append((char) tok.read());
            }

            if (rezultat.toString().equals("OK")) {
                Platform.runLater(() -> {
                    saljiBtn.setDisable(false);
                    postanskiBroj.getStyleClass().removeAll("nijepopunjeno");
                    postanskiBroj.getStyleClass().add("popunjeno");
                });
            } else {
                Platform.runLater(() -> {
                    saljiBtn.setDisable(true);
                    postanskiBroj.getStyleClass().removeAll("popunjeno");
                    postanskiBroj.getStyleClass().add("nijepopunjeno");
                });
            }
            Platform.runLater(() -> {
                postanskiBroj.setDisable(false);
            });
        }
    }
}
