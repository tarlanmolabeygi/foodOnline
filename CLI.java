
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class CLI {

    Connection con;

    public CLI () {
        con = ConnectionManager.connectionManager.getConnection();
    }


    public void readRestaurantsFiles() throws Exception{
        RestaurantRepository resturantEntityManager = new RestaurantRepository();
        resturantEntityManager.readRestaurantFile(con);
    }


    public void showRestaurant(int zone) throws Exception {
        RestaurantRepository resturantEntityManager = new RestaurantRepository();
        Map<RestaurantEntity, String> restaurants = resturantEntityManager.showRestaurant(this.con, zone);
        System.out.println("Resturant Name"+"  shipping Cost"+ "   Food Category");
        for(Map.Entry entry : restaurants.entrySet()) {
            RestaurantEntity r = (RestaurantEntity)entry.getKey();
            System.out.println(r.name+"               "+r.deliveryCost +"           "+entry.getValue());
        }

    }


    public void showRestaurantCategory(int region, String category) throws Exception {
        RestaurantRepository resturantEntityManager = new RestaurantRepository();
        Set<RestaurantEntity> resturants = resturantEntityManager.showResturantHasSpecificCategory(this.con, region, category);
        System.out.println("Resturant Name"+"  shipping Cost");
        for(RestaurantEntity r : resturants) {
            System.out.println(r.name+"               "+r.deliveryCost +"           ");
        }

    }


    public void showFoodsOfRestaurant(String restaurantName) throws Exception {
        RestaurantRepository restaurantEntity = new RestaurantRepository();
        List<FoodEntity> foods = restaurantEntity.showFoodsOfRestaurant(con, restaurantName);
        System.out.println("Food Name"+"            Prcie" + "             Category");
        for(FoodEntity f : foods) {
            System.out.println(f.name+"        "+f.price+"           "+ "    "+f.category);
        }
    }


    public void showFoodsCategory(String restaurantName, String category) throws Exception {
        RestaurantRepository resturantEntity = new RestaurantRepository();
        List<FoodEntity> foods = resturantEntity.showFoodsWithCategory(con, restaurantName, category);
        System.out.println("Food Name"+"            Prcie" + "             Category");
        for(FoodEntity f : foods) {
            System.out.println(f.name+"        "+f.price+"           "+ "    "+f.category);
        }
    }


    public FoodEntity choosefood(String restaurantName, String foodName) throws Exception {
        RestaurantRepository resturantEntity = new RestaurantRepository();
        List<FoodEntity> foods = resturantEntity.showFoodsOfRestaurant(con, restaurantName);
        for(FoodEntity f : foods) {
            if(f.name.equalsIgnoreCase(foodName))
                return f;
        }
        return null;
    }


    public void showCustomers() throws Exception{
        CustomerRepository customerEntityManager = new CustomerRepository();
        List<CustomerEntity> customers = customerEntityManager.showCustomerInformation(con);
        for(CustomerEntity c: customers) {
            System.out.println(c.name+"  "+c.phoneNumber+"  "+c.postalCode);
        }
    }


    public CustomerEntity exiteCustomer(long phoneNumber) throws Exception {
        CustomerRepository customerEntity = new CustomerRepository();
        if(customerEntity.customerExist(con, phoneNumber) != null)
            return customerEntity.customerExist(con, phoneNumber);
        else {
            return null;
        }
    }


    public void insetrCustomer(long phoneNumber, String name, long postalCode) throws Exception{
        CustomerRepository customerEntity = new CustomerRepository();
        customerEntity.InsertCustomerInformation(con, phoneNumber, name, postalCode);
        System.out.println("new customer aded successfull.");
    }

    public void customerEdit(long phoneNumber, String name, long postalCode) throws SQLException {
        CustomerRepository customerManager = new CustomerRepository();
        customerManager.customerInformationEdit(con, phoneNumber, name, postalCode);
    }


    public int orderInsert(long phoneNumber) throws Exception{
        OrderRepository orderEntity = new OrderRepository();
        return orderEntity.InserOrderInDb(con, phoneNumber);
    }


    public void orderDetailsInsert(int orderNumber, int foodNumber, int quantity) throws Exception{
        OrderRepository orderEntity = new OrderRepository();
        orderEntity.InsertOrderDetailsInDb(con, orderNumber, foodNumber, quantity);
    }

    @Override
    public String toString() {
        return "CLI{" +
                "con=" + con +
                '}';
    }
}
