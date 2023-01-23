package com.solvd.dp.web.dto.user;

import com.solvd.dp.web.dto.validation.OnCreate;
import com.solvd.dp.web.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Schema(description = "Address DTO")
public class AddressDto {

    @Schema(description = "Address ID", example = "1")
    @Null(message = "Id must be null", groups = {OnCreate.class})
    @NotNull(message = "Id must not be null", groups = {OnUpdate.class})
    private Long id;

    @Schema(description = "Address street name", example = "Kirova street")
    @NotNull(message = "Street name is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "Street name must be between {min} and {max} characters", groups = {OnCreate.class, OnUpdate.class})
    private String streetName;

    @Schema(description = "Address house number", example = "1")
    @NotNull(message = "House number is required", groups = {OnCreate.class, OnUpdate.class})
    @Min(value = 1, message = "House number must be greater than {value}", groups = {OnCreate.class, OnUpdate.class})
    private Integer houseNumber;

    @Schema(description = "Address floor number", example = "1")
    @Min(value = -2, message = "Floor number must be greater than {value}", groups = {OnCreate.class, OnUpdate.class})
    private Integer floorNumber;

    @Schema(description = "Address flat number", example = "1")
    @Min(value = 1, message = "Flat number must be greater than {value}", groups = {OnCreate.class, OnUpdate.class})
    private Integer flatNumber;

}
