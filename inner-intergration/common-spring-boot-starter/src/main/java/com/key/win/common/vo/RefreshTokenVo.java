package com.key.win.common.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class RefreshTokenVo implements Serializable {

    private String userName;
    private String token;
}
