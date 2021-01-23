import axios from "axios";

function getReplies(boardId){
    return axios.get('/api/replies/'+boardId);
}

function createReply(boardId,reply){
    return axios.post('/api/replies/'+boardId,reply,{
        headers:{'Authorization' : localStorage.getItem("access_token")}
    })
}

export default {getReplies,createReply}