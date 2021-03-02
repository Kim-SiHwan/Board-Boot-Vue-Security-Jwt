import Send from "@/apis/common_api";

function pushLike(payload){
    return Send({
        url:'/api/like',
        method:'POST',
        data:payload,
    });
}

export default {pushLike}