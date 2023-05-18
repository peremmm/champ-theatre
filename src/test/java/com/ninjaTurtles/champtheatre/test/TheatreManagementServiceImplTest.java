package com.ninjaTurtles.champtheatre.test;
import com.ninjaTurtles.champtheatre.bean.TheatreBean;
import com.ninjaTurtles.champtheatre.models.Theatre;
import com.ninjaTurtles.champtheatre.repository.TheatreRepository;
import com.ninjaTurtles.champtheatre.service.impl.TheatreManagementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TheatreManagementServiceImplTest {

    private TheatreManagementServiceImpl theatreManagementService;

    @Mock
    private TheatreRepository theatreRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        theatreManagementService = new TheatreManagementServiceImpl(theatreRepository);
    }

    @Test
    public void testGetAllTheatre() {
        // Create a list of test theatres
        List<Theatre> theatres = new ArrayList<>();
        theatres.add(new Theatre(1L, "Theatre 1", Theatre.Status.AVAILABLE, 100, null, null, null));
        theatres.add(new Theatre(2L, "Theatre 2", Theatre.Status.OCCUPIED, 200, null, null, null));
        theatres.add(new Theatre(3L, "Theatre 2", Theatre.Status.MAINTENANCE, 300, null, null, null));

        // Mock the repository response
        when(theatreRepository.findAll()).thenReturn(theatres);

        // Call the service method
        List<TheatreBean> theatreBeans = theatreManagementService.getAllTheatre();

        // Verify the repository method was called
        verify(theatreRepository, times(1)).findAll();

        // Assert the result
        assertEquals(theatres.size(), theatreBeans.size());
        for (int i = 0; i < theatres.size(); i++) {
            Theatre theatre = theatres.get(i);
            TheatreBean theatreBean = theatreBeans.get(i);
            assertEquals(theatre.getId(), theatreBean.getId());
            assertEquals(theatre.getName(), theatreBean.getName());
            assertEquals(theatre.getStatus(), theatreBean.getStatus());
            assertEquals(theatre.getCapacity(), theatreBean.getCapacity());
            assertEquals(theatre.getReservations(), theatreBean.getReservations());
            assertEquals(theatre.getCreatedDate(), theatreBean.getCreatedDate());
            assertEquals(theatre.getModifiedDate(), theatreBean.getModifiedDate());
        }
    }

    @Test
    public void testFindTheatreById() {
        // Create a test theatre
        Theatre theatre = new Theatre(1L, "Theatre 1", Theatre.Status.AVAILABLE, 100, null, null, null);

        // Mock the repository response
        when(theatreRepository.findById(1L)).thenReturn(Optional.of(theatre));

        // Call the service method
        TheatreBean theatreBean = theatreManagementService.findTheatreById(1L);

        // Verify the repository method was called
        verify(theatreRepository, times(1)).findById(1L);

        // Assert the result
        assertEquals(theatre.getId(), theatreBean.getId());
        assertEquals(theatre.getName(), theatreBean.getName());
        assertEquals(theatre.getStatus(), theatreBean.getStatus());
        assertEquals(theatre.getCapacity(), theatreBean.getCapacity());
        assertEquals(theatre.getReservations(), theatreBean.getReservations());
        assertEquals(theatre.getCreatedDate(), theatreBean.getCreatedDate());
        assertEquals(theatre.getModifiedDate(), theatreBean.getModifiedDate());
    }

//    @Test
//    public void testUpdateTheatreDetails() {
//        // Create a test theatre
//        Theatre theatre = new Theatre(1L, "Theatre 1", Theatre.Status.AVAILABLE, 100, null, null, null);
//
//        // Mock the repository response
//        when(theatreRepository.findById(1L)).thenReturn(Optional.of(theatre));
//        when(theatreRepository.save(any(Theatre.class))).thenReturn(theatre);
//
//        // Create a modified theatre object
//        TheatreBean modifiedTheatre = new TheatreBean(1L, "Theatre 1 - Modified", Theatre.Status.AVAILABLE, 200, null, null, null);
//
//        // Call the service method
//        theatreManagementService.updateTheatreDetails(modifiedTheatre);
//
//        // Verify the repository method was called
//        verify(theatreRepository, times(1)).findById(1L);
//        verify(theatreRepository, times(1)).save(theatre);
//
//        // Assert the updated details
//        assertEquals(modifiedTheatre.getName(), theatre.getName());
//        assertEquals(modifiedTheatre.getCapacity(), theatre.getCapacity());
//        assertEquals(modifiedTheatre.getReservations(), theatre.getReservations());
//        assertEquals(modifiedTheatre.getModifiedDate(), theatre.getModifiedDate());
//    }
}

