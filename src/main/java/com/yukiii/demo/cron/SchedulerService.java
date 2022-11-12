package com.yukiii.demo.cron;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service("schedulerService")
@EnableSchedulerLock(defaultLockAtMostFor = "${spring.shedlock.defaultLockAtMostFor}")
public class SchedulerService {

  @Scheduled(cron = "${setting.cron.expression}", zone = "${setting.cron.zone}")
  @SchedulerLock(name = "${spring.shedlock.name}")
  public void tryScheduler(){
    log.info("scheduler running");
  }
}
