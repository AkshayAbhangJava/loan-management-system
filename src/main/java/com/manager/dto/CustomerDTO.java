package com.manager.dto;

import java.util.List;
import lombok.Data;

@Data
public class CustomerDTO {
    private String name;
    private String phone;
    private String address;
    private List<LoanDTO> loans;
    private List<AttachmentDTO> attachments;

}
