package sample;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.datamodel.Plan;
import sample.datamodel.PlanData;

import java.time.LocalDate;

public class PlanDialog {
    @FXML
    private TextField nameField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private DatePicker dateField;

    public Plan processResult(){
        String planeName=nameField.getText();
        String description=descriptionArea.getText();
        LocalDate date=dateField.getValue();
        Plan plan=new Plan(planeName,description,date);
        PlanData.getInstance().getPlans().add(plan);
        return plan;
    }
}
