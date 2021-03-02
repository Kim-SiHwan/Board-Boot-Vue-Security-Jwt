import Send from "./common_api"
function getReplies(payload){
    console.log(payload);
    return Send({
        url:'/api/replies/'+payload,
        method:'GET'
    })
}

function createReply(payload){
    return Send({
        url:'/api/replies',
        method:'POST',
        data:payload,
    })
}

function deleteReply(payload){
    return Send({
        url:'/api/replies/'+payload,
        method:'DELETE'
    })

}

function updateReply(payload) {
    return Send({
        url: '/api/replies',
        method: 'PUT',
        data: payload,
    })
}

export default {getReplies,createReply,deleteReply,updateReply}