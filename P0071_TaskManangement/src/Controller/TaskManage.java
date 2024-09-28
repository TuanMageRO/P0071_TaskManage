package Controller;

import Model.Task;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import View.View;
        
public class TaskManage {
    private final Scanner sc = new Scanner(System.in);
    private final String normal = "[A-Za-z0-9\\s\\.]+";
    private final View v = new View();
    private final ArrayList<Task> taskList = new ArrayList<>();

    public int getInteger(String msg, String err, int min, int max) {
        int n;
        while(true) {
           try {
               System.out.print(msg);
               n = Integer.parseInt(sc.nextLine().trim());
               if(n < min || n > max) {
                   System.err.println("Only integer in range " + min + " to " + max);
                   continue;
               }
               return n;
           }
           catch(NumberFormatException e) {
               System.err.println(err);
           }
        }
    }
    
    public double checkDouble(String msg, String err, double min, double max) {
        String input;
        double result;
        while (true) {
            System.out.print(msg);
            input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.err.println("Input must not be empty");
                continue;
            }
            try {
                result = Double.parseDouble(input);
                if (result < min || result > max) {
                    System.out.println(err);
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.err.println("Only double type in range " + min + " to " + max);
            }
        }
        return result;
    }
    
    public String checkTaskType() {
        System.out.println("Task Type: 1.Code  2.Test  3.Manager  4.Learn");
        int n = getInteger("Enter Task Type: ", "Must be a number between 1-4", 1, 4);
        switch (n) {
            case 1: return "Code";
            case 2: return "Test";
            case 3: return "Manager";
            case 4: return "Learn";
            default: return "";
        }
    }
    
    public String checkDate(String mess) {
        String err = "Wrong format, the format must be (Date-Month-Year): ";
        while(true) {
            try{
                System.out.print(mess);
                String s = sc.nextLine().trim();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date date = sdf.parse(s);
                if(s.equals(sdf.format(date))) {
                    return s;
                }
                else System.err.println("Invalid Day or The day and month must consist of 2 digits.");
            }
            catch ( ParseException e) {System.err.println(err);}
        }
    }
    
    public String checkString(String msg) {
        while(true) {
            System.out.print(msg);
            String s = sc.nextLine().trim();
            if(s.isEmpty()) {
                System.err.println("String cannot be empty");
            } else if(s.matches(normal)) {
                return s;
            } else {
                System.err.println("Invalid input. Only letters, digits, and spaces allowed.");
            }
        }
    }
    
    public boolean checkDuplicateTask(ArrayList<Task> taskList,String date, double from, double to, String assignee) {
        for (Task task : taskList) {
            if (task.getDate().equals(date) && task.getAssignee().equalsIgnoreCase(assignee)) {
                if ((from >= task.getPlanFrom() && from < task.getPlanTo()) ||
                    (to > task.getPlanFrom() && to <= task.getPlanTo()) ||
                    (from <= task.getPlanFrom() && to >= task.getPlanTo())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private int checkIdExist(ArrayList<Task> list, int id) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                return i;
            }
        }
        return -1; // not found
    }
    
    public void addTask() {
        int id = taskList.size() + 1;
        String rName = checkString("Enter Requirement Name: ");
        String taskType = checkTaskType();
        String date = checkDate("Enter Date (dd-MM-yyyy): ");
        double planFrom = checkDouble("Enter Plan From: ", "Plan From must be between 8.0 and 17.5", 8.0, 17.5);
        double planTo = checkDouble("Enter Plan To: ", "Plan To must be between " + planFrom + " and 17.5", planFrom, 17.5);
        String assignee = checkString("Enter Assignee: ");
        String reviewer = checkString("Enter Reviewer: ");
        
        if(!checkDuplicateTask(taskList, date, planFrom, planTo, assignee)) {
            taskList.add(new Task(id, rName, taskType, date, planFrom, planTo, assignee, reviewer));
            System.out.println("Task added successfully");
        } else {
            System.out.println("Add failed. This assignee is on another task at this time.");
        }
    }
    
    public void deleteTask() {
        if(taskList.isEmpty()) {
            System.out.println("Task list is empty");
            return;
        }
        int id = getInteger("Enter ID of task to delete: ", "ID must be a positive integer", 1, taskList.size());
        
        // Find the task
        int indexToRemove = checkIdExist(taskList, id); 
        
        if (indexToRemove != -1) {
            taskList.remove(indexToRemove);  
            System.out.println("Task deleted successfully");
            // Reassign IDs
            for (int i = 0; i < taskList.size(); i++) {
                taskList.get(i).setId(i + 1);
            }
        } else {
            System.out.println("Task with ID " + id + " not found");
        }
    }
    
    public void getDataTask() {
        if(taskList.isEmpty()) {
            System.out.println("The task list is empty.");
        } else {
            System.out.printf("%-5s%-15s%-15s%-15s%-15s%-15s%-15s\n",
                    "ID", "Name", "Task Type", "Date", "Time", "Assignee", "Reviewer");
            for(Task task : taskList) {
                System.out.printf("%-5d%-15s%-15s%-15s%-15.1f%-15s%-15s\n",
                    task.getId(),
                    task.getRequirementName(),
                    task.getTaskTypeID(),
                    task.getDate(),
                    task.getPlanTo() - task.getPlanFrom(),
                    task.getAssignee(),
                    task.getReviewer());                  
            }
        }
    }
    
    public void run() {
        v.menu();
        while(true) {
            int n = getInteger("Enter your choice: ", "Only permit int from 1 to 4 and is not empty.", 1, 4);
            switch(n) {
                case 1 -> addTask();
                case 2 -> deleteTask();
                case 3 -> getDataTask();
                case 4 -> {
                    return;
                }
            }
        }
    }
}