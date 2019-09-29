package com.hof.yakamozauth.data;

import com.hof.yakamozauth.common.Utility;
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
