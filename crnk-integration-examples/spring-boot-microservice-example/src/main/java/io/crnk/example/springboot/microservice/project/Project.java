package io.crnk.example.springboot.microservice.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.crnk.core.resource.annotations.JsonApiId;
import io.crnk.core.resource.annotations.JsonApiLinksInformation;
import io.crnk.core.resource.annotations.JsonApiResource;
import io.crnk.core.resource.links.DefaultSelfLinksInformation;

@JsonApiResource(type = "project")
public class Project {

	@JsonApiId
	private Long id;

	@JsonProperty
	private String name;

	@JsonProperty
	private String programme;

	@JsonProperty
	private String businessSponsor;

	/**
	 * Links information necessary on objects to maintain urls from original microservice.
	 */
	@JsonApiLinksInformation
	private DefaultSelfLinksInformation links = new DefaultSelfLinksInformation();

	public Project() {
	}

	public Project(Long id, String name, String programme, String businessSponsor) {
		this.id = id;
		this.name = name;
		this.programme = programme;
		this.businessSponsor = businessSponsor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProgramme() {
		return programme;
	}

	public void setProgramme(String programme) {
		this.programme = programme;
	}

	public String getBusinessSponsor() {
		return businessSponsor;
	}

	public void setBusinessSponsor(String businessSponsor) {
		this.businessSponsor = businessSponsor;
	}

	public DefaultSelfLinksInformation getLinks() {
		return links;
	}

	public void setLinks(DefaultSelfLinksInformation links) {
		this.links = links;
	}
}
