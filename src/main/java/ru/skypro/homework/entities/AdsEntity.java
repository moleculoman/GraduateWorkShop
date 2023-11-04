package ru.skypro.homework.entities;
import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "ads")
public class AdsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    Integer price;
    String title;
    String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image")
    byte [] image;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    UserEntity user;
}