package com.workable.movierama.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.workable.movierama.model.Cast;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditsDTO {
	private List<Cast> cast;

	public List<Cast> getCast() {
		return cast;
	}

	public void setCast(List<Cast> cast) {
		this.cast = cast;
	}

}
