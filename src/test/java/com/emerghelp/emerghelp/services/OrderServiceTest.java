package com.emerghelp.emerghelp.services;

import com.emerghelp.emerghelp.dtos.responses.OrderMedicHistory;
import com.emerghelp.emerghelp.services.impls.EmergHelpMedicOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrderServiceTest {
    @Autowired
    private EmergHelpMedicOrderService orderService;

    @Test
    public void getRequestHistoryTest() {
        Long userId = 200L;
        List<OrderMedicHistory> orderHistory = orderService.viewAllOrderFor(200L);
        assertThat(orderHistory).hasSize(1);

    }
}
