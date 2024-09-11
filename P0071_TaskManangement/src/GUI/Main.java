
package GUI;

import Management.TaskManage;

public class Main {
    private static final TaskManage tm = new TaskManage();
    
    public static void main(String[] args) {
        displayMenu();
    }
    public static void displayMenu() {
        int id = 1;
        int choice;
        while(true) {
            System.out.println("1. Add task.");
            System.out.println("2. Delete task.");
            System.out.println("3. Display task.");
            System.out.println("4. Exit");
            choice = tm.checkInteger("Enter your choice", 1, 4);
            switch (choice) {
                case 1:
                    id = tm.addTask(id);                  
                    break;
                case 2:    
                    tm.deleteTask(id);
                    id--;
                    break;
                case 3:
                    tm.displayTasks();
                    break;
                case 4:
                    return;
            }
        }
    }       
}
