
import java.sql.Date;

import java.util.HashMap;

import java.util.Map;

public class CustomerEntity {

    String name;
    long phoneNumber;
    long postalCode;
    Date registrationDate;
    int totalPurchase;
    Map<FoodEntity,Integer> cartmap = new HashMap<FoodEntity,Integer>();

    public CustomerEntity(){}

    public CustomerEntity(long phoneNumber) throws Exception{
        setPhoneNumber(phoneNumber);
    }


    public CustomerEntity(String name, long phoneNumber, long postalCode) throws Exception{
        this.name = name;
        setPhoneNumber(phoneNumber);
        setPostalCode(postalCode);
    }


    public CustomerEntity(String name, long phoneNumber, Date registrationDate, int totalPurchase) throws Exception{
        this.name = name;
        setPhoneNumber(phoneNumber);
        this.registrationDate = registrationDate;
        this.totalPurchase = totalPurchase;
    }


    public String getName() {
        return name;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public long getPostalCode() {
        return postalCode;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(long phoneNumber) throws Exception{
        if(String.valueOf(phoneNumber).matches("^\\d{10}$"))
            this.phoneNumber = phoneNumber;
        else
            throw new Exception("phoneNumberException");
    }

    public void setPostalCode(long postalCode) throws Exception{
        if(String.valueOf(postalCode).matches("^\\d{10}$"))
            this.postalCode = postalCode;
        else
            throw new Exception("postalCodeException");
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "name='" + name + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", postalCode=" + postalCode +
                ", registrationDate=" + registrationDate +
                ", totalPurchase=" + totalPurchase +
                ", cart=" + cartmap +
                '}';
    }
}
