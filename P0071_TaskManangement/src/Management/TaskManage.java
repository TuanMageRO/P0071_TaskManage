            
package Management;

import Object.Task;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TaskManage {
    private final Scanner sc = new Scanner(System.in);
    private final String timeLine = "^[0-9]{1,2}\\.5|[0-9]{1,2}\\.0$";
    private final String normal = "[A-Za-z0-9\\s\\.]+";
    private final List<Task> taskList;

    public TaskManage() {
        taskList = new ArrayList<>();
    }
    
    public int checkInteger(String mess, int min, int max) {
        int n;
        while(true) {
           try {
               System.out.println(mess);
               n = Integer.parseInt(sc.nextLine().trim());
               if(n < min || n > max) {
                   System.err.println("Only integer in range" + min + " to " + max);
                   continue;
               }
               return n;
           }
           catch(NumberFormatException e) {
               System.err.println("Only Integer number.");
           }
        }
    }
    
    public String checkTaskType() {
        while (true) {            
            int n = checkInteger("Enter your task type from 1 to 4: ", 1, 4);
            String res = "";
            switch (n) {
                case 1:
                    res = "Code";
                    break;
                case 2:
                    res = "Test";
                    break;
                case 3:
                    res = "Manager";
                    break;
                case 4: 
                    res = "Learn";
            }
            return res;
        }
    }
    
    public String checkPlan( String mess) {
        while (true) {  
            System.out.println(mess);
            String s = checkString();
            if(s.matches(timeLine) && Double.parseDouble(s) >= 8.0 
                    && Double.parseDouble(s) <= 17.5) {
                return s;
            }
            else System.err.println("Invalid input, try again.");
        }
    }
    
    public String checkDate(String mess) {
        String err = "Enter the format (Date-Month-Year): ";
        while(true) {
            try{
                System.out.println(mess);
                String s = sc.nextLine().trim();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date date = sdf.parse(s);
                if(s.equalsIgnoreCase(sdf.format(date))) {
                    return s;
                }
            }
            catch (NumberFormatException | ParseException e) {System.err.println(err);}
        }
    }
    
    public String checkString() {
        while(true) {
            String s = sc.nextLine().trim();
            if(s.isEmpty()) {
                System.err.println("String can not be empty");
            }
            else {
                if(s.matches(normal)) {
                    return s;
                }
                else System.err.println("Invalid input. ");
            }
        }
    }
    
    public int addTask(int id) {
        System.out.println("Enter Requirement name: ");
        String rName = checkString();
        String taskType = checkTaskType();
        String date = checkDate("Enter the date of requirement: ");
        String planFrom =  checkPlan("Enter the starting time: ");
        String planTo =  checkPlan("Enter the due time: ");
        if(Double.parseDouble(planTo) < Double.parseDouble(planFrom)) {
            System.out.println("Add failed due to invalid due time or Starting time.");
        }
        else{
            System.out.println("Enter the Assignee: ");
            String asignee = checkString();
            System.out.println("Enter the Reviewer: ");
            String reviewer = checkString();         
            taskList.add(new Task(id, rName, taskType, date, planFrom, planTo, asignee, reviewer));
            System.out.println("Task was added successfully");
            id++;
        }
        return id;
    }
    
    public boolean findTask(int id) {
        for(Task t : taskList) {
            if(t.getId() == id) return true;
        }
        return false;
    }
    
    public void deleteTask(int id) {
        if(taskList.isEmpty()) {
            System.out.println("Task list is empty");
            return;
        }
        id = checkInteger("Enter ID of task to delete: ", 1, 20000);
        if(findTask(id)) {
        taskList.remove(id-1);
        System.out.println("Deleted succesfully");
        for(int i = id-1; i < taskList.size(); ++i) {
            taskList.get(i).setId(taskList.get(i).getId()-1);
            }
        }
        else System.out.println("This task does not exist.");
    }
    
    public void displayTasks() {
        if(taskList.isEmpty()) {
            System.out.println("The task list is empty.");
        }
        else{
            System.out.printf("%-5s%-15s%-15s%-15s%-15s%-15s%-15s\n",
                    "ID", "Name", "Task Type", "Date", "Time", "Assignee", "Reviewer");
            for(Task tl : taskList) {
                System.out.printf("%-5d%-15s%-15s%-15s%-15.1f%-15s%-15s\n",
                    tl.getId(),
                    tl.getRequirementName(),
                    tl.getDate(),
                    tl.getTaskTypeID(),
                    Double.parseDouble(tl.getPlanTo())-Double.parseDouble(tl.getPlanFrom()),
                    tl.getAssignee(),
                    tl.getReviewer());                  
            }
        }
    }
} 