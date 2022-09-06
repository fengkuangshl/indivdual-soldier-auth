package com.key.win.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UniqueCodeInfoVo implements Serializable  {
    private String androidId;
    private String serialNumber;
    private String uniqueCode;
    private boolean isOnLine = Boolean.FALSE;
}
