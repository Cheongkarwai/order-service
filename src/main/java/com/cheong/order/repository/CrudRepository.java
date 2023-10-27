package com.cheong.order.repository;

import com.cheong.domain.order.Order;
import org.hibernate.reactive.stage.Stage;

import java.util.concurrent.CompletionStage;

public interface CrudRepository<R,ID> {

    CompletionStage<R> findById(Stage.Session session, ID id);

    CompletionStage<Void> save(Stage.Session session, Order order);

}
