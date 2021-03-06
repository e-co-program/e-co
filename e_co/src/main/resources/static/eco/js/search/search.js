/**
 * ダウンロードモデル
 */
function DownLoadModel() {
	var self = this;
	
	self.count = ko.observable(0);
}

var downLoadModel = new DownLoadModel();

function TopClick() {
	// 注意事項非表示
	$("#notes").show();
	// 写真情報系クリア
	photoInfoModelsArray.title(null);
	photoInfoModelsArray.photoInfosArray.removeAll();
	photoInfoModelsArray.photoShow(false);
	photoInfoModelsArray.prev(false);
	photoInfoModelsArray.next(false);
	// 現在開いてるメニュー情報取得
	var year = menuInfoModel.year();
	menuInfoLoad(year);
}

/**
 * メニュー情報モデル
 */
function MenuInfoModel() {
	var self = this;
	// 年度
	self.year = ko.observable();
	// メニュー情報
	self.menuInfo = ko.observableArray();
	// 写真情報検索対象ID
	self.searchMenuId;
	
	// 写真情報を取得する
	self.getPhoto = function(menuInfo) {
		// console.log("Photo情報取得");
		self.searchMenuId = menuInfo.id;	// 検索対象IDセット
		$.ajax({
			type:"POST",	// method = "POST"
			url: ecoInfo.baseUrl + '/eco/getPhotoList',	// 送信先のURL
			data: JSON.stringify({ year: self.year(),
				id : menuInfo.id,
				offset : 0,
				ecoId: getCookie("eco-session")}),	// JSONデータ本体
			contentType: 'application/json', // リクエストの Content-Type
			dataType: "json",	// レスポンスをJSONとしてパースする
			success: function(data) {
				// console.log("success");
				// console.log(data);
				if (null == data || null == data.photoList || data.photoList.lenght == 0) {
					alert( "写真情報の取得に失敗しました。" );
					return;
				}
				// 注意事項非表示
				$("#notes").hide();
				// 写真情報クリア
				photoInfoModelsArray.photoInfosArray.removeAll();
				// オフセットクリア
				photoInfoModelsArray.offset = 0;
				convertToPhotoInfoModels(data);
			},
			error: function() {
				// console.log("error");
				alert( "写真情報の取得に失敗しました。" );
			},
			complete: function() {
				// console.log("complete");
			}
		});
	}
	
//	self.afterFunction = function(element) {
//		$(function(){
//			$(".event").click(function(){
//				$(this).children("div").slideToggle("slow");
//			});
//		});
//	}
}

var menuInfoModel = new MenuInfoModel();

//　チェックされた写真url格納
var checkedPhotos = [];

/**
 * 写真情報モデル
 */
function PhotoInfoModel() {
	var self = this;
	
	self.url = ko.observable();
	
	self.photoId;
	
	self.checked = ko.observable(false);
	
	self.cIconUrl = ko.observable("/eco/img/search/check_off.png");
	
	self.photoCheck = function(photoInfo) {
		// console.log("写真チェック");
		// console.log("photoId:" + photoInfo.photoId);
		// 配列に格納済みか確認
		var count = downLoadModel.count();
		var index = checkedPhotos.indexOf(photoInfo.photoId);
		if (index == -1) {
			checkedPhotos.push(photoInfo.photoId);
			photoInfo.checked(true);
			photoInfo.cIconUrl("/eco/img/search/check_on.png");
			count++;
		} else {
			checkedPhotos.splice(index, 1);
			photoInfo.checked(false);
			photoInfo.cIconUrl("/eco/img/search/check_off.png");
			count--;
		}
		downLoadModel.count(count);
		for (var i = 0; i < checkedPhotos.length; i++) {
			// console.log("選択写真： " + checkedPhotos[i]);
		}
	}
}

/**
 * 写真情報リストモデル
 */
