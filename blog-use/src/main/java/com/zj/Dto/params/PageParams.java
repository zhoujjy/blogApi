package com.zj.Dto.params;

import lombok.Data;

@Data
public class PageParams {
    private int page;
    private int pageSize;
    private Long categoryId;
    private Long tagId;
    private String year;
    private String month;

    public String getMonth(){
        if (this.month != null && this.month.length() == 1){
            return "0"+this.month;
        }
        return this.month;
    }
}