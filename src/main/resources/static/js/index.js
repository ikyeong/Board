function callApi( method , url , data , message , link){
    try{
        var asyncRequest = new XMLHttpRequest();
        asyncRequest.open(method, url);
        asyncRequest.setRequestHeader("Content-Type","application/json");
        asyncRequest.addEventListener("readystatechange", function (event){
            const {target} = event;
            if (target.readyState === XMLHttpRequest.DONE){
                const {status} = target;
                if (status === 0 || (status >= 200 && status <400)){ //이부분??나중에 찾아보고 그냥 200으로 정리
                    alert(message);
                    location.href = link;
                }else{
                    alert("연결오류");
                }
            }
        });
        if (data == null){
            asyncRequest.send();
        }else{
            asyncRequest.send(JSON.stringify(data));
        }
    } catch (exception){
        alert(JSON.stringify(exception));
    }
}

function savePost(){
    var title = document.getElementById("title");
    var contents = document.getElementById("contents");
    var data = {
        title: title.value,
        contents: contents.value
    }
    if (!data.title || data.title.trim() === "" || !data.contents || data.contents.trim() === "") {
        alert("입력하지 않은 부분이 있습니다");
    }else{
        callApi("POST",
            "/api/post",
            data,
            "등록되었습니다.",
            "/");
    }
}

function updatePost(){
    var id = document.getElementById("id");
    var title = document.getElementById("title");
    var contents = document.getElementById("contents");
    var data = {
        title : title.value,
        contents : contents.value
    }
    callApi("PUT",
        "/api/post/"+id.value,
        data,
        "수정되었습니다.",
        "/post/read/"+id.value);
}

function deletePost(){
    var check = confirm("삭제하시겠습니까?");
    var id = document.getElementById("id");
    if (check === true){
        callApi("DELETE",
            "/api/post/"+id.value,
            null,
            "삭제되었습니다.",
            "/")
    }
}

function saveComment(){
    var id = document.getElementById("postId");
    var contents = document.getElementById("comment-contents");
    var data = {
        contents : contents.value
    }
    callApi("POST",
        "/api/post/"+id.value+"/comment",
        data,
        "등록되었습니다.",
        "/post/read/"+id.value);
}

function deleteComment( commentId ){
    var check = confirm("댓글을 삭제하시겠습니까?");
    if (check === true){
        var postId = document.getElementById("postId");
        callApi("DELETE",
            "/api/post/"+postId.value+"/comment/"+commentId,
            null,
            "삭제되었습니다.",
            "/post/read/"+postId.value);
    }
}