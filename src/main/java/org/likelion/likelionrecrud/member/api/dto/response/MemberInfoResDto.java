package org.likelion.likelionrecrud.member.api.dto.response;

import lombok.Builder;
import org.likelion.likelionrecrud.member.domain.Member;
import org.likelion.likelionrecrud.member.domain.Part;

@Builder
public record MemberInfoResDto (
    String name,
    int age,
    Part part){
    public static MemberInfoResDto from(Member member){
        return MemberInfoResDto.builder()
                .name(member.getName())
                .age(member.getAge())
                .part(member.getPart())
                .build();
    }

}
