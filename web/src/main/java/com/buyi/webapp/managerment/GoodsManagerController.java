package com.buyi.webapp.managerment;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageableEx;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.buyi.dal.entity.dataobject.GoodsDetailDO;
import com.buyi.dal.entity.dataobject.GoodsTypeDO;
import com.buyi.dal.entity.viewobject.GoodsStatus;
import com.buyi.dal.entity.viewobject.Money;
import com.buyi.dal.entity.viewobject.PostfeeVO;
import com.buyi.domain.service.CategoryService;
import com.buyi.domain.service.GoodsDetailService;
import com.buyi.domain.service.GoodsTypeService;
import com.buyi.domain.vo.CategoryVO;
import com.buyi.domain.vo.FileUploadInfo;
import com.buyi.util.UrlUtil;
import com.buyi.webapp.common.JsonResult;
import com.buyi.webapp.managerment.vo.FileUploadVO;
import com.buyi.webapp.managerment.vo.GoodsForm;
import com.buyi.webapp.managerment.vo.GoodsTypeForm;

@Controller
public class GoodsManagerController {

	private static final Logger logger = Logger.getLogger(GoodsManagerController.class);

	private static final Integer MAX_UPLOAD_FILE_SIZE = 1000000;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private FileUploadInfo fileUploadInfo;

	@Autowired
	private GoodsDetailService goodsDetailService;
	
	@Autowired
	private GoodsTypeService goodsTypeService;

	@RequestMapping(value = { UrlUtil.MANAGER_INDEX })
	public void managerIndex() {
		// TODO 获取已经卖出的货物
	}

	@RequestMapping(value = { UrlUtil.SELLED_GOODS })
	public void selledGoods() {

	}

	/**
	 * 获取类目信息
	 * 
	 * @param model
	 */
	@RequestMapping(value = { UrlUtil.PUBLISH_GOODS }, method = RequestMethod.GET)
	public void showPublishGoods(Model model) {
		List<CategoryVO> categoryList = categoryService.getRootCategorys();
		model.addAttribute("categoryList", categoryList);
	}

	/**
	 * 异步获取类目信息
	 * 
	 * @param parentCategoryId
	 * @return
	 */
	@RequestMapping(value = { UrlUtil.QUERY_SUB_CATEGORY })
	public @ResponseBody
	List<CategoryVO> getSubCategorys(@RequestParam("pid") Long parentCategoryId) {
		List<CategoryVO> c = categoryService.queryCategoryByParentId(parentCategoryId);
		return c;
	}

	@RequestMapping(value = { UrlUtil.PUBLISH_GOODS }, method = RequestMethod.POST)
	public String publishGoods(Model model, @RequestParam("category_id") Long categoryId) {
		List<CategoryVO> categoryTree = categoryService.queryCategoryTree(categoryId);
		model.addAttribute("categoryTree", categoryTree);
		GoodsForm form = new GoodsForm();
		form.setCategoryId(categoryId);
		model.addAttribute("goodsForm", form);
		return UrlUtil.GOODS_SETTING;
	}

