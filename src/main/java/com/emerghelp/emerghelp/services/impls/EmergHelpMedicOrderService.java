package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.data.models.Medic;
import com.emerghelp.emerghelp.data.models.MedicRequest;
import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.data.repositories.MedicRepository;
import com.emerghelp.emerghelp.data.repositories.MedicRequestRepository;
import com.emerghelp.emerghelp.data.repositories.UserRepository;
import com.emerghelp.emerghelp.dtos.requests.MedicRequestDTO;
import com.emerghelp.emerghelp.dtos.responses.MedicRequestResponse;
import com.emerghelp.emerghelp.dtos.responses.OrderMedicHistory;
import com.emerghelp.emerghelp.exceptions.OrderMedicNotFoundException;
import com.emerghelp.emerghelp.exceptions.UserNotFoundException;
import com.emerghelp.emerghelp.services.MedicOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.emerghelp.emerghelp.Utils.LocationUtils.EARTH_RADIUS;
import static com.emerghelp.emerghelp.data.constants.RequestStatus.PENDING;

@Service
public class EmergHelpMedicOrderService implements MedicOrderService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final MedicRequestRepository medicRequestRepository;
    private final MedicRepository medicRepository;

    @Autowired
    public EmergHelpMedicOrderService(UserRepository userRepository,
                                ModelMapper modelMapper,
                                MedicRequestRepository medicRequestRepository,
                                MedicRepository medicRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.medicRequestRepository = medicRequestRepository;
        this.medicRepository = medicRepository;
    }

    @Override
    public MedicRequestResponse orderMedic(MedicRequestDTO medicRequestDTO) {
        User user = userRepository.findById(medicRequestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        MedicRequest medicRequest = buildMedicRequest(medicRequestDTO, user);
        List<Medic> allMedic = medicRepository.findAll();
        List<Medic> availableMedic = allMedic.stream()
                .filter(medic -> calculateDistance(medic, medicRequest) < 30)
                .toList();
        MedicRequestResponse medicRequestResponse = new MedicRequestResponse();
        medicRequestResponse.setAvailableMedic(availableMedic);
        return  medicRequestResponse;
    }
    @Override
    public MedicRequest getMedicOrderBy(long id) {
        return medicRequestRepository.findById(id)
                .orElseThrow(()-> new OrderMedicNotFoundException("No order found"));
    }
    @Override
    public List<OrderMedicHistory> viewAllOrderFor(Long id) {
        List<MedicRequest> medicRequests = medicRequestRepository.viewAllRequestById(id);
        if (medicRequests == null || medicRequests.isEmpty()) {
            return Collections.emptyList();
        }
        return medicRequests.stream().map(emergencyRequestItem -> modelMapper
                .map(emergencyRequestItem, OrderMedicHistory.class)).toList();
    }
    private MedicRequest buildMedicRequest(MedicRequestDTO medicRequestDTO, User user) {
        MedicRequest medicRequest = modelMapper.map(medicRequestDTO, MedicRequest.class);
        medicRequest.setUser(user);
        medicRequest.setDescription(medicRequestDTO.getDescription());
        medicRequest.setRequestStatus(PENDING);
        medicRequest.setLatitude(Double.parseDouble(medicRequestDTO.getLatitude()));
        medicRequest.setLongitude(Double.parseDouble(medicRequestDTO.getLongitude()));
        return medicRequest;
    }
    private Double calculateDistance(Medic medic, MedicRequest medicRequest) {
        double lat1 = Math.toRadians(Double.parseDouble(medic.getLatitude()));
        double lon1 = Math.toRadians(Double.parseDouble(medic.getLongitude()));
        double lat2 = Math.toRadians(medicRequest.getLatitude());
        double lon2 = Math.toRadians(medicRequest.getLongitude());
        double latDiff = lat2 - lat1;
        double lonDiff = lon2 - lon1;
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }
}