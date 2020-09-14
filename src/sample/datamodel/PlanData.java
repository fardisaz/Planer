package sample.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class PlanData {
    private static PlanData instance=new PlanData();
    private static String filename="PlanData.txt";

    private ObservableList<Plan> plans;
    private DateTimeFormatter formatter;

    public static PlanData getInstance() {
        return instance;
    }

    private PlanData(){
        formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public ObservableList<Plan> getPlans() {
        return plans;
    }
    public void setPlans(ObservableList<Plan> plans){
        this.plans=plans;
    }

    public void loadPlans() throws IOException{
        plans= FXCollections.observableArrayList();
        Path path= Paths.get(filename);
        BufferedReader br= Files.newBufferedReader(path);

        String input;
        try {
            while ((input=br.readLine())!=null){
                String[] itemPieces=input.split("\t");

                String name=itemPieces[0];
                String description=itemPieces[1];
                String dateString=itemPieces[2];

                LocalDate date=LocalDate.parse(dateString,formatter);
                Plan plan=new Plan(name,description,date);
                plans.add(plan);
            }
        }finally {
            br.close();
        }
    }

    public void storePlan() throws IOException {
        Path path= Paths.get(filename);
        BufferedWriter bw= Files.newBufferedWriter(path);

        try {
            Iterator<Plan> iter=plans.iterator();
            while (iter.hasNext()){
                Plan plan=iter.next();
                bw.write(String.format("%s\t%s\t%s",
                        plan.getName(),
                        plan.getDescription(),
                        plan.getDate().format(formatter)));
                bw.newLine();
            }

        }finally {
            if(bw !=null){
                bw.close();
            }
        }
    }
}
