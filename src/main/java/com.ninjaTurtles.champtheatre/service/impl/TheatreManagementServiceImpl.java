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
    private TheatreRepository theatreRepository;

    @Autowired
    public TheatreManagementServiceImpl(TheatreRepository theatreRepository) {
        this.theatreRepository = theatreRepository;
    }

    @Override
    public List<TheatreBean> getAllTheatre() {
        List<Theatre> theatres = theatreRepository.findAll();
        return theatres.stream().map((theatre) -> mapToTheatreBean(theatre)).collect(Collectors.toList());
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
            existingTheatre.setUpdateOn(theatre.getUpdateOn());
            theatreRepository.save(existingTheatre);
        }
    }

    private TheatreBean mapToTheatreBean(Theatre theatre) {
        TheatreBean theatreBean = TheatreBean.builder()
                .name(theatre.getName())
                .status(theatre.getStatus())
                .capacity(theatre.getCapacity())
                .reservations(theatre.getReservations())
                .createdOn(theatre.getCreatedOn())
                .updateOn(theatre.getUpdateOn())
                .build();
        return theatreBean;
    }

}