	/**
	 * 图片提交检查json
	 * 
	 * @param model
	 * @param pic
	 * @return
	 */
	@RequestMapping(value = { UrlUtil.UPLOAD_FILES }, method = RequestMethod.POST)
	public @ResponseBody
	FileUploadVO uploadFiles(Model model, @RequestParam(value = "imgFile", required = false) MultipartFile pic) {
		FileUploadVO fileUploadVO = new FileUploadVO();
		if (pic == null) {
			fileUploadVO.setError(FileUploadVO.UPLOAD_FAIL);
			fileUploadVO.setMessage("上传文件出错!");
			return fileUploadVO;
		}

		if (pic.getSize() > MAX_UPLOAD_FILE_SIZE) {
			fileUploadVO.setError(FileUploadVO.UPLOAD_FAIL);
			fileUploadVO.setMessage("文件超过1M,请检查");
			return fileUploadVO;
		}

		String fileName = null;
		try {
			fileName = pic.getName() + "_" + new Date().getTime() + ".jpg";
			String bigFile = fileUploadInfo.getImagePath(fileName);
			Thumbnails.of(pic.getInputStream()).allowOverwrite(true).size(800, 2000).toFile(bigFile);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		fileUploadVO.setError(FileUploadVO.UPLOAD_SUCCESS);
		fileUploadVO.setUrl(UrlUtil.getBigImagePath(fileName));
		return fileUploadVO;
	}

	/**
	 * 提交商品
	 */
	public void submitGoods() {

	}

	@RequestMapping(value = { UrlUtil.DIVIVE_GOODS })
	public void diviveGoods() {

	}

	/**
	 * 添加商品信息
	 * 
	 * @param goodsForm
	 * @param result
	 * @param imageuploads
	 * @return
	 */
	@RequestMapping(value = { UrlUtil.ADD_GOODS }, method = RequestMethod.POST)
	public String addGoods(@Valid GoodsForm goodsForm, BindingResult result, @RequestParam MultipartFile[] imageuploads) {
		if (result.hasErrors()) {
			return UrlUtil.GOODS_SETTING;
		}

		String title = goodsForm.getGoodsTitle();
		Long cid = goodsForm.getCategoryId();
		boolean hasObj = goodsDetailService.querySmallGoodsDetailByTitle(title, cid);
		if (hasObj) {
			FieldError dubbleFieldError = new FieldError("goodsForm", "goodsTitle", title, false, new String[] { "doubble" }, null, "该商品已经存在!");
			result.addError(dubbleFieldError);
			return UrlUtil.GOODS_SETTING;
		}

		GoodsDetailDO goodsDetailDO = new GoodsDetailDO();
		// 上傳文件
		if (imageuploads != null) {
			List<String> fileList = new ArrayList<String>();
			for (int i = 0; i < imageuploads.length; i++) {
				final MultipartFile file = imageuploads[i];
				if (file.getSize() > 0) {
					String fileName = null;
					try {
						fileName = file.getName() + "_" + new Date().getTime() + ".jpg";
						final String smallFile = fileUploadInfo.getSmallImagePath(fileName);
						String bigFile = fileUploadInfo.getImagePath(fileName);
						String tinyFile = fileUploadInfo.getTinyImagePath(fileName);
						Thumbnails.of(file.getInputStream()).size(800, 2000).toFile(bigFile);
						new GeneratorImageThread(file.getInputStream(), 330, 330, smallFile);
						new GeneratorImageThread(file.getInputStream(), 60, 60, tinyFile);
					} catch (IOException e) {
						logger.error(e.getMessage());
					}

					fileList.add(fileName);
				}
			}

			if (!fileList.isEmpty()) {
				for (int i = 0, size = fileList.size(); i < size; i++) {
					if (i == 0) {
						goodsDetailDO.setGoodsPic1(fileList.get(i));
					} else if (i == 1) {
						goodsDetailDO.setGoodsPic2(fileList.get(i));
					} else if (i == 2) {
						goodsDetailDO.setGoodsPic3(fileList.get(i));
					}
				}
			} else {
				FieldError picNamesFieldError = new FieldError("goodsForm", "picNames", title, false, new String[] { "goods.picNames.input" }, null, "请至少选择一个图片!");
				result.addError(picNamesFieldError);
				return UrlUtil.GOODS_SETTING;
			}
		}

		goodsDetailDO.setTitle(goodsForm.getGoodsTitle());
		goodsDetailDO.setCategoryId(goodsForm.getCategoryId());
		goodsDetailDO.setGoodsDesc(goodsForm.getGoodsContent());
		String price = goodsForm.getGoodsPrice();
		Integer amount = Integer.valueOf(price.split("\\.")[0]);
		goodsDetailDO.setOriginPrice(new Money(amount * 100));
		goodsDetailDO.setStatus(GoodsStatus.PREVIEW);

		PostfeeVO vo = new PostfeeVO();
		vo.setPostfeeType(goodsForm.getPostfeeType() == null ? 0 : goodsForm.getPostfeeType());
		vo.setEms(goodsForm.getEms() == null ? 0 : goodsForm.getEms());
		vo.setPinyou(goodsForm.getPinyou() == null ? 0 : goodsForm.getPinyou());
		vo.setKuaidi(goodsForm.getKuaidi() == null ? 0 : goodsForm.getKuaidi());
		goodsDetailDO.setPostfeeVO(vo);
		goodsDetailService.saveGoodsDetail(goodsDetailDO);
		return "redirect:" + UrlUtil.SEARCH_GOODS;
	}

	/**
	 * 搜索商品
	 * 
	 * @param model
	 * @param searchName
	 *            商品名称
	 * @param page
	 */
	@RequestMapping(value = { UrlUtil.SEARCH_GOODS })
	public void showGoodsDetailList(Model model, @RequestParam(value = "q", required = false) String searchName, @PageableDefaults(10) PageableEx page) {
		int startRow = page.getOffset();
		int pageSize = page.getPageSize();
		List<GoodsDetailDO> goods = goodsDetailService.querySmallGoodsDetail(searchName, startRow, pageSize);
		int totalRecords = goodsDetailService.countGoodsDetails(searchName);
		page.setTotalSize(totalRecords);
		model.addAttribute("goodsList", goods);
		model.addAttribute("pageable", page);
	}

	/**
	 * 查看商品详情
	 * 
	 * @param model
	 * @param goodsId
	 */
	@RequestMapping(value = { UrlUtil.SHOW_GOODS }, method = RequestMethod.GET)
	public void showGoodsDetail(Model model, @RequestParam(value = "id", required = true) Long goodsId) {
		GoodsDetailDO goodsDetail = goodsDetailService.queryGoodsDetailById(goodsId);
		Long categoryId = goodsDetail.getCategoryId();
		model.addAttribute("goodsDetail", goodsDetail);
		List<CategoryVO> categoryTree = categoryService.queryCategoryTree(categoryId);
		if (categoryTree.size() > 0) {
			List<CategoryVO> categoryList = new ArrayList<CategoryVO>();
			for (int i = categoryTree.size() - 1; i >= 0; i--) {
				categoryList.add(categoryTree.get(i));
			}
			model.addAttribute("categoryList", categoryList);
		}
		
		List<GoodsTypeDO> goodsTypes = goodsTypeService.fetchGoodsTypesByGoodsId(goodsId);
		model.addAttribute("goodsTypes", goodsTypes);
	}

	/**
	 * 显示编辑商品详情
	 * 
	 * @param model
	 * @param goodsId
	 */
	@RequestMapping(value = { UrlUtil.EDIT_GOODS }, method = RequestMethod.GET)
	public void showEditGoodsDetail(Model model, @RequestParam(value = "id", required = true) Long goodsId) {
		GoodsDetailDO goodsDetail = goodsDetailService.queryGoodsDetailById(goodsId);
		GoodsForm goodsForm = new GoodsForm();
		goodsForm.setId(goodsDetail.getId());
		goodsForm.setGoodsTitle(goodsDetail.getTitle());
		goodsForm.setCategoryId(goodsDetail.getCategoryId());
		goodsForm.setGoodsContent(goodsDetail.getGoodsDesc());
		goodsForm.setGoodsPrice(goodsDetail.getOriginPrice().convertString());
		PostfeeVO postfeeVO = goodsDetail.getPostfeeVO();
		goodsForm.setPostfeeType(postfeeVO.getPostfeeType());
		goodsForm.setEms(postfeeVO.getEms());
		goodsForm.setPinyou(postfeeVO.getPinyou());
		goodsForm.setKuaidi(postfeeVO.getKuaidi());
		model.addAttribute("goodsForm", goodsForm);
	}

	/**
	 * 提交编辑商品信息
	 * 
	 * @param goodsForm
	 * @param result
	 * @return
	 */
	@RequestMapping(value = { UrlUtil.EDIT_GOODS }, method = RequestMethod.POST)
	public String saveEditGoodsDetail(@Valid GoodsForm goodsForm, BindingResult result) {
		if (result.hasErrors()) {
			return UrlUtil.EDIT_GOODS;
		}

		Long id = goodsForm.getId();
		if (id == null) {
			return UrlUtil.EDIT_GOODS;
		}
		GoodsDetailDO goodsDetail = goodsDetailService.queryGoodsDetailById(id);
		if (goodsDetail == null) {
			return UrlUtil.EDIT_GOODS;
		}

		String price = goodsForm.getGoodsPrice();
		Integer amount = Integer.valueOf(price.split("\\.")[0]);
		goodsDetail.setOriginPrice(new Money(amount * 100));
		goodsDetail.setGoodsDesc(goodsForm.getGoodsContent());
		PostfeeVO vo = new PostfeeVO();
		vo.setPostfeeType(goodsForm.getPostfeeType() == null ? 0 : goodsForm.getPostfeeType());
		vo.setEms(goodsForm.getEms() == null ? 0 : goodsForm.getEms());
		vo.setPinyou(goodsForm.getPinyou() == null ? 0 : goodsForm.getPinyou());
		vo.setKuaidi(goodsForm.getKuaidi() == null ? 0 : goodsForm.getKuaidi());
		goodsDetail.setPostfeeVO(vo);
		goodsDetailService.updateGoodsDetail(goodsDetail);
		return "redirect:" + UrlUtil.SEARCH_GOODS;
	}
	
	/**
	 * 商品类型添加
	 * 
	 * @param model
	 */
	@RequestMapping(value = { UrlUtil.GOODS_TYPE_ADD }, method = RequestMethod.GET)
	public void showGoodsTypeAdd(Model model, @RequestParam(value = "goods_id", required = true) Long goodsId) {
		GoodsTypeForm goodsTypeForm = new GoodsTypeForm();
		goodsTypeForm.setGoodsId(goodsId);
		model.addAttribute("goodsTypeForm", goodsTypeForm);
	}
	
	@RequestMapping(value = { UrlUtil.GOODS_TYPE_ADD }, method = RequestMethod.POST)
	public String goodsTypeAdd(@Valid GoodsTypeForm goodsTypeForm, BindingResult result, @RequestParam MultipartFile imageupload) {
		if (result.hasErrors()) {
			return UrlUtil.GOODS_TYPE_ADD;
		}
		
		if (imageupload == null) {
			FieldError uploadFileError = new FieldError("goodsTypeForm", "picName", null, false, new String[] { "goods.picname.uploadfile" }, null, "请上传文件!");
			result.addError(uploadFileError);
			return UrlUtil.GOODS_TYPE_ADD;
		}

//		if (imageupload.getSize() > MAX_UPLOAD_FILE_SIZE) {
//			FieldError overMaxFieldError = new FieldError("goodsTypeForm", "picName", null, false, new String[] { "goods.picname.overmax" }, null, "请上传超过1M");
//			result.addError(overMaxFieldError);
//			return UrlUtil.GOODS_TYPE_ADD;
//		}
		
		Long goodsId = goodsTypeForm.getGoodsId();
		Integer goodsNum = goodsTypeForm.getGoodsNum();
		String typeName = goodsTypeForm.getTypeName();
		
		String fileName = null;
		try {
			fileName = imageupload.getName() + "_" + new Date().getTime() + ".jpg";
			final String smallFile = fileUploadInfo.getSmallImagePath(fileName);
			String tinyFile = fileUploadInfo.getTinyImagePath(fileName);
			Thumbnails.of(imageupload.getInputStream()).size(330, 330).toFile(smallFile);
			Thumbnails.of(imageupload.getInputStream()).size(40, 40).toFile(tinyFile);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
		GoodsTypeDO typeDO = new GoodsTypeDO();
		typeDO.setGoodsId(goodsId);
		typeDO.setGoodsNum(goodsNum);
		typeDO.setTypeName(typeName);
		typeDO.setPicName(fileName);
		goodsTypeService.insertGoodsType(typeDO);
		return "redirect:" + UrlUtil.getBackendUrl(UrlUtil.SHOW_GOODS, true).pm("id", goodsId);
	}
	
	/**
	 * 商品类型编辑显示
	 * 
	 */
	@RequestMapping(value={UrlUtil.GOODS_TYPE_EDIT}, method=RequestMethod.GET)
	public void showEditGoodsType(Model model, @RequestParam(value="id", required=true)Long id) {
		GoodsTypeDO typeDO = goodsTypeService.loadGoodsTypeById(id);
		if (typeDO == null) {
			return;
		}
		
		GoodsTypeForm typeForm = new GoodsTypeForm();
		typeForm.setGoodsId(typeDO.getGoodsId());
		typeForm.setGoodsNum(typeDO.getGoodsNum());
		typeForm.setPicName(typeDO.getPicName());
		typeForm.setId(typeDO.getId());
		typeForm.setTypeName(typeDO.getTypeName());
		model.addAttribute("goodsTypeForm", typeForm);
	}
	
	@RequestMapping(value={UrlUtil.GOODS_TYPE_EDIT}, method=RequestMethod.POST)
	public String editGoodsType(@Valid GoodsTypeForm goodsTypeForm, BindingResult result) {
		if (result.hasErrors()) {
			return UrlUtil.GOODS_TYPE_EDIT;
		}
		
		Long id = goodsTypeForm.getId();
		Long goodsId = goodsTypeForm.getGoodsId();
		GoodsTypeDO typeDO = goodsTypeService.loadGoodsTypeById(id);
		if (typeDO == null) {
			return "redirect:" + UrlUtil.getBackendUrl(UrlUtil.SHOW_GOODS, true).pm("id", goodsId);
		}
		String typeName = goodsTypeForm.getTypeName();
		int goodsNum = goodsTypeForm.getGoodsNum();
		typeDO.setTypeName(typeName);
		typeDO.setGoodsNum(goodsNum);
		goodsTypeService.updateGoodsType(typeDO);
		return "redirect:" + UrlUtil.getBackendUrl(UrlUtil.SHOW_GOODS, true).pm("id", goodsId);
	}
	
	
	@RequestMapping(value={UrlUtil.GOODS_TYPE_DELETE}, method=RequestMethod.GET)
	public @ResponseBody JsonResult deleteGoodsType(@RequestParam(value="id", required=true)Long id) {
		JsonResult result = new JsonResult();
		boolean deleteSuccess = goodsTypeService.deleteGoodsType(id);
		result.setSuccess(deleteSuccess);
		if (!deleteSuccess) {
			result.setMessage("删除商品类型出错,请检查!");
		}
		return result;
	}
	

	/**
	 * 生成缩略图线程
	 * 
	 * @author cxf128
	 * 
	 */
	public class GeneratorImageThread extends Thread {

		private int width;
		private int height;
		private InputStream is;
		private String toFilePath;

		public GeneratorImageThread(InputStream is, int width, int height, String toFilePath) {
			this.width = width;
			this.height = height;
			this.is = is;
			this.toFilePath = toFilePath;
			this.start();
		}

		@Override
		public void run() {
			try {
				Thumbnails.of(is).size(width, height).toFile(toFilePath);
			} catch (IOException e) {
				logger.error(e.toString());
			}
		}

	}

}
