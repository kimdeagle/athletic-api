package com.athletic.api.system.authority.service;

import com.athletic.api.admin.repository.AdminRepository;
import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.common.message.SuccessMessage;
import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import com.athletic.api.system.authority.dto.AuthorityRequestDto;
import com.athletic.api.system.authority.repository.AuthorityRepository;
import com.athletic.api.system.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorityService {
    private final AuthorityRepository authorityRepository;
    private final AdminRepository adminRepository;
    private final MenuRepository menuRepository;

    public ResponseDto deleteAuthorities(List<String> idList) {
        //권한 삭제 시 1)해당 권한을 가진 관리자 있는지 2)해당 권한을 가진 메뉴가 있는지
        for (String id : idList) {
            String displayName = authorityRepository.findDisplayNameById(id);
            List<String> existAdminNameList = adminRepository.findAllNameByAuthorityId(id);
            if (!existAdminNameList.isEmpty()) {
                throw new CustomException(ErrorCode.EXIST_ADMIN_HAS_AUTHORITY, displayName, String.join("\n", existAdminNameList));
            }
            List<String> existMenuNameList = menuRepository.findAllNameByAuthorityId(id);
            if (!existMenuNameList.isEmpty()) {
                throw new CustomException(ErrorCode.EXIST_MENU_HAS_AUTHORITY, displayName, String.join("\n", existMenuNameList));
            }
        }

        authorityRepository.deleteAllByIdInBatch(idList);

        return ResponseDto.success(SuccessMessage.getMessageByParams(SuccessMessage.Authority.DELETE_AUTHORITIES, String.valueOf(idList.size())));
    }

    public ResponseDto addAuthority(AuthorityRequestDto authorityRequestDto) {
        if (existByName(authorityRequestDto.getName())) {
            throw new CustomException(ErrorCode.EXIST_AUTHORITY_NAME);
        }

        authorityRepository.save(authorityRequestDto.toAuthority());

        return ResponseDto.success(SuccessMessage.Authority.ADD_AUTHORITIES);
    }

    public ResponseDto updateAuthority(AuthorityRequestDto authorityRequestDto) {
        if (existByNameAndIdNot(authorityRequestDto.getName(), authorityRequestDto.getId())) {
            throw new CustomException(ErrorCode.EXIST_AUTHORITY_NAME);
        }

        authorityRepository.save(authorityRequestDto.toUpdateAuthority());

        return ResponseDto.success(SuccessMessage.Authority.UPDATE_AUTHORITIES);
    }

    private boolean existByName(String name) {
        return authorityRepository.existsByName(name);
    }

    private boolean existByNameAndIdNot(String name, String id) {
        return authorityRepository.existsByNameAndIdNot(name, id);
    }
}
