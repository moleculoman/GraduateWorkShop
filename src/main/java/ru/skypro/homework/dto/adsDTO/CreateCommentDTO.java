package ru.skypro.homework.dto.adsDTO;

import lombok.experimental.FieldDefaults;
import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCommentDTO {
    String text;
}
