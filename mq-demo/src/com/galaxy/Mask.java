package com.galaxy;

/**
 * @author lane
 * @date 2021年08月15日 下午3:05
 */
public class Mask {

    private Integer id;
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Mask{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
