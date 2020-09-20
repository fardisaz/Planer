package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import sample.datamodel.Plan;
import sample.datamodel.PlanData;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class Controller {
    List<Plan> plans;
    @FXML
    BorderPane mainBorderPane;

    @FXML
    ListView<Plan> PlansList;

    @FXML
    TextArea planArea;

    @FXML
    Label dateLabel;

       public void initialize(){

        //eventListener
        PlansList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Plan>() {
            @Override
            public void changed(ObservableValue<? extends Plan> observableValue, Plan t0, Plan t1) {
                if(t1 != null){

                    planArea.setText(t1.getDescription());
                    DateTimeFormatter df=DateTimeFormatter.ofPattern("MMMM d, yyyy");
                    dateLabel.setText(df.format(t1.getDate()));
                }
            }
        });


        //PlansList.getItems().setAll(PlanData.getInstance().getPlans());
        PlansList.setItems(PlanData.getInstance().getPlans());
        PlansList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        PlansList.getSelectionModel().selectFirst();

        //cell factory
        PlansList.setCellFactory(new Callback<ListView<Plan>, ListCell<Plan>>() {
            @Override
            public ListCell<Plan> call(ListView<Plan> planListView) {
                ListCell<Plan> cell=new ListCell<Plan>(){
                    @Override
                    protected void updateItem(Plan plan, boolean b) {
                        super.updateItem(plan, b);
                        if(b){
                            setText(null);
                        }else {
                            setText(plan.getName());
                            if(plan.getDate().isBefore(LocalDate.now().plusDays(1))){
                                setTextFill(Color.ORANGERED);
                            }else if(plan.getDate().isEqual(LocalDate.now().plusDays(1))){
                                setTextFill(Color.HOTPINK);
                            }
                        }
                    }
                };
                return cell;
            }
        });

    }
    public  void createPlan(){
        Dialog<ButtonType> dialog=new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        //load the dialog
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("planDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e){
            e.printStackTrace();
            return;
        }
        //add OK &Cancel button
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        //show the dialog
        Optional<ButtonType> result=dialog.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK){
            PlanDialog controller=fxmlLoader.getController();
            Plan plan=controller.processResult();
            PlansList.getSelectionModel().select(plan);
        }

    }

}
