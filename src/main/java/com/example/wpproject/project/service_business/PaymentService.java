package com.example.wpproject.project.service_business;

import com.example.wpproject.project.model.dto.ChargeRequest;
import com.stripe.exception.*;
import com.stripe.model.Charge;

public interface PaymentService {

    Charge charge(ChargeRequest chargeRequest) throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException, CardException;
}
