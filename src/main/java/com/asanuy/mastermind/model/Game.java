package com.asanuy.mastermind.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Game extends BaseEntity {

    @Column
    @NotNull
    private LocalDateTime dateTime = LocalDateTime.now();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    @NotNull
    @JsonManagedReference
    private List<CodePeg> codePegs;

    @Column
    @NotNull
    private boolean deleted = false;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<CodePeg> getCodePegs() {
        return codePegs;
    }

    public void setCodePegs(List<CodePeg> codePegs) {
        this.codePegs = codePegs;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
