package com.cheong.order.service;

import com.cheong.domain.order.Order;
import com.cheong.domain.order.OrderItem;
import com.cheong.domain.order.dto.OrderDTO;
import com.cheong.order.repository.IOrderRepository;
import jakarta.persistence.Persistence;
import org.hibernate.reactive.stage.Stage;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;

@Service
public class OrderService implements IOrderService{

    private final IOrderRepository orderRepository;
    private final Stage.SessionFactory sessionFactory;


    public OrderService(IOrderRepository orderRepository, Stage.SessionFactory sessionFactory){
        this.orderRepository = orderRepository;
        this.sessionFactory = sessionFactory;
    }

    @Bean
    public Consumer<Flux<OrderDTO>> orderEvent(){
        return order->{
            order.concatMap(this::placeOrder)
                            .subscribe();
        };
    }


    @Bean
    public Consumer<Flux<String>> consumePayment(){
        return s->
//            s.concatMap(value -> Mono.fromCompletionStage(sessionFactory.withSession(session -> orderRepository.findById(session, 1L).thenApply(e -> "hi"))))
//                    .subscribe();


            s.concatMap(value -> {
                System.out.println(value);
               return Mono.fromCompletionStage(sessionFactory.withSession(session -> orderRepository.findById(session, 1L).thenApply(e -> "hi")));
            })
                    .subscribe();
            //Mono.fromCompletionStage(sessionFactory.withSession(session -> orderRepository.findById(session, 1L))).subscribe();
//            return "Hi";


//            return Mono.fromCompletionStage(sessionFactory.withSession(session -> orderRepository.findById(session,1L).thenApply(e->"hi")));


    }


    @Override
    public Mono<Void> placeOrder(OrderDTO orderDTO){

       //

        return Mono.fromCompletionStage(
                sessionFactory.withTransaction((session, transaction) -> {

                    List<OrderItem> orderItems = orderDTO.getItems().stream()
                            .map(items->new OrderItem(items.getQuantity()))
                            .toList();

                    System.out.println(orderItems);

                    Order order = Order.builder()
                            .paymentId(orderDTO.getPaymentId())
                            .build();

                    order.addAllOrderItems(orderItems);

                    return orderRepository.save(session,order);
                })
        );



    }


}
