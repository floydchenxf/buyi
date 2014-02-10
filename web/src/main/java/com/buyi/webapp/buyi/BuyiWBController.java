package com.buyi.webapp.buyi;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.buyi.dal.entity.dataobject.CompanyProductDO;
import com.buyi.dal.entity.dataobject.ProductTypeDO;
import com.buyi.dal.entity.dataobject.UserDO;
import com.buyi.dal.entity.viewobject.CompanyVO;
import com.buyi.dal.entity.viewobject.ProductTypeVO;
import com.buyi.domain.service.CompanyService;
import com.buyi.domain.service.UserService;
import com.buyi.domain.service.impl.SecurityPasswdConstant;
import com.buyi.domain.vo.FileUploadInfo;
import com.buyi.util.UrlUtil;
import com.buyi.webapp.buyi.vo.LoginVO;
import com.buyi.webapp.buyi.vo.ProductPublishedVO;

@Controller
public class BuyiWBController {

	private static final int PAGE_SIZE = 10;

	@Autowired
	private UserService userService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private PasswordEncoder pwdEncoder;

	@Autowired
	private FileUploadInfo fileUploadInfo;

	@RequestMapping(value = { UrlUtil.BUYI_WB_LOGIN }, method = RequestMethod.POST)
	public @ResponseBody
	LoginVO login(@RequestParam("uname") String name, @RequestParam("upwd") String pwd) {
		LoginVO loginVO = new LoginVO();
		if (name == null || name.trim().equals("") || pwd == null || pwd.trim().equals("")) {
			loginVO.setMessage("请输入用户名和密码!");
			return loginVO;
		}

		UserDO user = userService.loadUserByName(name);
		if (user == null) {
			loginVO.setMessage("该用户不存在!");
			return loginVO;
		}

		String dbPwd = user.getPwd();
		boolean isMatched = pwdEncoder.matches(pwd, dbPwd);
		if (!isMatched) {
			loginVO.setMessage("用户密码错误,请重新输入密码!");
			return loginVO;
		}

		String resultMessage = pwdEncoder.encode(SecurityPasswdConstant.encodingPasswd(name, pwd));
		loginVO.setMessage(resultMessage);
		loginVO.setSuccess(true);
		loginVO.setUserDO(user);
		return loginVO;
	}

	@RequestMapping(value = { UrlUtil.BUYI_WB_COMPANY })
	public @ResponseBody
	CompanyVO loadCompanyById(@RequestParam("id") Long companyId) {
		CompanyVO CompanyVO = companyService.loadCompanyVO(companyId);
		return CompanyVO;
	}

	@RequestMapping(value = { UrlUtil.BUYI_WB_PRODUCT_TYPE })
	public @ResponseBody
	List<ProductTypeDO> queryProductTypes(@RequestParam("company_id") Long companyId) {
		List<ProductTypeDO> productTypeDOs = companyService.queryProductTypes(companyId, 0, 12);
		return productTypeDOs;
	}

	@RequestMapping(value = { UrlUtil.BUYI_WB_TOP_PRODUCT_TYPE })
	public @ResponseBody
	List<ProductTypeVO> queryTopProductTypes(@RequestParam("company_id") Long companyId, @RequestParam(value = "page_no", defaultValue = "1") int pageNo) {
		int startRow = (pageNo - 1) * 6;
		List<ProductTypeVO> topProductVOs = companyService.queryTopProductVOs(companyId, startRow, 6);
		return topProductVOs;
	}

	@RequestMapping(value = { UrlUtil.BUYI_WB_PRODUCT })
	public @ResponseBody
	List<CompanyProductDO> queryProduct(@RequestParam("type_id") Long typeId, @RequestParam(value = "page_no", defaultValue = "1") int pageNo) {
		int startRow = (pageNo - 1) * PAGE_SIZE;
		List<CompanyProductDO> companyProductDOs = companyService.queryProductsByCondition(typeId, startRow, PAGE_SIZE);
		return companyProductDOs;
	}

	@RequestMapping(value = { UrlUtil.BUYI_WB_PRODUCT_PUBLISHED })
	public @ResponseBody
	ProductPublishedVO publishProduct(@RequestParam(value = "user_id", required = false) String userId, @RequestParam("product_name") String productName, @RequestParam("product_num") String productNum, @RequestParam("product_price") String productPrice, @RequestParam("product_intro") String productIntro, @RequestParam("product_pic") MultipartFile pic) {
		ProductPublishedVO productPublishedVO = new ProductPublishedVO();
		String fileName = null;
		if (pic != null) {
			try {
				fileName = pic.getName() + "_" + new Date().getTime() + ".jpg";
				File destFile = new File(fileUploadInfo.getImagePath(fileName));
				pic.transferTo(destFile);
			} catch (IOException e) {
				productPublishedVO.setMessage("文件上传失败,错误信息:" + e.getMessage());
				return productPublishedVO;
			}
		}

		CompanyProductDO productDO = new CompanyProductDO();

		// FIXME hardcord first.
		productDO.setCompanyId(2l);
		productDO.setProductInfo(productIntro);
		productDO.setProductName(productName);
		productDO.setProductNum(productNum);
		productDO.setProductPic("http://192.168.1.10:8199/img/" + fileName);
		productDO.setProductPrice(productPrice);
		productDO.setProductTypeId(1l);

		Long id = companyService.insertProduct(productDO);
		if (id > 0l) {
			productPublishedVO.setSuccess(true);
			productPublishedVO.setMessage("发布产品成功!");
		} else {
			productPublishedVO.setMessage("发布产品不成功!");
		}
		return productPublishedVO;
	}
}
