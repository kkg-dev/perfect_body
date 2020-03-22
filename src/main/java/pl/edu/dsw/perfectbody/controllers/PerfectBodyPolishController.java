package pl.edu.dsw.perfectbody.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.text.DecimalFormat;

public class PerfectBodyPolishController {

    @FXML
    private Pane polPane;

    @FXML
    private JFXButton buttonBackToMainMenu;

    @FXML
    private Text close;

    @FXML
    private JFXButton buttonCalcBMI;

    @FXML
    private JFXButton buttonCalcWeightIntake;

    @FXML
    private JFXButton buttonClearData;

    @FXML
    private JFXRadioButton genderMale;

    @FXML
    private ToggleGroup Gender;

    @FXML
    private JFXRadioButton genderFemale;

    @FXML
    private JFXTextField heightInput;

    @FXML
    private JFXTextField weightInput;

    @FXML
    private JFXTextField bmiOutput;

    @FXML
    private JFXTextField bmiStatusOutput;

    @FXML
    private JFXRadioButton activityLow;

    @FXML
    private ToggleGroup PhysicalActivity;

    @FXML
    private JFXRadioButton acitvityMedium;

    @FXML
    private JFXRadioButton activityHigh;

    @FXML
    private JFXTextField weightBrocaOutput;

    @FXML
    private JFXTextField weightLorenzOutput;

    @FXML
    private JFXTextField weightPottonOutput;

    @FXML
    private JFXTextField recommendedIntakeOutput;

    @FXML
    private JFXTextField ageInput;

    @FXML
    void CalcWeightIntake(ActionEvent event) {
        try { // get data required for Daily Intake and Ideal weight calculation
            double heightValue = Double.parseDouble(heightInput.getText());
            double weightValue = Double.parseDouble(weightInput.getText());

            int age = Integer.parseInt(ageInput.getText());

            // throw exception if age or weight is less than or equal to 0 OR if height is lower than 3 (implicitly 3m -> in case user provided 1.80 instead of 180)
            if ((age <= 0) || (heightValue <= 3) || (weightValue <= 0)) {
                throw new NumberFormatException();
            }
            Double IntakeBMRValue = null;
            // Generate BMR (for Harris-Bennedict equation)
            if (genderMale.isSelected()) {
                IntakeBMRValue = (10 * weightValue) + (6.25 * heightValue) - (5 * age) + 5;
            } else if (genderFemale.isSelected()) {
                IntakeBMRValue = (10 * weightValue) + (6.25 * heightValue) - (5 * age) - 161;
            }
            // define gender (Male [M] / Female [F])
            String gender = null;
            if (genderMale.isSelected()) {
                gender = "M";
            } else if (genderFemale.isSelected()) {
                gender = "F";
            }

            // call calculation methods
            calcIntake(IntakeBMRValue);
            assert gender != null;
            calcBrocaFormula(heightValue, gender);
            calcLorenzFormula(heightValue, gender);
            calcPottonFormula(heightValue, gender);

        } catch (NumberFormatException e) { // pass values to fields for exception
            recommendedIntakeOutput.setText("Wprowadź prawidłowe dane");
            weightBrocaOutput.setText("---");
            weightLorenzOutput.setText("---");
            weightPottonOutput.setText("---");
        }
    }

    void calcIntake(Double IntakeBMRValue) {
        DecimalFormat decimalf = new DecimalFormat("0.##");
        if (activityLow.isSelected()) {
            recommendedIntakeOutput.setText(decimalf.format(IntakeBMRValue * 1.53) + " kcal"); // PAL factor [Physical Activity Level] for Low Activity equals to 1.53
        } else if (acitvityMedium.isSelected()) {
            recommendedIntakeOutput.setText(decimalf.format(IntakeBMRValue * 1.76) + " kcal"); // PAL factor [Physical Activity Level] for Low Activity equals to 1.76
        } else if (activityHigh.isSelected()) {
            recommendedIntakeOutput.setText(decimalf.format(IntakeBMRValue * 2.25) + " kcal"); // PAL factor [Physical Activity Level] for Low Activity equals to 2.25
        }
    }

