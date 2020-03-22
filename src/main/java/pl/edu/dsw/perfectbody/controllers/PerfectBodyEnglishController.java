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

public class PerfectBodyEnglishController {

    @FXML
    private Pane engPane;

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
    private JFXTextField ageInput;

    @FXML
    private JFXTextField heightFeetInput;

    @FXML
    private JFXTextField heightInchesInput;

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
    void CalcWeightIntake(ActionEvent event) {
        try { // get data required for Daily Intake and Ideal weight calculation
            double heightFeetValue = Double.parseDouble(heightFeetInput.getText());
            double heightInchesValue = Double.parseDouble(heightInchesInput.getText());
            //1 feet = 12 inches. 1 inch = 2.54 cm. Calculating harmonized heightValueCM represented by cm
            double heightValueCM = ((12 * heightFeetValue) + heightInchesValue) * 2.54;

            double weightValue = Double.parseDouble(weightInput.getText());
            // 1 lb = 0.45359237 kg. Calculating weight value in kilograms
            double weightValueKG = 0.45359237 * weightValue;
            int age = Integer.parseInt(ageInput.getText());

            // throw exception if age, height or weight is less than or equal to 0 OR if height in feet/inches is stated as < 0
            if ((age <= 0) || (weightValueKG <= 0) || (heightValueCM <= 0) || (heightFeetValue < 0) || (heightInchesValue < 0)) {
                throw new NumberFormatException();
            }
            Double IntakeBMRValue = null;
            // Generate BMR (for Harris-Bennedict equation)
            if (genderMale.isSelected()) {
                IntakeBMRValue = (10 * weightValueKG) + (6.25 * heightValueCM) - (5 * age) + 5;
            } else if (genderFemale.isSelected()) {
                IntakeBMRValue = (10 * weightValueKG) + (6.25 * heightValueCM) - (5 * age) - 161;
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
            calcBrocaFormula(heightValueCM, gender);
            calcLorenzFormula(heightValueCM, gender);
            calcPottonFormula(heightValueCM, gender);

        } catch (NumberFormatException e) { // pass values to fields for exception
            recommendedIntakeOutput.setText("Enter valid data");
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
            weightBrocaOutput.setText(decimalf.format(((heightValueCM - 100) * 0.9) / 0.45359237) + " lb"); // ideal male weight calculation using Broca's formula
        } else if (gender.equals("F")) {
            weightBrocaOutput.setText(decimalf.format(((heightValueCM - 100) * 0.85) / 0.45359237) + " lb"); // ideal female weight calculation using Broca's formula
        }
    }

    void calcLorenzFormula(Double heightValueCM, String gender) {
        DecimalFormat decimalf = new DecimalFormat("0.##");
        if (gender.equals("M")) {
            weightLorenzOutput.setText(decimalf.format(((heightValueCM - 100) - ((heightValueCM - 150) / 4)) / 0.45359237) + " lb"); // ideal male weight calculation using Lorenz's formula
        } else if (gender.equals("F")) {
            weightLorenzOutput.setText(decimalf.format(((heightValueCM - 100) - ((heightValueCM - 150) / 2)) / 0.45359237) + " lb"); // ideal female weight calculation using Lorenz's formula
        }
    }

    void calcPottonFormula(Double heightValueCM, String gender) {
        DecimalFormat decimalf = new DecimalFormat("0.##");
        if (gender.equals("M")) {
            weightPottonOutput.setText(decimalf.format(((heightValueCM - 100) - ((heightValueCM - 100) / 20)) / 0.45359237) + " lb"); // ideal male weight calculation using Potton's formula
        } else if (gender.equals("F")) {
            weightPottonOutput.setText(decimalf.format(((heightValueCM - 100) - ((heightValueCM - 100) / 10)) / 0.45359237) + " lb");// ideal female weight calculation using Potton's formula
        }
    }

    @FXML
    void ClearData(ActionEvent event) {
        //clear radio buttons related to gender and age
        genderMale.setSelected(true);
        genderFemale.setSelected(false);
        ageInput.setText("");
        //clear radio buttons related to body measurements
        heightFeetInput.setText("");
        heightInchesInput.setText("");
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
            engPane.getChildren().setAll(mainPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void calcBMI(ActionEvent event) {
        try {
            double heightFeetValue = Double.parseDouble(heightFeetInput.getText());
            double heightInchesValue = Double.parseDouble(heightInchesInput.getText());
            //1 feet = 12 inches. Calculating harmonized heightValue represented by inches
            double heightValue = (12 * heightFeetValue) + heightInchesValue;

            double weightValue = Double.parseDouble(weightInput.getText());

            //throw exception if height or weight is less than or equal to 0 OR values in feet/inches are less than 0
            if ((heightValue <= 0) || (weightValue <= 0) || (heightFeetValue < 0) || (heightInchesValue < 0)) {
                throw new NumberFormatException();
            }
            double bmiValue = (weightValue * 703) / (heightValue * heightValue);
            setBMIResult(bmiValue);
        } catch (NumberFormatException e) {
            bmiOutput.setText("Enter valid measurements");
            bmiStatusOutput.setText("---");
        }
    }

    void setBMIResult(Double bmiValue) {
        DecimalFormat decimalf = new DecimalFormat("0.##");
        bmiOutput.setText(decimalf.format(bmiValue));
        if (bmiValue < 18.5) {
            bmiStatusOutput.setText("Below normal weight");
        } else if (bmiValue >= 18.5 && bmiValue < 25.0) {
            bmiStatusOutput.setText("Normal weight");
        } else if (bmiValue >= 25.0 && bmiValue < 30.0) {
            bmiStatusOutput.setText("Overweight");
        } else if (bmiValue >= 30.0 && bmiValue < 35.0) {
            bmiStatusOutput.setText("Class I Obesity");
        } else if (bmiValue >= 35.0 && bmiValue < 40.0) {
            bmiStatusOutput.setText("Class II Obesity");
        } else if (bmiValue >= 40.0) {
            bmiStatusOutput.setText("Class III Obesity");
        }
    }


    @FXML
    void close(MouseEvent event) {
        System.exit(0);
    }

}

