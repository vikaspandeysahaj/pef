package com.pef.models.repo;

import com.pef.models.Event;
import com.pef.models.PaymentCards;
import org.springframework.data.repository.CrudRepository;

public interface PaymentCardsRepository extends CrudRepository<PaymentCards, Long> {
}
