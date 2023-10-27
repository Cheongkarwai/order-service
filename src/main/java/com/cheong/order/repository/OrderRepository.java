package com.cheong.order.repository;

import com.cheong.domain.order.Order;
import jakarta.persistence.Persistence;
import org.hibernate.reactive.stage.Stage;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletionStage;

@Repository
public class OrderRepository implements IOrderRepository {

    private Stage.SessionFactory sessionFactory;

    public OrderRepository(){
        sessionFactory = Persistence.createEntityManagerFactory("postgres").unwrap(Stage.SessionFactory.class);
    }


    @Override
    public CompletionStage<Order> findById(Stage.Session session, Long id) {
        return session.find(Order.class,id);
    }

    @Override
    public CompletionStage<Void> save(Stage.Session session, Order order) {
        return session.persist(order);
    }
}
