package com.yukiii.demo.repository;

import com.yukiii.demo.entity.EventStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository("eventStoreRepository")
public interface EventStoreRepository extends JpaRepository<EventStore, UUID>, CrudRepository<EventStore, UUID> {

  @Query(
    value = "select * from event_store es \n" +
      "where es.published_at is null and exchange = :exchange \n" +
      "and routing_key = :routingKey ",
    nativeQuery = true
  )
  List<EventStore> findAllUnpublished(
    @Param("exchange") String exchange,
    @Param("routingKey") String routingKey
  );

}
