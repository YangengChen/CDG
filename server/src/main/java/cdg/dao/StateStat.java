package cdg.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class StateStat {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @Column(unique=true)
    private String name;

    private Long activityCount;
    
    public StateStat() {
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

    public Long getActivityCount() {
		return activityCount;
	}

	public void setActivityCount(Long activityCount) {
		this.activityCount = activityCount;
    }

    public void increaseActivityCount() {
        this.activityCount = this.activityCount + 1;
    }

}