function PhotoInfoModels() {
	var self = this;
	
	self.photoInfos = ko.observableArray();
}

/**
 * 写真情報連想配列モデル
 */
function PhotoInfoModelsArray() {
	var self = this;
	
	self.offset = 0;
	
	self.photoShow = ko.observable(false);
	
	self.prev = ko.observable(false);
	
	self.next = ko.observable(false);
	
	self.title = ko.observable();
	
	self.countStr = ko.observable();
	
	self.photoInfosArray = ko.observableArray();
	
	self.nextClick = function() {
		self.offset += 50;
		// console.log("Photo情報取得 next");
		$.ajax({
			type:"POST",	// method = "POST"
			url: ecoInfo.baseUrl + '/eco/getPhotoList',	// 送信先のURL
			data: JSON.stringify({ year: menuInfoModel.year(),
				id : menuInfoModel.searchMenuId,
				offset : self.offset,
				ecoId: getCookie("eco-session")}),	// JSONデータ本体
			contentType: 'application/json', // リクエストの Content-Type
			dataType: "json",	// レスポンスをJSONとしてパースする
			success: function(data) {
				// console.log("success");
				// console.log(data);
				if (null == data || null == data.photoList || data.photoList.lenght == 0) {
					self.offset -= 50;
					alert( "写真情報の取得に失敗しました。" );
					return;
				}
				// 写真情報クリア
				photoInfoModelsArray.photoInfosArray.removeAll();
				convertToPhotoInfoModels(data);
			},
			error: function() {
				// console.log("error");
				self.offset -= 50;
				alert( "写真情報の取得に失敗しました。" );
			},
			complete: function() {
				// console.log("complete");
			}
		});
	}
	
	self.prevClick = function() {
		self.offset -= 50;
		// console.log("Photo情報取得 prev");
		$.ajax({
			type:"POST",	// method = "POST"
			url: ecoInfo.baseUrl + '/eco/getPhotoList',	// 送信先のURL
			data: JSON.stringify({ year: menuInfoModel.year(),
				id : menuInfoModel.searchMenuId,
				offset : self.offset,
				ecoId: getCookie("eco-session")}),	// JSONデータ本体
			contentType: 'application/json', // リクエストの Content-Type
			dataType: "json",	// レスポンスをJSONとしてパースする
			success: function(data) {
				// console.log("success");
				// console.log(data);
				if (null == data || null == data.photoList || data.photoList.lenght == 0) {
					self.offset += 50;
					alert( "写真情報の取得に失敗しました。" );
					return;
				}
				// 写真情報クリア
				photoInfoModelsArray.photoInfosArray.removeAll();
				convertToPhotoInfoModels(data);
			},
			error: function() {
				// console.log("error");
				self.offset += 50;
				alert( "写真情報の取得に失敗しました。" );
			},
			complete: function() {
				// console.log("complete");
			}
		});
	}
}

photoInfoModelsArray = new PhotoInfoModelsArray();

/**
 * jsonデータをPhotoInfoModelsに変換する
 */
var convertToPhotoInfoModels = function(data) {
	// console.log("title: " + data.title);
	photoInfoModelsArray.title(data.title);
	var photoInfoModels = new PhotoInfoModels();
	var index = 0;
	for(var i in data.photoList) {
		var photoInfoModel = new PhotoInfoModel();
		photoInfoModel.url(data.photoList[i].url);
		photoInfoModel.photoId = data.photoList[i].photoId;
		// 選択済みの写真ならチェックを付ける
		if (checkedPhotos.indexOf(data.photoList[i].photoId) >= 0) {
			photoInfoModel.checked(true);
			photoInfoModel.cIconUrl("/eco/img/search/check_on.png");
		}
		photoInfoModels.photoInfos.push(photoInfoModel);
		if(index == 4) {
			photoInfoModelsArray.photoInfosArray.push(photoInfoModels);
			photoInfoModels = new PhotoInfoModels();
			index = 0;
		} else {
			index++;
		}
	}
	if(photoInfoModels.photoInfos().length > 0) {
		photoInfoModelsArray.photoInfosArray.push(photoInfoModels);
	}
	// 写真情報が取得できていたら
	if (data.photoList.length > 0) {
		photoInfoModelsArray.countStr((data.offset + 1) + " ～ " + (data.offset + data.photoList.length) + '枚/' + data.countAll + "枚");
		if (!photoInfoModelsArray.photoShow()) {
			photoInfoModelsArray.photoShow(true);
		}
		if(data.page > 1) {
			photoInfoModelsArray.prev(true);
		} else {
			photoInfoModelsArray.prev(false);
		}
		if(data.allPage > data.page) {
			photoInfoModelsArray.next(true);
		} else {
			photoInfoModelsArray.next(false);
		}
	}
}

