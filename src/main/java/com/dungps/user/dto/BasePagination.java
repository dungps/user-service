package com.dungps.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasePagination {
    private Integer size;

    private Integer number;

    private Long totalRows;

    private Integer totalPage;
}
