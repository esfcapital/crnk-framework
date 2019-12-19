package io.crnk.example.springboot.proxied.microservice.task;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.crnk.core.resource.annotations.*;
import io.crnk.core.resource.links.DefaultSelfLinksInformation;

import java.util.HashMap;
import java.util.Map;

@JsonApiResource(type = "project")
public class ProjectProxy  {

	@JsonApiId
	private Long id;

	@JsonProperty
	private String name;

	@JsonIgnore
	private Map<String, String> attributes = new HashMap<>();

	/**
	 * Links information necessary on objects to maintain urls from original microservice.
	 */
	@JsonApiLinksInformation
	private DefaultSelfLinksInformation links = new DefaultSelfLinksInformation();



	@JsonAnyGetter
	public Map<String, String> getAttributes() {
		return attributes;
	}


	@JsonAnySetter
	public void setProperty(String key, String value) {
		if ("notAllowed".equals(key)) {
			throw new IllegalStateException();
		}
		this.attributes.put(key, value);
	}

	public DefaultSelfLinksInformation getLinks() {
		return links;
	}

	public void setLinks(DefaultSelfLinksInformation links) {
		this.links = links;
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
}
