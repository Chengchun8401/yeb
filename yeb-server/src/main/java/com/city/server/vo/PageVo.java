package com.city.server.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class PageVo {

    @NonNull
    private Long total;

    @NonNull
    private List<?> data;

}
