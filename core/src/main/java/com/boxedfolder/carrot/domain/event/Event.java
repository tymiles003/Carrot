package com.boxedfolder.carrot.domain.event;

import com.boxedfolder.carrot.domain.AbstractEntity;
import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.util.DateTimeDeserializer;
import com.boxedfolder.carrot.domain.util.DateTimeSerializer;
import com.boxedfolder.carrot.domain.util.View;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NotificationEvent.class, name = "notification"),
        @JsonSubTypes.Type(value = TextEvent.class, name = "text")
})
@DiscriminatorColumn(name = "type")
@Table(name = "event")
@Inheritance(strategy = InheritanceType.JOINED)
@Entity(name = "event")
public abstract class Event extends AbstractEntity {
    public static final double TYPE_ENTER = 0;
    public static final double TYPE_EXIT = 1;
    public static final double TYPE_BOTH = 2;

    /**
     * Whether event is active or not
     */
    @JsonView(View.General.class)
    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private boolean active = true;

    /**
     * Retrigger threshold (in minutes)
     */
    @JsonView(View.General.class)
    private float threshold;

    /**
     * Sets a start date when this event is going to be active.
     */
    @JsonView(View.General.class)
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime scheduledStartDate;

    /**
     * Sets an end date when this event is going to be active.
     */
    @JsonView(View.General.class)
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime scheduledEndDate;

    /**
     * Type of event. May be one of the following types:
     * TYPE_ENTER(0)
     * TYPE_EXIT(1)
     * TYPE_BOTH(2)
     */
    @JsonView(View.General.class)
    @Column(nullable = false)
    private int eventType;

    @JsonView(View.Client.class)
    @JsonIgnoreProperties({"uuid", "major", "minor", "dateUpdated", "dateCreated"})
    @JoinTable(name = "event_beacon", joinColumns = {
            @JoinColumn(name = "event_id", nullable = false, updatable = false)
    }, inverseJoinColumns = {
            @JoinColumn(name = "beacon_id", nullable = false, updatable = false)
    })
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private List<Beacon> beacons = new ArrayList<Beacon>();

    @JsonView(View.Client.class)
    @JsonIgnoreProperties({"applicationKey", "dateUpdated", "dateCreated"})
    @JoinTable(name = "event_app", joinColumns = {
            @JoinColumn(name = "event_id", nullable = false, updatable = false)
    }, inverseJoinColumns = {
            @JoinColumn(name = "app_id", nullable = false, updatable = false)
    })
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private List<App> apps = new ArrayList<App>();

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public DateTime getScheduledStartDate() {
        return scheduledStartDate;
    }

    public void setScheduledStartDate(DateTime scheduledStartDate) {
        this.scheduledStartDate = scheduledStartDate;
    }

    public DateTime getScheduledEndDate() {
        return scheduledEndDate;
    }

    public void setScheduledEndDate(DateTime scheduledEndDate) {
        this.scheduledEndDate = scheduledEndDate;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public List<Beacon> getBeacons() {
        return beacons;
    }

    public void setBeacons(List<Beacon> beacons) {
        this.beacons = beacons;
    }

    public List<App> getApps() {
        return apps;
    }

    public void setApps(List<App> apps) {
        this.apps = apps;
    }
}