/**
 * メニュークリック処理
 */
var slide = function(element, event) {
	if($(element).hasClass('event') || $(element).hasClass('child_event')) {
		$(element).children("div").slideToggle("slow");
		event.stopPropagation();
	}
}

// "年度タブ"クリックシ処理
var changeYear = function(element) {
	// console.log("年度変更:" + element.id);
	// 写真情報系クリア
	photoInfoModelsArray.title(null);
	photoInfoModelsArray.photoInfosArray.removeAll();
	photoInfoModelsArray.photoShow(false);
	photoInfoModelsArray.prev(false);
	photoInfoModelsArray.next(false);
	// メニュー情報取得
	menuInfoLoad(element.id);
	// 子要素取得
	var childs = element.childNodes;

	var years = ecoInfo.years;
	// 選択されていたタブのクラス名変更
	$(".ad_select").removeClass("ad_select").addClass("ad");
	// 選択されたタブのクラス名変更
	$("#" + element.id).children('span').removeClass("ad").addClass("ad_select");
	
	// 選択したタブのindex取得処理
	var target = $("#" + element.id);
	var targetIndex = $("li").index(target);
	
	// タブのimg変更処理
	for (var i = 0; i < years.length; i++) {
		if (targetIndex == i) {
			switch (i) {
			case 0:
				$("#" + years[i]).children('img').attr("src", "/eco/img/search/first_on.png");
				break;
			case 1:
				$("#" + years[i]).children('img').attr("src", "/eco/img/search/second_on.png");
				break;
			case 2:
				$("#" + years[i]).children('img').attr("src", "/eco/img/search/third_on.png");
				break;
			case 3:
				$("#" + years[i]).children('img').attr("src", "/eco/img/search/force_on.png");
				break
			default:
				break;
			}
			continue;
		}
		
		$("#" + years[i]).children('img').attr("src", "/eco/img/search/tab_off.png");
	}
	
	
	if($(element).children('ad_select')) {
		// console.log("ad_select");
	} else if($(element).children('ad')) {
		// console.log("ad");
	}
	
}

// "ダウンロード"クリック処理
var downLoad = function() {
	// console.log("ダウンロード");
	// チェックされた写真情報のサイズ確認
	if (checkedPhotos.length == 0) {
		return;
	}
	jConfirm('ダウンロードを開始します。\n開始までしばらく時間がかかる事があります。', '写真ダウンロード', function(r) {
		if (r) {
			$.ajax({
				type: 'POST',	// method = "GET"
				url: ecoInfo.baseUrl + '/eco/createDownLoadFile',	// 送信先のURL
				data: JSON.stringify({
					ecoId : getCookie("eco-session"),
					photoIds : checkedPhotos}),	// JSONデータ本体
				contentType: 'application/json', // リクエストの Content-Type
				success: function(data) {
					// console.log("success");
					if (data == null || data.downLoadFileName == null) {
						return;
					}
					location.href = ecoInfo.baseUrl + "/eco/photoDownLoad?fileName=" + data.downLoadFileName
						+ "&youchien=" + data.youchienCode;
					// TODO:チャッククリア処理を追加する
					// 全行の写真情報
					var photoInfosArray = photoInfoModelsArray.photoInfosArray();
					for (var i = 0; i < photoInfosArray.length; i++) {
						// 1行分の写真情報
						var photoInfos = photoInfosArray[i].photoInfos();
						for (var j = 0; j < photoInfos.length; j++) {
							var photoInfo = photoInfos[j];
							photoInfo.checked(false);
							photoInfo.cIconUrl("/eco/img/search/check_off.png");
						}
					}
					// 選択写真保持配列クリア
					checkedPhotos = [];
					// 選択写真数初期化
					downLoadModel.count(0);
				},
				error: function() {
					// console.log("error");
				},
				complete: function() {
					// console.log("complete");
				}
			});
		}
     });
}

