package com.gamemini.api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner")
    private UserEntity owner;

    @Version
    private long version;

    @OneToMany(mappedBy = "pokemon", fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();
}
