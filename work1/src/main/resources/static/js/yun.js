var fileList1 = 1;
var fileList2 = 2;
var length = 1;
let iTime;
function flash(id){
    $.ajax({
        type:"post",
        async:false,
        url:"/GetCapacity",
        data:{packageId:id},
        dataType:'json',
        success:function (data) {
            fileList1 = data['pictures'];
            fileList2 = data['packages'];
            length = Math.ceil(data['length']/15);
        },
        error:function () {
            window.alert("服务器超时1");
        }
    })
}
flash($('#package').val());
var  submit=new Vue({
    el:'#form',
    methods: {
        upload:function(){
            var content =document.querySelector(".submit");
            var form=document.querySelector(".form");
            $('#submit').click();
        },
        submitFile:function () {
            let fileNameArray = $('#submit').val().split("\\");
            let fileName = fileNameArray[fileNameArray.length - 1];
            //alert(fileName);
            for(i =0;i<fileList1.length;i++){
                //alert(fileList1[i].filename)
                if(fileName==fileList1[i].filename){
                    alert("文件名重复");
                    return;
                }
            }
            //alert($('#submit').val());

            if($('#submit').val().size/1024+1024+parseFloat($('capacity').attr('capacity'))>100){
                alert('网盘容量不足');
                return;
            }
            $('#submitPic').click();
        },
        createPackage:function () {
            let name = prompt("请输入文件名","新建文件夹");
            //alert($('#package').val());
            if(name!=null&&name!=''){
                $.ajax({
                    type:"post",
                    url:"/createPackage",
                    data:{name:name,packageId:$('#package').val()},
                    dataType:'json',
                    success:function (data) {
                        if(data['result']== false){
                            alert("文件名重复");
                        }else{
                            history.go(0);
                        }
                    },
                    error:function () {
                        window.alert("服务器超时");
                    }
                })
            }else{
                return;
            }

        }
    }
})
let header = new Vue({
    el:'#header',
    data:{
        show:false
    },
    methods:{
        turnBefore:function () {
            $.ajax({
                type:"post",
                async:false,
                url:"/turnBefore",
                data:{packageId:$('#package').val()},
                dataType:'json',
                success:function (data) {
                    if(data['result']==true){
                        flash(data['packageId']);
                        pictureVue.file=fileList1;
                        pictureVue.indexRoot = fileList2.length;
                        pictureVue.activePage = 1;

                        packageVue.file=fileList2;
                        packageVue.activePage = 1;

                        pageVue.activePage = 1;
                        pageVue.pageNumber = length;

                        $('#package').val(data['packageId']);
                        header.show = data['show'];
                    }else{
                        alert('错误');
                    }
                },
                error:function () {
                    window.alert("服务器超时7");
                }
            })
        }
    }
})
let pictureShow = new Vue({
    el:'#pic',
    data:{
        place:'#',
        isShow:false
    }
})
let pictureVue =new Vue({
    el:'#filelist',
    data:{
        activePage:1,
        indexRoot:fileList2.length,
        file:fileList1
    },
    methods: {
        turn:function (event) {
            pictureShow.isShow = !pictureShow.isShow;
            pictureShow.place = event.place;
        },
        deletePicture:function (place,index) {
            $.ajax({
                type:"post",
                async:false,
                url:"/deletePicture",
                data:{place:place},
                dataType:'json',
                success:function (data) {
                    if(data['result']==true){
                        fileList1.splice(index,1);
                    }else{
                        alert('错误');
                    }
                },
                error:function () {
                    window.alert("服务器超时3");
                }
            })
        },
        examine:function (place) {
            $.ajax({
                type:"post",
                async:false,
                url:"/examine",
                data:{place:place},
                dataType:'json',
                success:function (data) {
                    if(data['result']==true){
                        alert("审核通过！");
                    }else{
                        alert('审核错误');
                    }
                },
                error:function () {
                    window.alert("服务器超时3");
                }
            })
        }
    }
})
var packageVue=new Vue({
    el:'#packagelist',
    data:{
        activePage:1,
        file:fileList2
    },
    methods:{
        turn:function (id) {
            flash(id);

            pictureVue.file=fileList1;
            pictureVue.indexRoot = fileList2.length;
            pictureVue.activePage = 1;

            this.file=fileList2;
            this.activePage = 1;

            pageVue.activePage = 1;
            pageVue.pageNumber = length;
            header.show = true;

            $('#package').val(id);
        },
        deletePackage:function (PackageId,index) {
            $.ajax({
                type:"post",
                async:false,
                url:"/deletePackage",
                data:{packageId:PackageId},
                dataType:'json',
                success:function (data) {
                    if(data['result']==true){
                        fileList2.splice(index,1);
                    }else{
                        alert('错误');
                    }
                },
                error:function () {
                    window.alert("服务器超时4");
                }
            })
        }
    }
})

var fileInput = $("#putPic");
/*fileInput.click(function () {
    // 检查文件是否选择:
    if(!$("#submit").val()) {
        window.alert("没有选择文件");
        return false;
    }
    $("#form").submit();
})*/
let pageVue = new Vue({
    el:'#page',
    data:{
        pageNumber:length,
        activePage:1
    },
    computed:{
        previousShow:function () {
            return this.activePage - 1>0;
        },
        lastShow:function () {
            return this.activePage <this.pageNumber;
        }
    },
    methods:{
        turnLast:function () {
            this.activePage-=1;
            pictureVue.activePage-=1;
            packageVue.activePage-=1;
            pictureShow.isShow = false;
        },
        turnBefore:function () {
            this.activePage+=1;
            pictureVue.activePage+=1;
            packageVue.activePage+=1;
            pictureShow.isShow = false;


        },
        turnTo:function (toPage) {
            this.activePage= toPage;
            pictureVue.activePage=toPage;
            packageVue.activePage=toPage;
            pictureShow.isShow = false;
        }
    }
})
new Vue({
    el:'#form1',
    data:{
        value:''
    },
    methods:{
        searchUser:function (value) {
            $.ajax({
                type:"post",
                async:false,
                url:"/GetPackageId",
                data:{email:value},
                dataType:'json',
                success:function (data) {
                    flash(data['packageId']);
                    pictureVue.file=fileList1;
                    pictureVue.indexRoot = fileList2.length;
                    pictureVue.activePage = 1;

                    packageVue.file=fileList2;
                    packageVue.activePage = 1;

                    pageVue.activePage = 1;
                    pageVue.pageNumber = length;

                    $('#package').val(data['packageId']);
                    header.show = data['show'];
                },
                error:function () {
                    window.alert("服务器超时");
                }
            })
        }
    }
})