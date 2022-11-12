package com.yukiii.demo.migration;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.callback.Context;
import org.flywaydb.core.api.callback.Event;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class FlywayCallback implements Callback {

  private int count = 0;

  @Override
  public boolean supports(Event event, Context context) {
    return event == Event.AFTER_EACH_MIGRATE;
  }

  @Override
  public boolean canHandleInTransaction(Event event, Context context) {
    return true;
  }

  @Override
  public void handle(Event event, Context context) {

    if(this.count == 0){
      //do something only for the first migration that happen when restart
      log.info("Migration applied");

      count++;
    }
  }

  @Override
  public String getCallbackName() {
    return getClass().getName();
  }
}
