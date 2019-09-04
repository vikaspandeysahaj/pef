package com.pef.models.repo;

import com.pef.models.Amount;
import com.pef.models.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {
}
