package com.fondef.ordenes.RepositorioOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fondef.ordenes.modelo.entity.Order;



public interface RepositorioOrder extends JpaRepository<Order, Long>{


    
}
