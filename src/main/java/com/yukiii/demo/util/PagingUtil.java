package com.yukiii.demo.util;

import com.yukiii.demo.constant.AppConstant;
import com.yukiii.demo.exception.DemoException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PagingUtil {

  public static Pageable buildPaginationUtil(int page, int size, List<String> sort){
    return (sort == null || sort.isEmpty())
      ? PageRequest.of(page, size)
      : PageRequest.of(page, size, Sort.by(buildSortUtil(sort)));
  }

  public static List<Sort.Order> buildSortUtil(List<String> sort){
    List<Sort.Order> orders = new ArrayList<>();

    for (String sortParam: sort){
      if (StringUtils.isBlank(sortParam)) continue;

      String[] sortArr = sortParam.split("\\.");
      if (sortArr.length > AppConstant.PARAM_SORT_COLUMN_DIRECTION){
        throw new DemoException(AppConstant.ResponseConstant.PARAM_INVALID);
      }

      if (sortArr.length == AppConstant.PARAM_SORT_COLUMN_ONLY){
        orders.add(new Sort.Order(buildDirectionUtil("asc"), sortArr[0]));
      } else{
        orders.add(new Sort.Order(buildDirectionUtil(sortArr[1]), sortArr[0]));
      }
    }

    return orders;
  }

  public static Sort.Direction buildDirectionUtil(String direction){
    if (direction.equals("asc")){
      return Sort.Direction.ASC;
    } else if (direction.equals("desc")){
      return Sort.Direction.DESC;
    }

    return Sort.Direction.ASC;
  }
}
