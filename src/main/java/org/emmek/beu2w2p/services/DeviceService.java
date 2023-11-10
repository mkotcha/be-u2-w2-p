package org.emmek.beu2w2p.services;

import org.emmek.beu2w2p.entities.Device;
import org.emmek.beu2w2p.exception.BadRequestException;
import org.emmek.beu2w2p.exception.NotFoundException;
import org.emmek.beu2w2p.payloads.DevicePostDTO;
import org.emmek.beu2w2p.payloads.DevicePutDTO;
import org.emmek.beu2w2p.repositories.DeviceRepository;
import org.emmek.beu2w2p.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DeviceService {
    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    UserRepository userRepository;

    public Device save(DevicePostDTO body) {
        if (isValidCategory(body.category())) {
            Device device = new Device();
            device.setCategory(body.category());
            device.setState("available");
            return deviceRepository.save(device);
        } else {
            throw new BadRequestException("Device category '" + body.category() + "' is not allowed");
        }
    }

    private boolean isValidCategory(String category) {
        List<String> allowedCategories = Arrays.asList("smartphone", "tablet", "laptop", "smartwatch");
        return allowedCategories.contains(category);
    }

    public Page<Device> getDevices(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return deviceRepository.findAll(pageable);
    }

    public Device findById(long id) {
        return deviceRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(long id) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        deviceRepository.delete(device);
    }

    public Device findByIdAndUpdate(long id, DevicePutDTO body) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        validateUpdateState(device, body.state());
        validateUpdateCategory(device, body.category());
        return deviceRepository.save(device);
    }

    private void validateUpdateState(Device device, String newState) {
        List<String> allowedStates = Arrays.asList("available", "assigned", "maintenance", "disused");
        if (!allowedStates.contains(newState)) {
            throw new BadRequestException("Device state '" + newState + "' is not allowed");
        }
        device.setState(newState);
    }

    private void validateUpdateCategory(Device device, String newCategory) {
        List<String> allowedCategories = Arrays.asList("smartphone", "tablet", "laptop");
        if (!allowedCategories.contains(newCategory)) {
            throw new BadRequestException("Device category '" + newCategory + "' is not allowed");
        }
        device.setCategory(newCategory);
    }

    public Page<Device> getDevicesByUserId(long id, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return deviceRepository.getDevicesByUserId(id, pageable);
    }
}