// onloadでコール
var menuInfoLoad = function(year) {
	// メニュー情報取得
	$.ajax({
		type: 'POST',	// method = "POST"
		url: ecoInfo.baseUrl + '/eco/getMenuInfo',	// 送信先のURL
		data: JSON.stringify({ year : year,
			ecoId : getCookie("eco-session")}),	// JSONデータ本体
		contentType: 'application/json', // リクエストの Content-Type
		dataType: 'json',	// レスポンスをJSONとしてパースする
		success: function(data) {
			// console.log("success");
			// console.log(data);
			if (data == null) {
				menuInfoModel.year(null);
				menuInfoModel.menuInfo(null);
				return;
			}
			menuInfoModel.year(data.mYear);
			menuInfoModel.menuInfo(data.menuList);
			// console.log(menuInfoModel.menuInfo());
		},
		error: function() {
			// console.log("error");
		},
		complete: function() {
			// console.log("complete");
		}
	});
};

// キーを指定してCookieから値を取得する
var getCookie = function(name) {
	var result = null;
	var cookieName = name + '=';
	var allcookies = document.cookie;
	
	var position = allcookies.indexOf( cookieName );
	if( position != -1 ) {
		var startIndex = position + cookieName.length;
		var endIndex = allcookies.indexOf( ';', startIndex );
		if( endIndex == -1 ) {
			endIndex = allcookies.length;
		}
		result = decodeURIComponent(allcookies.substring( startIndex, endIndex ) );
	}
	return result;
}

// ページ読み込み後に実行
window.onload = function() {
	// console.log("baseUrl:" + ecoInfo.baseUrl);
	
	ko.applyBindings(downLoadModel, document.getElementById('dl_count'));
	ko.applyBindings(menuInfoModel, document.getElementById('side_menu'));
	ko.applyBindings(photoInfoModelsArray, document.getElementById('main'));
	
	// メニュー情報取得
	menuInfoLoad(ecoInfo.initYear);
	
	// 拡大画面ボタン押下時のイベントをバインド
	lbSelectClickBind();
};

function lbSelectClickBind(){
	$('.lb-select-image-btn').on("click", function() {
		var photoInfosArray = photoInfoModelsArray.photoInfosArray();
		var target;
		for (var i = 0; i < photoInfosArray.length; i++) {
			var photoInfos = photoInfosArray[i].photoInfos();
			for (var j = 0; j < photoInfos.length; j++) {
				if(photoInfos[j].photoId === $(this).attr("photoId")){
					target = photoInfos[j];
				}
			}
		}

		var count = downLoadModel.count();
		var index = checkedPhotos.indexOf($(this).attr("photoId"));
		if (index == -1) {
			checkedPhotos.push($(this).attr("photoId"));
			target.checked(true);
			target.cIconUrl("img/search/check_on.png");
			count++;
		} else {
			checkedPhotos.splice(index, 1);
			target.checked(false);
			target.cIconUrl("img/search/check_off.png");
			count--;
		}
		downLoadModel.count(count);
	});
}