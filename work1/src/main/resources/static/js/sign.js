window.addEventListener("load",function(){
    var flag=1;
    var sphere=document.querySelector(".sphere");/**控件圆球 */
    var box=document.querySelector(".box");/**表单容器*/
    var back=document.querySelector(".back");/** 注册表单*/
    var face=document.querySelector(".face");/** 登录表单*/
    new Vue({
        el:'#sign-submit',
        data:{
            seeShow:false,
            email:'',
            password:''
        },
        methods: {
            signUser:function () {
                let testEmail=new RegExp("[a-zA-Z0-9]{1,11}@[a-zA-Z0-9]{1,5}.[a-zA-Z0-9]{1,3}");
                let testPassword=new RegExp("[^a-z0-9A-Z,./\\?!;:\"'，。/？；：‘“]+");
                if(this.email==''){
                    window.alert('邮箱不能为空');
                    return;
                }
                if(this.password==''){
                    window.alert('密码不能为空');
                    return;
                }
                if(this.password.search(testPassword)!=-1&&this.password!=""){
                    window.alert('密码格式错误');
                    return;
                }
                if(this.email.search(testEmail)==-1){
                    window.alert('邮箱格式错误');
                    return;
                }
                $.ajax({
                    type:"post",
                    url:"/signBefore",
                    data:{email:this.email,password:this.password},
                    dataType:'json',
                    success:function (data) {
                        if(data['result']== false){
                            window.alert('邮箱或密码错误');
                            this.password='';
                            return;
                        }else{
                            $('#sign-submit').submit();
                        }
                    },
                    error:function () {
                        window.alert("服务器超时");
                    }
                })
            }
        },
        computed:{
            isSee:function () {
                if(this.seeShow) return 'text';
                else return 'password';
            }
        }
    })

    new Vue({
        el:'#login-submit',
        data:{
            email:'',
            emailText:'规则:登录名@主机号.域名',
            emailShow:false,
            passwordShow:false,
            passwordText:'',
            password:'',
            color1:'red',
            color2:'red',
            inner1:true,
            inner2:true,
            seeShow:false
        },
        methods:{
            testEmail:function () {
                let re=new RegExp("[a-zA-Z0-9]{1,11}@[a-zA-Z0-9]{1,5}.[a-zA-Z0-9]{1,3}");
                if(this.email=="") this.emailText = '规则:登录名@主机号.域名';
                else if(this.email.search(re)==-1) this.emailText = '格式错误！';
                else  this.emailText = '格式正确！';

            },
            testPassword:function () {
                let re=new RegExp("[^a-z0-9A-Z,./\\?!;:\"'，。/？；：‘“]+");
                if(this.password.length<5||this.password.length>20){
                    this.color1="red";
                    this.inner1=true;
                }else{
                    this.color1="green";
                    this.inner1=false;
                }
                if(this.password.search(re)!=-1&&this.password!=""){
                    this.color2="red";
                    this.inner2=true;
                }else if(this.password==""){
                    this.color2="red";
                    this.inner2=true;
                }else{
                    this.color2="green";
                    this.inner2=false;
                }
            },
            login:function () {
                let testEmail=new RegExp("[a-zA-Z0-9]{1,11}@[a-zA-Z0-9]{1,5}.[a-zA-Z0-9]{1,3}");
                let testPassword=new RegExp("[^a-z0-9A-Z,./\\?!;:\"'，。/？；：‘“]+");
                if(this.email==''){
                    window.alert('邮箱不能为空');
                    return;
                }
                if(this.password==''){
                    window.alert('密码不能为空');
                    return;
                }
                if(this.password.search(testPassword)!=-1&&this.password!=""){
                    window.alert('密码格式错误');
                    return;
                }
                if(this.email.search(testEmail)==-1){
                    window.alert('邮箱格式错误');
                    return;
                }
                $.ajax({
                    type:"post",
                    url:"/loginUser",
                    data:{email:this.email,password:this.password},
                    dataType:'json',
                    success:function (data) {
                        if(data['result']== true){
                            window.alert('注册成功');
                            history.go(0);
                        }else{
                            alert('邮箱重复');
                        }
                    },
                    error:function () {
                        window.alert("服务器超时");
                    }
                })
            }
        },
        computed:{
            isSee:function () {
                if(this.seeShow) return 'text';
                else return 'password';
            }
        }
    })

    sphere.addEventListener("click",function(){/**注册与登录表单切换 */
        if(flag){
            flag=0;/**注册转登录*/
            sphere.style.animation="left 0.6s";
            sphere.style.animationFillMode="forwards";
            box.style.transform="rotateY(180deg)";
            back.style.display="block";
            setTimeout(function(){
                face.style.display="none";
            },700)
            
            
        }else{/**登录转注册*/
            flag=1;
            sphere.style.animation="right 0.6s";
            sphere.style.animationFillMode="forwards";
            box.style.transform="rotateY(0deg)";
            setTimeout(function(){
                back.style.display="none";
            },700)
            face.style.display="block";

        }
    })
})