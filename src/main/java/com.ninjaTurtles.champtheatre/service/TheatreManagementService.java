package com.ninjaTurtles.champtheatre.service;

import com.ninjaTurtles.champtheatre.bean.TheatreBean;
import com.ninjaTurtles.champtheatre.models.Theatre;

import java.util.List;


public interface TheatreManagementService {
	
	List<TheatreBean> getAllTheatre();

	TheatreBean findTheatreById(Long theatreId);

	void changeTheatreStatus(Theatre theatre);
	
	void updateTheatreDetails(Theatre theatre);
}
