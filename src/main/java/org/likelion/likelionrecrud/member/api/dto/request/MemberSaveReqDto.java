package org.likelion.likelionrecrud.member.api.dto.request;

import org.likelion.likelionrecrud.member.domain.Part;

public record MemberSaveReqDto (
        String name,
        int age,
        Part part

){

}

