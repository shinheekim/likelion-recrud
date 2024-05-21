package org.likelion.likelionrecrud.member.api;

import org.likelion.likelionrecrud.member.api.dto.request.MemberSaveReqDto;
import org.likelion.likelionrecrud.member.api.dto.request.MemberUpdateReqDto;
import org.likelion.likelionrecrud.member.api.dto.response.MemberInfoResDto;
import org.likelion.likelionrecrud.member.api.dto.response.MemberListResDto;
import org.likelion.likelionrecrud.member.application.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping()
    public ResponseEntity<MemberListResDto> memberFindAll() {
        List<MemberInfoResDto> members = memberService.memberFindAll();
        MemberListResDto memberListResDto = MemberListResDto.from(members);
        return new ResponseEntity<>(memberListResDto, HttpStatus.OK);
    }

    @PostMapping()
       public ResponseEntity<String> memberSave(@RequestBody MemberSaveReqDto memberSaveReqDto) {
            memberService.memberSave(memberSaveReqDto);
            return new ResponseEntity<>("회원 저장!", HttpStatus.CREATED);
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<String> memberUpdate(@PathVariable Long memberId,
                                               @RequestBody MemberUpdateReqDto memberUpdateReqDto) {
        memberService.memberUpdate(memberId, memberUpdateReqDto);
        return new ResponseEntity<>("회원 수정!", HttpStatus.OK);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<String> memberDelete(@PathVariable("memberId") Long memberId) {
        memberService.memberDelete(memberId);
        return new ResponseEntity<>("회원 삭제!", HttpStatus.OK);
    }


}
