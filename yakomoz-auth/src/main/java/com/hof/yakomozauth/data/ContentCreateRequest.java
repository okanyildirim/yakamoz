package com.hof.yakomozauth.data;

import com.hof.yakomozauth.common.Utility;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ContentCreateRequest {
    private String name;

    public void validate(){
        Utility.notNullAndLessThan(this.name,"Name", 1,500);
    }
}
