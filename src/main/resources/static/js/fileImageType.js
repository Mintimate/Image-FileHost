$(function() {
    loadingImageType();
});

// 获取图片分类
function loadingImageType(){
    $.ajax({
        url: "/imageType/getType", //json文件位置
        type: "GET", //请求方式为get
        dataType: "json", //返回数据格式为json
        success: function(data) {
            $(".imageSelect").empty();
            $(".imageSelect").append("<option value='Other'>Other</option>");
            console.log(data);
            for (i = 0; i < data.length; i++) {
                var data1 = data[i];
                $(".imageSelect").append("<option value='" + data1.imageType + "'>" + data1.imageType + "</option>");
            }

        }
    })
}

// 删除图片分类
function deleteImageType(){
    var httpRequest = new XMLHttpRequest();//第一步：建立所需的对象
    var oldType=$("#oldType").val();
    console.log(newType);
    httpRequest.open('GET', '/imageType/removeType/'+oldType, true);//第二步：打开连接  将请求参数写在url中  ps:"./Ptest.php?name=test&nameone=testone"
    httpRequest.send();//第三步：发送请求  将请求参数写在URL中
    /**
     * 获取数据后的处理程序
     */
    httpRequest.onreadystatechange = function () {
        if (httpRequest.readyState == 4 && httpRequest.status == 200) {
            var json = httpRequest.responseText;//获取到json字符串，还需解析
            document.getElementById("deleteTypeResult").innerHTML=
                '<div class="alert alert-success alert-dismissible col-12 text-center" style="font-size: small">'+
                '<button type="button" class="close" data-dismiss="alert">&times;</button>删除成功</div>'
            console.log(json);
            loadingImageType()
        }
        else {
            document.getElementById("deleteTypeResult").innerHTML='<div class="alert alert-danger col-12 text-center" style="font-size: small">' +
                '<button type="button" class="close" data-dismiss="alert">&times;</button>删除失败</div>'
        }
    };
}

// 上传图片
function addImageType(){
    var httpRequest = new XMLHttpRequest();//第一步：建立所需的对象
    var newType=$("#newType").val();
    console.log(newType);
    httpRequest.open('GET', '/imageType/addType/'+newType, true);//第二步：打开连接  将请求参数写在url中  ps:"./Ptest.php?name=test&nameone=testone"
    httpRequest.send();//第三步：发送请求  将请求参数写在URL中
    /**
     * 获取数据后的处理程序
     */
    httpRequest.onreadystatechange = function () {
        if (httpRequest.readyState == 4 && httpRequest.status == 200) {
            var json = httpRequest.responseText;//获取到json字符串，还需解析
            document.getElementById("addTypeResult").innerHTML='<div class="alert alert-success col-12 text-center" style="font-size: small">' +
                '<button type="button" class="close" data-dismiss="alert">&times;</button>添加成功</div>'
            console.log(json);
            loadingImageType()
        }
        else {
            document.getElementById("addTypeResult").innerHTML='<div class="alert alert-danger col-12 text-center" style="font-size: small">' +
                '<button type="button" class="close" data-dismiss="alert">&times;</button>添加失败</div>'
        }
    };
}

// 文件细分分类
$("#fileType").change(function(){
    var opt=$("#fileType").val();
    console.log(opt)
    $.ajax({
        url: "/fileType/fileTypeDetail/"+opt, //json文件位置
        type: "GET", //请求方式为get
        dataType: "json", //返回数据格式为json
        success: function(data) {
            $("#fileTypeDetail").empty();
            console.log(data);
            for (i = 0; i < data.length; i++) {
                var data1 = data[i];
                $("#fileTypeDetail").append("<option value='" + data1 + "'>" + data1 + "</option>");
            }

        }
    })
})