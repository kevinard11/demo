package com.yukiii.demo.repository;

import com.yukiii.demo.entity.Demo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("demoRepository")
public interface DemoRepository extends JpaRepository<Demo, Long> {
}
