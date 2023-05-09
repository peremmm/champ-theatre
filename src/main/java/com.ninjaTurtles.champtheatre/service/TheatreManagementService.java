package com.ninjaTurtles.champtheatre.service;

import com.ninjaTurtles.champtheatre.models.Theatre;

import java.util.List;


public interface TheatreManagementService {
	
	List<Theatre> getAllTheatre();

	void changeTheatreStatus(Theatre theatre);
	
	void updateTheatreDetails(Theatre theatre);
}
