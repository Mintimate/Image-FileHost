$(function() {
		initImageInput("input-image");
		initFileInput("input-file")
	})

	function initImageInput(ctrlName) {
		var control = $('#' + ctrlName);
		control.fileinput({
			language: 'zh', //设置语言
			uploadUrl: "/imageHost/uploadImage", //上传的地址
			allowedFileExtensions: ['jpg','jpeg', 'png', 'webp', 'bmp'], //接收的文件后缀
			maxFilesNum: 1, //上传最大的文件数量
			//uploadExtraData:{"id": 1, "fileName":'123.mp3'},
			uploadAsync: true, //默认异步上传
			showUpload: true, //是否显示上传按钮
			showRemove: true, //显示移除按钮
			showPreview: true, //是否显示预览
			showCaption: true, //是否显示标题
			dropZoneTitle: "拖动图片到这里即可压缩&转换图片嗷",
			browseClass: "btn btn-primary", //按钮样式
			dropZoneEnabled: true, //是否显示拖拽区域
			//minImageWidth: 50, //图片的最小宽度
			//minImageHeight: 50,//图片的最小高度
			//maxImageWidth: 1000,//图片的最大宽度
			//maxImageHeight: 1000,//图片的最大高度
			maxFileSize: 1024 * 50, //单位为kb，如果为0表示不限制文件大小
			//minFileCount: 0,
			maxFileCount: 3, //表示允许同时上传的最大文件个数
			enctype: 'multipart/form-data',
			validateInitialCount: true,
			previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
			msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
			uploadExtraData: function() {
				var data = {
					imageType: $("#imageSelect").val(),
					theUserName: $("#theUserName").text()
				};
				console.log(data.imageType)
				return data;
			}

		}).on('filepreupload', function(event, data, previewId, index) { //上传中
			var form = data.form,
				files = data.files,
				extra = data.extra,
				response = data.response,
				reader = data.reader;
			console.log('文件正在上传');
		}).on("fileuploaded", function(event, data, previewId, index) { //一个文件上传成功
			console.log('文件上传成功！' + data.id);
			imageName = data.response.name;
			console.log(data.response.path)
			document.getElementById("download").innerHTML = '<div class="alert alert-success col-12 text-center" style="font-size: small">' +
				'<button type="button" class="close" data-dismiss="alert">&times;</button>添加成功</div>'
		}).on('fileerror', function(event, data, msg) { //一个文件上传失败
			console.log('文件上传失败！' + data.id);
		})
	}
	
	function initFileInput(ctrlName) {
		var control = $('#' + ctrlName);
		control.fileinput({
			language: 'zh', //设置语言
			uploadUrl: "/fileHost/uploadFile", //上传的地址
			allowedFileExtensions: ['.*'], //接收的文件后缀
			maxFilesNum: 1, //上传最大的文件数量
			//uploadExtraData:{"id": 1, "fileName":'123.mp3'},
			uploadAsync: true, //默认异步上传
			showUpload: true, //是否显示上传按钮
			showRemove: true, //显示移除按钮
			showPreview: true, //是否显示预览
			showCaption: true, //是否显示标题
			dropZoneTitle: "拖动文件到此嗷～",
			browseClass: "btn btn-primary", //按钮样式
			dropZoneEnabled: true, //是否显示拖拽区域
			//minImageWidth: 50, //图片的最小宽度
			//minImageHeight: 50,//图片的最小高度
			//maxImageWidth: 1000,//图片的最大宽度
			//maxImageHeight: 1000,//图片的最大高度
			maxFileSize: 1024 * 1024 *10, //单位为kb，如果为0表示不限制文件大小
			//minFileCount: 0,
			maxFileCount: 3, //表示允许同时上传的最大文件个数
			enctype: 'multipart/form-data',
			validateInitialCount: true,
			previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
			msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
			uploadExtraData: function() {
				var data = {
					fileType: $("#fileType").val(),
					fileTypeDetail: $("#fileTypeDetail").val(),
					theUserName: $("#theUserName").text()
				};
				console.log(data);
				return data;
			}
	
		}).on('filepreupload', function(event, data, previewId, index) { //上传中
			var form = data.form,
				files = data.files,
				extra = data.extra,
				response = data.response,
				reader = data.reader;
			console.log('文件正在上传');
		}).on("fileuploaded", function(event, data, previewId, index) { //一个文件上传成功
			console.log('文件上传成功！' + data.id);
			imageName = data.response.name;
			console.log(data.response.path)
			document.getElementById("download").innerHTML = '<div class="alert alert-success col-12 text-center" style="font-size: small">' +
				'<button type="button" class="close" data-dismiss="alert">&times;</button>添加成功</div>'
		}).on('fileerror', function(event, data, msg) { //一个文件上传失败
			console.log('文件上传失败！' + data.id);
		})
	}
	
	
