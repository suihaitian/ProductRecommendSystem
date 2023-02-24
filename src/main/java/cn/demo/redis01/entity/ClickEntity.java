package cn.demo.redis01.entity;

public class ClickEntity {
    private Long userId;
    private Long productId;

    public ClickEntity(Long userId,Long productId) {
        this.userId=userId;
        this.productId=productId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
