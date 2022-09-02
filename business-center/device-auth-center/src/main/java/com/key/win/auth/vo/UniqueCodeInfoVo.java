package com.key.win.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UniqueCodeInfoVo {
    private String androidId;
    private String serialNumber;
    private String uniqueCode;
    private boolean isOnLine = Boolean.FALSE;
}
