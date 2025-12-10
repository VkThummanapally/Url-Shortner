package com.vk.urlshortner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ShortenRequest {

    @NotBlank
    @Pattern(regexp = "https?://.*", message = "must be a valid http/https URL")
    private String url;

}
