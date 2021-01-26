import axios from "axios";
import Send from "@/apis/common_api";

function getBoards(vo){
    return Send({
        url:'/api/board',
        params:{
            keyword:vo
        },
        method:'GET'
    })
}

function getBests(){
    return Send({
        url:'/api/board/best',
        method:'GET'
    });
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
export default {getBoard,getBests,getBoards,createBoard,deleteBoard,updateBoard}