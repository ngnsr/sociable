package com.rr.sociable.entity;

import com.rr.sociable.dto.GroupDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.FetchType.EAGER;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="app_group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    public Group(GroupDto groupDto){
        name = groupDto.getName();
    }

    @ElementCollection(fetch = EAGER)
    private Set<Long> memberIds = new HashSet<>();

    @Transient
    @Getter(AccessLevel.NONE)
    private int memberCount;

    public int getMemberCount() {
        return memberIds.size();
    }

}
