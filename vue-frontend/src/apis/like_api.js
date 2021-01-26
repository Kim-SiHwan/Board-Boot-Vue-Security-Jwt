import Send from "@/apis/common_api";

function pushLike(likeDto){
    return Send({
        url:'/api/like',
        data:likeDto,
        method:'POST'
    });
}

export default {pushLike}