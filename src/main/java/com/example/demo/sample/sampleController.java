package com.example.demo.sample;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/sample")
public class sampleController {

    //repository (DB connection object), service (자주사용하는 method), 객체 선언시 autowired 어노테이션 사용.
    // autowired = spring3 의 bean 생성을 자동으로 도와줌.

    @Autowired
    private sampleRepository sampleRepo;



	@GetMapping("/list")
	public String list(HttpServletRequest request, Model model, @PageableDefault(size = 10) Pageable pageable,
			@RequestParam(required = false, defaultValue = "") String searchText) {

		Page<boardData> list = sampleRepo.findByTitleContainingOrMemoContainingOrderByIdDesc(searchText, searchText,pageable);

		int startPage = Math.max(1, (list.getPageable().getPageNumber() / pageable.getPageSize()) * pageable.getPageSize()+1 );
		int endPage = Math.min(list.getTotalPages(), startPage + pageable.getPageSize() -1);

		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("list", list);

		return "sample/sampleList";
	}

}
