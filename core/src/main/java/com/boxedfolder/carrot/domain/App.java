package com.boxedfolder.carrot.domain;

import com.boxedfolder.carrot.domain.event.Event;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public class App extends AbstractEntity {
    @JsonProperty
    @Size(min = 16, max = 16)
    private String applicationKey;

    @JsonIgnore
    @ManyToMany(mappedBy = "apps", fetch = FetchType.LAZY)
    private List<Beacon> beacons = new ArrayList<Beacon>();

    @JsonIgnore
    @ManyToMany(mappedBy = "apps", fetch = FetchType.LAZY)
    private List<Event> events = new ArrayList<Event>();

    public String getApplicationKey() {
        return applicationKey;
    }

    public void setApplicationKey(String applicationKey) {
        this.applicationKey = applicationKey;
    }

    public List<Beacon> getBeacons() {
        return beacons;
    }

    public void setBeacons(List<Beacon> beacons) {
        this.beacons = beacons;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
