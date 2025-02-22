package edu.ucr.rp.algoritmos.proyecto.util.test;

import edu.ucr.rp.algoritmos.proyecto.logic.domain.*;
import edu.ucr.rp.algoritmos.proyecto.logic.persistance.implementation.AdminAvailabilityPersistence;
import edu.ucr.rp.algoritmos.proyecto.logic.persistance.implementation.CustomerDatePersistence;
import edu.ucr.rp.algoritmos.proyecto.logic.service.implementation.*;
import edu.ucr.rp.algoritmos.proyecto.logic.tdamethods.implementation.UserLinkedList;
import edu.ucr.rp.algoritmos.proyecto.util.Utility;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class TestUtility {
    /////////////////////////////////////////////////// TEST USER
    public static User user;
    Utility utility = new Utility();

    public User randomUser() {
        user = new User();
        generateUserName();
        generateEmail();
        generatePassword();
        generateAddress();
        generatePhoneNumber();
        generateID();
        generateRol();
        return user;
    }

    public void generateUserName() {
        String[] name = {"Carlitos Aguilar", "Braulio Carrillo", "Andre Turrio", "Hern Olivar", "Rosa Marquez", "Lolo Quesada", "Fran Mars", "Yeiny Misun"};
        user.setName(name[random(name.length)] + random(10000000));
    }

    public void generateEmail() {
        user.setEmail(user.getName() + "123@gmail.com");
    }

    public void generatePassword() {
        user.setPassword(utility.encrypt(user.getName() + "123"));
    }

    public void generateAddress() {
        user.setAddress("25 mts al O, casa #" + random(100) + " a nombre de " + user.getName());
    }

    public void generatePhoneNumber() {
        int phoneNumber = 0;
        for (int i = 0; i < 4; i++) {
            phoneNumber += random(101);
        }
        user.setPhoneNumber(phoneNumber);
    }

    public void generateID() {
        int iD = 0;
        for (int i = 0; i < 4; i++) {
            iD += random(101);
        }
        user.setID(iD);
    }

    public void generateRol() {
        int rol = randomRol();
        user.setRol(rol);
    }

    public int random(int bound) {
        return (int) Math.floor(Math.random() * bound);
    }

    public int randomRol() {
        Random r = new Random();
        int valorDado = r.nextInt(4) + 2;
        if (valorDado == 4) {
            valorDado -= 2;
        } else if (valorDado == 5) {
            valorDado -= 2;
        }

        return valorDado;
    }

    /////////////////////////////////////////////////// TEST CUSTOMER DATE
    private CustomerDate customerDate;
    private int customerSetID = 0;
    UserService userService;
    CustomerDateService customerDateService;
    UserLinkedList userLinkedList;
    AdminAvailabilityService adminAvailabilityService;

    private void initializeService() {
        userService = UserService.getInstance();
        customerDateService = CustomerDateService.getInstance();
        adminAvailabilityService = AdminAvailabilityService.getInstance();
        userLinkedList = userService.getAll();
    }

    public boolean validate() {
        initializeService();
        if (userService.getAll() != null) {
            userLinkedList = userService.getAll();
            return true;
        }
        return false;
    }

    public CustomerDate generateDate(String date, String hour, int adminID) {
        initializeService();
        if (validate()) {
            customerDate = new CustomerDate();
            setID();
            customerDate.setDate(date);
            customerDate.setHour(hour);
            customerDate.setAdminID(adminID);
            return customerDate;
        }
        return null;
    }

    private void setID() {
        initializeService();
        int iD = userService.getAll().get(random(userLinkedList.size())).getID();
        int rol;
        boolean customerID = true;
        int accountant = 0;

        while (accountant != 1) {
            rol = userService.getByID(iD).getRol();
            if (rol == 3) {
                if (customerID) {
                    customerDate.setCustomerID(iD);
                    customerID = false;
                    accountant++;
                    customerSetID = iD;
                }
            }
            if (accountant != 1) {
                iD = userService.getAll().get(random(userLinkedList.size())).getID();
            }
        }
    }

    ////////////////////////////////////////////////////////////// GENERADOR DE PLANES DE COMIDA
    private String[] carbohydrates = {"pan", "cereal", "arroz", "galletas", "leche", "papa", "maíz", "frijoles", "ponche de frutas", "yogur", "jugo"};
    private String[] fruits = {"aguacate", "ananá", "banana ", "arándano", "cereza ", "ciruela", "coco", "durazno ", "frambuesa", "fresa ", "Jugo", "granada"};


    public EatingPlan generateEatingPlan() {
        initializeService();
        EatingPlan eatingPlan = new EatingPlan();
        EatingPlanService eatingPlanService = EatingPlanService.getInstance();
        eatingPlan.setID(eatingPlanService.getAll().size() + 1);
        String carbohydratesList = "";
        String fruitsList = "";
        for (int i = 0; i < 3; i++) {
            carbohydratesList += carbohydrates[random(carbohydrates.length)] + ", ";
            fruitsList += fruits[random(fruits.length)] + ", ";
        }
        eatingPlan.setCarbohydrates(carbohydratesList);
        eatingPlan.setFruits(fruitsList);
        eatingPlan.setFat(random(30));
        carbohydratesList = "";
        fruitsList = "";
        return eatingPlan;
    }

    ////////////////////////////////////////////////////////////// GENERADOR DISPONIBILIDAD DE ADMIN
    AdminAvailability adminAvailability;

    public void update() {
        adminAvailability = new AdminAvailability();
    }

    public AdminAvailability generateAdminAvailability(int adminID) {
        update();
        generateAdminAvailableDayAndHour();
        adminAvailability.setAdminID(adminID);
        return adminAvailability;
    }

    public void generateAdminAvailableDayAndHour() {
        List<String> hours = new ArrayList<>();
        for (int i = 8; i < 18; i++) {
            hours.add(i + ":00");
        }
        Map<String, List> availability = new HashMap<>();
        LocalDate localDate = LocalDate.now();
        String dayOfWeek = localDate.getDayOfWeek().toString();
        int j = 0;
        if (dayOfWeek.equalsIgnoreCase("Monday")) {
            j++;
        } else if (dayOfWeek.equalsIgnoreCase("Tuesday")) {
            j += 2;
        } else if (dayOfWeek.equalsIgnoreCase("Wednesday")) {
            j += 3;
        } else if (dayOfWeek.equalsIgnoreCase("Thursday")) {
            j += 4;
        } else if (dayOfWeek.equalsIgnoreCase("Friday")) {
            j += 5;
        }else if (dayOfWeek.equalsIgnoreCase("Saturday")) {
            j++;
        }
        for (int x = j ; x < 5; x++) {
            int dayOfMonth = localDate.getDayOfMonth();

            if (dayOfMonth + x <= 30) {
                availability.put(dayOfMonth + x + "/" + localDate.getMonthValue() + "/" + localDate.getYear(), hours);
            } else {
                availability.put(dayOfMonth + x - 30 + "/" + (localDate.getMonthValue() + j) + "/" + localDate.getYear(), hours);
            }
        }
        adminAvailability.setAdminAvailability(availability);
    }

    public void generateAdminAvailableDayAndHour2() {
        LocalDate localDate = LocalDate.now();
        String dayOfWeek = localDate.getDayOfWeek().toString();
        //String dayOfWeek = "Saturday";

        if (dayOfWeek.equalsIgnoreCase("Saturday")) {
            LocalTime localTime = LocalTime.now();
            int hour = localTime.getHour();
            //int hour = 18;
            if (hour == 18) {
                UserService userService = UserService.getInstance();
                AdminAvailabilityPersistence adminAvailabilityPersistence = new AdminAvailabilityPersistence();
                CustomerDatePersistence customerDatePersistence = new CustomerDatePersistence();
                AdminAvailabilityService adminAvailabilityService = AdminAvailabilityService.getInstance();

                UserLinkedList userLinkedList = userService.getAll();

                if (adminAvailabilityPersistence.deleteAll())
                    System.out.println("Se eliminó admin availability persistence\"");
                else
                    System.out.println("No se eliminó admin availability persistence\"");

                if (customerDatePersistence.deleteAll())
                    System.out.println("Se eliminó admin customer persistence\"");
                else
                    System.out.println("No se eliminó admin customer persistence\"");

                List<String> hours = new ArrayList<>();
                for (int i = 8; i < 18; i++) {
                    hours.add(i + ":00");
                }

                for (int i = 0; i < userLinkedList.size(); i++) {
                    User user = userLinkedList.get(i);
                    if (user.getRol() == 2) {
                        AdminAvailability adminAvailability = new AdminAvailability();
                        Map<String, List> availability = new HashMap<>();
                        for (int j = 1; j < 6; j++) {
                            int dayOfMonth = localDate.getDayOfMonth();
                            //int dayOfMonth = 27;
                            if (dayOfMonth + j + 1 <= 30) {
                                availability.put(dayOfMonth + j + 1 + "/" + localDate.getMonthValue() + "/" + localDate.getYear(), hours);
                            } else {
                                availability.put(dayOfMonth + j - 29 + "/" + (localDate.getMonthValue() + 1) + "/" + localDate.getYear(), hours);
                            }
                        }
                        adminAvailability.setAdminID(user.getID());
                        adminAvailability.setAdminAvailability(availability);
                        adminAvailabilityService.add(adminAvailability);
                    }
                }
            }
        }
    }

    ////////////////////////////////////////////////////////////// GENERADOR ANOTACIONES DE ADMIN EN CITA
    AdminAnnotation adminAnnotation;

    private void refresh() {
        adminAnnotation = new AdminAnnotation();
    }

    public AdminAnnotation generateAnnotations(CustomerDate customerDate) {
        AdminAnnotation adminAnnotation = new AdminAnnotation();
        EatingPlanService eatingPlanService = EatingPlanService.getInstance();
        adminAnnotation.setDate(customerDate.getDate());
        adminAnnotation.setCustomerID(customerDate.getCustomerID());
        adminAnnotation.setDocID(customerDate.getAdminID());
        EatingPlan eatingPlan = eatingPlanService.getByPlanID(5);
        adminAnnotation.setEatingPlan(eatingPlan);

        int[] fat = {16, 17, 18, 19, 20, 21, 22, 23, 24};
        int[] weight = {50, 55, 60, 65, 70, 75, 80, 85, 90};
        int[] height = {110, 115, 120, 125, 130, 135, 140, 145, 150};
        int randomNumber = random(fat.length);
        adminAnnotation.setFat(fat[randomNumber]);
        adminAnnotation.setWeight(weight[randomNumber]);
        adminAnnotation.setHeight(height[randomNumber]);
        return adminAnnotation;
    }

    public void generateRandomAnnotations() {
        refresh();

    }
}
