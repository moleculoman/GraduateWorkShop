package ru.skypro.homework.dto.adsDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)

public class AdsDTO {
    Integer count;
    List<AdDTO> results;
}