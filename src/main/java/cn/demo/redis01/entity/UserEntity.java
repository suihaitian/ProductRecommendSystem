package cn.demo.redis01.entity;

public class UserEntity {
    private int id;
    private InterestEntity interestEntity;

    public UserEntity(){

    }

    public UserEntity(int id, InterestEntity interestEntity) {
        this.id = id;
        this.interestEntity = interestEntity;
    }

    public UserEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public InterestEntity getInterestEntity() {
        return interestEntity;
    }

    public void setInterestEntity(InterestEntity interestEntity) {
        this.interestEntity = interestEntity;
    }
}
