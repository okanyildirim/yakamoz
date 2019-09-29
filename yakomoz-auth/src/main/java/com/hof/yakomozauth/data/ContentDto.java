package com.hof.yakomozauth.data;

import com.hof.yakomozauth.entity.ContentState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentDto {
    private Long id;
    private String name;
    private ContentState state;
}