    void calcBrocaFormula(double heightValueCM, String gender) {
        DecimalFormat decimalf = new DecimalFormat("0.##");
        if (gender.equals("M")) {
            weightBrocaOutput.setText(decimalf.format((heightValueCM - 100) * 0.9) + " kg"); // ideal male weight calculation using Broca's formula
        } else if (gender.equals("F")) {
            weightBrocaOutput.setText(decimalf.format((heightValueCM - 100) * 0.85) + " kg"); // ideal female weight calculation using Broca's formula
        }
    }

    void calcLorenzFormula(Double heightValueCM, String gender) {
        DecimalFormat decimalf = new DecimalFormat("0.##");
        if (gender.equals("M")) {
            weightLorenzOutput.setText(decimalf.format((heightValueCM - 100) - ((heightValueCM - 150) / 4)) + " kg"); // ideal male weight calculation using Lorenz's formula
        } else if (gender.equals("F")) {
            weightLorenzOutput.setText(decimalf.format((heightValueCM - 100) - ((heightValueCM - 150) / 2)) + " kg"); // ideal female weight calculation using Lorenz's formula
        }
    }

    void calcPottonFormula(Double heightValueCM, String gender) {
        DecimalFormat decimalf = new DecimalFormat("0.##");
        if (gender.equals("M")) {
            weightPottonOutput.setText(decimalf.format((heightValueCM - 100) - ((heightValueCM - 100) / 20)) + " kg"); // ideal male weight calculation using Potton's formula
        } else if (gender.equals("F")) {
            weightPottonOutput.setText(decimalf.format((heightValueCM - 100) - ((heightValueCM - 100) / 10)) + " kg");// ideal female weight calculation using Potton's formula
        }
    }

    @FXML
    void ClearData(ActionEvent event) {
        //clear radio buttons related to gender and age
        genderMale.setSelected(true);
        genderFemale.setSelected(false);
        ageInput.setText("");
        //clear radio buttons related to body measurements
        heightInput.setText("");
        weightInput.setText("");
        //clear radio buttons related to activity level
        activityLow.setSelected(true);
        acitvityMedium.setSelected(false);
        activityHigh.setSelected(false);
        //clear results
        bmiOutput.setText("");
        bmiStatusOutput.setText("");
        weightBrocaOutput.setText("");
        weightLorenzOutput.setText("");
        weightPottonOutput.setText("");
        recommendedIntakeOutput.setText("");
    }

    @FXML
    void backToMainMenu(ActionEvent event) throws IOException {
        try {
            Pane mainPane = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
            polPane.getChildren().setAll(mainPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void calcBMI(ActionEvent event) {
        try { // get data required for Daily Intake and Ideal weight calculation
            double heightValue = Double.parseDouble(heightInput.getText());
            double weightValue = Double.parseDouble(weightInput.getText());

            // throw exception if weight is lower than or equal to 0 OR if height is lower than 3 (implicitly 3m -> in case user provided 1.80 instead of 180)
            if ((heightValue <= 3) || (weightValue <= 0)) {
                throw new NumberFormatException();
            }
            double bmiValue = weightValue / ((heightValue/100) * (heightValue/100));
            setBMIResult(bmiValue);
        } catch (NumberFormatException e) {
            bmiOutput.setText("Wprowadź poprawne dane");
            bmiStatusOutput.setText("---");
        }
    }

    void setBMIResult(Double bmiValue) {
        DecimalFormat decimalf = new DecimalFormat("0.##");
        bmiOutput.setText(decimalf.format(bmiValue));
        if (bmiValue < 18.5) {
            bmiStatusOutput.setText("Niedowaga");
        } else if (bmiValue >= 18.5 && bmiValue < 25.0) {
            bmiStatusOutput.setText("Waga prawidłowa");
        } else if (bmiValue >= 25.0 && bmiValue < 30.0) {
            bmiStatusOutput.setText("Nadwaga");
        } else if (bmiValue >= 30.0 && bmiValue < 35.0) {
            bmiStatusOutput.setText("Otyłość I stopnia");
        } else if (bmiValue >= 35.0 && bmiValue < 40.0) {
            bmiStatusOutput.setText("Otyłość II stopnia");
        } else if (bmiValue >= 40.0) {
            bmiStatusOutput.setText("Otyłość III stopnia");
        }
    }


    @FXML
    void close(MouseEvent event) {
        System.exit(0);
    }

}

