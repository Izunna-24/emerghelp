package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.data.models.Medic;
import com.emerghelp.emerghelp.data.models.OrderMedic;
import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.data.repositories.MedicRepository;
import com.emerghelp.emerghelp.data.repositories.OrderMedicRepository;
import com.emerghelp.emerghelp.data.repositories.UserRepository;
import com.emerghelp.emerghelp.dtos.requests.AcceptOrderMedicDTO;
import com.emerghelp.emerghelp.dtos.requests.OrderMedicDTO;

import com.emerghelp.emerghelp.dtos.responses.AcceptOrderMedicResponse;
import com.emerghelp.emerghelp.dtos.responses.OrderMedicResponse;
import com.emerghelp.emerghelp.dtos.responses.OrderMedicHistory;
import com.emerghelp.emerghelp.exceptions.*;
import com.emerghelp.emerghelp.services.MedicOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.emerghelp.emerghelp.Utils.LocationUtils.EARTH_RADIUS;
import static com.emerghelp.emerghelp.data.constants.OrderMedicStatus.ACCEPTED;
import static com.emerghelp.emerghelp.data.constants.OrderMedicStatus.PENDING;

@Service
public class EmergHelpMedicOrderService implements MedicOrderService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final OrderMedicRepository orderMedicRepository;
    private final MedicRepository medicRepository;

    @Autowired
    public EmergHelpMedicOrderService(UserRepository userRepository,
                                ModelMapper modelMapper,
                                OrderMedicRepository orderMedicRepository,
                                MedicRepository medicRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.orderMedicRepository = orderMedicRepository;
        this.medicRepository = medicRepository;
    }


    @Override
    public OrderMedicResponse orderMedic(OrderMedicDTO orderMedicDTO) {
        validateCoordinates(orderMedicDTO.getLatitude(), orderMedicDTO.getLongitude());
        User user = userRepository.findById(orderMedicDTO.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        OrderMedic orderMedic = buildMedicRequest(orderMedicDTO, user);
        List<Medic> availableMedic = medicRepository.findAvailableMedicsWithinDistance(
                orderMedic.getLatitude(),
                orderMedic.getLongitude(),
                30
        );
        if (availableMedic.isEmpty()) {
            throw new MedicNotFoundException("No medic close to you");
        }
        OrderMedicResponse medicRequestResponse = new OrderMedicResponse();
        medicRequestResponse.setAvailableMedic(availableMedic);
        return medicRequestResponse;
    }



    @Override
    public OrderMedic getMedicOrderBy(long id) {
        return orderMedicRepository.findById(id)
                .orElseThrow(()-> new OrderMedicNotFoundException("No order found"));
    }

    @Override
    public AcceptOrderMedicResponse acceptOrder(AcceptOrderMedicDTO acceptOrderMedic) {
        OrderMedic orderMedic = orderMedicRepository.findById(acceptOrderMedic.getOrderId())
                .orElseThrow(() -> new OrderMedicNotFoundException("Order not found"));
        Medic medic = medicRepository.findById(acceptOrderMedic.getMedicId())
                .orElseThrow(() -> new MedicNotFoundException("Medic not found"));

        if (calculateDistance(medic, orderMedic) >= 30) {
            throw new MedicTooFarException("Medic too far to accept this order");
        }

        orderMedic.setAssignedMedic(medic);
        orderMedic.setOrderMedicStatus(ACCEPTED);
        orderMedicRepository.save(orderMedic);

        AcceptOrderMedicResponse response = modelMapper.map(orderMedic, AcceptOrderMedicResponse.class);
        response.setOrderId(orderMedic.getId());
        response.setMedicId(medic.getId());
        response.setStatus(ACCEPTED);
        return response;
    }



    @Override
    public List<OrderMedicHistory> viewAllOrderFor(Long id) {
        List<OrderMedic> orderMedics = orderMedicRepository.findMedicRequestByUserId(id);
        if (orderMedics == null || orderMedics.isEmpty()) {
            return Collections.emptyList();
        }
        return orderMedics.stream().map(orderMedic -> new OrderMedicHistory()).toList();
    }
    private OrderMedic buildMedicRequest(OrderMedicDTO orderMedicDTO, User user) {
        OrderMedic orderMedic = modelMapper.map(orderMedicDTO, OrderMedic.class);
        orderMedic.setUser(user);
        orderMedic.setDescription(orderMedicDTO.getDescription());
        orderMedic.setOrderMedicStatus(PENDING);
        orderMedic.setLatitude(orderMedicDTO.getLatitude());
        orderMedic.setLongitude(orderMedicDTO.getLongitude());
        return orderMedic;
    }
    private Double calculateDistance(Medic medic, OrderMedic orderMedic) {
        double lat1 = Math.toRadians(medic.getLatitude());
        double lon1 = Math.toRadians(medic.getLongitude());
        double lat2 = Math.toRadians(orderMedic.getLatitude());
        double lon2 = Math.toRadians(orderMedic.getLongitude());
        double latDiff = lat2 - lat1;
        double lonDiff = lon2 - lon1;
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

    private void validateCoordinates(double latitude, double longitude) {
        if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
            throw new InvalidCoordinatesException("Latitude and longitude must be valid geographical coordinates.");
        }
    }

}