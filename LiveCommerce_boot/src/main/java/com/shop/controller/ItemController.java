package com.shop.controller;

import com.shop.dto.ChatMessageDTO;
import com.shop.dto.ItemFormDto;
import com.shop.dto.ItemSearchDto;
import com.shop.dto.ReviewFormDto;
import com.shop.entity.ChatMessage;
import com.shop.entity.Item;
import com.shop.entity.Member;
import com.shop.entity.Review;
import com.shop.repository.MemberRepository;
import com.shop.service.ChatMessageService;
import com.shop.service.ItemService;
import com.shop.service.MemberService;
import com.shop.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@Log4j2
@AllArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ReviewService reviewService;
    private final MemberService memberService;
    private final ChatMessageService chatMessageService;


    @GetMapping("/admin/item/new")
    public String itemForm(Model model){
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "item/itemForm";
    }

    @PostMapping("/admin/item/new")
    public String ItemNew(@Valid ItemFormDto itemFormDto,
                          BindingResult bindingResult,
                          Model model,
                          @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList){

        if(bindingResult.hasErrors()){
            return "item/itemForm";
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "item/itemForm";
        }

        try{
            itemService.saveItem(itemFormDto, itemImgFileList);
        }catch(Exception e){
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생했습니다.");
            return "item/itemForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/admin/item/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model){

        try{
            ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
            model.addAttribute("itemFormDto", itemFormDto);

        }catch(Exception e){
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
            model.addAttribute("itemFormDto", new ItemFormDto());
            return "item/itemForm";
        }

        return "item/itemForm";
    }

    @PostMapping(value = "/admin/item/{itemId}")
    public String itemUpdate(@Valid ItemFormDto itemFormDto,
                             BindingResult bindingResult,
                             @PathVariable("itemId") Long itemId,
                             @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList,
                             Model model){
        log.info("--------------itemUpdate------------------");
        log.info("itemFormDto: " + itemFormDto);


        if(bindingResult.hasErrors()){
            return "item/itemForm";
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "item/itemForm";
        }

        try{

            itemService.updateItem(itemFormDto, itemImgFileList);

        }catch(Exception e){
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생했습니다.");
            return "item/itemForm";
        }

        return "redirect:/";

    }

    @GetMapping(value = {"/admin/items", "/admin/items/{page}"})
    public String itemManage(ItemSearchDto itemSearchDto,
                             @PathVariable("page") Optional<Integer> page,
                             Model model
    ){

        log.info("--------------itemManage---------------");
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);

        Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);

        //페이지 전환시 기존 검색조건을 유지한 채 이동할 수 있도록 뷰전달
        model.addAttribute("itemSearchDto", itemSearchDto);

        model.addAttribute("maxPage", 5);
        return "item/itemMng";
    }

    @GetMapping(value = "/item/{itemId}")
    public String itemDtl2(@PathVariable("itemId") Long itemId, Model model){

        // 아이템 상세 정보 조회
        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);

        // 아이템 리뷰 조회
        List<ReviewFormDto> reviews = reviewService.getReviewsByItemId(itemId);

        //현재 로그인한 회원 조회
        Member member = memberService.getCurrentLoggedInMember();

        // 채팅 메시지 조회
        List<ChatMessage> chatMessages = chatMessageService.getMessagesByItemId(itemId);


        log.info("itemId : " + itemId);


        model.addAttribute("item", itemFormDto);
        model.addAttribute("reviews", reviews); // 리뷰 정보를 모델에 추가
        model.addAttribute("member", member);
        model.addAttribute("chatMessages", chatMessages);


        log.info("-------------------itemDtl2-----------------------------");
        log.info(itemFormDto);
        log.info(itemFormDto.getItemImgDtoList().get(0).getImgUrl());
        log.info("====================================");
        log.info("itemId : " + itemId);
        log.info("====================================");
        log.info("리뷰" + reviews);
        log.info("==============================");
        log.info("로그인한 회원 : " + member);
        log.info("==============================");
        log.info("채팅 메시지 : " + chatMessages);
        log.info("==============================");


        return "item/itemDtl"; // itemDtl.html로 뷰 리턴
    }


}
