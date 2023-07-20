package com.eCom.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCom.Model.Order;

public interface OrderRepo extends JpaRepository<Order, Integer>{

}
