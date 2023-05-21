package loverduck.clover.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import loverduck.clover.entity.Funding;
import loverduck.clover.entity.FundingReply;
import loverduck.clover.entity.Users;
import loverduck.clover.repository.UsersRepository;
import loverduck.clover.service.FundingService;

/**
 *  펀딩 전체 목록 페이지, 펀딩 상세 페이지, 펀딩 투자하기 
 */
@Controller
@RequiredArgsConstructor
public class FundingController {

	@Autowired
	private final FundingService fundingService;
	//임시 - 적용시 지울것
	//private final UsersRepository usersRepository;
	
	/**
	 * 펀딩 전체 목록 페이지
	 */
	@RequestMapping("/fundingList")
	public String fundingList(Model model) {
		List<Funding> fundingList = fundingService.fundingList();
        model.addAttribute("fundingList", fundingList);
		return "/fundingList";
	}
	
	/**
	 * 펀딩 상세 페이지
	 */
	@RequestMapping("/fundingDetail/{id}")
	public String fundingDetail(@PathVariable Long id, Model model) {
		// 펀딩 정보 출력 
		Funding fund = fundingService.fundingDetail(id);
		model.addAttribute("fund", fund);
		
		// 해당 펀딩의 기업의 진행 중인 펀딩들 출력 
//		List<Funding> nowFunds = fundingService.findByCompanyName(fund.getCompany());
		System.out.println(fund.getCompany().getFunds());
		model.addAttribute("nowFunds", fund.getCompany().getFunds());
		
		// 해당 펀딩의 댓글 출력 
		List<FundingReply> commentList = fundingService.commentList(id);
        System.out.println("commentList ->" + commentList);
        if (commentList != null && !commentList.isEmpty()) {
            model.addAttribute("commentList", commentList);
        }
		return "/fundingDetail";
	}

	
	/**
	 * 펀딩 투자하기 - 약관동의
	 */
	@RequestMapping("/fundingAgree")
	public String fundingAgree() {
		
		return "/fundingAgree";
	}
	
	/**
	 * 펀딩 투자하기 - 펀딩하기
	 * ->KG이니시스 결제창으로 가야한다
	 */
	@RequestMapping("/fundingPay")
	public String fundingPay() {
		
		return "/fundingPay";
	}
	
	/**
	 * 펀딩 투자하기 - 펀딩완료
	 */
	@RequestMapping("/fundingPayFin")
	public String fundingPayFin() {
		
		return "/fundingPayFin";
	}
	
	/**
	 * 펀딩 상세 페이지 댓글 작성 
	 */
	@RequestMapping(value = "/fundingDetail/{id}/comment", method = RequestMethod.POST)
	public String fundingComment(FundingReply fundingReply, HttpSession session) throws Exception {
		System.out.println("reply -> " + fundingReply.toString());
		fundingReply.setUser((Users)session.getAttribute("user"));
		
		//fundingReply.setUser(usersRepository.findAll().get(0));
		
		fundingService.fundingComment(fundingReply);
		
		return "redirect:/fundingDetail/{id}";
	}
	
	/**
	 * 검색 
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String searchFunding(@RequestParam("keyword") String keyword, Model model) {
	    List<Funding> searchResults1 = fundingService.searchFundingByTitle(keyword);
	    List<Funding> searchResults2 = fundingService.searchFundingByCompany(keyword);

	    

	    model.addAttribute("searchResults1", searchResults1);
	    model.addAttribute("searchResults2", searchResults2);
	    model.addAttribute("keyword", keyword);
	    return "/searchResults"; 
	}
	
	
}
