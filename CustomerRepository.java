
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//import Entity.CustomerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerRepository {
    Logger logger = LoggerFactory.getLogger(CustomerRepository.class);

    public List<CustomerEntity> showCustomerInformation(Connection con) throws Exception{
        Statement stmt=con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM onlinefoodordering.customers");
        List<CustomerEntity> customers = new ArrayList<>();
        while(rs.next()) {
            customers.add(new CustomerEntity(rs.getString(2),rs.getInt(1),rs.getInt(3)));
        }
        stmt.close();
        return customers;
    }

    public CustomerEntity customerExist(Connection con, long phoneNumber) throws Exception{
        Statement stmt=con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM onlinefoodordering.customers where PhoneNumber="+phoneNumber);
        if(rs.next()) {
            CustomerEntity customer = new CustomerEntity(rs.getString(2),rs.getLong(1),rs.getLong(3));
            stmt.close();
            return customer;
        }
        else {
            stmt.close();
            return null;
        }
    }


    public int InsertCustomerInformation(Connection con, long phoneNumber, String name, long postalCode) throws Exception{
        String sql = "insert into customers (PhoneNumber, Name, PostalCode, RegistrationDate) values (?, ?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setLong(1, phoneNumber);
        pstmt.setString(2, name);
        pstmt.setLong(3, postalCode);
        java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
        pstmt.setDate(4, sqlDate);
        int customerNumber = pstmt.executeUpdate();
        pstmt.close();
        logger.trace("new customer added to DB: phoneNumber:{} name:{} postalCode",phoneNumber,name,sqlDate);
        return customerNumber;
    }


    public void customerInformationEdit(Connection con, long phoneNumber, String name, long postalCode) throws SQLException {
        Statement stmt=con.createStatement();
        stmt.executeUpdate("UPDATE customers SET Name = \""+name+"\", PostalCode = "+postalCode+" where PhoneNumber="
                +phoneNumber+";");
        logger.trace("Information of customer {} is editted. new name:{} new postalCode:{}",phoneNumber,name,postalCode);
        stmt.close();

    }

    @Override
    public String toString() {
        return "CustomerRepository{}";
    }
}
