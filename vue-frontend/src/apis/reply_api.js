import Send from "./common_api"
function getReplies(boardId){
    return Send({
        url:'/api/replies/'+boardId,
        method:'GET'
    })
}

function getReply(ids){
    return Send({
        url:'/api/replies/'+ids.boardId+'/'+ids.id,
        method:'GET'
    })
}
function createReply(boardId,reply){
    return Send({
        url:'/api/replies/'+boardId,
        data:reply,
        method:'POST'
    })
}

function deleteReply(ids){
    return Send({
        url:'/api/replies/'+ids.boardId+'/'+ids.id,
        method:'DELETE'
    })

}

function updateReply(reply){
    return Send({
        url:'/api/replies/'+reply.id,
        data:reply,
        method:'PUT'
    })
/*    return axios.put('/api/replies/'+reply.id,reply,{
        headers:{'Authorization' : localStorage.getItem("access_token")}
    })*/
}
export default {getReply,getReplies,createReply,deleteReply,updateReply}