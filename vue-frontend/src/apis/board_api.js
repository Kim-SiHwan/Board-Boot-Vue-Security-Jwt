import axios from "axios";
import Send from "@/apis/common_api";

function getBoards(){
    return axios.get('/api/board');
}

function getBoard(boardId){
    return axios.get('/api/board/'+boardId);
}

function createBoard(board){
    return Send({
        url:'/api/board',
        data:board,
        method:'POST'
    })
}

function deleteBoard(boardId){
    return Send({
        url:'/api/board/'+boardId,
        method:'DELETE'
    })
}

function updateBoard(board){

    return axios.put('/api/board/'+board.id,board,{
        headers:{'Authorization' : localStorage.getItem("access_token")}

    })
}
export default {getBoard,getBoards,createBoard,deleteBoard,updateBoard}