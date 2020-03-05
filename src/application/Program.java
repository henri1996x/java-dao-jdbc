package application;


import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) throws ParseException, IllegalStateException {
        Scanner sc = new Scanner(System.in);
        //Select an option to run.

        System.out.print("Do you want to manage Department or Seller? d/s   ");
        char op = sc.next().charAt(0);

        if(op == 'd'){
            System.out.println("Select an option for manage Department:");
            System.out.println("1 - Find by Id");
            System.out.println("2 - Find All");
            System.out.println("3 - Insert a Department");
            System.out.println("4 - Update a Department");
            System.out.println("5 - Delete a Department");
            System.out.print("Please choose one option: ");
            int optionD = sc.nextInt();
            switch (optionD){
                case 1 : departmentFindById();
                break;
                case 2 : departmentFindAll();
                break;
                case 3: departmentInsert();
                break;
                case 4 : departmentUpdate();
                break;
                case 5 : departmentDelete();
                break;
                default:
                    System.out.println("Invalid option!");
            }

        }
        else if (op == 's'){
            System.out.println("Select an option for manage Seller:");
            System.out.println("1 - Find by Id");
            System.out.println("2 - Find All");
            System.out.println("3 - Find by Department");
            System.out.println("4 - Insert a Seller");
            System.out.println("5 - Update a Seller");
            System.out.println("6 - Delete a Seller");
            System.out.print("Please choose one option: ");
            int optionD = sc.nextInt();
            switch (optionD){
                case 1 : sellerFindByid();
                    break;
                case 2 : sellerFindAll();
                    break;
                case 3: sellerFindByDepartment();
                    break;
                case 4: sellerInsert();
                    break;
                case 5 : sellerUpdate();
                    break;
                case 6 : sellerDelete();
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }



    }


    public static void sellerFindByid() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter an id:    ");
        int id = sc.nextInt();
        SellerDao sellerDao = DaoFactory.createSellerDao();
        Seller seller = sellerDao.findById(id);
        System.out.println(seller);
        sc.close();
    }

    public static void sellerFindByDepartment() throws IllegalStateException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Find a Seller by Department:");
        departmentFindAll();
        System.out.println("Enter an DepartmentId:  ");
        int op = sc.nextInt();
        SellerDao sellerDao = DaoFactory.createSellerDao();
        Department department = new Department(op, null);
        List<Seller> list = sellerDao.findByDepartment(department);
        for (Seller obj : list) {
            if(list.isEmpty()){
                throw new IllegalStateException("List is empty!");
            }
            System.out.println(obj);
        }
    }

    public static void sellerFindAll() {
        SellerDao sellerDao = DaoFactory.createSellerDao();
        List<Seller> list1 = sellerDao.findAll();
        for (Seller obj : list1) {
            System.out.println(obj);
        }
    }

    public static void sellerInsert() throws ParseException {
        Locale.setDefault(Locale.US);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        System.out.println("Insert Seller");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a name for new Seller:    ");
        String name = sc.next();
        System.out.println("Enter a email for new Seller:    ");
        String email = sc.next();
        System.out.println("Enter a birth date for new Seller:    ");
        Date date = sdf.parse(sc.next());
        System.out.println("Enter a base salary for new Seller:    ");
        double baseSalary = sc.nextDouble();
        departmentFindAll();
        System.out.println("Enter a Department id for new Seller:    ");
        int depId = sc.nextInt();
        SellerDao sellerDao = DaoFactory.createSellerDao();
        Department department = new Department(depId, null);
        Seller newSeller = new Seller(null, name, email, date, baseSalary, department);
        sellerDao.insert(newSeller);
        System.out.println("Inserted! New Id= " + newSeller.getId());
    }

    public static void sellerUpdate() throws ParseException {
        Locale.setDefault(Locale.US);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Scanner sc = new Scanner(System.in);
        System.out.println("Update Seller");
        sellerFindAll();
        System.out.println("Select an id for Seller: ");
        int op = sc.nextInt();
        SellerDao sellerDao = DaoFactory.createSellerDao();
        Seller seller = new Seller();
        seller = sellerDao.findById(op);
        System.out.println("Enter a name for new Seller:    ");
        String name = sc.next();
        System.out.println("Enter a email for new Seller:    ");
        String email = sc.next();
        System.out.println("Enter a birth date for new Seller:    ");
        Date date = sdf.parse(sc.next());
        System.out.println("Enter a base salary for new Seller:    ");
        double baseSalary = sc.nextDouble();
        seller.setName(name);
        seller.setEmail(email);
        seller.setBirthDate(date);
        seller.setBaseSalary(baseSalary);
        departmentFindAll();
        System.out.println("Enter a Department id for new Seller:    ");
        int depId = sc.nextInt();
        Department department = new Department(depId, null);
        seller.setDepartment(department);
        sellerDao.update(seller);
        System.out.println("Update Complete!");
        }

    public static void sellerDelete() {
        System.out.println("Delete Seller");
        sellerFindAll();
        SellerDao sellerDao = DaoFactory.createSellerDao();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter an Id: ");
        int id = sc.nextInt();
        sellerDao.deleteById(id);
        System.out.println("Delete Complete");
        sc.close();
    }

    public static void departmentFindById(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter an id:    ");
        int id = sc.nextInt();
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        Department dep = departmentDao.findById(id);
        System.out.println(dep);
        sc.close();
    }

    public static void departmentFindAll(){
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        List<Department> list = departmentDao.findAll();
        for (Department d : list) {
            System.out.println(d);
        }
    }

    public static void departmentInsert(){
        System.out.println("Insert Department");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a name for new Department:    ");
        String depName = sc.next();
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        Department newDepartment = new Department(null, depName);
        departmentDao.insert(newDepartment);
        System.out.println("Inserted! New id: " + newDepartment.getId());
        sc.close();
    }

    public static void departmentUpdate(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Update Department");
        departmentFindAll();
        System.out.println("Select an id for Department: ");
        int op = sc.nextInt();
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        Department dep = departmentDao.findById(op);
        System.out.println("Enter a new name for this Department:   ");
        String depName = sc.next();
        dep.setName(depName);
        departmentDao.update(dep);
        System.out.println("Update completed");
    }

    public static void departmentDelete(){
        Scanner sc = new Scanner(System.in);
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        departmentFindAll();
        System.out.print("Enter id for delete ");
        int id = sc.nextInt();
        departmentDao.deleteById(id);
        System.out.println("Delete completed");
        sc.close();
    }
}
