package jp.co.e_co.app.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.e_co.app.bean.MenuInfoBean;
import jp.co.e_co.app.bean.PhotoInfo;
import jp.co.e_co.app.common.EcoLog;
import jp.co.e_co.app.common.Utils;
import jp.co.e_co.app.constant.Constants;
import jp.co.e_co.app.entity.ParentUser;
import jp.co.e_co.app.function.Authentication;
import jp.co.e_co.app.repository.ParentUserRepositry;
import jp.co.e_co.app.request.CreateDownLoadFile;
import jp.co.e_co.app.request.GetMenuInfoRequestModel;
import jp.co.e_co.app.request.GetPhotoListRequestModel;
import jp.co.e_co.app.response.CreateDownLoadFileResponseModel;
import jp.co.e_co.app.response.GetMenuInfoResponseModel;
import jp.co.e_co.app.response.GetPhotoListResponseModel;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {
	
	@Autowired
	StringRedisTemplate redisTemplate;
	
	@Autowired
	ParentUserRepositry parentUserRepositry;
	
	final DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * メニュー情報を返す
	 * @return GetMenuInfoResponseModel
	 */
	@RequestMapping(value="/eco/getMenuInfo", method=RequestMethod.POST)
	public GetMenuInfoResponseModel getMenuInfo(@RequestBody GetMenuInfoRequestModel request,
			HttpServletRequest httprRequest,
			HttpServletResponse httpResponse,
			Locale locale, Model model) {
		// 以下2行はクロスドメイン問題を回避
		httpResponse.addHeader( "Access-Control-Allow-Origin", "*" );
		httpResponse.addHeader( "Access-Control-Allow-Methods", "POST" );
		// キャッシュを有効にしたい場合は、こちらのコメントアウトを外します。
//		httpResponse.addHeader( "Access-Control-Max-Age", "1000" );
		if (EcoLog.DEBUG) {
			EcoLog.printOut("getMenuInfo(): year=" + request.getYear() 
					+ ", ecoId=" + request.getEcoId());
		}

		// 認証処理
		Authentication authentication = new Authentication(httprRequest, request.getEcoId());
		if (!authentication.execute()) {
			System.out.println("getMenuInfo(): 認証に失敗しました。");
			return null;
		}
		System.out.println("getMenuInfo(): 認証に成功しました。");
		
		// cookieからセッションID取得
		String sessionId = Utils.extractEcoSessionId(httprRequest);
		// セッション内容確認	
		String userCode = (String) redisTemplate.opsForHash().get(sessionId, "USER_CODE");
		String youchienCode = (String) redisTemplate.opsForHash().get(sessionId, "YOUCHIEN_CODE");
		if (userCode == null || youchienCode == null) {
			System.out.println("USER_CODE or YOUCHIEN_CODEがみつかりません。");
			return null;
		}
		// expire延長
		// トランザクションサポート有効化
		redisTemplate.setEnableTransactionSupport(true);
		redisTemplate.expire(sessionId, Constants.REDIS_TIME_OUT, TimeUnit.SECONDS);
		
		GetMenuInfoResponseModel response = new GetMenuInfoResponseModel();
		response.setmYear(request.getYear());
		
		// 幼稚園コードディレクトリ
		File kgCodeDir = new File("C:\\inetpub\\wwwroot\\sample" + File.separator + youchienCode);
		if(!kgCodeDir.exists()) {
			// 存在しない
			System.out.println("幼稚園ディレクトリが見つかれません。 youchienCode: " + youchienCode);
			return null;
		}
		if(!kgCodeDir.isDirectory()) {
			// ディレクトリではない
			return null;
		}
		// 幼稚園コードディレクトリ下Fileリスト取得
		File[] kgBelowFileList = kgCodeDir.listFiles();
		if(kgBelowFileList == null || kgBelowFileList.length == 0) {
			// Fileリストなし
			System.out.println("幼稚園ディレクトリ配下にFileが見つかりません。youchienCode: " + youchienCode);
			return null;
		}
		// 対象年度ディレクトリ取得
		File yearDir = null;
		for(File _file : kgBelowFileList) {
			if(_file.isDirectory()
					&& _file.getName().equals(request.getYear())) {
				yearDir = _file;
			}
		}
		if(yearDir == null) {
			// 対象年のディレクトリがない
			System.out.println("対象年度が見つかりません。対象年度: " + request.getYear());
			return null;
		}
		// 年度ディレクトリ下Fileリスト取得
		File[] yearDirBelowFileList = yearDir.listFiles();
		if(yearDirBelowFileList == null || yearDirBelowFileList.length == 0) {
			// Fileリストなし
			System.out.println("対象年度配下にファイルが見つかりません。対象年度: " + request.getYear());
			return null;
		}
		response.setMenuList(getMenuInfoBeanList(yearDirBelowFileList, 0, null));
		return response;
	}
	
	/**
	 * メニュー情報を返す
	 * @return GetMenuInfoResponseModel
	 */
	@RequestMapping(value="/eco/getMenuInfoTest", method=RequestMethod.GET)
	public GetMenuInfoResponseModel getMenuInfoTest(String year) {
		GetMenuInfoResponseModel response = new GetMenuInfoResponseModel();
		
		// 幼稚園コードディレクトリ
		File kgCodeDir = new File("C:\\inetpub\\wwwroot\\c9999");
		if(!kgCodeDir.exists()) {
			// 存在しない
			return null;
		}
		if(!kgCodeDir.isDirectory()) {
			// ディレクトリではない
			return null;
		}
		// 幼稚園コードディレクトリ下Fileリスト取得
		File[] kgBelowFileList = kgCodeDir.listFiles();
		if(kgCodeDir == null || kgCodeDir.length() == 0) {
			// Fileリストなし
			return null;
		}
		// 対象年度ディレクトリ取得
		File yearDir = null;
		for(File _file : kgBelowFileList) {
			if(_file.isDirectory()
					&& _file.getName().equals("2015")) {
				yearDir = _file;
			}
		}
		if(yearDir == null) {
			// 対象年のディレクトリがない
			return null;
		}
		// 年度ディレクトリ下Fileリスト取得
		File[] yearDirBelowFileList = yearDir.listFiles();
		if(yearDirBelowFileList == null || yearDirBelowFileList.length == 0) {
			// Fileリストなし
			return null;
		}
		response.setMenuList(getMenuInfoBeanList(yearDirBelowFileList, 0, null));
		
		return response;
	}
	
	/**
	 * メニュー情報を取得する。メニューがなければ nullを返す。
	 * @param fileList File[]
	 * @param type int
	 * @param parent String
	 * @return List<MenuInfoBean>
	 */
	private List<MenuInfoBean> getMenuInfoBeanList(File[] fileList, int type, String parent) {
		if(fileList == null || fileList.length == 0) {
			return null;
		}
		String id = "";
		if(parent != null) {
			id = parent + File.separator;
		}
		
		List<MenuInfoBean> menuInfoList = new ArrayList<MenuInfoBean>();
		for(File _file : fileList) {
			// ディレクトリでなければ次へ
			if(!_file.isDirectory()) {
				continue;
			}
			
			MenuInfoBean menuInfoBean = new MenuInfoBean();
			menuInfoBean.setId(id + _file.getName());	// ID
			menuInfoBean.setType(type);	// タイプ 0：メインメニュー　1：サブメニュー
			menuInfoBean.setName(_file.getName()); // メニュー名
			
			File[] childFileList = _file.listFiles();
			if(childFileList != null && childFileList.length > 0) {
				for(File chileFile : childFileList) {
					if(chileFile.isDirectory()) {
						menuInfoBean.setType(0);	// タイプ 0：メインメニュー　1：サブメニュー
						if (EcoLog.DEBUG) {
							EcoLog.printOut("メニュー：" + menuInfoBean.getName() + " Type0セット");
							EcoLog.printOut("type：" + menuInfoBean.getType());
						}
						break;
					}
				}
				// サブメニューセット
				menuInfoBean.setSubMenuList(getMenuInfoBeanList(childFileList, 1, id + _file.getName()));
			}
			menuInfoList.add(menuInfoBean);
		}
		if(menuInfoList.size() == 0) {
			return null;
		}
		return menuInfoList;
	}
	
	/**
	 * Photo情報のリストを返す
	 * @param request GetPhotoListRequestModel
	 * @return GetPhotoListResponseModel
	 */
	@RequestMapping(value="/eco/getPhotoList", method=RequestMethod.POST)
	public GetPhotoListResponseModel getPhotoList(@RequestBody GetPhotoListRequestModel request,
			HttpServletRequest httprRequest,
			HttpServletResponse httpResponse,
			Locale locale, Model model) {
		if(null == request.getEcoId()) {
			System.out.println("ecoIdなし");
			return null;
		}
		if(null == request.getYear() || "".equals(request.getYear())) {
			System.out.println("yearなし");
			return null;
		}
		if(null == request.getId() || "".equals(request.getId())) {
			System.out.println("idなし");
			return null;
		}
		if (EcoLog.DEBUG) {
			EcoLog.printOut("year:" + request.getYear() + " id:" + request.getId()
					+ " offset:" + request.getOffset()
					+ " ecoId:" + request.getEcoId());
		}
		// 認証処理
		Authentication authentication = new Authentication(httprRequest, request.getEcoId());
		if (!authentication.execute()) {
			System.out.println("getPhotoList(): 認証に失敗しました。");
			return null;
		}
		System.out.println("getPhotoList(): 認証に成功しました。");
		
		// cookieからセッションID取得
		String sessionId = Utils.extractEcoSessionId(httprRequest);
		// セッション内容確認	
		String userCode = (String) redisTemplate.opsForHash().get(sessionId, "USER_CODE");
		String youchienCode = (String) redisTemplate.opsForHash().get(sessionId, "YOUCHIEN_CODE");
		if (userCode == null || youchienCode == null) {
			System.out.println("USER_CODE or YOUCHIEN_CODEがみつかりません。");
			return null;
		}
		// expire延長
		// トランザクションサポート有効化
		redisTemplate.setEnableTransactionSupport(true);
		redisTemplate.expire(request.getEcoId(), Constants.REDIS_TIME_OUT, TimeUnit.SECONDS);
		
		List<ParentUser> parentUsers = 
				parentUserRepositry.findByYouchienCodeAndParentUserCode(Long.parseLong(youchienCode), Long.parseLong(userCode));
		if (parentUsers == null || parentUsers.size() == 0) {
			System.out.println("Faild to find ParentUser: userId=" + userCode);
			return null;
		}
		
		String targetPath = "C:\\inetpub\\wwwroot\\sample" + File.separator + parentUsers.get(0).getYouchienCode() + File.separator + request.getYear() + File.separator + request.getId();
		File targetDir = new File(targetPath);
		if(!targetDir.exists()) {
			return null;
		}
		File[] fileList = targetDir.listFiles();
		if(null == fileList || fileList.length == 0) {
			return null;
		}
		int countOfPage = 50;
		List<PhotoInfo> photoList = new ArrayList<>();
		for(int i = request.getOffset(); i < fileList.length && i - request.getOffset() < countOfPage; i++) {
			PhotoInfo photoInfo = new PhotoInfo();
			File photoFile = fileList[i];
			photoInfo.setUrl(Constants.BASE_URL
					+ File.separator + "eco"
					+ File.separator + "sample"
					+ File.separator + parentUsers.get(0).getYouchienCode()
					+ File.separator + request.getYear() 
					+ File.separator + request.getId()
					+ File.separator + photoFile.getName());
			photoInfo.setPhotoId(parentUsers.get(0).getYouchienCode()
					+ File.separator + request.getYear() 
					+ File.separator + request.getId()
					+ File.separator + photoFile.getName());
			photoList.add(photoInfo);
		}
		// 現在ページ
		int page = 0;
		if(request.getOffset() + 1 >= countOfPage) {
			page = request.getOffset() / countOfPage;
		}
		if(request.getOffset() + 1 % countOfPage > 0) {
			page++;
		}
		// 全ページ数
		int allPage = fileList.length / countOfPage;
		int fraction = fileList.length % countOfPage;
		if(fraction > 0) {
			allPage++;
		}
		
		GetPhotoListResponseModel responseModel = new GetPhotoListResponseModel();
		responseModel.setOffset(request.getOffset());
		responseModel.setCountAll(fileList.length);
		responseModel.setPage(page);
		responseModel.setAllPage(allPage);
		responseModel.setPhotoList(photoList);
		responseModel.setMenuId(request.getId());
		responseModel.setTitle(request.getId().replaceAll("\\" + File.separator, " "));
		return responseModel;
	}
	
	@RequestMapping(value="/eco/createDownLoadFile", method=RequestMethod.POST)
	public CreateDownLoadFileResponseModel createDownLoadFile(@RequestBody CreateDownLoadFile request,
			HttpServletRequest httprRequest,
			HttpServletResponse httpResponse) {
		System.out.println("createDownLoadFile");
		if (request.getEcoId() == null) {
			System.out.println("createDownLoadFile(): ecoIdなし");
			return null;
		}
		if (request.getPhotoIds() == null
				|| request.getPhotoIds().size() == 0) {
			System.out.println("createDownLoadFile(): photoIdsなし");
			return null;
		}
		// セッション内容確認	
		String userCode = (String) redisTemplate.opsForHash().get(request.getEcoId(), "USER_CODE");
		String youchienCode = (String) redisTemplate.opsForHash().get(request.getEcoId(), "YOUCHIEN_CODE");
		if (userCode == null || youchienCode == null) {
			System.out.println("createDownLoadFile(): USER_CODE or YOUCHIEN_CODEがみつかりません。");
			return null;
		}
		// expire延長
		// トランザクションサポート有効化
		redisTemplate.setEnableTransactionSupport(true);
		redisTemplate.expire(request.getEcoId(), Constants.REDIS_TIME_OUT, TimeUnit.SECONDS);
		
		List<File> fileList = new ArrayList<>();
		for (String photoId : request.getPhotoIds()) {
			File targetFile = new File("C:\\production" + File.separator + photoId);
			fileList.add(targetFile);
		}
		ZipOutputStream zos = null;
		System.out.println("now data: " + getNowDate());
		String zipFileName = getNowDate() + "_eco.zip";
		File zipFile = new File("C:\\inetpub\\wwwroot\\sample" 
				+ File.separator + youchienCode + File.separator + "download" + File.separator + zipFileName);
		try {
			zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
			createZip(zos, fileList);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(zos);
		}
		InputStream is = null;
		try {
			is = new FileInputStream(zipFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
		}
		CreateDownLoadFileResponseModel response = new CreateDownLoadFileResponseModel();
		response.setDownLoadFileName(zipFileName);
		response.setYouchienCode(youchienCode);
		return response;
		
	}
	
//	@RequestMapping(value="/createDownLoadFile", method=RequestMethod.POST)
//	public void photoDownLoad(HttpServletResponse response) {
//		System.out.println("photoDownLoad");
//		List<File> fileList = new ArrayList<>();
//		File file_01 = new File("C:\\inetpub\\wwwroot\\c9999\\2015\\入園式\\１前半\\1_Wガンダム.jpg");
//		File file_02 = new File("C:\\inetpub\\wwwroot\\c9999\\2015\\入園式\\１前半\\1_ガンダム.jpg");
//		fileList.add(file_01);
//		fileList.add(file_02);
//		ZipOutputStream zos = null;
//		File zipFile = new File("C:\\inetpub\\wwwroot\\c9999\\download\\hoge.zip");
//		try {
//			zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
//			createZip(zos, fileList);
//		} catch(IOException e) {
//			e.printStackTrace();
//		} finally {
//			IOUtils.closeQuietly(zos);
//		}
//		InputStream is = null;
//		try {
//			is = new FileInputStream(zipFile);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		response.setHeader("Content-Disposition","attachment; filename=\"" + zipFile.getName() +"\"");
//		try {
//			IOUtils.copy(is, response.getOutputStream());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * 現在日時をyyyy/MM/dd HH:mm:ss形式で取得する.<br>
	 */
	private String getNowDate(){
	    final Date date = new Date(System.currentTimeMillis());
	    return df.format(date);
	}
	
	private void createZip(ZipOutputStream zos, List<File> files) throws IOException {
	    byte[] buf = new byte[1024];
	    InputStream is = null;
	    try {
	        for (File file : files) {
	            ZipEntry entry = new ZipEntry(file.getName());
	            zos.putNextEntry(entry);
	            is = new BufferedInputStream(new FileInputStream(file));
	            int len = 0;
	            while ((len = is.read(buf)) != -1) {
	                zos.write(buf, 0, len);
	            }
	        }
	    } finally {
	        IOUtils.closeQuietly(is);
	    }
	}
	
	@RequestMapping(value="/eco/photoDownLoad", method=RequestMethod.GET)
	public void photoDownLoad(String fileName, String youchien, HttpServletResponse response) {
		System.out.println("photoDownLoad");
		System.out.println("fileName: " + fileName);
		File zipFile = new File("C:\\inetpub\\wwwroot\\sample" + File.separator + youchien 
				+ File.separator + "download" + File.separator + fileName);
		response.setHeader("Content-Disposition","attachment; filename=\"" + zipFile.getName() +"\"");
		InputStream is = null;
		try {
			is = new FileInputStream(zipFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			IOUtils.copy(is, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
		}
		zipFile.delete();
	}
}
