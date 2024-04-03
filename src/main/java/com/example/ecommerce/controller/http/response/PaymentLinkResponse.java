package com.example.ecommerce.controller.http.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentLinkResponse {

    private String payment_link_id;
    private String payment_link_url;

}
