package com.emerghelp.emerghelp.data.repositories;

import com.emerghelp.emerghelp.data.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
