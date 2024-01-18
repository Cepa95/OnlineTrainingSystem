package com.training.OnlineTraining.controller;

import com.training.OnlineTraining.model.Measurement;
import com.training.OnlineTraining.service.MeasurementService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/charts")
public class ChartController {

    private final MeasurementService measurementService;


    @GetMapping("/{contractId}")
    public String getPersonalMeasurementsAsc(@PathVariable UUID contractId, Model model) {
        List<Measurement> measurements = measurementService.getMeasurementsByContractIdSortedByDateAsc(contractId);

        model.addAttribute("measurements", measurements);

        return "client/chart";
    }
}