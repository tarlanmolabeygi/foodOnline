import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CLI cli = new CLI();

        try {

            System.out.println("Enter your phone number without the zero:");
            long phoneNumber = scanner.nextLong();
            CustomerEntity customer = new CustomerEntity(phoneNumber);
            System.out.println("Enter your region:");
            int region = scanner.nextInt();

            String category,restaurantName,foodName;
            Integer count;
            FileWriter fw;
            BufferedWriter bf;
            int totalPrice=0;
            boolean a,b;

            while(true) {
                System.out.println("1. Show all restaurants in your region");
                System.out.println("2. Show restaurants in your region with a spicify category");
                System.out.println("3. Exit");
                int n = scanner.nextInt();

                switch(n){
                    case 1://Show restaurants in a specify region then order a food
                        cli.showRestaurant(region);
                        System.out.println("Enter restaurant that you want:");
                        restaurantName = scanner.next();
                        cli.showFoodsOfRestaurant(restaurantName);
                        a = true;
                        while(a) {
                            System.out.println("1. Select a food");
                            System.out.println("2. Show  Cart");
                            System.out.println("3. Finalize the order");
                            System.out.println("4. Rturne to main menu");
                            int operation = scanner.nextInt();

                            switch(operation) {
                                case 1:
                                    scanner.nextLine();
                                    System.out.println("Enter your favorite food:");
                                    foodName = scanner.nextLine();
                                    System.out.println("Enter qauntity of order:");
                                    count = scanner.nextInt();
                                    customer.cartmap.put(cli.choosefood(restaurantName, foodName),count);
                                    break;
                                case 2:
                                    totalPrice =0;
                                    if (customer.cartmap.size()!=0) {
                                        for (Map.Entry<FoodEntity, Integer> entry : customer.cartmap.entrySet()) {
                                            FoodEntity f = (FoodEntity) entry.getKey();
                                            System.out.println("[Name: "+f.name+"  Price: "+f.price+"  Category: "
                                                    +f.category+"  Count: "+entry.getValue()+"]"+"\n");
                                            totalPrice += f.price*entry.getValue();
                                        }
                                        System.out.println(totalPrice);
                                    }
                                    else {
                                        System.out.println("your Shopping Cart is empty!");
                                    }
                                    b = true;
                                    while(b) {
                                        System.out.println("1. delete a food from  Cart");
                                        System.out.println("2. return to previous menu");
                                        int n1 = scanner.nextInt();
                                        switch(n1) {
                                            case 1:
                                                scanner.nextLine();
                                                System.out.println("Enter the food's name that you want to delede:");
                                                String foodName1 = scanner.nextLine();
                                                for (Map.Entry<FoodEntity, Integer> entry : customer.cartmap
                                                        .entrySet()) {
                                                    FoodEntity f = (FoodEntity) entry.getKey();
                                                    if(f.name.equals(foodName1)) {
                                                        customer.cartmap.remove(f);
                                                        break;
                                                    }
                                                }
                                                break;
                                            case 2:
                                                b=false;
                                                break;
                                        }
                                    }

                                    break;
                                case 3:
                                    if(cli.exiteCustomer(phoneNumber) != null) {
                                        Map<FoodEntity, Integer> map = customer.cartmap;
                                        customer = cli.exiteCustomer(phoneNumber);
                                        customer.cartmap = map;
                                        System.out.println(customer.name+"  "+customer.phoneNumber+"  "
                                                +customer.postalCode);
                                        System.out.println("1. Edit  information");
                                        System.out.println("2. Confirm  information");
                                        int operation1 = scanner.nextInt();
                                        switch(operation1) {
                                            case 1:
                                                System.out.println("Enter new username");
                                                customer.name = scanner.next();
                                                System.out.println("Enter new postal code");
                                                customer.postalCode = scanner.nextLong();
                                                cli.customerEdit(phoneNumber, customer.name, customer.postalCode);
                                                System.out.println("Your information edit successfully");
                                                break;
                                            case 2:
                                                break;
                                        }

                                    }
                                    else {
                                        System.out.println("Customer with this phone number doesn't exist already");
                                        System.out.println("Enter your name");
                                        String name = scanner.next();
                                        System.out.println("Enter your postalCode");
                                        long postalCode = scanner.nextLong();
                                        customer.name=name;
                                        customer.postalCode=postalCode;
                                        cli.insetrCustomer(phoneNumber, name, postalCode);
                                    }

                                    int orderNumber = cli.orderInsert(phoneNumber);

                                    totalPrice =0;
                                    fw = new FileWriter("C:\\Users\\User\\Desktop\\"+customer.name+".txt");
                                    bf = new BufferedWriter(fw);
                                    bf.write("Time of order: "+new Timestamp(System.currentTimeMillis())+"\n");
                                    bf.write("Foods"+"\n");
                                    for (Map.Entry<FoodEntity, Integer> entry : customer.cartmap.entrySet()) {
                                        FoodEntity f = (FoodEntity) entry.getKey();
                                        bf.write("[Name: "+f.name+"  Price: "+f.price+"  Category: "+f.category+
                                                "  Count: "+entry.getValue()+"]"+"\n");
                                        cli.orderDetailsInsert(orderNumber, f.id, entry.getValue());
                                        totalPrice += f.price*entry.getValue();
                                    }
                                    bf.write("Total Price= "+totalPrice+"\n");
                                    bf.flush();
                                    bf.close();
                                    System.out.println("Your order has been successfully registered");
                                    break;
                                case 4:
                                    if(customer.cartmap.size()!=0) {
                                        System.out.println("Your cart will be empty. Are you sure? yes/no");
                                        String s = scanner.next();
                                        if(s.equalsIgnoreCase("yes")) {
                                            customer.cartmap.clear();
                                            a=false;
                                        }
                                    }else {a=false;}
                                    break;
                            }
                        }
                        break;
                    case 2:
                        System.out.println("Enter your favorite category:");
                        category = scanner.next();
                        cli.showRestaurantCategory(region, category);

                        System.out.println("Enter your favorite restaurant:");
                        restaurantName = scanner.next();
                        cli.showFoodsCategory(restaurantName, category);

                        a = true;
                        while(a) {
                            System.out.println("1. Select a food");
                            System.out.println("2. Show Shopping Cart");
                            System.out.println("3. Finalize the order");
                            System.out.println("4. Rturn to main menu");
                            int operation = scanner.nextInt();

                            switch(operation) {
                                case 1:
                                    scanner.nextLine();
                                    System.out.println("Enter your favorite food:");
                                    foodName = scanner.nextLine();
                                    System.out.println("Enter qauntity of order:");
                                    count = scanner.nextInt();
                                    customer.cartmap.put(cli.choosefood(restaurantName, foodName),count);
                                    break;
                                case 2:
                                    totalPrice =0;
                                    if (customer.cartmap.size()!=0) {
                                        for (Map.Entry<FoodEntity, Integer> entry : customer.cartmap.entrySet()) {
                                            FoodEntity f = (FoodEntity) entry.getKey();
                                            System.out.println("[Name: "+f.name+"  Price: "+f.price+"  Category: "
                                                    +f.category+"  Count: "+entry.getValue()+"]"+"\n");
                                            totalPrice += f.price*entry.getValue();
                                        }
                                        System.out.println(totalPrice);
                                    }
                                    else {
                                        System.out.println("your Shopping Cart is empty!");
                                    }
                                    b = true;
                                    while(b) {
                                        System.out.println("1. delete a food from Shopping Cart");
                                        System.out.println("2. back previous menu");
                                        int n1 = scanner.nextInt();
                                        switch(n1) {
                                            case 1:
                                                scanner.nextLine();
                                                System.out.println("Enter the food's name that you want to delede:");
                                                String foodName1 = scanner.nextLine();
                                                for (Map.Entry<FoodEntity, Integer> entry : customer.cartmap.entrySet()) {
                                                    FoodEntity f = (FoodEntity) entry.getKey();
                                                    if(f.name.equals(foodName1)) {
                                                        customer.cartmap.remove(f);
                                                        break;
                                                    }
                                                }
                                                break;
                                            case 2:
                                                b=false;
                                                break;
                                        }
                                    }

                                    break;
                                case 3:
                                    if(cli.exiteCustomer(phoneNumber) != null) {
                                        Map<FoodEntity, Integer> map = customer.cartmap;
                                        customer = cli.exiteCustomer(phoneNumber);
                                        customer.cartmap = map;
                                        System.out.println(customer.name+"  "+customer.phoneNumber+"  "+customer.postalCode);
                                        System.out.println("1. Edit youre information");
                                        System.out.println("2. Confirm your information");
                                        int operation1 = scanner.nextInt();
                                        switch(operation1) {
                                            case 1:
                                                System.out.println("Enter  username");
                                                customer.name = scanner.next();
                                                System.out.println("Enter  postal code");
                                                customer.postalCode = scanner.nextLong();
                                                cli.customerEdit(phoneNumber, customer.name, customer.postalCode);
                                                System.out.println("Your information edit successfully");
                                                break;
                                            case 2:
                                                break;
                                        }
                                    }
                                    else {
                                        System.out.println("Customer with this phone number doesn't exist already");
                                        System.out.println("Enter your name");
                                        String name = scanner.next();
                                        System.out.println("Enter your postalCode");
                                        long postalCode = scanner.nextLong();
                                        customer.name=name;
                                        customer.postalCode=postalCode;
                                        cli.insetrCustomer(phoneNumber, name, postalCode);
                                    }

                                    int orderNumber = cli.orderInsert(phoneNumber);

                                    totalPrice =0;
                                    fw = new FileWriter("C:\\Users\\User\\Desktop\\customer.txt");
                                    bf = new BufferedWriter(fw);
                                    bf.write("Time of order: "+new Timestamp(System.currentTimeMillis())+"\n");
                                    bf.write("Foods"+"\n");
                                    for (Map.Entry<FoodEntity, Integer> entry : customer.cartmap.entrySet()) {
                                        FoodEntity f = (FoodEntity) entry.getKey();
                                        bf.write("[Name: "+f.name+"  Price: "+f.price+"  Category: "+f.category
                                                +"  Count: "+entry.getValue()+"]"+"\n");
                                        cli.orderDetailsInsert(orderNumber, f.id, entry.getValue());
                                        totalPrice += f.price*entry.getValue();
                                    }
                                    bf.write("Total Price= "+totalPrice+"\n");
                                    bf.flush();
                                    System.out.println("Your order has been successfully registered");
                                    break;
                                case 4:
                                    if(customer.cartmap.size()!=0) {
                                        System.out.println("Your cart will be empty.");
                                        String s = scanner.next();
                                        if(s.equalsIgnoreCase("yes")) {
                                            customer.cartmap.clear();
                                            a=false;
                                        }
                                    }else {a=false;}
                                    break;
                            }
                        }
                        break;
                    case 3:
                        System.exit(0);
                }

            }
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }

}
