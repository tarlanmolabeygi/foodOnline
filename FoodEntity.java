
public class FoodEntity {
    int id;
    String name;
    int price;
    String category;
    RestaurantEntity resturant;

    public FoodEntity(int id, String name, int price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "FoodEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", resturant=" + resturant +
                '}';
    }
}
