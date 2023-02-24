package cn.demo.redis01.entity;

import java.util.HashSet;
import java.util.Set;

public class InterestEntity {
    private String cat;
    private String color;
    private String city;
    private int kuanshi;
    private int size;
    private int liangzedu;
    private double wcat=0.6;
    private double wcolor=0.1;
    private double wcity=0.03;
    private double wkuanshi=0.2;
    private double wsize=0.03;
    private double wliangzedu;

    Set<String> colors=new HashSet(){{
        add("red");
        add("yellow");
        add("blue");
        add("white");
    }};

    Set<String> cats=new HashSet(){{
        add("shoe");
        add("clothes");
        add("lip-sticks");
        add("hat");
        add("fake-hair");
    }};

    Set<String> cities=new HashSet(){{
        add("hengyang");
        add("chnagsha");
        add("wuhan");
        add("huangshi");
        add("beijing");
    }};

    Set<Integer> kuanshis=new HashSet(){{
        add("1");
        add("2");
        add("3");
        add("4");
    }};

    Set<Integer> sizes=new HashSet(){{
        add("37");
        add("38");
        add("39");
        add("40");
        add("41");
        add("42");
        add("43");
    }};

    Set<Integer> liangzedus=new HashSet(){{
        add("0");
        add("1");
        add("2");
        add("3");
        add("4");
        add("5");
    }};

    public InterestEntity(){

    }

    public double getWsize() {
        return wsize;
    }

    public void setWsize(double wsize) {
        this.wsize = wsize;
    }

    public double getWliangzedu() {
        return wliangzedu;
    }

    public void setWliangzedu(double wliangzedu) {
        this.wliangzedu = wliangzedu;
    }

    public InterestEntity(String cat, String color, String city, int kuanshi, int size, int liangzedu, double wsize, double wliangzedu) {
        this.cat=cat;
        this.color = color;
        this.city = city;
        this.kuanshi = kuanshi;
        this.size = size;
        this.liangzedu = liangzedu;
        this.wsize=cat.equals("shoe")?0.1:0.02;
        this.wliangzedu=cat.equals("fake-hair")?0.6:0.04;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getKuanshi() {
        return kuanshi;
    }

    public void setKuanshi(int kuanshi) {
        this.kuanshi = kuanshi;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getLiangzedu() {
        return liangzedu;
    }

    public void setLiangzedu(int liangzedu) {
        this.liangzedu = liangzedu;
    }

    public double compare(InterestEntity interestEntity0) {
        double sum=0;
        double intersect=0;

        if(this.cat!=null){
            sum+=wcat;
            if(interestEntity0.cat!=null){
                if(this.cat.equals(interestEntity0.getCat())){
                    intersect+=wcat;
                }else sum++;
            }
        }

        if(this.color!=null){
            sum+=wcolor;
            if(interestEntity0.color!=null){
                if(this.color.equals(interestEntity0.getColor())){
                    intersect+=wcolor;
                }else sum+=wcolor;
            }
        }

        if(this.city!=null){
            sum+=wcity;
            if(interestEntity0.city!=null){
                if(this.city.equals(interestEntity0.getCity())){
                    intersect+=wcity;
                }else sum+=wcity;
            }
        }

        if(this.kuanshi>=0){
            sum++;
            if(interestEntity0.kuanshi>=0){
                if(this.kuanshi==interestEntity0.getKuanshi()){
                    intersect+=wkuanshi;
                }else sum++;
            }
        }

        if(this.size>=0){
            sum++;
            if(interestEntity0.size>=0){
                if(this.size==interestEntity0.getSize()){
                    intersect+=wsize;
                }else sum++;
            }
        }

        if(this.liangzedu>=0){
            sum++;
            if(interestEntity0.liangzedu>=0){
                if(this.liangzedu==interestEntity0.getLiangzedu()){
                    intersect+=wliangzedu;
                }else sum++;
            }
        }

        if(sum>0){
          return  (intersect/sum);
        }
        return 0.0;
    }

    public double compareD(InterestEntity interestEntity0){
        double sumD=0.0;
        if(this.getCat()!=null&&interestEntity0.getCat()!=null)
            sumD+=(this.getCat().equals(interestEntity0.getCat())?0:1)*(this.wcat);
        if(this.getColor()!=null&&interestEntity0.getColor()!=null)
            sumD+=(this.getColor().equals(interestEntity0.getColor())?0:1)*(this.wcolor);
        if(this.getCity()!=null&&interestEntity0.getCity()!=null)
            sumD+=(this.getCity().equals(interestEntity0.getCity())?0:1)*(this.wcity);
        if(this.getKuanshi()>=0&&interestEntity0.getKuanshi()>=0)
            sumD+=Math.pow((this.getKuanshi()-interestEntity0.getKuanshi())/(18f/4),2)*(this.wkuanshi);
        if(this.getSize()>=0&&interestEntity0.getSize()>=0)
            sumD+=Math.pow((this.getSize()-interestEntity0.getSize())/(280/7f),2)*(this.wsize);
        if(this.getLiangzedu()>=0&&interestEntity0.getLiangzedu()>=0)
            sumD+=Math.pow((this.getLiangzedu()-interestEntity0.getLiangzedu())/(15/6f),2)*(this.wliangzedu);
        return sumD;
    }
}
