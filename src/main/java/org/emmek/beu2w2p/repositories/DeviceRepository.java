package org.emmek.beu2w2p.repositories;

import org.emmek.beu2w2p.entities.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Page<Device> getDevicesByUserId(long id, Pageable pageable);
}