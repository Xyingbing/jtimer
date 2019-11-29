var api = {
    del:function(url,layer,trObj){
        $.ajax({
            url:url,
            success:function(data){
                checkLogin(data.code);
                if(data.code ===1){
                    layer.msg(data.msg,{icon:1});
                    trObj.del();
                }else{
                    layer.msg(data.msg,{icon:5});
                }
            }
        });
    },
    update:function(url,data,alert){
        $.ajax({
            url:url,
            data:data,
            type:'post',
            success:function(data){
                checkLogin(data.code);
                var icon = data.code ===1 ? 1 : 5;
                if(alert){
                    layer.msg(data.msg,{icon:icon});
                }
            }
        });
    },
    post:function(url,data){
        $.ajax({
            url:url,
            data:data,
            type:'post',
            success:function(data){
                checkLogin(data.code);
                var icon = data.code ===1 ? 1 : 5;
                layer.msg(data.msg,{icon:icon});
                if(data.code === 1){
                    if(data.url){
                        setTimeout(function(){
                            location.href = data.url;
                        },1000)
                    }
                }
            }
        });
    }
}

function openPage(url, appId, appname, refresh){
    window.parent.openPage(url, appId, appname, refresh);
}

function checkLogin(code){
    if(code === -1){
        layer.msg("请登录",{icon:5});
        setTimeout(function(){
            location.href = "/public/login";
        },1300)
    }
}
