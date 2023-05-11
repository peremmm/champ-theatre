package com.ninjaTurtles.champtheatre.test;

import com.ninjaTurtles.champtheatre.bean.RoleBean;
import com.ninjaTurtles.champtheatre.bean.TheatreBean;
import com.ninjaTurtles.champtheatre.models.Role;
import com.ninjaTurtles.champtheatre.models.RoleModule;
import com.ninjaTurtles.champtheatre.models.Theatre;
import com.ninjaTurtles.champtheatre.repository.RoleRepository;
import com.ninjaTurtles.champtheatre.repository.TheatreRepository;
import com.ninjaTurtles.champtheatre.service.impl.RoleManagementServiceImpl;
import com.ninjaTurtles.champtheatre.service.impl.TheatreManagementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TheatreManagementServiceImplTest {

    @Mock
    private TheatreRepository theatreRepository;

//    @InjectMocks
//    private TheatreManagementService theatreManagementService = new TheatreManagementServiceImpl(theatreRepository);

    @InjectMocks
    private TheatreManagementServiceImpl theatreManagementService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTheatres() {
        // Mocking the repository response
        List<Theatre> theatres = new ArrayList<>();
//        theatres.add(new Theatre(1L, "Admin", new HashSet<>(), new HashSet<>()));
//        theatres.add(new Theatre(2L, "User", new HashSet<>(), new HashSet<>()));
        when(theatreRepository.findAll()).thenReturn(theatres);

        // Calling the service method
        List<TheatreBean> theatreBeans = theatreManagementService.getAllTheatre();

        // Asserting the result
        assertEquals(theatres.size(), theatreBeans.size());
        assertEquals(theatres.get(0).getId(), theatreBeans.get(0).getId());
        assertEquals(theatres.get(0).getName(), theatreBeans.get(0).getName());
        assertEquals(theatres.get(0).getCapacity(), theatreBeans.get(0).getCapacity());
        assertEquals(theatres.get(0).getStatus(), theatreBeans.get(0).getStatus());
        assertEquals(theatres.get(1).getId(), theatreBeans.get(1).getId());
        assertEquals(theatres.get(1).getName(), theatreBeans.get(1).getName());
        assertEquals(theatres.get(1).getCapacity(), theatreBeans.get(1).getCapacity());
        assertEquals(theatres.get(1).getStatus(), theatreBeans.get(1).getStatus());

        // Verifying the repository method was called
        verify(theatreRepository, times(1)).findAll();
        verifyNoMoreInteractions(theatreRepository);
    }
}

