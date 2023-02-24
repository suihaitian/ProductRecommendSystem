package cn.demo.redis01.entity;

public class ProductEntity {

    private int id=0;
    private String productName = "";
    private String productImg = "";
    private int productCount = 0;
    private String brief = "";
    private double price=0;
    private String brand="";

    // 构造函数
    public ProductEntity(){

    }

    public ProductEntity(String productName, String productImg) {
        this.productName = productName;
        this.productImg = productImg;
    }

    public ProductEntity(String productName, String productImg, int productCount) {
        this.productName = productName;
        this.productImg = productImg;
        this.productCount = productCount;
    }

    public ProductEntity(int id,String productName, String productImg, int productCount) {
        this.id=id;
        this.productName = productName;
        this.productImg = productImg;
        this.productCount = productCount;
    }

    public ProductEntity(int id, String productName, String productImg, int productCount, String brief, double price, String brand) {
        this.id = id;
        this.productName = productName;
        this.productImg = productImg;
        this.productCount = productCount;
        this.brief = brief;
        this.price = price;
        this.brand = brand;
    }

    // set和get方法
    public void setId(int id) {
        this.id =id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public int getId(){return id;}

    public String getProductName() {
        return productName;
    }

    public String getProductImg() {
        return productImg;
    }

    public int getProductCount() {
        return productCount;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productImg='" + productImg + '\'' +
                ", productCount=" + productCount +
                ", brief='" + brief + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                '}';
    }
}
