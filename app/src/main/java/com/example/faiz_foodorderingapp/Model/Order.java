package com.example.faiz_foodorderingapp.Model;

public class Order {
    private Integer OrderId;
    private String OrderDueDate;
    private String CustomerName;
    private String CustomerAddress;
    private String CustomerPhone;
    private String OrderTotal;
    private String City;
    private String Country;

    public Order() {

    }

    public Order(Integer orderId, String orderDueDate, String customerName, String customerAddress, String customerPhone,String orderTotal,String city,String country) {
        OrderId = orderId;
        OrderDueDate = orderDueDate;
        CustomerName = customerName;
        CustomerAddress = customerAddress;
        CustomerPhone = customerPhone;
        OrderTotal = orderTotal;
        City = city;
        Country = country;
    }

    public Integer getOrderId() {
        return OrderId;
    }

    public void setOrderId(Integer orderId) {
        OrderId = orderId;
    }

    public String getOrderDueDate() {
        return OrderDueDate;
    }

    public void setOrderDueDate(String orderDueDate) {
        OrderDueDate = orderDueDate;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        CustomerAddress = customerAddress;
    }

    public String getCustomerPhone() {
        return CustomerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        CustomerPhone = customerPhone;
    }

    public String getOrderTotal() {
        return OrderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        OrderTotal = orderTotal;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }
}
