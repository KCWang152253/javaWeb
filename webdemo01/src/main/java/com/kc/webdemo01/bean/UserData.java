package com.kc.webdemo01.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author KCWang
 * @version 1.0
 * @date 2023/6/25 上午11:39
 */

@NoArgsConstructor
@Data
@AllArgsConstructor
public class UserData implements Serializable {
    private Integer userId;
    private String userName;
    private Integer gender;
    private String birthday;
    private String totalTravalRoad;
    private String totalTravalTime;

    @Override
    public String toString() {
        return "UserData{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", gender=" + gender +
                ", birthday='" + birthday + '\'' +
                ", totalTravalRoad='" + totalTravalRoad + '\'' +
                ", totalTravalTime='" + totalTravalTime + '\'' +
                '}';
    }
}

