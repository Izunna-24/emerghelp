package com.emerghelp.emerghelp.services;

import com.emerghelp.emerghelp.data.models.Admin;
import com.emerghelp.emerghelp.dtos.requests.ActivateUserRequests;
import com.emerghelp.emerghelp.dtos.requests.DeactivateUserRequests;
import com.emerghelp.emerghelp.dtos.requests.RegisterAdminRequests;
import com.emerghelp.emerghelp.dtos.responses.ActivateUserResponse;
import com.emerghelp.emerghelp.dtos.responses.DeactivateUserResponse;
import com.emerghelp.emerghelp.dtos.responses.RegisterAdminResponse;

public interface AdminService {

    RegisterAdminResponse registerAdmin(RegisterAdminRequests requests);

    Admin getAdminById(Long id);

    DeactivateUserResponse deactivateUserAccount(DeactivateUserRequests requests);
    ActivateUserResponse activateUserAccount(ActivateUserRequests requests);

}
