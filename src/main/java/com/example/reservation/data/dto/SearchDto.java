package com.example.reservation.data.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
@ToString
public class SearchDto {
  private int pageNum;
  private int size;
  private int startPage;
  private int endPage;
  private int buttonLimit;
  private int direction; // | 0 asc | 1 desc |
  private String directionColumn;
  private String keyword;

  public SearchDto() {
    this.pageNum = 1;
    this.size = 4;
    this.buttonLimit = 3;
    this.direction = 0;
    this.directionColumn = "shopName";
  }

  public void setPaging(Pageable pageable, int totalPage) {
    int startPageTmp =
      (((int) Math.ceil(((double) pageable.getPageNumber() / this.buttonLimit))) - 1) * this.buttonLimit + 1;

    this.startPage = Math.max(1, startPageTmp);
    this.endPage =  Math.min((startPage + this.buttonLimit - 1), totalPage);
  }
}
