package com.yukiii.demo.util;

import com.yukiii.demo.entity.Demo;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class SpecificationUtil {

  public static Specification<Demo> hasDemoIds(List<String> demoId){
    return (root, query, criteriaBuilder) -> demoId.isEmpty() ? null : root.get("demoId").in(demoId);
  }

  public static Specification<Demo> hasDemoName(String demoName){
    return (root, query, criteriaBuilder) -> demoName.isEmpty() ? null :criteriaBuilder.equal(root.get("demoName"), demoName);
  }

  public static Specification<Demo> hasLevel(Integer level){
    return (root, query, criteriaBuilder) -> level == null ? null : criteriaBuilder.equal(root.get("level"), level);
  }

  public static Specification<Demo> isActive(){
    return (root, query, criteriaBuilder) ->
      criteriaBuilder.and(
        criteriaBuilder.equal(root.get("active"), Boolean.TRUE),
        criteriaBuilder.isNull(root.get("deletedAt"))
      );
  }
}
