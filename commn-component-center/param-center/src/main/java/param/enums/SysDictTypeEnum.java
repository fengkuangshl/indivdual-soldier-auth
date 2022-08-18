package param.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.key.win.basic.enumjson.TextureEnumSerializerCode;

@JsonSerialize(using = TextureEnumSerializerCode.class)
public enum SysDictTypeEnum {

    NORMAL(0, "普通"),
    TREE(1, "树");
    @EnumValue
    private int code;
    private String text;

    private SysDictTypeEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }


    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}
