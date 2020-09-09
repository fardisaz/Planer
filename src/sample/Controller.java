package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import sample.datamodel.Plan;
import sample.datamodel.PlanData;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class Controller {
    List<Plan> plans;

    @FXML
    ListView<Plan>PlansList;

    @FXML
    TextArea planArea;

    @FXML
    Label dateLabel;

       public void initialize(){
//        Plan plan1=new Plan("Personal Photo","Taking a personal photo for job application",
//                LocalDate.of(2020, Month.OCTOBER,11));
//        Plan plan2=new Plan("Birthday Present","Mathew's birthday is on 23rd of October",
//                LocalDate.of(2020,Month.OCTOBER,22));
//        Plan plan3=new Plan("Frankfurt Trip","Renew my passport on Tuesday",
//                LocalDate.of(2020,Month.OCTOBER,15));
//        Plan plan4=new Plan("Master Thesis","I have a meeting with my supervisor",
//                LocalDate.of(2020,Month.SEPTEMBER,11));
//        Plan plan5=new Plan("Visit Köln","Meet my previous co-worker",
//                LocalDate.of(2020,Month.NOVEMBER,28));
//        Plan plan6=new Plan("Christmas Presents","Buying presents for my friends",
//                LocalDate.of(2020,12,10));
//        plans=new ArrayList<Plan>();
//
//        plans.add(plan1);
//        plans.add(plan2);
//        plans.add(plan3);
//        plans.add(plan4);
//        plans.add(plan5);
//        plans.add(plan6);
//
//        PlanData.getInstance().setPlans(plans);

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
        PlansList.getItems().setAll(PlanData.getInstance().getPlans());
        PlansList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        PlansList.getSelectionModel().selectFirst();

    }

}
