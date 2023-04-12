package com.athletic.api.dues.service;

import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.common.message.SuccessMessage;
import com.athletic.api.dues.dto.DuesRequestDto;
import com.athletic.api.dues.repository.DuesRepository;
import com.athletic.api.util.excel.ExcelReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DuesService {
    private final DuesRepository duesRepository;

    public ResponseDto deleteDues(String id) {
        duesRepository.deleteById(id);

        return ResponseDto.success(SuccessMessage.Dues.DELETE_DUES);
    }

    public ResponseDto addDues(DuesRequestDto duesRequestDto) {
        duesRepository.save(duesRequestDto.toDues());

        return ResponseDto.success(SuccessMessage.Dues.ADD_DUES);
    }

    public ResponseDto updateDues(DuesRequestDto duesRequestDto) {
        duesRepository.save(duesRequestDto.toUpdateDues());

        return ResponseDto.success(SuccessMessage.Dues.UPDATE_DUES);
    }

    public ResponseDto uploadExcel(MultipartFile file) {
        List<DuesRequestDto> excelList = ExcelReader.read(file, DuesRequestDto.class);

        duesRepository.saveAll(excelList.stream().map(DuesRequestDto::toDuesByExcel).collect(Collectors.toList()));

        return ResponseDto.success(SuccessMessage.Excel.UPLOAD);

    }
}
