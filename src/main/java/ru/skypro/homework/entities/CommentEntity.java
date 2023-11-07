package ru.skypro.homework.entities;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
@FieldDefaults(level = AccessLevel.PRIVATE)
    public class CommentEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Integer id;
        @Column(name = "createdAt")
        LocalDateTime createdAt;
        @Column(name = "text")
        String text;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        UserEntity user;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "ads_id")
        AdsEntity ads;
}