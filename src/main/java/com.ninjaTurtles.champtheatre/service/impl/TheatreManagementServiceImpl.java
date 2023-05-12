package com.ninjaTurtles.champtheatre.service.impl;

import com.ninjaTurtles.champtheatre.bean.TheatreBean;
import com.ninjaTurtles.champtheatre.models.Theatre;
import com.ninjaTurtles.champtheatre.repository.TheatreRepository;
import com.ninjaTurtles.champtheatre.service.TheatreManagementService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public void changeTheatreStatus(Theatre theatre) {
        Theatre existingTheatre = theatreRepository.findById(theatre.getId()).orElse(null);
        if(existingTheatre != null) {
            existingTheatre.setStatus(theatre.getStatus());
            theatreRepository.save(existingTheatre);
        }
    }

    @Override
    public void updateTheatreDetails(Theatre theatre) {
        Theatre existingTheatre = theatreRepository.findById(theatre.getId()).orElse(null);
        if(existingTheatre != null) {
            existingTheatre.setName(theatre.getName());
            existingTheatre.setCapacity(theatre.getCapacity());
            existingTheatre.setReservations(theatre.getReservations());
            theatreRepository.save(existingTheatre);
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

}
