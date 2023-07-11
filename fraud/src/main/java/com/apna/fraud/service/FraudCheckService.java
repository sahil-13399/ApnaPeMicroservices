package com.apna.fraud.service;

import com.apna.fraud.dto.FraudCheckHistory;
import com.apna.fraud.repository.FraudCheckHistoryRepository;
import java.time.LocalDateTime;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FraudCheckService {

  private final FraudCheckHistoryRepository fraudCheckHistoryRepository;
  private static final String EMAIL_PATTERN =
      "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
          + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

  private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

  public boolean isFraudulentCustomer(Integer customerId, String emailId) {
    fraudCheckHistoryRepository.save(
        FraudCheckHistory.builder()
            .customerId(customerId)
            .isFraudster(pattern.matcher(emailId).matches())
            .createdAt(LocalDateTime.now())
            .build()
    );
    return false;
  }

}
