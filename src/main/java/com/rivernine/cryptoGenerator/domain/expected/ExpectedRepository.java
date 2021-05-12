package com.rivernine.cryptoGenerator.domain.expected;

import java.time.LocalDateTime;
import java.util.List;

import com.rivernine.cryptoGenerator.schedule.analysisMarket.dto.AnalysisMarketResponseDto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpectedRepository extends JpaRepository<Expected, String>{
}
