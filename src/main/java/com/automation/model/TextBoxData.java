package com.automation.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TextBoxData {
    private String fullName;
    private String email;
    private String currentAddress;
    private String permanentAddress;
}
