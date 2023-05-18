package com.ninjaTurtles.champtheatre.service.impl;

import com.ninjaTurtles.champtheatre.bean.TheatreBean;
import com.ninjaTurtles.champtheatre.models.Theatre;
import com.ninjaTurtles.champtheatre.repository.TheatreRepository;
import com.ninjaTurtles.champtheatre.service.EmployeeManagementService;
import com.ninjaTurtles.champtheatre.service.TheatreManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TheatreManagementServiceImpl implements TheatreManagementService {
    private final TheatreRepository theatreRepository;

    @Autowired
    public TheatreManagementServiceImpl(TheatreRepository theatreRepository) {
        this.theatreRepository = theatreRepository;
    }

    @Override
    public List<TheatreBean> getAllTheatre() {

        List<Theatre> theatres = theatreRepository.findAll();
        return theatres.stream().map(this::mapToTheatreBean).collect(Collectors.toList());
    }

    @Override
    public TheatreBean findTheatreById(Long theatreId) {
        Theatre theatre = theatreRepository.findById(theatreId).get();
        return mapToTheatreBean(theatre);
    }

//    @Override
//    public void changeTheatreStatus(Theatre theatre) {
//        Theatre existingTheatre = theatreRepository.findById(theatre.getId()).orElse(null);
//        if(existingTheatre != null) {
//            existingTheatre.setStatus(theatre.getStatus());
//            theatreRepository.save(existingTheatre);
//        }
//    }

    @Override
    public void updateTheatreDetails(TheatreBean theatreBean) {

        Theatre theatre = mapToTheatre(theatreBean);
        Long theatreId = theatreBean.getId(); // Store the theater ID in a variable
        Theatre existingTheatre = theatreRepository.findById(theatreId).orElse(null); // Find the existing theater by ID

        if (existingTheatre != null) {
            existingTheatre.setName(theatre.getName()); // Update the name
            existingTheatre.setCapacity(theatre.getCapacity()); // Update the capacity
            existingTheatre.setStatus(theatre.getStatus()); // Update the status

            theatreRepository.save(existingTheatre); // Save the updated theater
        }
    }


    private TheatreBean mapToTheatreBean(Theatre theatre) {
        return TheatreBean.builder()
                .id(theatre.getId())
                .name(theatre.getName())
                .status(theatre.getStatus())
                .capacity(theatre.getCapacity())
                .reservations(theatre.getReservations())
                .createdDate(theatre.getCreatedDate())
                .modifiedDate(theatre.getModifiedDate())
                .build();
    }

    private Theatre mapToTheatre(TheatreBean theatreBean) {
        return Theatre.builder()
                .id(theatreBean.getId())
                .name(theatreBean.getName())
                .status(theatreBean.getStatus())
                .capacity(theatreBean.getCapacity())
                .reservations(theatreBean.getReservations())
                .createdDate(theatreBean.getCreatedDate())
                .modifiedDate(theatreBean.getModifiedDate())
                .build();
    }

}
