package com.green.biz;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.green.biz.admin.AdminService;
import com.green.biz.dto.ManagerVO;
import com.green.biz.dto.MemberVO;
import com.green.biz.dto.NoticeVO;
import com.green.biz.dto.OrderVO;
import com.green.biz.dto.ProductVO;
import com.green.biz.dto.QnaVO;
import com.green.biz.dto.SalesQuantity;
import com.green.biz.member.MemberService;
import com.green.biz.notice.NoticeService;
import com.green.biz.order.OrderService;
import com.green.biz.product.ProductService;
import com.green.biz.qna.QnaService;

@Controller
@SessionAttributes("adminUser")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private ProductService productService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private QnaService qnaService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private NoticeService noticeService;
	
	// �α��� ȭ��
	@GetMapping(value="/admin_login_form")
	public String adminLoginView() {
		
		return "admin/login";
	}
	
	//�α���
	@PostMapping(value="/admin_login")
	public String adminLogin(@RequestParam(value="managerId") String managerId,
						     @RequestParam(value="managerPwd") String managerPwd,
						     Model model) {
		
		ManagerVO vo = new ManagerVO();
		vo.setId(managerId);
		vo.setPwd(managerPwd);
		
		int result = adminService.managerCheck(vo);
		
		// ���� �α����̸� ��ǰ���ȭ������ �̵�
		if (result == 1) {
			ManagerVO adminUser = adminService.getManager(managerId);
			
			model.addAttribute("adminUser", adminUser);
			
			return "redirect:admin_product_list";
		} else {
			// ������ �α����̸� �޽����� �����ϰ� �α��� �������� �̵�
			if (result == 0) {
				model.addAttribute("message", "��й�ȣ�� Ȯ���ϼ���.");
			} else {
				model.addAttribute("message", "���̵� Ȯ���ϼ���!");
			}
			
			return "admin/login";
		}
	}
	
	//������ ���� �α׾ƿ� ó��	 
	@GetMapping(value="/admin_logout")
	public String adminLogout(SessionStatus status) {
			
		status.setComplete();
			
		return "index";
	}
	
	//��ǰ ��� ��ȸ
	@RequestMapping(value="/admin_product_list")
	public String adminProductList(HttpSession session, Model model) {
		// ������ �α��� Ȯ��
		ManagerVO adminUser = (ManagerVO)session.getAttribute("adminUser");
				
		if (adminUser == null) {
			return "admin/login";
		} else {
			// ��ǰ��� ��ȸ
			List<ProductVO> prodList = productService.listProduct("");
					
			model.addAttribute("productList", prodList);
					
			return "admin/product/productList";
		}
	}
	
	/*
	 * ��ǰ ��������� ǥ��
	 */
	@PostMapping(value="/admin_product_write_form")
	public String adminProductWriteView(Model model) {
		String categoryList[] = {"�Ҽ�", "�濵/����", "�ι�/��ȸ/����", "�ڱ���", "���Ҽ�", "����ȭ"};
		
		model.addAttribute("categoryList", categoryList);
		
		return "admin/product/productWrite";
	}
	
	/*
	 * ��ǰ ��� ó��
	 */
	@PostMapping(value="/admin_product_write")
	public String adminProductWrite(@RequestParam(value="product_image") MultipartFile uploadFile,
								    ProductVO vo, HttpSession session) {
		// ������ �α��� Ȯ��
		ManagerVO adminUser = (ManagerVO)session.getAttribute("adminUser");
		
		if (adminUser == null) {
			return "admin/login";
		} else {
			String fileName = "";
			if (!uploadFile.isEmpty()) {  // �̹��� ������ �о��
				fileName = uploadFile.getOriginalFilename();
				// vo ��ü�� �̹������� ����
				vo.setImage(fileName);
				
				// �̹��� ������ ���� ������ ���ϱ�
				String image_path = 
					session.getServletContext().getRealPath("resources/product_images/");
				System.out.println("�̹��� ���: " + image_path);
						
				try {
					// �̹��� ������ ���� ��η� �̵���Ŵ
					File dest = new File(image_path + fileName);
					uploadFile.transferTo(dest);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
		}
		productService.insertProduct(vo);
		
		return "redirect:admin_product_list";
	}
	
	/*
	 * ��ǰ �� ���� ���
	 */
	@RequestMapping(value="/admin_product_detail")
	public String adminProductDetail(ProductVO vo, Model model) {
		String[] categoryList = {"", "�Ҽ�", "�濵/����", "�ι�/��ȸ/����", "�ڱ���", "���Ҽ�", "����ȭ"};
		
		ProductVO product = productService.getProduct(vo);
		
		model.addAttribute("productVO", product);
		
		// ��ǰ���� ����
		int index = Integer.parseInt(product.getCategory());
		model.addAttribute("category", categoryList[index]);
		
		return "admin/product/productDetail";
	}
	
	/*
	 * ��ǰ ����ȭ�� ���
	 */
	@RequestMapping(value="/admin_product_update_form")
	public String adminProductUpdateView(ProductVO vo, Model model) {
		String[] categoryList = {"�Ҽ�", "�濵/����", "�ι�/��ȸ/����", "�ڱ���", "���Ҽ�", "����ȭ"};
		
		ProductVO product = productService.getProduct(vo);
		
		model.addAttribute("productVO", product);	// ȭ�鿡 ������ ��ǰ������	
		model.addAttribute("categoryList", categoryList);
		
		return "admin/product/productUpdate";
	}
	
	/*
	 * ��ǰ���� ����
	 */
	@RequestMapping(value="/admin_product_update")
	public String adminProductUpdate(@RequestParam(value="product_image") MultipartFile uploadFile,
					@RequestParam(value="nonmakeImg") String origImage,
					ProductVO vo, HttpSession session) {
		// ������ �α��� Ȯ��
		ManagerVO adminUser = (ManagerVO)session.getAttribute("adminUser");
		
		if (adminUser == null) {
			return "admin/login";
		} else {
			String fileName = "";
			
			// �̹��� ������ ���� �� ����
			if (!uploadFile.isEmpty()) {  // �̹��� ������ �о��
				fileName = uploadFile.getOriginalFilename();
				// vo ��ü�� �̹������� ����
				vo.setImage(fileName);
				
				// �̹��� ������ ���� ������ ���ϱ�
				String image_path = 
					session.getServletContext().getRealPath("resources/product_images/");
				System.out.println("�̹��� ���: " + image_path);
						
				try {
					// �̹��� ������ ���� ��η� �̵���Ŵ
					File dest = new File(image_path + fileName);
					uploadFile.transferTo(dest);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			} else {
				// ���� �̹����� image �ʵ� ����
				vo.setImage(origImage);
			}
			// ����Ʈ ��ǰ, �Ż�ǰ�� üũ���� ������ ���� null�� ����
			if (vo.getUseyn() == 0) {
				vo.setUseyn('n');
			}
			if (vo.getLikeyn() == 0) {
				vo.setLikeyn('n');
			}
			
			productService.updateProduct(vo);
			
			return "redirect:admin_product_list";
		}
	}
	
	@PostMapping(value="/admin_product_delete")
	public String adminProductDelete( ProductVO vo, HttpSession session) {
		// ������ �α��� Ȯ��
		ManagerVO adminUser = (ManagerVO)session.getAttribute("adminUser");
		
		if (adminUser == null) {
			return "admin/login";
		} else {
			productService.deleteProduct(vo);
		}
		
		return "redirect:admin_product_list";
	}
	
	/*
	 * ȸ����� ��ȸ ó��
	 */
	@RequestMapping(value="/admin_member_list")
	public String adminMemberList(
			@RequestParam(value="key", defaultValue="") String name,
			Model model) {
		
		List<MemberVO> listMember = memberService.listMember(name);
		
		model.addAttribute("memberList", listMember);
		
		return "admin/member/memberList";
	}
	
	/*
	 * Q&A ��� ��ȸ ó��
	 */
	@RequestMapping(value="/admin_qna_list")
	public String adminQnaList(Model model) {
		
		// Q&A ����� ���̺��� ��ȸ
		List<QnaVO> qnaList = qnaService.listAllQna();
		
		// ��ȸ ����� model ��ü�� ����
		model.addAttribute("qnaList", qnaList);
		
		// QnA ȭ�� ȣ��
		return "admin/qna/qnaList";
	}
	
	/*
	 * Ŭ���� Qna�Խñ� �� ��ȸ
	 */
	@RequestMapping(value="/admin_qna_detail")
	public String adminQnaDetail(QnaVO vo, Model model) {
		// �Խñ� �Ϸù�ȣ�� �������� �Խñ� �� ��ȸ
		QnaVO qna = qnaService.getQna(vo.getQseq());
		
		// ��ȸ ����� model ��ü�� ����
		model.addAttribute("qnaVO", qna);
		
		// �Խñ� ��ȭ�� ȣ��
		return "admin/qna/qnaDetail";
	}
	
	/*
	 * Q&A ������ �亯 ��û ó��
	 */
	@PostMapping(value="/admin_qna_repsave")
	public String adminQnaRepSave(QnaVO vo) {
		
		// Qna������ Update ȣ��
		qnaService.updateQna(vo);
		
		// Qna �Խñ� ��� ȣ��
		return "redirect:admin_qna_list";
	}
	
	/*
	 * �ֹ���� ��ȸ ��ûó��
	 */
	@RequestMapping(value="/admin_order_list")
	public String adminOrderList(@RequestParam(value="key", defaultValue="") String key,
			Model model) {
		
		List<OrderVO> orderList = orderService.listOrder(key);
		
		model.addAttribute("orderList", orderList);
		
		return "admin/order/orderList";
	}
	
	/*
	 * �ֹ��Ϸ� ó��(�Ա� Ȯ��)
	 * �Է� �Ķ����:
	 * 	   �Ա�Ȯ���� result �ʵ��� ���ֹ���ȣ(odseq) �迭�� ���޵�
	 */
	@RequestMapping(value="/admin_order_save")
	public String adminOrderSave(@RequestParam(value="result") int[] odseq) {
		
		for(int i=0; i<odseq.length; i++) {
			orderService.updateOrderResult(odseq[i]);
		}
		
		return "redirect:admin_order_list";
	}
	
	/*
	 * notice ��� ��ȸ ó��
	 */
	@RequestMapping(value="/admin_notice_list")
	public String adminNoticeList(Model model) {
		
		// notice ����� ���̺��� ��ȸ
		List<NoticeVO> noticeList = noticeService.listAllNotice();
		
		// ��ȸ ����� model ��ü�� ����
		model.addAttribute("noticeList", noticeList);
		
		// notice ȭ�� ȣ��
		return "admin/notice/noticeList";
	}
	
	/*
	 * �������� ��������� ǥ��
	 */
	@PostMapping(value="/admin_notice_write_form")
	public String adminNoticeWriteView() {
		
		return "admin/notice/noticeWrite";
	}
	
	@PostMapping(value="/admin_notice_write")
	public String adminNoticeWrite(NoticeVO vo, HttpSession session) {
		
		noticeService.insertNotice(vo);
		
		return "redirect:admin_notice_list";
	}
	
	/*
	 * Ŭ���� notice�Խñ� �� ��ȸ
	 */
	@RequestMapping(value="/admin_notice_detail")
	public String adminNoticeDetail(NoticeVO vo, Model model) {
		// �Խñ� �Ϸù�ȣ�� �������� �Խñ� �� ��ȸ
		NoticeVO notice = noticeService.getNotice(vo.getNseq());
		
		// ��ȸ ����� model ��ü�� ����
		model.addAttribute("noticeVO", notice);
		
		// �Խñ� ��ȭ�� ȣ��
		return "admin/notice/noticeDetail";
	}
	
	@RequestMapping(value="/admin_notice_update_form")
	public String adminNoticeUpdateView(NoticeVO vo, Model model) {
		
		NoticeVO notice = noticeService.getNotice(vo.getNseq());
		
		model.addAttribute("noticeVO", notice);	// ȭ�鿡 ������ ��ǰ������	
		
		return "admin/notice/noticeUpdate";
	}
	
	@RequestMapping(value="/admin_notice_update")
	public String adminNoticeUpdate(NoticeVO vo, HttpSession session) {
			
		noticeService.updateNotice(vo);
			
		return "redirect:admin_notice_list";
	}
	
	@PostMapping(value="/admin_notice_delete")
	public String adminNoticeDelete(NoticeVO vo, HttpSession session) {
		
		noticeService.deleteNotice(vo);
		
		return "redirect:admin_notice_list";
	}
	
	/*
	 * ��ǰ�� �Ǹ� ���� ȭ�� ���
	 */
	@RequestMapping(value="/admin_sales_record_form")
	public String adminProductSalesChart() {
		
		return "admin/order/salesRecord";
	}
	
	/*
	 * ��Ʈ�� ���� ��ǰ�� �Ǹ� ���� ��ȸ(JSON ������ ���� ����)
	 */
	@RequestMapping(value="/sales_record_chart",
			produces="application/json; charset=UTF-8")
	@ResponseBody
	public List<SalesQuantity> salesRecordChart() {
		List<SalesQuantity> listSales = productService.getProductSales();
		
		for (SalesQuantity item: listSales ) {
			System.out.println(item);
		}
		return listSales;
	}
}
