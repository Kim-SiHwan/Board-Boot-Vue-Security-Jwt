import axios from "axios";
import {store} from "../../store";

function getBoards(){
    return axios.get('/api/board');
}

function getBoard(boardId){
    return axios.get('/api/board/'+boardId);
}

function createBoard(board){
    console.log("token");
    console.log(store.state.token);
    return axios.post('/api/board',board,{
        headers:{'Authorization' : localStorage.getItem("access_token")}
    });
}
export default {getBoard,getBoards,createBoard}